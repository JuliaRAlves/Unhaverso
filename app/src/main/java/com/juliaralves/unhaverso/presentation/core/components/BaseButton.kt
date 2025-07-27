package com.juliaralves.unhaverso.presentation.core.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juliaralves.unhaverso.R
import com.juliaralves.unhaverso.presentation.core.theme.UnhaversoTheme

@Composable
fun BaseButton(text: String, icon: Painter? = null, onClick: () -> Unit) {
    Button(
        onClick,
        modifier = Modifier.height(48.dp),
        shape = RoundedCornerShape(size = 16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        if (icon != null) {
            Icon(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BaseButtonDarkMode() {
    UnhaversoTheme {
        BaseButton("Clique aqui", painterResource(R.drawable.ic_inspiration)) {

        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun BaseButtonLightMode() {
    UnhaversoTheme {
        BaseButton("Clique aqui", painterResource(R.drawable.ic_inspiration)) {

        }
    }
}