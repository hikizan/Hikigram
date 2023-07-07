package com.hikizan.hikigram

import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun dateFormatNewest() {
        val parsedDate = LocalDateTime.parse("2023-07-05T13:23:37.980Z", DateTimeFormatter.ISO_DATE_TIME)
        val formattedDate = parsedDate.format(DateTimeFormatter.ofPattern("dd.MMMM.yyyy HH:mm", Locale("id", "ID")))
        assertEquals("05.Juli.2023 13:23", formattedDate)
    }

    @Test
    fun dateFormatTest() {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val createdAt = "2023-07-05T13:23:37.980Z"
        val formatDate = SimpleDateFormat(
            "dd MMMM yyyy", Locale("id", "ID")
        ).format(parser.parse(createdAt))
        assertEquals("05 Juli 2023", formatDate)
    }
}