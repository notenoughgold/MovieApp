package com.altayiskender.movieapp.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.altayiskender.movieapp.utils.getPosterUrl
import movieapp.composeapp.generated.resources.Res
import movieapp.composeapp.generated.resources.ic_person_placeholder
import org.jetbrains.compose.resources.painterResource

@Composable
fun PersonItem(
    person: PersonUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.width(100.dp),
        color = Color.Transparent,
        onClick = onClick
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = getPosterUrl(person.profilePath),
                contentScale = ContentScale.Crop,
                contentDescription = person.name,
                fallback = painterResource(resource = Res.drawable.ic_person_placeholder),
                error = painterResource(resource = Res.drawable.ic_person_placeholder),
                placeholder = painterResource(resource = Res.drawable.ic_person_placeholder),
                modifier = Modifier
                    .clip(CircleShape)
                    .height(80.dp)
                    .aspectRatio(1f)
            )
            Text(
                text = person.name,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = person.role,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
