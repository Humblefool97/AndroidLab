package com.thebyteshoppe.data

import com.thebyteshoppe.common.model.FeedItem

interface FeedRepository {
    fun getFeed(): List<FeedItem>

    fun getItem(id: Long): FeedItem?
}

object DefaultFeedRepository : FeedRepository {
    private const val ITEM_COUNT = 1_000
    private const val BASE_TIMESTAMP = 1_735_689_600_000L
    private val categories = listOf("Android", "Kotlin", "Compose", "Gradle", "Performance")

    private val items: List<FeedItem> = List(ITEM_COUNT) { index ->
        val id = index + 1L
        val category = categories[index % categories.size]
        FeedItem(
            id = id,
            title = "$category performance case study #$id",
            summary = "A deterministic summary for item $id covering practical $category techniques.",
            content = buildString {
                repeat(8) { paragraph ->
                    append(
                        "Paragraph ${paragraph + 1}: This deterministic article examines " +
                            "$category performance behavior for case $id. "
                    )
                    append(
                        "Its fixed text keeps layout, allocation, and scrolling work comparable " +
                            "between benchmark runs.\n\n"
                    )
                }
            },
            category = category,
            publishedAt = BASE_TIMESTAMP - index * 86_400_000L,
            imageIndex = index % 6,
        )
    }

    override fun getFeed(): List<FeedItem> = items

    override fun getItem(id: Long): FeedItem? = items.getOrNull((id - 1L).toInt())
        ?.takeIf { it.id == id }
}
