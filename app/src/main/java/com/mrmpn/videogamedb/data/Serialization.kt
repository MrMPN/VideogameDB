package com.mrmpn.videogamedb.data

import com.mrmpn.videogamedb.data.models.GameDataModel
import com.mrmpn.videogamedb.domain.Game
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object LocalDateSerializer : KSerializer<LocalDate> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalDate", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDate) {
        val result = value.format(DateTimeFormatter.BASIC_ISO_DATE)
        encoder.encodeString(result)
    }

    override fun deserialize(decoder: Decoder): LocalDate {
        return LocalDate.parse(decoder.decodeString())
    }
}

val json: Json = Json {
    ignoreUnknownKeys = true
    isLenient = true
    serializersModule = SerializersModule {
        contextual(LocalDate::class, LocalDateSerializer)
        polymorphic(Game::class) {
            subclass(GameDataModel::class, GameDataModel.serializer())
            defaultDeserializer { GameDataModel.serializer() }
        }
    }
}
