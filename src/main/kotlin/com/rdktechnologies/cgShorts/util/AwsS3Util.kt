package com.rdktechnologies.cgShorts.util

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.rdktechnologies.cgShorts.util.AwsS3Util.uploadFile
import com.rdktechnologies.cgShorts.util.FileUtil.deleteFile
import com.rdktechnologies.cgShorts.util.FileUtil.getExtension
import net.coobird.thumbnailator.Thumbnails
import org.springframework.scheduling.annotation.Async
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileInputStream
import java.util.*

object AwsS3Util {

    @Async
    fun MultipartFile.uploadFile(s3Client: AmazonS3, bucketName: String="cg-shorts"): String {
        val uuid = UUID.randomUUID().toString()
        val extension = this.getExtension()
        val fileName = "shorts/$uuid.$extension"
        s3Client.putObject(PutObjectRequest(bucketName, fileName, this.inputStream,ObjectMetadata().apply {
            contentLength = this@uploadFile.size
        }))
        return s3Client.getUrl(bucketName, fileName).toString()
    }
    @Async
    fun MultipartFile.uploadThumbnail(s3Client: AmazonS3, bucketName: String="cg-shorts"): String {
        val uuid = UUID.randomUUID().toString()
        val extension = this.getExtension()
        val fileName = "thumbnail/$uuid.$extension"
        s3Client.putObject(PutObjectRequest(bucketName, fileName, this.inputStream,ObjectMetadata().apply {
            contentLength = this@uploadThumbnail.size
        }))
        return s3Client.getUrl(bucketName, fileName).toString()
    }
    fun File.uploadThumbnail(
        s3Client: AmazonS3,
        bucketName: String = "cg-shorts",
        thumbnailWidth: Int = 1920,
        thumbnailHeight: Int = 1080
    ): String {
        val uuid = UUID.randomUUID().toString()
        val extension = this.extension
        val fileName = "thumbnail/$uuid.$extension"

        FileInputStream(this).use { inputStream ->
            val metadata = ObjectMetadata().apply {
                contentLength = this@uploadThumbnail.length()
            }
            s3Client.putObject(PutObjectRequest(bucketName, fileName, inputStream, metadata))
        }
        this.deleteFile()
        return s3Client.getUrl(bucketName, fileName).toString()
    }

}