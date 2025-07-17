package com.squrlabs.sca.domain.service.file

import com.squrlabs.sca.config.AppProperties
import com.squrlabs.sca.domain.model.chat.FileModel
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*
import kotlin.collections.ArrayList
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class FileStorageServiceImpl(private val appProperties: AppProperties) : FileStorageService {

    private val uploadDir =
        Paths.get(appProperties.uploadsFolder) // Directory to save uploaded files

    init {
        if (Files.notExists(uploadDir)) {
            Files.createDirectories(uploadDir)
        }
    }

    override fun store(files: List<MultipartFile>, chatId: String): List<FileModel> {
        val uploadedFiles = ArrayList<FileModel>()

        files.forEach {
            val fileUrl = store(it)
            uploadedFiles.add(FileModel(fileUrl, it.contentType ?: ""))
        }

        return uploadedFiles
    }

    override fun store(file: MultipartFile): String {
        val fileName = file.originalFilename ?: "unknown_file"
        val targetLocation = uploadDir.resolve(fileName)
        Files.copy(file.inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING)

        val fileUrl = "http://localhost:8080/uploads/$fileName" + "?" + UUID.randomUUID()
        println("File uploaded successfully; fileName: $fileName, fileUrl: $fileUrl")

        return fileUrl
    }

    companion object {
        const val ROOT_URL = "uploads"
    }
}

interface FileStorageService {
    fun store(files: List<MultipartFile>, chatId: String): List<FileModel>

    fun store(file: MultipartFile): String
}
