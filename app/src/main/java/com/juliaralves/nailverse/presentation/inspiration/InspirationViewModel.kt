package com.juliaralves.nailverse.presentation.inspiration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.Bitmap
import com.juliaralves.nailverse.domain.model.ColorFamilyEnum
import com.juliaralves.nailverse.domain.model.InspirationPictureVO
import com.juliaralves.nailverse.domain.model.NailPolishTagEnum
import com.juliaralves.nailverse.domain.usecase.GetInspirationPicturesUseCase
import com.juliaralves.nailverse.presentation.inspiration.InspirationPictureAction.Download
import com.juliaralves.nailverse.presentation.inspiration.InspirationPictureAction.Share
import com.juliaralves.nailverse.presentation.inspiration.InspirationScreenEffect.HideFilterOptionsBottomSheet
import com.juliaralves.nailverse.presentation.inspiration.InspirationScreenEffect.ShareImage
import com.juliaralves.nailverse.presentation.inspiration.InspirationScreenEffect.ShowFilterOptionsBottomSheet
import com.juliaralves.nailverse.presentation.inspiration.InspirationScreenState.Empty
import com.juliaralves.nailverse.presentation.inspiration.InspirationScreenState.Loaded
import com.juliaralves.nailverse.presentation.inspiration.InspirationScreenState.Loading
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream


class InspirationViewModel(
    private val getInspirationPicturesUseCase: GetInspirationPicturesUseCase
) : ViewModel() {

    private val bottomSheetState = MutableStateFlow(
        FilterOptionsBottomSheetState(
            colorMap = ColorFamilyEnum.entries.associateWith { false },
            tagMap = NailPolishTagEnum.entries.filter { it.isFilterable }.associateWith { false }
        )
    )

    private val pictureList = MutableStateFlow<List<InspirationPictureVO>?>(null)
    private val isError = MutableStateFlow(false)

    private val _screenEffect = Channel<InspirationScreenEffect>(capacity = Channel.BUFFERED)
    val screenEffect: Flow<InspirationScreenEffect>
        get() = _screenEffect.receiveAsFlow()

    val screenState: StateFlow<InspirationScreenState> =
        combine(pictureList, bottomSheetState, isError) { pictures, bottomSheet, error ->
            when {
                error -> InspirationScreenState.Error
                pictures == null -> Loading
                pictures.isEmpty() -> Empty
                else -> {
                    val selectedColors = bottomSheet.colorMap.filterValues { it }.keys
                    val selectedTags = bottomSheet.tagMap.filterValues { it }.keys
                    val filterList = selectedColors.map { it.textRes } + selectedTags.map { it.textRes }

                    Loaded(
                        inspirationList = pictures,
                        filterList = filterList,
                        filterOptionsBottomSheetState = bottomSheet,
                        isFilterEnabled = filterList.isNotEmpty()
                    )
                }
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)

    init {
        viewModelScope.launch {
            bottomSheetState.collect {
                fetchPictures()
            }
        }
    }

    private fun fetchPictures() {
        viewModelScope.launch {
            isError.value = false
            pictureList.value = null

            runCatching {
                val state = bottomSheetState.value
                val selectedColors = state.colorMap.filterValues { it }.keys.toList()
                val selectedTags = state.tagMap.filterValues { it }.keys.toList()

                getInspirationPicturesUseCase.execute(
                    GetInspirationPicturesUseCase.Params(
                        colorFilterList = selectedColors,
                        tagFilterList = selectedTags
                    )
                )
            }.onSuccess { list ->
                pictureList.value = list
            }.onFailure {
                isError.value = true
            }
        }
    }

    fun retry() {
        fetchPictures()
    }

    fun onAction(action: InspirationPictureAction) {
        viewModelScope.launch {
            when (action) {
                is Download -> action.bitmap?.let { saveImage(it) }
                is Share -> _screenEffect.send(ShareImage(action.item.imageUrl))
            }
        }
    }

    private fun saveImage(bitmap: Bitmap) {
        viewModelScope.launch(Dispatchers.IO) {
            val imageName = "Inspiration_${System.currentTimeMillis()}.png"
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, outputStream)
            val imageBytes = outputStream.toByteArray()

            _screenEffect.send(
                InspirationScreenEffect.SaveImage(
                    imageBytes = imageBytes,
                    imageName = imageName
                )
            )
        }
    }

    fun onFilterClicked() {
        viewModelScope.launch {
            _screenEffect.send(ShowFilterOptionsBottomSheet)
        }
    }

    fun onDismissBottomSheet() {
        viewModelScope.launch {
            _screenEffect.send(HideFilterOptionsBottomSheet)
        }
    }

    fun onTagClicked(tag: NailPolishTagEnum) {
        bottomSheetState.update {
            val newMap = it.tagMap.toMutableMap()
            newMap[tag] = newMap[tag]?.not() ?: false
            it.copy(tagMap = newMap)
        }
    }

    fun onColorClicked(color: ColorFamilyEnum) {
        bottomSheetState.update {
            val newMap = it.colorMap.toMutableMap()
            newMap[color] = newMap[color]?.not() ?: false
            it.copy(colorMap = newMap)
        }
    }

    fun onFilterUnselected(filterRes: Int) {
        bottomSheetState.update { state ->
            val color = ColorFamilyEnum.entries.firstOrNull { it.textRes == filterRes }
            val tag = NailPolishTagEnum.entries.firstOrNull { it.textRes == filterRes }

            if (color != null) {
                val newMap = state.colorMap.toMutableMap()
                newMap[color] = false
                state.copy(colorMap = newMap)
            } else if (tag != null) {
                val newMap = state.tagMap.toMutableMap()
                newMap[tag] = false
                state.copy(tagMap = newMap)
            } else {
                state
            }
        }
    }

    fun clearFilter() {
        viewModelScope.launch {
            _screenEffect.send(HideFilterOptionsBottomSheet)
            bottomSheetState.update {
                FilterOptionsBottomSheetState(
                    colorMap = ColorFamilyEnum.entries.associateWith { false }.toMutableMap(),
                    tagMap = NailPolishTagEnum.entries.filter { it.isFilterable }
                        .associateWith { false }.toMutableMap()
                )
            }
        }
    }
}

data class FilterOptionsBottomSheetState(
    val colorMap: Map<ColorFamilyEnum, Boolean>,
    val tagMap: Map<NailPolishTagEnum, Boolean>
)

sealed interface InspirationPictureAction {
    data class Download(val bitmap: Bitmap?) : InspirationPictureAction
    data class Share(val item: InspirationPictureVO) : InspirationPictureAction
}

sealed interface InspirationScreenState {
    data object Loading : InspirationScreenState
    data object Error : InspirationScreenState
    data object Empty : InspirationScreenState
    data class Loaded(
        val inspirationList: List<InspirationPictureVO>,
        val filterList: List<Int>,
        val filterOptionsBottomSheetState: FilterOptionsBottomSheetState,
        val isFilterEnabled: Boolean
    ) : InspirationScreenState
}

sealed interface InspirationScreenEffect {
    data object ShowFilterOptionsBottomSheet : InspirationScreenEffect
    data object HideFilterOptionsBottomSheet : InspirationScreenEffect
    data class ShareImage(val url: String) : InspirationScreenEffect
    data class SaveImage(
        val imageName: String,
        val imageBytes: ByteArray
    ) : InspirationScreenEffect
}