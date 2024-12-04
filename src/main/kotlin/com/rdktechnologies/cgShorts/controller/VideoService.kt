package com.rdktechnologies.cgShorts.controller

import com.amazonaws.services.s3.AmazonS3
import com.rdktechnologies.cgShorts.constant.Constants
import com.rdktechnologies.cgShorts.dto.response.ApiResponse
import com.rdktechnologies.cgShorts.repository.video.Video
import com.rdktechnologies.cgShorts.repository.video.VideoRepository
import com.rdktechnologies.cgShorts.repository.video.VideoType
import com.rdktechnologies.cgShorts.util.AwsS3Util.uploadFile
import com.rdktechnologies.cgShorts.util.AwsS3Util.uploadThumbnail
import com.rdktechnologies.cgShorts.util.FileUtil.createFileAndGetUrl
import com.rdktechnologies.cgShorts.util.FileUtil.generateThumbnail
import com.rdktechnologies.cgShorts.util.autowired
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.Paths

@Service
class VideoService :IVideoService{

    private val  s3Client: AmazonS3 by autowired()

   private val videoRepository:VideoRepository by autowired()

    override fun upload(title:String,description:String,file: MultipartFile,thubnail:MultipartFile): ResponseEntity<Any> {
        if (file.isEmpty) {
            throw FileNotFoundException("Request must contain file..")
        }
        val video = Video(
            title=title,
            description = description,
            videoType =VideoType.SHORT,
            url =  file.uploadFile(s3Client),
            thumbnailUrl =thubnail.uploadThumbnail(s3Client)
        )
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(
                ApiResponse(
                    error = false,
                    statusCode = HttpStatus.CREATED.value(),
                    message = Constants. VIDEO_UPLOADED,
                    serverError = "",
                    data =videoRepository.save(video)
                )
            )
    }

    override fun delete(videoId: Long): ResponseEntity<Any> {
       val data= videoRepository.deleteById(videoId)
        return ResponseEntity.status(HttpStatus.OK)
            .body(
                ApiResponse(
                    error = false,
                    statusCode = HttpStatus.OK.value(),
                    message = Constants. VIDEO_UPLOADED,
                    serverError = "",
                    data =data
                )
            )
    }

    override fun get(): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.OK )
            .body(
                ApiResponse(
                    error = false,
                    statusCode = HttpStatus.OK.value(),
                    message = "",
                    serverError = "",
                    data = videoRepository.findAll()
                )
            )
    }


    override fun downloadFile(fileName: String): ResponseEntity<Resource> {
        val path = Paths.get(uploadLocation).toAbsolutePath().resolve(fileName)
        val resource = UrlResource(path.toUri())
        if (resource.exists() && resource.isReadable){
            return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "inline;fileName"+resource.filename)
                .body(resource)
        }else{
            throw RuntimeException("file is not available... $fileName")
        }
    }




    init {
        val uploadPath = Paths.get("shorts")
        if (!Files.exists(uploadPath)) {
            Files.createDirectory(uploadPath)
        }
    }

    companion object {
        var uploadLocation = "shorts"
    }

}