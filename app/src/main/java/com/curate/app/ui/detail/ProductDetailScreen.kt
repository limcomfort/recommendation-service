package com.curate.app.ui.detail

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Interests
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.curate.app.data.Product
import com.curate.app.ui.components.CurateChip
import com.curate.app.ui.components.MatchChip
import com.curate.app.ui.components.ProductImagePlaceholder
import com.curate.app.ui.theme.BlueTint
import com.curate.app.ui.theme.MutedInk
import com.curate.app.ui.theme.PrimaryBlue
import com.curate.app.ui.theme.PrimaryInk
import com.curate.app.ui.theme.StarAmber
import com.curate.app.ui.theme.SuccessGreen

private val reasons = listOf(
    "Inspired by your history" to "You've been exploring wireless audio and noise-canceling headphones.",
    "Fits your Tech & Traveler interests" to "Premium sound at your desk. A quieter journey on the go.",
    "Popular with people like you" to "A top-rated pick among tech enthusiasts this week.",
)

@Composable
fun ProductDetailScreen(
    product: Product,
    related: List<Product>,
    onBack: () -> Unit,
    onProductClick: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                contentDescription = "Back",
                tint = MutedInk,
                modifier = Modifier
                    .size(22.dp)
                    .clickable(onClick = onBack),
            )
            Text(
                text = "Back",
                style = MaterialTheme.typography.labelLarge,
                color = MutedInk,
                modifier = Modifier
                    .padding(start = 6.dp)
                    .clickable(onClick = onBack),
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text("Your feed", style = MaterialTheme.typography.labelMedium, color = MutedInk)
            Text("  /  ", style = MaterialTheme.typography.labelMedium, color = MutedInk)
            Text("Audio", style = MaterialTheme.typography.labelMedium, color = MutedInk)
            Text("  /  ", style = MaterialTheme.typography.labelMedium, color = MutedInk)
            Text(product.name, style = MaterialTheme.typography.labelMedium, color = PrimaryInk)
        }

        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Box {
                        ProductImagePlaceholder(
                            emoji = product.emoji,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(260.dp),
                        )
                        Surface(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(10.dp),
                            shape = MaterialTheme.shapes.small,
                            color = BlueTint,
                        ) {
                            Text(
                                "⚡ A match for your everyday",
                                style = MaterialTheme.typography.labelSmall,
                                color = PrimaryBlue,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        product.highlights.forEach { (icon, title, detail) ->
                            Surface(
                                shape = MaterialTheme.shapes.medium,
                                color = MaterialTheme.colorScheme.surface,
                                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                                modifier = Modifier.weight(1f),
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(icon, fontSize = 18.sp)
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(title, style = MaterialTheme.typography.labelLarge, color = PrimaryInk)
                                    Text(detail, style = MaterialTheme.typography.labelSmall, color = MutedInk)
                                }
                            }
                        }
                    }
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        MatchChip("⚡ ${product.matchScore}% match")
                        CurateChip("In stock", selected = true)
                    }
                    Text(
                        "${product.brand} · WIRELESS AUDIO",
                        style = MaterialTheme.typography.labelSmall,
                        color = MutedInk,
                        letterSpacing = 1.sp,
                    )
                    Text(product.name, style = MaterialTheme.typography.headlineMedium)
                    Text("Your world. Beautifully uninterrupted.", style = MaterialTheme.typography.bodyMedium)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Row {
                            repeat(5) {
                                Icon(
                                    imageVector = Icons.Outlined.Star,
                                    contentDescription = null,
                                    tint = StarAmber,
                                    modifier = Modifier.size(16.dp),
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            "${product.rating}  (${product.reviews} reviews)",
                            style = MaterialTheme.typography.labelMedium,
                            color = PrimaryInk,
                        )
                    }
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            "$${product.price}",
                            style = MaterialTheme.typography.displayMedium,
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            "Free delivery · Free 30-day returns",
                            style = MaterialTheme.typography.labelSmall,
                            color = MutedInk,
                            modifier = Modifier.padding(bottom = 8.dp),
                        )
                    }
                    Text(product.description, style = MaterialTheme.typography.bodyMedium)

                    Surface(
                        shape = MaterialTheme.shapes.medium,
                        color = BlueTint,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Outlined.Interests,
                                    contentDescription = null,
                                    tint = PrimaryBlue,
                                    modifier = Modifier.size(16.dp),
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    "Why we recommend this",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = PrimaryInk,
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            reasons.forEachIndexed { index, (title, detail) ->
                                Row {
                                    Icon(
                                        when (index) {
                                            0 -> Icons.Outlined.History
                                            1 -> Icons.Outlined.Interests
                                            else -> Icons.Outlined.Group
                                        },
                                        contentDescription = null,
                                        tint = PrimaryBlue,
                                        modifier = Modifier.size(16.dp),
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(title, style = MaterialTheme.typography.labelLarge, color = PrimaryInk)
                                        Text(detail, style = MaterialTheme.typography.bodySmall)
                                    }
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                    }

                    var liked by remember { mutableStateOf(false) }
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Button(
                            onClick = {},
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            shape = MaterialTheme.shapes.medium,
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                        ) {
                            Icon(Icons.Outlined.ShoppingCart, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Add to cart")
                        }
                        OutlinedButton(
                            onClick = { liked = !liked },
                            modifier = Modifier
                                .width(110.dp)
                                .height(48.dp),
                            shape = MaterialTheme.shapes.medium,
                        ) {
                            Icon(
                                Icons.Outlined.FavoriteBorder,
                                contentDescription = null,
                                tint = if (liked) PrimaryBlue else MutedInk,
                                modifier = Modifier.size(18.dp),
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Like", color = PrimaryInk)
                        }
                    }
                    Row(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(Icons.Outlined.Bolt, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(12.dp))
                        Text(
                            "Generated in 310 ms · cached",
                            style = MaterialTheme.typography.labelSmall,
                            color = MutedInk,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            Text("Good company for a great setup", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                related.forEach { item ->
                    Surface(
                        shape = MaterialTheme.shapes.medium,
                        color = MaterialTheme.colorScheme.surface,
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                        modifier = Modifier
                            .width(220.dp)
                            .clickable { onProductClick(item.id) },
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            ProductImagePlaceholder(
                                emoji = item.emoji,
                                modifier = Modifier.size(48.dp),
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(item.brand, style = MaterialTheme.typography.labelSmall, color = MutedInk)
                                Text(item.name, style = MaterialTheme.typography.labelLarge, color = PrimaryInk)
                                Text("$${item.price}", style = MaterialTheme.typography.labelMedium, color = PrimaryInk)
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
