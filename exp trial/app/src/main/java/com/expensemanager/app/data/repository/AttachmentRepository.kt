package com.expensemanager.app.data.repository

import com.expensemanager.app.data.attachment.AttachmentStorage
import com.expensemanager.app.data.db.dao.AttachmentDao
import com.expensemanager.app.data.db.entity.AttachmentEntity
import android.net.Uri
import javax.inject.Inject

class AttachmentRepository @Inject constructor(
    private val attachmentDao: AttachmentDao,
    private val attachmentStorage: AttachmentStorage
) {
    suspend fun addAttachment(transactionId: Long, uri: Uri): AttachmentEntity {
        val mimeType = attachmentStorage.getMimeType(uri) ?: "image/jpeg"
        val extension = android.webkit.MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
            ?: if (mimeType.contains("pdf")) "pdf" else "jpg"
        val fileName = "attachment_${System.currentTimeMillis()}.$extension"
        val (filePath, thumbnailPath) = attachmentStorage.saveAttachment(uri, transactionId, fileName)
        val attachment = AttachmentEntity(
            transactionId = transactionId,
            fileName = fileName,
            filePath = filePath,
            thumbnailPath = thumbnailPath,
            mimeType = mimeType
        )
        val id = attachmentDao.insert(attachment)
        return attachment.copy(id = id)
    }

    suspend fun deleteAttachment(attachment: AttachmentEntity) {
        attachmentStorage.deleteAttachment(attachment.filePath, attachment.thumbnailPath)
        attachmentDao.delete(attachment)
    }

    suspend fun deleteAllForTransaction(transactionId: Long) {
        val attachments = attachmentDao.getByTransaction(transactionId)
        attachments.forEach { attachment ->
            attachmentStorage.deleteAttachment(attachment.filePath, attachment.thumbnailPath)
        }
        attachmentDao.deleteByTransaction(transactionId)
    }

    fun getByTransactionFlow(transactionId: Long) = attachmentDao.getByTransactionFlow(transactionId)
    suspend fun getByTransaction(transactionId: Long) = attachmentDao.getByTransaction(transactionId)
    suspend fun getAll() = attachmentDao.getAll()
}
