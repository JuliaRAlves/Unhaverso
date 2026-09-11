package com.juliaralves.unhaverso.presentation.nailpolishbox

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juliaralves.unhaverso.domain.model.NailPolishTagEnum
import com.juliaralves.unhaverso.domain.model.NailPolishVO
import com.juliaralves.unhaverso.domain.usecase.AddNailPolishUseCase
import com.juliaralves.unhaverso.domain.usecase.GetNailPolishUseCase
import com.juliaralves.unhaverso.presentation.nailpolishbox.NailPolishBoxScreenEffect.HideAddNailPolishBottomSheet
import com.juliaralves.unhaverso.presentation.nailpolishbox.NailPolishBoxScreenEffect.ShowAddNailPolishBottomSheet
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

class NailPolishBoxViewModel(
    private val addNailPolishUseCase: AddNailPolishUseCase,
    private val getNailPolishUseCase: GetNailPolishUseCase
) : ViewModel() {

    private var bottomSheetState = MutableStateFlow(
        AddNailPolishBottomSheetState(
            selectedColor = Color(0xFF9CCC65),
            nameInput = "",
            brandInput = "",
            tagMap = NailPolishTagEnum.entries.associateWith { false }.toMutableMap()
        )
    )

    private val nailPolishMap = MutableStateFlow<Map<String, List<NailPolishVO>>>(emptyMap())

    val screenState: StateFlow<NailPolishBoxScreenState> = combine(
        nailPolishMap,
        bottomSheetState
    ) { map, bottomSheet ->
        if (map.isEmpty()) {
            NailPolishBoxScreenState.Empty(bottomSheet)
        } else {
            NailPolishBoxScreenState.Filled(bottomSheet, map)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = NailPolishBoxScreenState.Empty(bottomSheetState.value)
    )

    private val _screenEffect = Channel<NailPolishBoxScreenEffect>(capacity = Channel.BUFFERED)
    val screenEffect: Flow<NailPolishBoxScreenEffect>
        get() = _screenEffect.receiveAsFlow()

    init {
        viewModelScope.launch {
            nailPolishMap.update {
                getNailPolishUseCase.execute(GetNailPolishUseCase.Params())
            }
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
        }
    }

    fun onColorPicked(hexColor: Long) {
        bottomSheetState.update {
            it.copy(selectedColor = Color(hexColor))
        }
    }

    fun onNameChanged(name: String) {
        bottomSheetState.update {
            it.copy(nameInput = name)
        }
    }

    fun onBrandChanged(brand: String) {
        bottomSheetState.update {
            it.copy(brandInput = brand)
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
                    hexColor = bottomSheetState.value.selectedColor.value.toLong(),
                    name = bottomSheetState.value.nameInput,
                    brand = bottomSheetState.value.brandInput,
                    tagMap = bottomSheetState.value.tagMap,
                )
            )
            _screenEffect.send(HideAddNailPolishBottomSheet)
        }
    }
}

sealed class NailPolishBoxScreenState(open val addNailPolishBottomSheetState: AddNailPolishBottomSheetState) {
    data class Empty(
        override val addNailPolishBottomSheetState: AddNailPolishBottomSheetState
    ) : NailPolishBoxScreenState(addNailPolishBottomSheetState)

    data class Filled(
        override val addNailPolishBottomSheetState: AddNailPolishBottomSheetState,
        val nailPolishMap: Map<String, List<NailPolishVO>>
    ) : NailPolishBoxScreenState(addNailPolishBottomSheetState)
}

data class AddNailPolishBottomSheetState(
    val selectedColor: Color,
    val nameInput: String,
    val brandInput: String,
    val tagMap: Map<NailPolishTagEnum, Boolean>
)

sealed interface NailPolishBoxScreenEffect {
    data object ShowAddNailPolishBottomSheet : NailPolishBoxScreenEffect
    data object HideAddNailPolishBottomSheet : NailPolishBoxScreenEffect
}