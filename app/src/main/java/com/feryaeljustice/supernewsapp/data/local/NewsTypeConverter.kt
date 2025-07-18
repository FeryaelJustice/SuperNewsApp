package com.feryaeljustice.supernewsapp.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.feryaeljustice.supernewsapp.domain.model.Source

@ProvidedTypeConverter
class NewsTypeConverter {
    @TypeConverter
    fun sourceToString(source: Source): String = "${source.id},${source.name}"

    @TypeConverter
    fun stringToSource(source: String): Source =
        source.split(',').let { sourceArray ->
            Source(id = sourceArray[0], name = sourceArray[1])
        }
}
