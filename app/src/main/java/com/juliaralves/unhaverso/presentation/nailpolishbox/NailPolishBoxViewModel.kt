package com.juliaralves.unhaverso.presentation.nailpolishbox

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juliaralves.unhaverso.domain.model.NailPolishGroupByEnum
import com.juliaralves.unhaverso.domain.model.NailPolishSortByEnum
import com.juliaralves.unhaverso.domain.model.NailPolishTagEnum
import com.juliaralves.unhaverso.domain.model.NailPolishVO
import com.juliaralves.unhaverso.domain.usecase.AddNailPolishUseCase
import com.juliaralves.unhaverso.domain.usecase.GetNailPolishUseCase
import com.juliaralves.unhaverso.domain.usecase.RemoveNailPolishUseCase
import com.juliaralves.unhaverso.presentation.nailpolishbox.NailPolishBoxScreenEffect.HideAddNailPolishBottomSheet
import com.juliaralves.unhaverso.presentation.nailpolishbox.NailPolishBoxScreenEffect.HideColorPicker
import com.juliaralves.unhaverso.presentation.nailpolishbox.NailPolishBoxScreenEffect.ShowAddNailPolishBottomSheet
import com.juliaralves.unhaverso.presentation.nailpolishbox.NailPolishBoxScreenEffect.ShowColorPicker
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)

