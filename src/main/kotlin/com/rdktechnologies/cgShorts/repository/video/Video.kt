package com.rdktechnologies.cgShorts.repository.video

import jakarta.persistence.*
import java.time.Instant
import java.time.LocalDateTime

@Entity
@Table(name = "videos")
data class Video(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var description: String,

    @Column(nullable = false)
    var url: String,

    @Column(nullable = false)
    var thumbnailUrl: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var videoType: VideoType,

    @Column(name = "upload_date")
    var uploadDate: Instant?=Instant.now()
)
