// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package com.htmake.reader

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.htmake.reader.api.YueduApi
import io.vertx.core.Vertx
import io.vertx.core.http.HttpClientOptions
import io.vertx.core.json.Json
import io.vertx.ext.web.client.WebClient
import io.vertx.ext.web.client.WebClientOptions
import mu.KLogger
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling
import javax.annotation.PostConstruct

private val logger: KLogger = KotlinLogging.logger { }

fun main(args: Array<String>) {
    logger.info("Starting application with args: {}", args)
    SpringApplication.run(ReaderApplication::class.java, *args)
}

@SpringBootApplication(exclude = [MongoAutoConfiguration::class, MongoDataAutoConfiguration::class])
@EnableScheduling
open class ReaderApplication {

    @Autowired
    private lateinit var yueduApi: YueduApi

    @PostConstruct
    open fun deployVerticle() {
        Json.mapper.apply {
            registerKotlinModule()
        }
        Json.prettyMapper.apply {
            registerKotlinModule()
        }
        Json.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        vertx.deployVerticle(yueduApi)
    }

    @Bean
    open fun webClient(): WebClient {
        val options = WebClientOptions()
        options.isTryUseCompression = true
        options.logActivity = true
        options.isFollowRedirects = true
        options.isTrustAll = true
        val httpClient = vertx.createHttpClient(HttpClientOptions().setTrustAll(true))
        return WebClient.wrap(httpClient, options)
    }

    companion object {
        val vertx: Vertx by lazy { Vertx.vertx() }

        fun vertx(): Vertx = vertx
    }
}
