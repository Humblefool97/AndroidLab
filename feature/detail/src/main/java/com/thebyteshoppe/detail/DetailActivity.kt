package com.thebyteshoppe.detail

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.thebyteshoppe.data.DefaultFeedRepository

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val itemId = intent.getLongExtra(EXTRA_ITEM_ID, INVALID_ITEM_ID)
        val item = DefaultFeedRepository.getItem(itemId)
        if (item == null) {
            finish()
            return
        }

        findViewById<TextView>(R.id.detail_title).text = item.title
        findViewById<TextView>(R.id.detail_category).text = item.category
        findViewById<TextView>(R.id.detail_body).text = item.content
        findViewById<ImageView>(R.id.detail_image).setBackgroundColor(imageColor(item.imageIndex))
    }

    companion object {
        private const val EXTRA_ITEM_ID = "feed_item_id"
        private const val INVALID_ITEM_ID = -1L

        fun createIntent(context: Context, itemId: Long): Intent =
            Intent(context, DetailActivity::class.java)
                .putExtra(EXTRA_ITEM_ID, itemId)
    }
}

private fun imageColor(index: Int): Int {
    val colors = intArrayOf(
        Color.rgb(21, 101, 192),
        Color.rgb(106, 27, 154),
        Color.rgb(0, 137, 123),
        Color.rgb(239, 108, 0),
        Color.rgb(198, 40, 40),
        Color.rgb(69, 90, 100),
    )
    return colors[index.mod(colors.size)]
}
