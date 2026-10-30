package com.curate.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.curate.app.data.Product
import com.curate.app.ui.theme.BlueTint
import com.curate.app.ui.theme.ImageBackdrop
import com.curate.app.ui.theme.MutedInk
import com.curate.app.ui.theme.PrimaryBlue
import com.curate.app.ui.theme.PrimaryInk
import com.curate.app.ui.theme.SuccessGreen

@Composable
fun ProductImagePlaceholder(emoji: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.background(ImageBackdrop),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = emoji, fontSize = 44.sp)
    }
}

@Composable
fun MatchChip(text: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(6.dp),
        color = BlueTint,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Icon(
                imageVector = Icons.Outlined.Bolt,
                contentDescription = null,
                tint = PrimaryBlue,
                modifier = Modifier.size(12.dp),
            )
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium,
                color = PrimaryBlue,
            )
        }
    }
}

@Composable
fun CurateChip(label: String, modifier: Modifier = Modifier, selected: Boolean = false) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        color = if (selected) BlueTint else MaterialTheme.colorScheme.surface,
        border = if (selected) null else androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = if (selected) PrimaryBlue else MutedInk,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
        )
    }
}

/**
 * Product card anatomy (style guide 04): image · brand · product · price ·
 * match score · instant · tailored tag.
 */
@Composable
fun ProductCard(
    product: Product,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onLike: () -> Unit = {},
    showForYouBadge: Boolean = false,
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
    ) {
        Column {
            Box {
                ProductImagePlaceholder(
                    emoji = product.emoji,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.25f),
                )
                if (showForYouBadge) {
                    MatchChip(
                        text = "For you",
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(8.dp),
                    )
                } else {
                    IconButton(
                        onClick = onLike,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(4.dp)
                            .size(28.dp)
                            .background(MaterialTheme.colorScheme.surface, CircleShape),
                    ) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Like",
                            tint = MutedInk,
                            modifier = Modifier.size(14.dp),
                        )
                    }
                }
            }
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = product.brand,
                    style = MaterialTheme.typography.labelSmall,
                    color = MutedInk,
                )
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = PrimaryInk,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = product.subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Text(
                        text = "$${product.price}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = PrimaryInk,
                    )
                    Text(
                        text = "${product.matchScore}% match",
                        style = MaterialTheme.typography.labelMedium,
                        color = PrimaryBlue,
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Bolt,
                        contentDescription = null,
                        tint = SuccessGreen,
                        modifier = Modifier.size(12.dp),
                    )
                    Text(
                        text = "Instant",
                        style = MaterialTheme.typography.labelSmall,
                        color = SuccessGreen,
                    )
                    Text(
                        text = "· Tailored to your interests",
                        style = MaterialTheme.typography.labelSmall,
                        color = MutedInk,
                    )
                }
            }
        }
    }
}
