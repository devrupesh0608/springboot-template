package com.rdktechnologies.cgShorts.repository.video

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VideoRepository:JpaRepository<Video,Long> {

}