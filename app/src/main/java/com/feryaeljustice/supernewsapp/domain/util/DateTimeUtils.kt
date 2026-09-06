package com.feryaeljustice.supernewsapp.domain.util

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

object DateTimeUtils {

    /**
     * Convierte una fecha ISO 8601 (ej. "2026-03-01T14:30:00Z") a un formato relativo legible.
     * Retorna cadenas como "Hace unos momentos", "Hace 15 min", "Hace 2 h", "Ayer" o "1 mar".
     */
    fun formatRelativeTime(isoDateString: String?): String {
        if (isoDateString.isNullOrBlank()) return ""
        return try {
            val instant = Instant.parse(isoDateString)
            val now = Instant.now()
            val diffSeconds = ChronoUnit.SECONDS.between(instant, now)

            when {
                diffSeconds < 60 -> "Hace unos instantes"
                diffSeconds < 3600 -> "Hace ${diffSeconds / 60} min"
                diffSeconds < 86400 -> "Hace ${diffSeconds / 3600} h"
                diffSeconds < 172800 -> "Ayer"
                diffSeconds < 604800 -> "Hace ${diffSeconds / 86400} d"
                else -> {
                    val zone = ZoneId.systemDefault()
                    val formatter = DateTimeFormatter.ofPattern("d MMM", Locale.getDefault())
                    instant.atZone(zone).format(formatter)
                }
            }
        } catch (e: Exception) {
            // Si el formato no es parseable directamente por Instant, devolver la cadena recortada
            isoDateString.take(10)
        }
    }

    /**
     * Calcula una estimación del tiempo de lectura en minutos en base al número de palabras.
     * Toma en cuenta tanto el resumen/descripción como el contenido disponible.
     */
    fun calculateReadingTime(description: String?, content: String?): String {
        val totalWords = ((description ?: "") + " " + (content ?: ""))
            .split(Regex("\\s+"))
            .count { it.isNotBlank() }

        val minutes = (totalWords / 150).coerceAtLeast(1)
        return "$minutes min de lectura"
    }

    /**
     * Retorna la fecha actual formateada para la cabecera de la app (ej. "Domingo, 6 de septiembre").
     */
    fun getFormattedCurrentDate(): String {
        return try {
            val locale = Locale.forLanguageTag("es-ES")
            val formatter = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM", locale)
            val formatted = java.time.LocalDate.now().format(formatter)
            formatted.replaceFirstChar { if (it.isLowerCase()) it.titlecase(locale) else it.toString() }
        } catch (e: Exception) {
            ""
        }
    }
}
