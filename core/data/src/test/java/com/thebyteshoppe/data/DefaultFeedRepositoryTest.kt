package com.thebyteshoppe.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertSame
import org.junit.Test

class DefaultFeedRepositoryTest {
    @Test
    fun feed_isStableAndContainsExactlyOneThousandItems() {
        val firstRead = DefaultFeedRepository.getFeed()
        val secondRead = DefaultFeedRepository.getFeed()

        assertSame(firstRead, secondRead)
        assertEquals(1_000, firstRead.size)
        assertEquals((1L..1_000L).toList(), firstRead.map { it.id })
        assertEquals("Android performance case study #1", firstRead.first().title)
        assertEquals("Performance", firstRead.last().category)
    }

    @Test
    fun getItem_returnsMatchingItemOrNull() {
        assertEquals(500L, DefaultFeedRepository.getItem(500L)?.id)
        assertNull(DefaultFeedRepository.getItem(0L))
        assertNull(DefaultFeedRepository.getItem(1_001L))
    }
}
