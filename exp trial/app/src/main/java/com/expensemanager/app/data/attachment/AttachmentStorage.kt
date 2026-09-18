package com.expensemanager.app.data.attachment

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

class AttachmentStorage(private val context: Context) {

    private val attachmentsDir: File
        get() = File(context.filesDir, "attachments").also { it.mkdirs() }

    private val thumbnailsDir: File
        get() = File(context.filesDir, "thumbnails").also { it.mkdirs() }

    /**
     * Copies an image from the given URI to app-internal storage and generates a thumbnail.
     * Returns (filePath, thumbnailPath).
     */
    fun saveAttachment(uri: Uri, transactionId: Long, fileName: String): Pair<String, String?> {
        val uniqueName = "${transactionId}_${UUID.randomUUID()}_$fileName"
        val destFile = File(attachmentsDir, uniqueName)

        // Copy original
        context.contentResolver.openInputStream(uri)?.use { input ->
            FileOutputStream(destFile).use { output ->
                input.copyTo(output)
            }
        }

        // Generate thumbnail
        val thumbnailPath = try {
            val thumbFile = File(thumbnailsDir, "thumb_$uniqueName")
            val bitmap = BitmapFactory.decodeFile(destFile.absolutePath)
            if (bitmap != null) {
                val thumbBitmap = createThumbnail(bitmap, 200)
                FileOutputStream(thumbFile).use { out ->
                    thumbBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out)
                }
                bitmap.recycle()
                thumbBitmap.recycle()
                thumbFile.absolutePath
            } else null
        } catch (e: Exception) {
            null
        }

        return destFile.absolutePath to thumbnailPath
    }

    fun deleteAttachment(filePath: String, thumbnailPath: String?) {
        File(filePath).delete()
        thumbnailPath?.let { File(it).delete() }
    }

    fun getAttachmentFile(filePath: String): File = File(filePath)

    fun getMimeType(uri: Uri): String? = context.contentResolver.getType(uri)

    fun getAllAttachmentFiles(): List<File> = attachmentsDir.listFiles()?.toList() ?: emptyList()

    private fun createThumbnail(bitmap: Bitmap, maxSize: Int): Bitmap {
        val ratio = minOf(maxSize.toFloat() / bitmap.width, maxSize.toFloat() / bitmap.height)
        val width = (bitmap.width * ratio).toInt()
        val height = (bitmap.height * ratio).toInt()
        return Bitmap.createScaledBitmap(bitmap, width, height, true)
    }
}
