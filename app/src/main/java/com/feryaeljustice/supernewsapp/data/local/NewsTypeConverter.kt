package com.feryaeljustice.supernewsapp.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.feryaeljustice.supernewsapp.domain.model.Source

@ProvidedTypeConverter
class NewsTypeConverter {
    @TypeConverter
    fun sourceToString(source: Source?): String? = source?.let { "${it.id},${it.name}" }

    @TypeConverter
    fun stringToSource(source: String?): Source? =
        source?.split(',')?.let { sourceArray ->
            if (sourceArray.size >= 2) {
                Source(id = sourceArray[0], name = sourceArray[1])
            } else {
                null
            }
        }
}
