package com.juliaralves.unhaverso.presentation.nailpolishbox

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.juliaralves.unhaverso.R
import com.juliaralves.unhaverso.presentation.core.components.BasePrimaryButton

@Composable
fun ColorPickerDialog(
    state: NailPolishBoxScreenState,
    onDismissed: () -> Unit,
    onColorPicked: (Color) -> Unit
) {
    val controller = rememberColorPickerController()

    Dialog(onDismissRequest = onDismissed) {
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
                    onColorPicked(controller.selectedColor.value)
                    onDismissed()
                }
            }
        }
    }
}