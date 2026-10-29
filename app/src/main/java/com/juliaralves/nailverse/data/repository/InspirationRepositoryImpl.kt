package com.juliaralves.nailverse.data.repository

import com.juliaralves.nailverse.data.local.datasource.InspirationPictureLocalDataSource
import com.juliaralves.nailverse.data.mapper.InspirationPictureDataMapper
import com.juliaralves.nailverse.domain.model.InspirationPictureVO
import com.juliaralves.nailverse.domain.repository.InspirationRepository

class InspirationRepositoryImpl(
    private val localDataSource: InspirationPictureLocalDataSource,
    private val mapper: InspirationPictureDataMapper
) : InspirationRepository {
    override suspend fun getInspirationPicturesFilteredBy(
        query: String,
        isFiltered: Boolean
    ): List<InspirationPictureVO> {
        val inspirationPictureLocal = localDataSource.getInspirationPictureList()
        val shouldGetNewList =
            inspirationPictureLocal.isEmpty() || inspirationPictureLocal[0].query != query
        val shouldSaveNewList = shouldGetNewList && isFiltered.not()

        if (shouldGetNewList) {
            // TODO: add remote call
            val pictureList = listOf(
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
            ).mapIndexed { index, item -> InspirationPictureVO(index.toLong(), item) }

            if (shouldSaveNewList) {
                localDataSource.removeAllInspirationPicture()
                pictureList.forEach {
                    localDataSource.addInspirationPicture(
                        it.id,
                        it.imageUrl,
                        query
                    )
                }
            }

            return pictureList
        } else {
            return inspirationPictureLocal.map(mapper::mapInspirationPictureDtoToVo)
        }
    }

    override suspend fun clearInspirationFeed() {
        localDataSource.removeAllInspirationPicture()
    }
}