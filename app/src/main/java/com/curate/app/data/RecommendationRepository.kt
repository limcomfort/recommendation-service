package com.curate.app.data

/**
 * Port mirrored from the backend's domain layer (src/domain/entities/Item.ts).
 * The UI talks to this interface only; a real implementation would call the
 * recommendation-service HTTP API, the same way MockRecommendationRepository
 * stands in for MockMlModelAdapter + PostgresHistoryRepository on the server.
 */
interface RecommendationRepositoryPort {
    suspend fun getFeed(): FeedState
    suspend fun getProduct(id: String): Product?
    suspend fun getRelated(id: String): List<Product>
    suspend fun like(productId: String)
}

class MockRecommendationRepository : RecommendationRepositoryPort {

    private val catalog = listOf(
        Product(
            id = "wh-1000xm5",
            brand = "SONY",
            name = "WH-1000XM5",
            subtitle = "Wireless headphones",
            price = 349,
            matchScore = 98,
            emoji = "🎧",
            rating = 4.8,
            reviews = 2418,
            description = "Find your focus with exceptional noise cancellation, rich sound, and all-day comfort. From your gaming setup to your next long-haul flight.",
            highlights = listOf(
                Triple("🔇", "Less noise.", "More of your world."),
                Triple("🔋", "30-hour battery", "Go further between charges."),
                Triple("🔗", "Seamless pairing", "Two devices. No switching."),
            ),
        ),
        Product(
            id = "mx-master-3s",
            brand = "LOGITECH",
            name = "MX Master 3S",
            subtitle = "Performance mouse",
            price = 99,
            matchScore = 96,
            emoji = "🖱️",
        ),
        Product(
            id = "k2-pro",
            brand = "KEYCHRON",
            name = "K2 Pro",
            subtitle = "Mechanical keyboard",
            price = 99,
            matchScore = 95,
            emoji = "⌨️",
        ),
        Product(
            id = "steam-deck-oled",
            brand = "VALVE",
            name = "Steam Deck OLED",
            subtitle = "Handheld gaming",
            price = 549,
            matchScore = 94,
            emoji = "🎮",
        ),
        Product(
            id = "everyday-backpack",
            brand = "PEAK DESIGN",
            name = "Everyday Backpack 20L",
            subtitle = "Everyday carry",
            price = 279,
            matchScore = 93,
            emoji = "🎒",
        ),
        Product(
            id = "t7-shield",
            brand = "SAMSUNG",
            name = "T7 Shield · 1TB",
            subtitle = "Portable storage",
            price = 109,
            matchScore = 92,
            emoji = "💾",
        ),
        Product(
            id = "737-power-bank",
            brand = "ANKER",
            name = "737 Power Bank",
            subtitle = "Travel charging",
            price = 129,
            matchScore = 91,
            emoji = "🔌",
        ),
        Product(
            id = "roam-2",
            brand = "SONOS",
            name = "Roam 2",
            subtitle = "Portable speaker",
            price = 179,
            matchScore = 90,
            emoji = "🔊",
        ),
        Product(
            id = "x4-action-camera",
            brand = "INSTA360",
            name = "X4 Action Camera",
            subtitle = "Adventure cameras",
            price = 499,
            matchScore = 89,
            emoji = "📷",
        ),
    )

    private val liked = mutableSetOf<String>()

    override suspend fun getFeed(): FeedState = FeedState(
        userName = "Alex",
        recommended = catalog.take(5),
        picked = catalog,
        interests = listOf("Gamer", "Tech", "Traveler"),
        recentlyViewed = listOf("wh-1000xm5", "mx-master-3s", "everyday-backpack", "737-power-bank")
            .mapNotNull { id -> catalog.find { it.id == id } },
        servedFromCache = true,
    )

    override suspend fun getProduct(id: String): Product? = catalog.find { it.id == id }

    override suspend fun getRelated(id: String): List<Product> =
        catalog.filter { it.id != id }.shuffled().take(3).sortedBy { it.price }

    override suspend fun like(productId: String) {
        if (liked.contains(productId)) liked.remove(productId) else liked.add(productId)
    }
}
