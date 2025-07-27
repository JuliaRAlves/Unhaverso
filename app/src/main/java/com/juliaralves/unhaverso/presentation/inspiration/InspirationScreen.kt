package com.juliaralves.unhaverso.presentation.inspiration

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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.juliaralves.unhaverso.R
import com.juliaralves.unhaverso.domain.model.InspirationPictureVO
import com.juliaralves.unhaverso.presentation.core.components.BaseButton
import com.juliaralves.unhaverso.presentation.inspiration.components.InspirationActionButtons
import com.juliaralves.unhaverso.presentation.inspiration.components.InspirationFeed

@Composable
fun InspirationScreen(viewModel: InspirationViewModel = viewModel()) {
    val state = viewModel.screenState
    when (state) {
        is InspirationScreenState.Error -> InspirationScreenError()
        is InspirationScreenState.Loaded -> InspirationScreenLoaded(state.inspirationList)
        is InspirationScreenState.Loading -> InspirationScreenLoading()
        is InspirationScreenState.Empty -> InspirationScreenEmpty()
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun InspirationScreenLoaded(inspirationList: List<InspirationPictureVO>) {
    Scaffold(
        floatingActionButton = { InspirationActionButtons() }
    ) { _ ->
        InspirationFeed(inspirationList)
    }

}

@Composable
private fun InspirationScreenError() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.bg_sample),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )
        Text(
            text = stringResource(id = R.string.inspiration_error_text),
            modifier = Modifier.padding(vertical = 24.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
        BaseButton(
            text = stringResource(id = R.string.inspiration_error_button)
        ) { }
    }
}

@Composable
private fun InspirationScreenLoading() {

}

@Composable
private fun InspirationScreenEmpty() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.inspiration_empty_text),
            modifier = Modifier.padding(bottom = 52.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
        BaseButton(
            text = stringResource(id = R.string.inspiration_empty_button),
            icon = painterResource(id = R.drawable.ic_plus)
        ) { }
    }
}