class NailPolishBoxViewModel(
    private val addNailPolishUseCase: AddNailPolishUseCase,
    private val getNailPolishUseCase: GetNailPolishUseCase,
    private val removeNailPolishUseCase: RemoveNailPolishUseCase
) : ViewModel() {

    private val bottomSheetState = MutableStateFlow(
        AddNailPolishBottomSheetState(
            selectedColor = Color.White,
            nameInput = "",
            brandInput = "",
            tagMap = NailPolishTagEnum.entries.associateWith { false }.toMutableMap(),
            showBrandInputError = false,
            showNameInputError = false,
            isButtonEnabled = false
        )
    )
    private val sortByEnum = MutableStateFlow<NailPolishSortByEnum?>(null)
    private val groupByEnum = MutableStateFlow<NailPolishGroupByEnum?>(null)
    private val searchInput = MutableStateFlow<String?>(null)

    val screenState: StateFlow<NailPolishBoxScreenState> = combine(
        searchInput, sortByEnum, groupByEnum
    ) { search, sort, group ->
        GetNailPolishUseCase.Params(filterText = search, sortBy = sort, groupBy = group)
    }.flatMapLatest { params ->
        getNailPolishUseCase.execute(params)
    }.combine(bottomSheetState) { map, bottomSheet ->
        if (map.isEmpty() || map.all { it.value.isEmpty() }) {
            NailPolishBoxScreenState.Empty(bottomSheet)
        } else {
            NailPolishBoxScreenState.Filled(
                addNailPolishBottomSheetState = bottomSheet,
                nailPolishMap = map,
                isGrouped = map.all { it.key != 0 })
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = NailPolishBoxScreenState.Empty(bottomSheetState.value)
    )

    private val _screenEffect = Channel<NailPolishBoxScreenEffect>(capacity = Channel.BUFFERED)
    val screenEffect: Flow<NailPolishBoxScreenEffect>
        get() = _screenEffect.receiveAsFlow()

    fun onSearchInputChanged(text: String) {
        searchInput.update { text }
    }

    fun onSortSelected(sortByEnum: NailPolishSortByEnum) {
        this.sortByEnum.update { sortByEnum }
    }

    fun onGroupSelected(groupByEnum: NailPolishGroupByEnum) {
        this.groupByEnum.update { groupByEnum }
    }

    fun onEditClicked(nailPolish: NailPolishVO) {
        viewModelScope.launch {
            bottomSheetState.update {
                AddNailPolishBottomSheetState(
                    selectedColor = Color(nailPolish.colorArgb),
                    nameInput = nailPolish.name,
                    brandInput = nailPolish.brand,
                    tagMap = NailPolishTagEnum.entries.associateWith { tag ->
                        nailPolish.tagList.contains(tag)
                    }.toMutableMap(),
                    showBrandInputError = false,
                    showNameInputError = false,
                    isButtonEnabled = false,
                    editId = nailPolish.id
                )
            }
            _screenEffect.send(ShowAddNailPolishBottomSheet)
        }
    }

    fun onDeleteClicked(nailPolish: NailPolishVO) {
        viewModelScope.launch {
            removeNailPolishUseCase.execute(RemoveNailPolishUseCase.Params(nailPolish.id))
        }
    }

    fun onAddClicked() {
        viewModelScope.launch {
            _screenEffect.send(ShowAddNailPolishBottomSheet)
        }
    }

    fun onDismissBottomSheet() {
        viewModelScope.launch {
            _screenEffect.send(HideAddNailPolishBottomSheet)
            clearBottomSheet()
        }
    }

    fun onColorPickerClicked() {
        viewModelScope.launch {
            _screenEffect.send(ShowColorPicker)
        }
    }

    fun onColorPickerDismissed() {
        viewModelScope.launch {
            _screenEffect.send(HideColorPicker)
        }
    }

    fun onColorPicked(color: Color) {
        bottomSheetState.update {
            it.copy(selectedColor = color)
        }
    }

    fun onNameChanged(name: String) {
        bottomSheetState.update {
            val isButtonEnabled = name.isNotBlank() && it.brandInput.isNotBlank()
            it.copy(
                nameInput = name,
                showNameInputError = name.isBlank(),
                isButtonEnabled = isButtonEnabled
            )
        }
    }

    fun onBrandChanged(brand: String) {
        bottomSheetState.update {
            val isButtonEnabled = brand.isNotBlank() && it.nameInput.isNotBlank()
            it.copy(
                brandInput = brand,
                showBrandInputError = brand.isBlank(),
                isButtonEnabled = isButtonEnabled
            )
        }
    }

    fun onTagClicked(tag: NailPolishTagEnum) {
        bottomSheetState.update {
            val newMap = it.tagMap.toMutableMap()
            newMap[tag] = newMap[tag]?.not() ?: false
            it.copy(tagMap = newMap)
        }
    }

    fun addNailPolish() {
        viewModelScope.launch {
            addNailPolishUseCase.execute(
                AddNailPolishUseCase.Params(
                    id = bottomSheetState.value.editId,
                    colorArgb = bottomSheetState.value.selectedColor.toArgb(),
                    name = bottomSheetState.value.nameInput,
                    brand = bottomSheetState.value.brandInput,
                    tagMap = bottomSheetState.value.tagMap,
                )
            )
            _screenEffect.send(HideAddNailPolishBottomSheet)
            clearBottomSheet()
        }
    }

    private fun clearBottomSheet() {
        bottomSheetState.update {
            AddNailPolishBottomSheetState(
                selectedColor = Color.White,
                nameInput = "",
                brandInput = "",
                tagMap = NailPolishTagEnum.entries.associateWith { false }.toMutableMap(),
                showBrandInputError = false,
                showNameInputError = false,
                isButtonEnabled = false
            )
        }
    }
}

sealed class NailPolishBoxScreenState(open val addNailPolishBottomSheetState: AddNailPolishBottomSheetState) {
    data class Empty(
        override val addNailPolishBottomSheetState: AddNailPolishBottomSheetState
    ) : NailPolishBoxScreenState(addNailPolishBottomSheetState)

    data class Filled(
        override val addNailPolishBottomSheetState: AddNailPolishBottomSheetState,
        val nailPolishMap: Map<Int, List<NailPolishVO>>,
        val isGrouped: Boolean
    ) : NailPolishBoxScreenState(addNailPolishBottomSheetState)
}

data class AddNailPolishBottomSheetState(
    val selectedColor: Color,
    val nameInput: String,
    val brandInput: String,
    val showNameInputError: Boolean,
    val showBrandInputError: Boolean,
    val tagMap: Map<NailPolishTagEnum, Boolean>,
    val isButtonEnabled: Boolean,
    val editId: Long? = null
)

sealed interface NailPolishBoxScreenEffect {
    data object ShowAddNailPolishBottomSheet : NailPolishBoxScreenEffect
    data object HideAddNailPolishBottomSheet : NailPolishBoxScreenEffect
    data object ShowColorPicker : NailPolishBoxScreenEffect
    data object HideColorPicker : NailPolishBoxScreenEffect
}