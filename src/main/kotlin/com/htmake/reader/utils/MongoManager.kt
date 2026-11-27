// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package com.htmake.reader.utils

import com.htmake.reader.entity.MongoFile
import com.mongodb.MongoClientSettings
import com.mongodb.MongoException
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.pojo.PojoCodecProvider

object MongoManager {

    private lateinit var mongoClient: MongoClient

    fun isInit(): Boolean {
        return ::mongoClient.isInitialized
    }

    fun connect(uri: String) {
        try {
            mongoClient = MongoClients.create(uri)
        } catch (e: MongoException) {
            logger.info("mongodb 连接失败，请检查链接({})是否正确", uri)
            e.printStackTrace()
        }
    }

    fun db(db: String): MongoDatabase? {
        if (!isInit()) {
            return null
        }
        val pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build()
        val pojoCodecRegistry = CodecRegistries.fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            CodecRegistries.fromProviders(pojoCodecProvider)
        )
        return mongoClient.getDatabase(db).withCodecRegistry(pojoCodecRegistry)
    }

    fun fileStorage(db: String, collection: String): MongoCollection<MongoFile>? {
        return db(db)?.getCollection(collection, MongoFile::class.java)
    }
}
