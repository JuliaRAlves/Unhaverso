package com.juliaralves.unhaverso.presentation.nailpolishbox

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juliaralves.unhaverso.R
import com.juliaralves.unhaverso.presentation.nailpolishbox.components.NailPolishBoxActionButton
import org.koin.androidx.compose.koinViewModel

@Composable
fun NailPolishBoxScreen(viewModel: NailPolishBoxViewModel = koinViewModel()) {
    val state = viewModel.screenState
    when (state) {
        is NailPolishBoxScreenState.Empty -> {
            NailPolishBoxEmptyScreen(onNewNailPolishClick = { })
        }

        is NailPolishBoxScreenState.Filled -> {
            NailPolishBoxFilledScreen(onNewNailPolishClick = { })
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun NailPolishBoxEmptyScreen(onNewNailPolishClick: () -> Unit) {
    Scaffold(
        floatingActionButton = { NailPolishBoxActionButton(onNewNailPolishClick) }
    ) { _ ->
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.img_empty_box),
                contentDescription = null,
                modifier = Modifier.size(400.dp)
            )
            Text(
                text = stringResource(id = R.string.nail_polish_box_empty_text),
                modifier = Modifier.padding(vertical = 24.dp),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun NailPolishBoxFilledScreen(onNewNailPolishClick: () -> Unit) {
    Scaffold(
        floatingActionButton = { NailPolishBoxActionButton(onNewNailPolishClick) }
    ) { _ ->

    }
}

@Preview
@Composable
fun NailPolishBoxScreenPreview() {
    NailPolishBoxScreen()
}