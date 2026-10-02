package com.juliaralves.nailverse.presentation.inspiration

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.juliaralves.nailverse.R
import com.juliaralves.nailverse.domain.model.InspirationPictureVO
import com.juliaralves.nailverse.presentation.core.components.BasePrimaryButton
import com.juliaralves.nailverse.presentation.inspiration.InspirationScreenEffect.HideFilterOptionsBottomSheet
import com.juliaralves.nailverse.presentation.inspiration.InspirationScreenEffect.ShowFilterOptionsBottomSheet
import com.juliaralves.nailverse.presentation.inspiration.components.InspirationActionButtons
import com.juliaralves.nailverse.presentation.inspiration.components.InspirationFeed
import com.juliaralves.nailverse.presentation.inspiration.components.InspirationFilterBottomSheet
import org.koin.androidx.compose.koinViewModel

@Composable
fun InspirationScreen(viewModel: InspirationViewModel = koinViewModel()) {
    val state by viewModel.screenState.collectAsState()
    when (state) {
        is InspirationScreenState.Error -> InspirationScreenError()
        is InspirationScreenState.Loaded -> InspirationScreenLoaded(
            viewModel,
            (state as InspirationScreenState.Loaded).inspirationList,
            (state as InspirationScreenState.Loaded).filterList,
            (state as InspirationScreenState.Loaded).filterOptionsBottomSheetState,
            (state as InspirationScreenState.Loaded).isFilterEnabled
        )

        is InspirationScreenState.Loading -> InspirationScreenLoading()
        is InspirationScreenState.Empty -> InspirationScreenEmpty()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun InspirationScreenLoaded(
    viewModel: InspirationViewModel,
    inspirationList: List<InspirationPictureVO>,
    filterTagsResourceText: List<Int>,
    filterOptionsBottomSheetState: FilterOptionsBottomSheetState,
    isFilterEnabled: Boolean
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(viewModel.screenEffect) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.screenEffect.collect {
                when (it) {
                    ShowFilterOptionsBottomSheet -> showBottomSheet = true
                    HideFilterOptionsBottomSheet -> showBottomSheet = false
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            InspirationActionButtons(
                isFilterSelected = isFilterEnabled,
                onFilterClicked = { viewModel.onFilterClicked() })
        }
    ) { _ ->
        InspirationFeed(viewModel, inspirationList, filterTagsResourceText)

        if (showBottomSheet) {
            FilterOptionsBottomSheetModal(
                state = filterOptionsBottomSheetState,
                viewModel = viewModel,
                sheetState = sheetState
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FilterOptionsBottomSheetModal(
    state: FilterOptionsBottomSheetState,
    viewModel: InspirationViewModel,
    sheetState: SheetState
) {
    ModalBottomSheet(
        onDismissRequest = { viewModel.onDismissBottomSheet() },
        sheetState = sheetState
    ) {
        InspirationFilterBottomSheet(
            tagMap = state.tagMap,
            colorMap = state.colorMap,
            onTagClick = { viewModel.onTagClicked(it) },
            onColorClick = { viewModel.onColorClicked(it) },
            onSecondaryButtonClick = { viewModel.clearFilter() }
        )
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
        BasePrimaryButton(
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
        BasePrimaryButton(
            text = stringResource(id = R.string.inspiration_empty_button),
            icon = painterResource(id = R.drawable.ic_plus)
        ) { }
    }
}