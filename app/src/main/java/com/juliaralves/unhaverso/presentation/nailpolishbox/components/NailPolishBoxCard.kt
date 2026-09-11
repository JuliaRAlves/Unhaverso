package com.juliaralves.unhaverso.presentation.nailpolishbox.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juliaralves.unhaverso.R
import com.juliaralves.unhaverso.domain.model.NailPolishVO
import com.juliaralves.unhaverso.presentation.core.theme.UnhaversoTheme

@Composable
fun NailPolishBoxCard(
    modifier: Modifier = Modifier,
    nailPolish: NailPolishVO,
    isExpanded: Boolean = true,
    onEditClick: (NailPolishVO) -> Unit,
    onDeleteClick: (NailPolishVO) -> Unit
) {
    val boxModifier = if (isExpanded) modifier.fillMaxWidth() else modifier
    var showDropDown by remember { mutableStateOf(false) }

    Box(
        modifier = boxModifier
            .padding(top = 8.dp)
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(16.dp)
            )
            .defaultMinSize(minHeight = 120.dp)
    ) {
        if (showDropDown) {
            Column {
                Spacer(Modifier.height(120.dp))
                Text(
                    modifier = Modifier
                        .padding(0.dp)
                        .fillMaxWidth()
                        .clipToBounds()
                        .clickable { onEditClick(nailPolish) }
                        .padding(16.dp),
                    text = stringResource(R.string.nail_polish_box_dropdown_edit),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier
                        .padding(0.dp)
                        .fillMaxWidth()
                        .clipToBounds()
                        .clickable { onDeleteClick(nailPolish) }
                        .padding(16.dp),
                    text = stringResource(R.string.nail_polish_box_dropdown_delete),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
    Box(
        modifier = boxModifier
            .background(
                color = Color(nailPolish.colorArgb),
                shape = RoundedCornerShape(16.dp)
            )
            .height(120.dp)
    ) {
        val textBoxModifier = if (isExpanded) Modifier else Modifier.fillMaxWidth()

        Box(
            textBoxModifier
                .clickable(enabled = true, onClick = { showDropDown = showDropDown.not() })
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = if (isExpanded) {
                        RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                    } else {
                        RectangleShape
                    }
                )
                .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
                .align(Alignment.BottomCenter)
                .defaultMinSize(40.dp, 40.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.weight(1f, fill = false),
                    text = "${nailPolish.name} - ${nailPolish.brand}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis
                )
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_dropdown),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun NailPolishBoxCardDarkPreview() {
    UnhaversoTheme {
        NailPolishBoxCard(
            Modifier.width(200.dp),
            NailPolishVO(
                Color.Blue.toArgb(),
                "Azulcrination",
                "Risqué",
                emptyList(),
                0L
            ),
            isExpanded = true,
            onEditClick = {},
            onDeleteClick = {}
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun NailPolishBoxCardLightPreview() {
    UnhaversoTheme {
        NailPolishBoxCard(
            Modifier.width(200.dp),
            NailPolishVO(
                Color.Blue.toArgb(),
                "Azulcrination",
                "Risqué",
                emptyList(),
                0L
            ),
            isExpanded = true,
            onEditClick = {},
            onDeleteClick = {}
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun NailPolishBoxCardCompactDarkPreview() {
    UnhaversoTheme {
        NailPolishBoxCard(
            Modifier.width(200.dp),
            NailPolishVO(
                Color.Blue.toArgb(),
                "Azulcrination",
                "Risqué",
                emptyList(),
                0L
            ),
            isExpanded = false,
            onEditClick = {},
            onDeleteClick = {}
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun NailPolishBoxCardCompactLightPreview() {
    UnhaversoTheme {
        NailPolishBoxCard(
            Modifier.width(200.dp),
            NailPolishVO(
                Color.Blue.toArgb(),
                "Azulcrination",
                "Risqué",
                emptyList(),
                0L
            ),
            isExpanded = false,
            onEditClick = {},
            onDeleteClick = {}
        )
    }
}