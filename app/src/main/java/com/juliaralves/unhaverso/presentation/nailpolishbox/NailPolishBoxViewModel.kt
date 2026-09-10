package com.juliaralves.unhaverso.presentation.nailpolishbox

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.juliaralves.unhaverso.domain.model.NailPolishTagEnum
import com.juliaralves.unhaverso.domain.model.NailPolishVO

class NailPolishBoxViewModel: ViewModel() {

    var screenState: NailPolishBoxScreenState by mutableStateOf(NailPolishBoxScreenState.Empty)
        private set

    private var hexColor: String = ""
    private var name: String = ""
    private var brand: String = ""
    private var tagList: MutableList<NailPolishTagEnum> = mutableListOf()

    init {
        //TODO: get items from database
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
        val nailPolish = NailPolishVO(
            hexColor = hexColor,
            name = name,
            brand = brand,
            tagList = tagList
        )
        // TODO: add nail polish to database
    }

}

sealed interface NailPolishBoxScreenState {
    data object Empty : NailPolishBoxScreenState
    data class Filled(val nailPolishList: List<NailPolishVO>) : NailPolishBoxScreenState
}