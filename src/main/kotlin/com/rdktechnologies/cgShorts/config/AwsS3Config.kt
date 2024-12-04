package com.rdktechnologies.cgShorts.config



import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AmazonS3Config {

    @Bean
    fun amazonS3(): AmazonS3 {
        val accessKey = "AKIA4VDBLXJWPJE5XCWT" // Load from properties or environment
        val secretKey = "j8MtDtMNwZ4dP+X0aw+UVp4HlD5MT1mo4HJvkhzJ" // Load from properties or environment
        val region = Regions.AP_SOUTH_1// Set your desired region

        val awsCredentials = BasicAWSCredentials(accessKey, secretKey)

        return AmazonS3ClientBuilder
            .standard()
            .withRegion(region)
            .withCredentials(AWSStaticCredentialsProvider(awsCredentials))
            .build()
    }
}

