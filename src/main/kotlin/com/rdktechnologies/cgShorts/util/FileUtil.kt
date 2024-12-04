package com.rdktechnologies.cgShorts.util

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import com.rdktechnologies.cgShorts.util.FileUtil.createFileAndGetUrl
import net.coobird.thumbnailator.Thumbnails
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*

object FileUtil {

    fun MultipartFile.getExtension() = StringUtils.getFilenameExtension(this.originalFilename)

    fun MultipartFile.createFileAndGetUrl(): String {
        val uuid = UUID.randomUUID()
        val extension = this.getExtension()
        val path = Paths.get(File("shorts${File.separator}$uuid.$extension").toURI())
        val fileUrl = "shorts/$uuid.$extension"
        Files.copy(this.inputStream, path, StandardCopyOption.REPLACE_EXISTING)
        return fileUrl
    }
    fun MultipartFile.generateThumbnail(thumbnailWidth: Int = 1920, thumbnailHeight: Int = 1080): File {
        val uuid = UUID.randomUUID().toString()
        val extension = "jpg"
        val thumbnailFileName = "thumbnails/thumb-$uuid.$extension"
        val thumbnailFile = File(thumbnailFileName)
        // Generate the thumbnail and save it to the specified file
        Thumbnails.of(this.inputStream)
            .size(thumbnailWidth, thumbnailHeight)
            .outputFormat(extension)
            .toFile(thumbnailFile)
        return thumbnailFile
    }

    fun File.deleteFile(): Boolean {
        return if (this.exists()) {
            this.delete()
        } else {
            false
        }
    }



}