package es.jgalcolea.axpejorgegil.tests

import es.jgalcolea.axpejorgegil.util.DateUtil
import junit.framework.TestCase.assertEquals
import org.junit.Test

class DateUtilTest {
    private val incorrectDate = "283193890"
    private val correctDate = "2008-12-13T04:29:12.891Z"
    private val correctFormattedDate = "13/12/2008"
    @Test
    fun doesReturnCorrectDateFormat() {
        val formattedDate = DateUtil.parseDate(correctDate)
        assertEquals(formattedDate, correctFormattedDate)
    }

    @Test
    fun doesReturnEmptyStringOnIncorrectDate() {
        val formattedDate = DateUtil.parseDate(incorrectDate)
        assertEquals("", formattedDate)
    }
}