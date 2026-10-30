package com.curate.app.ui.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.curate.app.data.FeedState
import com.curate.app.data.Product
import com.curate.app.ui.components.CurateChip
import com.curate.app.ui.components.ProductCard
import com.curate.app.ui.components.ProductImagePlaceholder
import com.curate.app.ui.theme.BlueTint
import com.curate.app.ui.theme.MutedInk
import com.curate.app.ui.theme.PrimaryBlue
import com.curate.app.ui.theme.PrimaryInk
import com.curate.app.ui.theme.SuccessGreen

private data class RailItem(
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val showDot: Boolean = false,
)

@Composable
fun FeedScreen(
    feed: FeedState,
    onProductClick: (String) -> Unit,
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf(
        RailItem("Feed", Icons.Outlined.GridView),
        RailItem("Catalog", Icons.Outlined.Category),
        RailItem("History", Icons.Outlined.History, showDot = true),
        RailItem("Segments", Icons.Outlined.People),
        RailItem("Settings", Icons.Outlined.Settings),
    )

    Row(modifier = Modifier.fillMaxSize()) {
        NavigationRail(
            containerColor = MaterialTheme.colorScheme.surface,
            modifier = Modifier.width(88.dp),
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(PrimaryBlue, MaterialTheme.shapes.small),
                contentAlignment = Alignment.Center,
            ) {
                Text("c", color = Color.White, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(24.dp))
            tabs.forEachIndexed { index, item ->
                NavigationRailItem(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    icon = {
                        Box {
                            Icon(item.icon, contentDescription = item.label)
                            if (item.showDot) {
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .size(6.dp)
                                        .background(SuccessGreen, CircleShape),
                                )
                            }
                        }
                    },
                    label = { Text(item.label, style = MaterialTheme.typography.labelSmall) },
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier.padding(bottom = 24.dp, start = 8.dp, end = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    color = BlueTint,
                ) {
                    Column(
                        modifier = Modifier.padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(SuccessGreen, CircleShape),
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            "Made for you",
                            style = MaterialTheme.typography.labelSmall,
                            color = PrimaryInk,
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
        ) {
            TopBar()
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Spacer(modifier = Modifier.height(8.dp))
                GreetingHeader(feed)
                Spacer(modifier = Modifier.height(24.dp))
                RecommendedSection(feed.recommended, onProductClick)
                Spacer(modifier = Modifier.height(28.dp))
                PickedSection(feed, onProductClick)
                Spacer(modifier = Modifier.height(28.dp))
                RecentlyViewedSection(feed.recentlyViewed, onProductClick)
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    "You're all caught up. More discoveries are on the way.",
                    style = MaterialTheme.typography.bodySmall,
                    color = SuccessGreen,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    "Curate · Personalization & Recommendation Service",
                    style = MaterialTheme.typography.labelSmall,
                    color = MutedInk,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "curate",
            style = MaterialTheme.typography.titleLarge,
            color = PrimaryInk,
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = {}) {
            Icon(Icons.Outlined.Search, contentDescription = "Search", tint = MutedInk)
        }
        IconButton(onClick = {}) {
            Icon(Icons.Outlined.Notifications, contentDescription = "Notifications", tint = MutedInk)
        }
        Spacer(modifier = Modifier.width(4.dp))
        Column(horizontalAlignment = Alignment.End) {
            Text("Alex Morgan", style = MaterialTheme.typography.labelLarge, color = PrimaryInk)
            Text("Personal workspace", style = MaterialTheme.typography.labelSmall, color = MutedInk)
        }
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(BlueTint, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Text("AM", style = MaterialTheme.typography.labelMedium, color = PrimaryBlue)
        }
    }
}

@Composable
private fun GreetingHeader(feed: FeedState) {
    Text(
        text = "YOUR PERSONAL DISCOVERY SPACE",
        style = MaterialTheme.typography.labelSmall,
        color = PrimaryBlue,
        letterSpacing = 1.2.sp,
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Good morning, ${feed.userName}.",
            style = MaterialTheme.typography.displayMedium,
        )
        Spacer(modifier = Modifier.weight(1f))
        if (feed.servedFromCache) {
            CurateChip("⚡ Served from cache · 12 ms")
        }
    }
    Text(
        text = "Great finds for the way you work, play, and explore.",
        style = MaterialTheme.typography.bodyMedium,
    )
    Spacer(modifier = Modifier.height(12.dp))
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        feed.interests.forEach { CurateChip(it, selected = true) }
    }
}

@Composable
private fun RecommendedSection(items: List<Product>, onProductClick: (String) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column {
            Text(
                text = "Recommended for you",
                style = MaterialTheme.typography.headlineMedium,
            )
            Text(
                text = "A few things we think you'll love. No endless scrolling required.",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
    Row(
        modifier = Modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items.forEach { product ->
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surface,
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                modifier = Modifier
                    .width(150.dp)
                    .clickable { onProductClick(product.id) },
            ) {
                Column {
                    Box {
                        ProductImagePlaceholder(
                            emoji = product.emoji,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(110.dp),
                        )
                        MatchBadge(modifier = Modifier.align(Alignment.TopStart).padding(6.dp))
                    }
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(product.brand, style = MaterialTheme.typography.labelSmall, color = MutedInk)
                        Text(
                            product.name,
                            style = MaterialTheme.typography.labelLarge,
                            color = PrimaryInk,
                            maxLines = 1,
                        )
                        Text(
                            "$${product.price}",
                            style = MaterialTheme.typography.titleMedium,
                            color = PrimaryInk,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MatchBadge(modifier: Modifier = Modifier) {
    Surface(modifier = modifier, shape = MaterialTheme.shapes.extraSmall, color = BlueTint) {
        Text(
            "⚡ For you",
            style = MaterialTheme.typography.labelSmall,
            color = PrimaryBlue,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
        )
    }
}

@Composable
private fun PickedSection(feed: FeedState, onProductClick: (String) -> Unit) {
    var filter by remember { mutableStateOf(0) }
    val filters = listOf("All picks", "For your setup", "On the go")
    Row(verticalAlignment = Alignment.Bottom) {
        Column {
            Text("Picked for you", style = MaterialTheme.typography.headlineMedium)
            Text(
                "Your interests, translated into your next favorites.",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            "${feed.picked.size} recommendations",
            style = MaterialTheme.typography.labelMedium,
            color = MutedInk,
        )
    }
    Spacer(modifier = Modifier.height(12.dp))
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        filters.forEachIndexed { index, label ->
            CurateChip(
                label = label,
                selected = filter == index,
                modifier = Modifier.clickable { filter = index },
            )
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
    val filtered = when (filter) {
        1 -> feed.picked.filter { it.price >= 99 }
        2 -> feed.picked.filter { it.price < 99 }
        else -> feed.picked
    }
    filtered.chunked(2).forEach { rowItems ->
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            rowItems.forEach { product ->
                ProductCard(
                    product = product,
                    modifier = Modifier.weight(1f),
                    showForYouBadge = false,
                    onClick = { onProductClick(product.id) },
                )
            }
            if (rowItems.size == 1) Spacer(modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
private fun RecentlyViewedSection(items: List<Product>, onProductClick: (String) -> Unit) {
    Text("Recently viewed", style = MaterialTheme.typography.titleLarge)
    Spacer(modifier = Modifier.height(12.dp))
    Row(
        modifier = Modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items.forEach { product ->
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surface,
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                modifier = Modifier.clickable { onProductClick(product.id) },
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ProductImagePlaceholder(
                        emoji = product.emoji,
                        modifier = Modifier
                            .size(44.dp)
                            .border(1.dp, MaterialTheme.colorScheme.outline, MaterialTheme.shapes.small),
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            product.name,
                            style = MaterialTheme.typography.labelLarge,
                            color = PrimaryInk,
                        )
                        Text(
                            "$${product.price} · ${product.subtitle}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MutedInk,
                        )
                    }
                }
            }
        }
    }
}
