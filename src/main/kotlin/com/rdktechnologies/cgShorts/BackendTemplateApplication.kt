package com.rdktechnologies.cgShorts

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.event.EventListener
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.bind.annotation.CrossOrigin

@EnableJpaRepositories
@SpringBootApplication
@CrossOrigin
@OpenAPIDefinition(
	info = Info(
		title = "Video App",
		description = "A Ai based dating app.",
		termsOfService = "http://swagger.io/terms/",
		license = License(
			name = "Apache 2.0",
			url = "http://springdoc.org"),
		version = "1.0"
	)
)
class Application {

	@EventListener(ApplicationReadyEvent::class)
	fun runAfterStartup() {

	}
}

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}


