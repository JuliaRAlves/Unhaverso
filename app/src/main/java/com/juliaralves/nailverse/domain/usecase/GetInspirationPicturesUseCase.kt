package com.juliaralves.nailverse.domain.usecase

import com.juliaralves.nailverse.domain.model.ColorFamilyEnum
import com.juliaralves.nailverse.domain.model.InspirationPictureVO
import com.juliaralves.nailverse.domain.model.NailPolishTagEnum
import com.juliaralves.nailverse.domain.repository.InspirationRepository
import com.juliaralves.nailverse.domain.repository.NailPolishRepository
import com.juliaralves.nailverse.domain.utils.getColorFamilyByArgb
import kotlinx.coroutines.flow.firstOrNull

class GetInspirationPicturesUseCase(
    private val nailPolishRepository: NailPolishRepository,
    private val inspirationRepository: InspirationRepository
) {
    suspend fun execute(params: Params): List<InspirationPictureVO> {
        val nailPolishBox =
            nailPolishRepository.getNailPolishFilteredBy().firstOrNull() ?: return emptyList()
        val query = params.colorFilterList.asSequence().map { it.name.lowercase() }
            .plus(params.tagFilterList.map { it.name.lowercase() })
            .plus(nailPolishBox.map { getColorFamilyByArgb(it.colorArgb).name.lowercase() })
            .plus(nailPolishBox.flatMap { polish -> polish.tagList.map { tag -> tag.name.lowercase() } })
            .joinToString()
        val isFiltered = params.colorFilterList.isNotEmpty() || params.tagFilterList.isNotEmpty()

        return inspirationRepository.getInspirationPicturesFilteredBy(query, isFiltered)
    }

    data class Params(
        val colorFilterList: List<ColorFamilyEnum>,
        val tagFilterList: List<NailPolishTagEnum>
    )
}