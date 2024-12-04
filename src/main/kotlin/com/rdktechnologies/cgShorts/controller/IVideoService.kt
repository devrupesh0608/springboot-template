package com.rdktechnologies.cgShorts.controller

import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.web.multipart.MultipartFile

interface IVideoService {
    fun upload(title:String,description:String,file:MultipartFile,thumbnail:MultipartFile):ResponseEntity<Any>
    fun delete(videoId:Long):ResponseEntity<Any>
    fun get():ResponseEntity<Any>
    fun downloadFile(fileName: String): ResponseEntity<Resource>
}