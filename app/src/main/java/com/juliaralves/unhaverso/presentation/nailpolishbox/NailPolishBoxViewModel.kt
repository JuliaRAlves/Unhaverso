package com.juliaralves.unhaverso.presentation.nailpolishbox

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juliaralves.unhaverso.domain.model.NailPolishTagEnum
import com.juliaralves.unhaverso.domain.model.NailPolishVO
import com.juliaralves.unhaverso.domain.usecase.AddNailPolishUseCase
import com.juliaralves.unhaverso.domain.usecase.GetNailPolishUseCase
import kotlinx.coroutines.launch

class NailPolishBoxViewModel(
    private val addNailPolishUseCase: AddNailPolishUseCase,
    private val getNailPolishUseCase: GetNailPolishUseCase
) : ViewModel() {

    var screenState: NailPolishBoxScreenState by mutableStateOf(NailPolishBoxScreenState.Empty)
        private set

    private var hexColor: String = ""
    private var name: String = ""
    private var brand: String = ""
    private var tagList: MutableList<NailPolishTagEnum> = mutableListOf()

    init {
        viewModelScope.launch {
            getNailPolishUseCase.execute(GetNailPolishUseCase.Params())
            // TODO: add logic to show list
        }
    }

    fun onColorPicked(hexColor: String) {
        this.hexColor = hexColor
    }

    fun onNameChanged(name: String) {
        this.name = name
    }

    fun onBrandChanged(brand: String) {
        this.brand = brand
    }

    fun onTagSelected(tag: NailPolishTagEnum) {
        tagList.add(tag)
    }

    fun onTagDeselected(tag: NailPolishTagEnum) {
        tagList.remove(tag)
    }

    fun addNailPolish() {
        viewModelScope.launch {
            addNailPolishUseCase.execute(
                AddNailPolishUseCase.Params(
                    hexColor = hexColor,
                    name = name,
                    brand = brand,
                    tagList = tagList
                )
            )
        }
    }

}

sealed interface NailPolishBoxScreenState {
    data object Empty : NailPolishBoxScreenState
    data class Filled(val nailPolishList: List<NailPolishVO>) : NailPolishBoxScreenState
}