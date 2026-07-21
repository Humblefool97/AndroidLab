package com.thebyteshoppe.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.thebyteshoppe.common.model.FeedItem
import com.thebyteshoppe.data.FeedRepository

private val ImageColors = listOf(
    Color(0xFF1565C0),
    Color(0xFF6A1B9A),
    Color(0xFF00897B),
    Color(0xFFEF6C00),
    Color(0xFFC62828),
    Color(0xFF455A64),
)

@Composable
fun FeedRoute(
    repository: FeedRepository,
    onItemSelected: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    val items = remember(repository) { repository.getFeed() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .semantics { testTagsAsResourceId = true },
    ) {
        Text(
            text = "Android Performance Lab",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("feed_list"),
        ) {
            items(
                items = items,
                key = FeedItem::id,
            ) { item ->
                FeedItemRow(
                    item = item,
                    onClick = { onItemSelected(item.id) },
                )
                HorizontalDivider()
            }
        }
    }
}

@Composable
private fun FeedItemRow(
    item: FeedItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("feed_item_${item.id}")
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .background(imageColor(item.imageIndex)),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = item.category.take(1),
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = item.summary,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = item.category,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelMedium,
            )
        }
    }
}

private fun imageColor(index: Int): Color = ImageColors[index.mod(ImageColors.size)]
