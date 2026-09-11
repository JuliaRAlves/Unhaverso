package com.juliaralves.unhaverso.data.mapper

import com.juliaralves.unhaverso.data.local.room.entity.NailPolish
import com.juliaralves.unhaverso.data.model.NailPolishDto
import com.juliaralves.unhaverso.domain.model.NailPolishTagEnum
import com.juliaralves.unhaverso.domain.model.NailPolishVO

class NailPolishDataMapper {
    fun mapNailPolishDtoToEntity(dto: NailPolishDto): NailPolish {
        return NailPolish(
            colorArgb = dto.colorArgb,
            name = dto.name,
            brand = dto.brand,
            tagList = formatTagListAsString(dto.tagList),
            createdAt = dto.createdAt
        )
    }

    fun mapNailPolishDtoToVo(dto: NailPolishDto): NailPolishVO {
        return NailPolishVO(
            colorArgb = dto.colorArgb,
            name = dto.name,
            brand = dto.brand,
            tagList = dto.tagList,
            createdAt = dto.createdAt
        )
    }

    fun mapNailPolishVOToDto(vo: NailPolishVO): NailPolishDto {
        return NailPolishDto(
            colorArgb = vo.colorArgb,
            name = vo.name,
            brand = vo.brand,
            tagList = vo.tagList,
            createdAt = vo.createdAt
        )
    }

    fun mapNailPolishToDto(entity: NailPolish): NailPolishDto {
        return NailPolishDto(
            colorArgb = entity.colorArgb,
            name = entity.name,
            brand = entity.brand,
            tagList = formatStringAsTagList(entity.tagList),
            createdAt = entity.createdAt
        )
    }

    private fun formatTagListAsString(original: List<NailPolishTagEnum>): String {
        return original.joinToString(",")
    }

    private fun formatStringAsTagList(original: String): List<NailPolishTagEnum> {
        val originalSplit = original.split(",")
        val tagList = originalSplit.mapNotNull { tagText ->
            NailPolishTagEnum.entries.firstOrNull { it.name == tagText }
        }

        return if (tagList.size == originalSplit.size) {
            tagList
        } else {
            throw NoSuchElementException("Tag list contains invalid element.")
        }
    }
}