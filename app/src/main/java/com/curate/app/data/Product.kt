package com.curate.app.data

data class Product(
    val id: String,
    val brand: String,
    val name: String,
    val subtitle: String,
    val price: Int,
    val matchScore: Int,
    val emoji: String,
    val rating: Double = 4.8,
    val reviews: Int = 2418,
    val inStock: Boolean = true,
    val description: String = "",
    val highlights: List<Triple<String, String, String>> = emptyList(),
)

data class RecommendationReason(
    val title: String,
    val detail: String,
)

data class FeedState(
    val userName: String,
    val recommended: List<Product>,
    val picked: List<Product>,
    val interests: List<String>,
    val recentlyViewed: List<Product>,
    val servedFromCache: Boolean,
)
