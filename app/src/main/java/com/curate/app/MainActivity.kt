package com.curate.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.curate.app.data.FeedState
import com.curate.app.data.MockRecommendationRepository
import com.curate.app.data.Product
import com.curate.app.ui.detail.ProductDetailScreen
import com.curate.app.ui.feed.FeedScreen
import com.curate.app.ui.theme.CurateTheme

class MainActivity : ComponentActivity() {

    private val repository = MockRecommendationRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurateTheme {
                var feed by remember { mutableStateOf<FeedState?>(null) }
                var selectedProductId by remember { mutableStateOf<String?>(null) }
                var selectedProduct by remember { mutableStateOf<Product?>(null) }
                var related by remember { mutableStateOf<List<Product>>(emptyList()) }

                LaunchedEffect(Unit) {
                    feed = repository.getFeed()
                }

                LaunchedEffect(selectedProductId) {
                    selectedProduct = selectedProductId?.let { repository.getProduct(it) }
                    related = selectedProductId
                        ?.let { repository.getRelated(it) }
                        .orEmpty()
                }

                val product = selectedProduct
                val currentFeed = feed
                when {
                    product != null -> ProductDetailScreen(
                        product = product,
                        related = related,
                        onBack = { selectedProductId = null },
                        onProductClick = { selectedProductId = it },
                    )
                    currentFeed != null -> FeedScreen(
                        feed = currentFeed,
                        onProductClick = { selectedProductId = it },
                    )
                }
            }
        }
    }
}
