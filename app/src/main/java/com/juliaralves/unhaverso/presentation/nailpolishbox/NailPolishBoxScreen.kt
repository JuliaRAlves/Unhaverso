package com.juliaralves.unhaverso.presentation.nailpolishbox

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.juliaralves.unhaverso.R
import com.juliaralves.unhaverso.domain.model.NailPolishVO
import com.juliaralves.unhaverso.presentation.nailpolishbox.NailPolishBoxScreenEffect.HideAddNailPolishBottomSheet
import com.juliaralves.unhaverso.presentation.nailpolishbox.NailPolishBoxScreenEffect.HideColorPicker
import com.juliaralves.unhaverso.presentation.nailpolishbox.NailPolishBoxScreenEffect.ShowAddNailPolishBottomSheet
import com.juliaralves.unhaverso.presentation.nailpolishbox.NailPolishBoxScreenEffect.ShowColorPicker
import com.juliaralves.unhaverso.presentation.nailpolishbox.components.AddNailPolishBottomSheet
import com.juliaralves.unhaverso.presentation.nailpolishbox.components.ColorPickerDialog
import com.juliaralves.unhaverso.presentation.nailpolishbox.components.NailPolishBoxActionButton
import com.juliaralves.unhaverso.presentation.nailpolishbox.components.NailPolishBoxCard
import com.juliaralves.unhaverso.presentation.nailpolishbox.components.NailPolishBoxOptionsBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NailPolishBoxScreen(viewModel: NailPolishBoxViewModel = koinViewModel()) {
    val state by viewModel.screenState.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current
    var showBottomSheet by remember { mutableStateOf(false) }
    var showColorPicker by remember { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(viewModel.screenEffect) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.screenEffect.collect {
                when (it) {
                    ShowAddNailPolishBottomSheet -> showBottomSheet = true
                    HideAddNailPolishBottomSheet -> showBottomSheet = false
                    ShowColorPicker -> showColorPicker = true
                    HideColorPicker -> showColorPicker = false
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = { NailPolishBoxActionButton { viewModel.onAddClicked() } }
    ) { _ ->
        when (state) {
            is NailPolishBoxScreenState.Empty -> NailPolishBoxEmptyScreen()
            is NailPolishBoxScreenState.Filled -> {
                NailPolishBoxFilledScreen(
                    viewModel = viewModel,
                    nailPolishMap = (state as NailPolishBoxScreenState.Filled).nailPolishMap,
                    isGrouped = (state as NailPolishBoxScreenState.Filled).isGrouped
                )
            }
        }

        if (showBottomSheet) {
            AddNailPolishBottomSheetModal(
                state = state.addNailPolishBottomSheetState,
                viewModel = viewModel,
                sheetState = sheetState,
                scope = scope
            )
        }

    }

    if (showColorPicker) {
        ColorPickerDialog(
            state = state,
            onDismissed = { viewModel.onColorPickerDismissed() },
            onColorPicked = { viewModel.onColorPicked(it) })
    }
}

@Composable
private fun NailPolishBoxEmptyScreen() {
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

@Composable
private fun NailPolishBoxFilledScreen(
    viewModel: NailPolishBoxViewModel,
    nailPolishMap: Map<Int, List<NailPolishVO>>,
    isGrouped: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        NailPolishBoxOptionsBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            searchInput = "",
            onSearchInputChange = { viewModel.onSearchInputChanged(it) },
            onGroupClick = { viewModel.onGroupSelected(it) },
            onSortClick = { viewModel.onSortSelected(it) }
        )

        val columnCount = if (isGrouped) 2 else 1

        LazyVerticalGrid(
            columns = GridCells.Fixed(columnCount),
            contentPadding = PaddingValues(4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            nailPolishMap.forEach { (groupTextRes, nailPolishList) ->
                if (isGrouped) {
                    stickyHeader {
                        Text(
                            modifier = Modifier.padding(vertical = 16.dp),
                            text = stringResource(groupTextRes),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
                items(nailPolishList) { nailPolish ->
                    NailPolishBoxCard(
                        modifier = Modifier.weight(1f),
                        nailPolish = nailPolish,
                        isExpanded = isGrouped.not(),
                        onEditClick = { viewModel.onEditClicked(it) },
                        onDeleteClick = { viewModel.onDeleteClicked(it) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddNailPolishBottomSheetModal(
    state: AddNailPolishBottomSheetState,
    viewModel: NailPolishBoxViewModel,
    sheetState: SheetState,
    scope: CoroutineScope
) {
    ModalBottomSheet(
        onDismissRequest = { viewModel.onDismissBottomSheet() },
        sheetState = sheetState
    ) {
        AddNailPolishBottomSheet(
            selectedColor = state.selectedColor,
            onEditColorClick = { viewModel.onColorPickerClicked() },
            tagMap = state.tagMap,
            onTagClick = { viewModel.onTagClicked(it) },
            onPrimaryButtonClick = { viewModel.addNailPolish() },
            onSecondaryButtonClick = {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        viewModel.onDismissBottomSheet()
                    }
                }
            },
            nameInputText = state.nameInput,
            onNameInputTextChange = { viewModel.onNameChanged(it) },
            onClearNameInput = { viewModel.onNameChanged("") },
            brandInputText = state.brandInput,
            onBrandInputTextChange = { viewModel.onBrandChanged(it) },
            onClearBrandInput = { viewModel.onBrandChanged("") },
            showNameInputError = state.showNameInputError,
            showBrandInputError = state.showBrandInputError,
            isButtonEnabled = state.isButtonEnabled
        )
    }
}

@Preview
@Composable
fun NailPolishBoxScreenPreview() {
    NailPolishBoxScreen()
}