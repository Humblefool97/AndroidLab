package com.thebyteshoppe.common.model

data class FeedItem(
    val id: Long,
    val title: String,
    val summary: String,
    val content: String,
    val category: String,
    val publishedAt: Long,
    val imageIndex: Int,
)
