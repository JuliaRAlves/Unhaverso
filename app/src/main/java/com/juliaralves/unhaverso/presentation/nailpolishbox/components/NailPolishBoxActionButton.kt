package com.juliaralves.unhaverso.presentation.nailpolishbox.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juliaralves.unhaverso.R
import com.juliaralves.unhaverso.presentation.core.theme.UnhaversoTheme

@Composable
fun NailPolishBoxActionButton(onClick: () -> Unit) {
    Column {
        FloatingActionButton(
            onClick = onClick,
            elevation = FloatingActionButtonDefaults.elevation(0.dp),
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_plus),
                contentDescription = "Criar esmalte",
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FilterNotSelectedDarkMode() {
    UnhaversoTheme {
        NailPolishBoxActionButton {}
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun FilterNotSelectedLightMode() {
    UnhaversoTheme {
        NailPolishBoxActionButton {}
    }
}