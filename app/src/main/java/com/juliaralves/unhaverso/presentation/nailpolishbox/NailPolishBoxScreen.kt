package com.juliaralves.unhaverso.presentation.nailpolishbox

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.juliaralves.unhaverso.R
import com.juliaralves.unhaverso.presentation.core.components.BasePrimaryButton
import com.juliaralves.unhaverso.presentation.nailpolishbox.NailPolishBoxScreenEffect.HideAddNailPolishBottomSheet
import com.juliaralves.unhaverso.presentation.nailpolishbox.NailPolishBoxScreenEffect.HideColorPicker
import com.juliaralves.unhaverso.presentation.nailpolishbox.NailPolishBoxScreenEffect.ShowAddNailPolishBottomSheet
import com.juliaralves.unhaverso.presentation.nailpolishbox.NailPolishBoxScreenEffect.ShowColorPicker
import com.juliaralves.unhaverso.presentation.nailpolishbox.components.NailPolishBoxActionButton
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun NailPolishBoxScreen(viewModel: NailPolishBoxViewModel = koinViewModel()) {
    val state by viewModel.screenState.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current
    var showBottomSheet by remember { mutableStateOf(false) }
    var showColorPicker by remember { mutableStateOf(false) }

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

    if (showColorPicker) {
        ColorPicker(viewModel, state)
    }

    when (state) {
        is NailPolishBoxScreenState.Empty -> {
            NailPolishBoxEmptyScreen(viewModel, state, showBottomSheet)
        }

        is NailPolishBoxScreenState.Filled -> {
            NailPolishBoxFilledScreen(viewModel, state, showBottomSheet)
        }
    }
}

@Composable
private fun ColorPicker(viewModel: NailPolishBoxViewModel, state: NailPolishBoxScreenState) {
    val controller = rememberColorPickerController()

    Dialog(onDismissRequest = { viewModel.onColorPickerDismissed() }) {
        Box(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = stringResource(R.string.add_nail_polish_color_picker_text))

                HsvColorPicker(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .height(300.dp),
                    controller = controller,
                    initialColor = state.addNailPolishBottomSheetState.selectedColor
                )

                AlphaSlider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .height(35.dp),
                    controller = controller,
                    initialColor = state.addNailPolishBottomSheetState.selectedColor
                )

                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .padding(8.dp)
                        .background(
                            color = controller.selectedColor.value,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.onPrimaryContainer,
                            RoundedCornerShape(8.dp)
                        )
                )

                BasePrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    text = stringResource(R.string.add_nail_polish_color_picker_confirm_button)
                ) {
                    viewModel.onColorPicked(controller.selectedColor.value)
                    viewModel.onColorPickerDismissed()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun NailPolishBoxEmptyScreen(
    viewModel: NailPolishBoxViewModel,
    state: NailPolishBoxScreenState,
    showBottomSheet: Boolean
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = { NailPolishBoxActionButton { viewModel.onAddClicked() } }
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

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { viewModel.onDismissBottomSheet() },
                sheetState = sheetState
            ) {
                AddNailPolishBottomSheet(
                    selectedColor = state.addNailPolishBottomSheetState.selectedColor,
                    onEditColorClick = { viewModel.onColorPickerClicked() },
                    tagMap = state.addNailPolishBottomSheetState.tagMap,
                    onTagClick = { viewModel.onTagClicked(it) },
                    onPrimaryButtonClick = { viewModel.addNailPolish() },
                    onSecondaryButtonClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                viewModel.onDismissBottomSheet()
                            }
                        }
                    },
                    nameInputText = state.addNailPolishBottomSheetState.nameInput,
                    onNameInputTextChange = { viewModel.onNameChanged(it) },
                    onClearNameInput = { viewModel.onNameChanged("") },
                    brandInputText = state.addNailPolishBottomSheetState.brandInput,
                    onBrandInputTextChange = { viewModel.onBrandChanged(it) },
                    onClearBrandInput = { viewModel.onBrandChanged("") },
                    showNameInputError = state.addNailPolishBottomSheetState.showNameInputError,
                    showBrandInputError = state.addNailPolishBottomSheetState.showBrandInputError,
                    isButtonEnabled = state.addNailPolishBottomSheetState.isButtonEnabled
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun NailPolishBoxFilledScreen(
    viewModel: NailPolishBoxViewModel,
    state: NailPolishBoxScreenState,
    showBottomSheet: Boolean
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = { NailPolishBoxActionButton { viewModel.addNailPolish() } }
    ) { _ ->

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddNailPolishBottomSheetModal() {


}

@Preview
@Composable
fun NailPolishBoxScreenPreview() {
    NailPolishBoxScreen()
}