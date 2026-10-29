package com.juliaralves.nailverse.data.mapper

import com.juliaralves.nailverse.data.local.room.entity.InspirationPicture
import com.juliaralves.nailverse.data.model.InspirationPictureDto
import com.juliaralves.nailverse.domain.model.InspirationPictureVO

class InspirationPictureDataMapper {
    fun mapInspirationPictureDtoToVo(dto: InspirationPictureDto): InspirationPictureVO {
        return InspirationPictureVO(
            id = dto.id,
            imageUrl = dto.imageUrl
        )
    }

    fun mapInspirationPictureToDto(entity: InspirationPicture): InspirationPictureDto {
        return InspirationPictureDto(
            id = entity.uid,
            imageUrl = entity.imageUrl,
            query = entity.query
        )
    }
}