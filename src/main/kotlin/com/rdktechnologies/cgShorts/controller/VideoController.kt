package com.rdktechnologies.cgShorts.controller

import com.rdktechnologies.cgShorts.util.autowired
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.constraints.NotEmpty
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping("/api/v1/shorts")
@CrossOrigin("*")
@Tag(name = "Video")
class VideoController {

    val iVideoService: IVideoService by autowired()

    @PutMapping("/upload")
    fun uploadVideo(
        @RequestParam("file") file: MultipartFile,
        @RequestParam("thumbnail") thumbnail: MultipartFile,
        @RequestParam("title", defaultValue = "title") title: String,
        @RequestParam("description", defaultValue = "description") description: String,
    ): ResponseEntity<Any> {
        return iVideoService.upload(title,description,file,thumbnail)
    }

    @GetMapping
    fun getAllVideos(): ResponseEntity<Any>{
       return iVideoService.get()
    }


    @DeleteMapping("/{videoId}")
    fun deleteVideo(
        @PathVariable("videoId") videoId:Long
    ): ResponseEntity<Any>{
        return iVideoService.delete(videoId = videoId)
    }
    @GetMapping("/{fileName}")
    fun downloadFile(@PathVariable("fileName")
                     @NotEmpty(message = "Please enter file name.")
                     fileName:String): ResponseEntity<Resource> {
        return iVideoService.downloadFile(fileName)
    }
}