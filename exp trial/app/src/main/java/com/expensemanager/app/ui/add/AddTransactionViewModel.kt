package com.expensemanager.app.ui.add

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expensemanager.app.data.db.entity.*
import com.expensemanager.app.data.repository.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import javax.inject.Inject

/** Represents one person in a bill split */
data class SplitParticipant(
    val id: Int,             // local UI id only
    val name: String = "",
    val shareAmount: String = "0",
    val isSettled: Boolean = false
)

data class AddTransactionUiState(
    val amountText: String = "0",
    val type: TransactionType = TransactionType.EXPENSE,
    val date: LocalDate = LocalDate.now(),
    val time: java.time.LocalTime? = java.time.LocalTime.now(),
    val categoryId: Long? = null,
    val subcategoryId: Long? = null,
    val accountId: Long? = null,
    val paymentMethod: PaymentMethod? = null,
    val note: String = "",
    val merchantName: String = "",
    val tags: List<String> = emptyList(),
    val categories: List<CategoryEntity> = emptyList(),
    val subcategories: List<SubcategoryEntity> = emptyList(),
    val accounts: List<AccountEntity> = emptyList(),
    val pendingAttachmentUris: List<Uri> = emptyList(),
    val bulkMode: Boolean = false,
    val isEditing: Boolean = false,
    val isSaving: Boolean = false,
    val savedSuccessfully: Boolean = false,
    val error: String? = null,
    // Numpad
    val numpadVisible: Boolean = false,
    // Split
    val splitEnabled: Boolean = false,
    val splitParticipants: List<SplitParticipant> = emptyList()
) {
    /** Amount not yet allocated across split participants */
    val splitUnallocated: BigDecimal
        get() {
            val total = amountText.toBigDecimalOrNull() ?: BigDecimal.ZERO
            val allocated = splitParticipants.sumOf { it.shareAmount.toBigDecimalOrNull() ?: BigDecimal.ZERO }
            return (total - allocated).setScale(2, RoundingMode.HALF_UP)
        }
}

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    savedStateHandle: androidx.lifecycle.SavedStateHandle,
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository,
    private val accountRepository: AccountRepository,
    private val attachmentRepository: AttachmentRepository,
    private val splitExpenseRepository: SplitExpenseRepository
) : ViewModel() {

    private val transactionId: Long? = savedStateHandle.get<Long>("transactionId")

    private val _uiState = MutableStateFlow(AddTransactionUiState(
        numpadVisible = transactionId == null || transactionId == -1L,
        isEditing = transactionId != null && transactionId != -1L
    ))
    val uiState: StateFlow<AddTransactionUiState> = _uiState.asStateFlow()

    private var splitIdCounter = 0

    init {
        loadPickerData()
    }

    private fun loadPickerData() {
        viewModelScope.launch {
            categoryRepository.getAllCategoriesFlow().collect { categories ->
                _uiState.update { it.copy(categories = categories) }
            }
        }
        viewModelScope.launch {
            accountRepository.getAllFlow().collect { accounts ->
                _uiState.update { it.copy(accounts = accounts) }
                if (_uiState.value.accountId == null) {
                    val default = accounts.firstOrNull { it.isDefault } ?: accounts.firstOrNull()
                    _uiState.update { it.copy(accountId = default?.id) }
                }
            }
        }
    }

    fun loadTransaction(id: Long) {
        viewModelScope.launch {
            val transaction = transactionRepository.getById(id) ?: return@launch
            val existingSplits = splitExpenseRepository.getByTransaction(id)
            val participants = existingSplits.mapIndexed { idx, s ->
                SplitParticipant(
                    id = idx,
                    name = s.personName,
                    shareAmount = s.shareAmount.toPlainString(),
                    isSettled = s.isSettled
                )
            }
            splitIdCounter = participants.size

            val attachments = attachmentRepository.getByTransaction(id)
            val attachmentUris = attachments.map { android.net.Uri.fromFile(java.io.File(it.filePath)) }
            _uiState.update {
                it.copy(
                    amountText = transaction.amount.toPlainString(),
                    type = transaction.type,
                    date = transaction.date,
                    time = transaction.time,
                    categoryId = transaction.categoryId,
                    subcategoryId = transaction.subcategoryId,
                    accountId = transaction.accountId,
                    paymentMethod = transaction.paymentMethod,
                    note = transaction.note ?: "",
                    merchantName = transaction.merchantName ?: "",
                    tags = transaction.tags ?: emptyList(),
                    isEditing = true,
                    numpadVisible = false,   // collapsed when editing — form is focus
                    splitEnabled = participants.isNotEmpty(),
                    splitParticipants = participants,
                    pendingAttachmentUris = attachmentUris
                )
            }
            transaction.categoryId?.let { loadSubcategories(it) }
        }
    }

    // ── Numpad ────────────────────────────────────────────────────────────────

    fun toggleNumpad() {
        _uiState.update { it.copy(numpadVisible = !it.numpadVisible) }
    }

    fun onDigit(digit: String) {
        _uiState.update { state ->
            val current = state.amountText
            val newText = when {
                current == "0" && digit != "." -> digit
                current.contains(".") && current.substringAfter(".").length >= 2 -> current
                else -> current + digit
            }
            state.copy(amountText = newText)
        }
    }

    fun onDecimal() {
        _uiState.update { state ->
            if (!state.amountText.contains(".")) {
                state.copy(amountText = state.amountText + ".")
            } else state
        }
    }

    fun onBackspace() {
        _uiState.update { state ->
            val newText = if (state.amountText.length > 1) state.amountText.dropLast(1) else "0"
            state.copy(amountText = newText)
        }
    }

    fun onClear() {
        _uiState.update { it.copy(amountText = "0") }
    }

    // ── Fields ────────────────────────────────────────────────────────────────

    fun setType(type: TransactionType) { _uiState.update { it.copy(type = type) } }
    fun setDate(date: LocalDate) { _uiState.update { it.copy(date = date) } }
    fun setTime(time: java.time.LocalTime?) { _uiState.update { it.copy(time = time) } }
    fun setCategory(categoryId: Long) {
        _uiState.update { it.copy(categoryId = categoryId, subcategoryId = null) }
        loadSubcategories(categoryId)
    }
    private fun loadSubcategories(categoryId: Long) {
        viewModelScope.launch {
            categoryRepository.getSubcategoriesFlow(categoryId).collect { subs ->
                _uiState.update { it.copy(subcategories = subs) }
            }
        }
    }
    fun setSubcategory(id: Long?) { _uiState.update { it.copy(subcategoryId = id) } }
    fun setAccount(id: Long) { _uiState.update { it.copy(accountId = id) } }
    fun setPaymentMethod(method: PaymentMethod?) { _uiState.update { it.copy(paymentMethod = method) } }
    fun setNote(note: String) { _uiState.update { it.copy(note = note) } }
    fun setMerchant(merchant: String) { _uiState.update { it.copy(merchantName = merchant) } }
    fun setBulkMode(enabled: Boolean) { _uiState.update { it.copy(bulkMode = enabled) } }
    fun addAttachmentUri(uri: Uri) { _uiState.update { it.copy(pendingAttachmentUris = it.pendingAttachmentUris + uri) } }
    fun removeAttachmentUri(uri: Uri) { _uiState.update { it.copy(pendingAttachmentUris = it.pendingAttachmentUris - uri) } }

    // ── Category creation ─────────────────────────────────────────────────────

    fun createCategory(name: String, colorHex: String) {
        viewModelScope.launch {
            val id = categoryRepository.insertCategory(
                CategoryEntity(name = name.trim(), colorHex = colorHex)
            )
            // Auto-select the newly created category
            _uiState.update { it.copy(categoryId = id, subcategoryId = null) }
        }
    }

    // ── Split ─────────────────────────────────────────────────────────────────

    fun toggleSplit() {
        _uiState.update { state ->
            val wasEnabled = state.splitEnabled
            if (!wasEnabled) {
                // Enable: pre-populate with "You" and an empty second person
                splitIdCounter = 2
                state.copy(
                    splitEnabled = true,
                    splitParticipants = listOf(
                        SplitParticipant(id = 0, name = "You", shareAmount = "0"),
                        SplitParticipant(id = 1, name = "", shareAmount = "0")
                    )
                )
            } else {
                state.copy(splitEnabled = false, splitParticipants = emptyList())
            }
        }
    }

    fun addSplitParticipant() {
        _uiState.update { state ->
            state.copy(
                splitParticipants = state.splitParticipants + SplitParticipant(
                    id = splitIdCounter++,
                    name = "",
                    shareAmount = "0"
                )
            )
        }
    }

    fun removeSplitParticipant(id: Int) {
        _uiState.update { state ->
            state.copy(splitParticipants = state.splitParticipants.filter { it.id != id })
        }
    }

    fun updateSplitName(id: Int, name: String) {
        _uiState.update { state ->
            state.copy(splitParticipants = state.splitParticipants.map {
                if (it.id == id) it.copy(name = name) else it
            })
        }
    }

    fun updateSplitShare(id: Int, amount: String) {
        _uiState.update { state ->
            state.copy(splitParticipants = state.splitParticipants.map {
                if (it.id == id) it.copy(shareAmount = amount) else it
            })
        }
    }

    fun toggleSplitSettled(id: Int) {
        _uiState.update { state ->
            state.copy(splitParticipants = state.splitParticipants.map {
                if (it.id == id) it.copy(isSettled = !it.isSettled) else it
            })
        }
    }

    fun splitEvenly() {
        val state = _uiState.value
        val total = state.amountText.toBigDecimalOrNull() ?: return
        val count = state.splitParticipants.size
        if (count == 0) return
        val share = total.divide(BigDecimal(count), 2, RoundingMode.HALF_UP)
        _uiState.update { s ->
            s.copy(splitParticipants = s.splitParticipants.map {
                it.copy(shareAmount = share.toPlainString())
            })
        }
    }

    // ── Save ──────────────────────────────────────────────────────────────────

    fun save(existingId: Long? = null) {
        val state = _uiState.value
        val amount = try {
            BigDecimal(state.amountText)
        } catch (e: Exception) {
            _uiState.update { it.copy(error = "Invalid amount") }
            return
        }

        if (amount <= BigDecimal.ZERO) {
            _uiState.update { it.copy(error = "Amount must be greater than zero") }
            return
        }

        _uiState.update { it.copy(isSaving = true, error = null) }

        viewModelScope.launch {
            try {
                val transaction = TransactionEntity(
                    id = existingId ?: 0,
                    amount = amount,
                    type = state.type,
                    date = state.date,
                    time = state.time,
                    categoryId = state.categoryId,
                    subcategoryId = state.subcategoryId,
                    accountId = state.accountId,
                    paymentMethod = state.paymentMethod,
                    note = state.note.ifBlank { null },
                    merchantName = state.merchantName.ifBlank { null },
                    tags = state.tags.ifEmpty { null },
                    hasAttachments = state.pendingAttachmentUris.isNotEmpty()
                )

                val transactionId = if (existingId != null) {
                    transactionRepository.update(transaction)
                    existingId
                } else {
                    transactionRepository.insert(transaction)
                }

                // Save attachments
                val existingAttachments = if (existingId != null) attachmentRepository.getByTransaction(existingId) else emptyList()
                val pendingPaths = state.pendingAttachmentUris.map { it.path }
                
                // Delete attachments that were removed from the UI
                existingAttachments.forEach { existing ->
                    if (existing.filePath !in pendingPaths) {
                        attachmentRepository.deleteAttachment(existing)
                    }
                }

                // Add new attachments
                val existingPaths = existingAttachments.map { it.filePath }
                state.pendingAttachmentUris.forEach { uri ->
                    if (uri.scheme != "file" || uri.path !in existingPaths) {
                        attachmentRepository.addAttachment(
                            transactionId = transactionId,
                            uri = uri
                        )
                    }
                }

                // Save split entries
                if (state.splitEnabled && state.splitParticipants.isNotEmpty()) {
                    val splits = state.splitParticipants
                        .filter { it.name.isNotBlank() }
                        .map { p ->
                            SplitExpenseEntity(
                                transactionId = transactionId,
                                personName = p.name,
                                shareAmount = p.shareAmount.toBigDecimalOrNull() ?: BigDecimal.ZERO,
                                isSettled = p.isSettled
                            )
                        }
                    splitExpenseRepository.replaceForTransaction(transactionId, splits)
                } else if (!state.splitEnabled && existingId != null) {
                    // If split was disabled on edit, clear any existing splits
                    splitExpenseRepository.replaceForTransaction(transactionId, emptyList())
                }

                if (state.bulkMode) {
                    _uiState.update {
                        AddTransactionUiState(
                            categories = it.categories,
                            accounts = it.accounts,
                            accountId = it.accountId,
                            bulkMode = true,
                            numpadVisible = true, // Automatically open numpad in bulk mode
                            savedSuccessfully = true
                        )
                    }
                } else {
                    _uiState.update { it.copy(isSaving = false, savedSuccessfully = true) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isSaving = false, error = e.message) }
            }
        }
    }

    fun clearSavedFlag() {
        _uiState.update { it.copy(savedSuccessfully = false) }
    }
}
