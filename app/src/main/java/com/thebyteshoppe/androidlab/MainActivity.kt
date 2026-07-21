package com.thebyteshoppe.androidlab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.thebyteshoppe.data.DefaultFeedRepository
import com.thebyteshoppe.detail.DetailActivity
import com.thebyteshoppe.feed.FeedRoute
import com.thebyteshoppe.ui.theme.AndroidLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidLabTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FeedRoute(
                        repository = DefaultFeedRepository,
                        onItemSelected = { itemId ->
                            startActivity(DetailActivity.createIntent(this, itemId))
                        },
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}