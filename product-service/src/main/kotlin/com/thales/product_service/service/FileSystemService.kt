package com.thales.product_service.service

import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.FileNotFoundException
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.util.*


@Service
class FileSystemService {

    @Throws(IOException::class)
    fun doesFileExist(directory: String, imageName: String): Boolean {
        val filePath: Path = Path.of(directory).resolve(imageName)

        return Files.exists(filePath)
    }

    @Throws(IOException::class)
    fun saveFileToStorage(uploadDirectory: String, file: MultipartFile): String {
        val uniqueFileName = UUID.randomUUID().toString() + "_" + Date().time

        val uploadPath: Path = Path.of(uploadDirectory)
        val filePath: Path = uploadPath.resolve(uniqueFileName)

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath)
        }

        Files.copy(file.inputStream, filePath, StandardCopyOption.REPLACE_EXISTING)

        return uniqueFileName
    }

    @Throws(IOException::class)
    fun loadAsResource(imageDirectory: String, imageName: String): Resource {
        val imagePath: Path = Path.of(imageDirectory, imageName)
        val resource = UrlResource(imagePath.toUri())
        if (!resource.exists() || !resource.isReadable) {
            throw FileNotFoundException()
        }
        return resource
    }
}