package com.juliaralves.nailverse.presentation.inspiration

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment.DIRECTORY_PICTURES
import android.provider.MediaStore
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.juliaralves.nailverse.R
import com.juliaralves.nailverse.domain.model.InspirationPictureVO
import com.juliaralves.nailverse.presentation.core.components.BasePrimaryButton
import com.juliaralves.nailverse.presentation.inspiration.InspirationScreenEffect.HideFilterOptionsBottomSheet
import com.juliaralves.nailverse.presentation.inspiration.InspirationScreenEffect.SaveImage
import com.juliaralves.nailverse.presentation.inspiration.InspirationScreenEffect.ShareImage
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
    val context = LocalContext.current

    LaunchedEffect(viewModel.screenEffect) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.screenEffect.collect {
                when (it) {
                    is ShowFilterOptionsBottomSheet -> showBottomSheet = true
                    is HideFilterOptionsBottomSheet -> showBottomSheet = false
                    is ShareImage -> shareImage(it.url, context)
                    is SaveImage -> saveImageToGallery(context, it.imageBytes, it.imageName)
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

private fun shareImage(url: String, context: Context) {
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT, url.toUri())
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)

    context.startActivity(shareIntent)
}

private fun saveImageToGallery(context: Context, imageBytes: ByteArray, imageName: String) {
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, imageName)
        put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            put(
                MediaStore.MediaColumns.RELATIVE_PATH,
                "$DIRECTORY_PICTURES/Inspiration"
            )
            put(MediaStore.MediaColumns.IS_PENDING, 1)
        }
    }

    val resolver = context.contentResolver
    val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

    if (uri != null) {
        resolver.openOutputStream(uri)?.use { outputStream ->
            outputStream.write(imageBytes)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentValues.clear()
            contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
            resolver.update(uri, contentValues, null, null)
        }

        Toast.makeText(context, R.string.inspiration_save_image_success_toast, Toast.LENGTH_SHORT)
            .show()
    } else {
        Toast.makeText(context, R.string.inspiration_save_image_error_toast, Toast.LENGTH_SHORT)
            .show()
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