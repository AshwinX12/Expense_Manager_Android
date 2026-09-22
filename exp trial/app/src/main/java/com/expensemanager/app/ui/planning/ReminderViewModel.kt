package com.expensemanager.app.ui.planning

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expensemanager.app.data.db.entity.ReminderEntity
import com.expensemanager.app.data.db.entity.ReminderType
import com.expensemanager.app.data.repository.ReminderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

data class RemindersUiState(
    val reminders: List<ReminderEntity> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class ReminderViewModel @Inject constructor(
    private val reminderRepository: ReminderRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(RemindersUiState())
    val uiState: StateFlow<RemindersUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            reminderRepository.getAllFlow().collect { reminders ->
                _uiState.update { it.copy(reminders = reminders, isLoading = false) }
            }
        }
    }

    fun addReminder(
        title: String,
        description: String,
        dueDate: LocalDate,
        leadTimeDays: Int,
        type: ReminderType
    ) {
        viewModelScope.launch {
            reminderRepository.insert(
                ReminderEntity(
                    title = title,
                    description = description.ifBlank { null },
                    dueDate = dueDate,
                    leadTimeDays = leadTimeDays,
                    type = type
                )
            )
        }
    }

    fun deleteReminder(reminder: ReminderEntity) {
        viewModelScope.launch { reminderRepository.delete(reminder) }
    }

    fun toggleActive(reminder: ReminderEntity) {
        viewModelScope.launch {
            // Re-enabling a reminder should let it fire again rather than staying silenced
            // by whatever isNotified state it was left in.
            reminderRepository.update(
                reminder.copy(isActive = !reminder.isActive, isNotified = false)
            )
        }
    }
}
