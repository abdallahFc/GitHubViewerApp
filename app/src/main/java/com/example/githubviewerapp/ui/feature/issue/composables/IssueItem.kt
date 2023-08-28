package com.example.githubviewerapp.ui.feature.issue.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.githubviewerapp.R
import com.example.githubviewerapp.ui.composables.ImageNetwork

@Composable
fun IssueItem(
    title: String,
    state: String,
    repositoryOwner: String,
    ownerAvatarUrl: String,
    date: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        ImageNetwork(
            modifier = Modifier
                .weight(0.15f)
                .aspectRatio(1f)
                .align(Alignment.Top)
                .clip(CircleShape),
            imageUrl = ownerAvatarUrl,
        )
        Column(
            modifier = Modifier
                .weight(0.8f)
                .padding(start = 16.dp)
        ) {
            Text(
                text = repositoryOwner,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.padding(2.dp))
            Row {
                val image = when (state) {
                    "open" -> R.drawable.open_issues
                    "closed" -> R.drawable.close_issuse
                    else -> R.drawable.open_issues
                }
                Image(
                    modifier = Modifier
                        .size(16.dp)
                        .align(Alignment.CenterVertically),
                    painter = painterResource(id = image),
                    contentDescription = "state"
                )
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = state,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary,
                )

            }
            Text(
                text = date,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

    }

}