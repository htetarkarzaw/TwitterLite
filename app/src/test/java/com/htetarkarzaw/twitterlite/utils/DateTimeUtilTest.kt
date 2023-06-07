package com.htetarkarzaw.twitterlite.utils

import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

class DateTimeUtilTest {

    @Test
    fun `check time different with 2days`(){
        val timeDifferent = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(2)
        val result = DateTimeUtil.convertTimestampToRelativeTime(timeDifferent)
        assertEquals("2 d",result)
    }

    @Test
    fun `check time different with 30 minutes`(){
        val timeDifferent = System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(30)
        val result = DateTimeUtil.convertTimestampToRelativeTime(timeDifferent)
        assertEquals("30 min",result)
    }

    @Test
    fun `check time different with 5 months`(){
        val timeDifferent = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(150)
        val result = DateTimeUtil.convertTimestampToRelativeTime(timeDifferent)
        assertEquals("5 m",result)
    }

    @Test
    fun `check time different with 1 year`(){
        val timeDifferent = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(380)
        val result = DateTimeUtil.convertTimestampToRelativeTime(timeDifferent)
        assertEquals("1 y",result)
    }

    @Test
    fun `check date time for detail`(){
        //"hh:mm aa MM/dd/yyyy"
        val date = "10:30 PM 06/08/2023"
        val simpleDateFormat = SimpleDateFormat("hh:mm aa MM/dd/yyyy", Locale.getDefault())
        val time = simpleDateFormat.parse(date)?.time?:0L
        val result = DateTimeUtil.dateFormatForDetail(time)
        assertEquals("10:30 PM 06/08/2023",result)
    }
}