package com.hshim.escapemanager.database.base.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import util.DateUtil.stringToDate
import java.time.LocalTime

@Converter(autoApply = false)
class LocalTimesConverter : AttributeConverter<List<LocalTime>, String> {
    override fun convertToDatabaseColumn(attribute: List<LocalTime>?): String? {
        return attribute?.joinToString(separator = ",")
    }

    override fun convertToEntityAttribute(dbData: String?): List<LocalTime>? {
        return dbData
            ?.split(",")
            ?.map { timeStr ->
                val cleaned = timeStr.trim()
                val pattern = if (cleaned.count { it == ':' } == 1) "HH:mm" else "HH:mm:ss"
                cleaned.stringToDate(pattern)
            }
    }
}