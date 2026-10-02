package com.juliaralves.nailverse.presentation.inspiration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juliaralves.nailverse.domain.model.ColorFamilyEnum
import com.juliaralves.nailverse.domain.model.InspirationPictureVO
import com.juliaralves.nailverse.domain.model.NailPolishTagEnum
import com.juliaralves.nailverse.presentation.inspiration.InspirationScreenEffect.HideFilterOptionsBottomSheet
import com.juliaralves.nailverse.presentation.inspiration.InspirationScreenEffect.ShowFilterOptionsBottomSheet
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

class InspirationViewModel : ViewModel() {

    private val bottomSheetState = MutableStateFlow(
        FilterOptionsBottomSheetState(
            colorMap = ColorFamilyEnum.entries.associateWith { false }.toMutableMap(),
            tagMap = NailPolishTagEnum.entries.filter { it.isFilterable }.associateWith { false }
                .toMutableMap()
        )
    )

    private val isFilterEnabled = MutableStateFlow(false)

    private val _downloadLiveData = MutableLiveData<String>()
    val downloadLiveData: LiveData<String> = _downloadLiveData

    private val _shareLiveData = MutableLiveData<String>()
    val shareLiveData: LiveData<String> = _shareLiveData

    val screenState: StateFlow<InspirationScreenState> =
        combine(bottomSheetState, isFilterEnabled) { bottomSheet, isFilterEnabled ->
            val filterList =
                bottomSheet.colorMap.mapNotNull { if (it.value) it.key.textRes else null }
                    .plus(bottomSheet.tagMap.mapNotNull { if (it.value) it.key.textRes else null })
            InspirationScreenState.Loaded(pictureList, filterList, bottomSheet, isFilterEnabled)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = InspirationScreenState.Loading
        )

    private val _screenEffect = Channel<InspirationScreenEffect>(capacity = Channel.BUFFERED)
    val screenEffect: Flow<InspirationScreenEffect>
        get() = _screenEffect.receiveAsFlow()

    // temp
    private val pictureList = listOf(
        "https://m.media-amazon.com/images/I/81hwPfRNT7L._SL1500_.jpg",
        "https://www.byrdie.com/thmb/buMlOI4jQxZI2NSLrezikkpuiME=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/neutralanails_recirc-2d3a9cda11dd4d94be17a5fafff54880.jpg",
        "https://media.glamourmagazine.co.uk/photos/66d1b1ad94d5017f0a75866d/1:1/w_1081,h_1081,c_limit/BOW%20NAILS%20300824%20MAIN.jpg",
        "https://negociosdebeleza.beautyfair.com.br/wp-content/uploads/2023/09/tendencias-nail-art-2024-3-1.png",
        "https://www.refinery29.com/images/11798078.jpg",
        "https://www.instyle.com/thmb/Q0PMTVFmvHLDoyr8f0aOQhe_H9M=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/366441869_296892119600354_6448618165582235578_n-9de5af90aa044dc09494925896fb3832.jpg",
        "https://static.stealthelook.com.br/wp-content/uploads/2022/10/tendencias-de-nail-art-abstrato-verde-20221018201922.jpg",
        "https://www.coloramaesmaltes.com.br/-/media/Project/Loreal/Brand-Sites/Essie/MASTER/DMI/articles/tips_trends/2023/jelly-nails/jelly-nails.jpg",
        "https://belezamoderna.com.br/wp-content/uploads/2023/04/glass-nail-2-e1682517372696.webp",
        "https://harpersbazaar.uol.com.br/wp-content/uploads/2023/05/nail-art-cromada-@carolina-683x1024.jpg"
    ).mapIndexed { index, item -> InspirationPictureVO(index, item) }

    fun onDownloadPictureClicked(picture: InspirationPictureVO) {
        _downloadLiveData.postValue(picture.imageUrl)
    }

    fun onSharePictureClicked(picture: InspirationPictureVO) {
        _shareLiveData.postValue(picture.imageUrl)
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
        bottomSheetState.update {
            val color = ColorFamilyEnum.entries.firstOrNull { it.textRes == filterRes }
            val tag = NailPolishTagEnum.entries.firstOrNull { it.textRes == filterRes }

            if (color != null) {
                val newMap = it.colorMap.toMutableMap()
                newMap[color] = false
                it.copy(colorMap = newMap)
            } else if (tag != null) {
                val newMap = it.tagMap.toMutableMap()
                newMap[tag] = false
                it.copy(tagMap = newMap)
            } else {
                it
            }
        }
    }

    fun clearFilter() {
        viewModelScope.launch {
            _screenEffect.send(HideFilterOptionsBottomSheet)
            isFilterEnabled.update { false }
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
}