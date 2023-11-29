package com.example.mobdev21_night_at_the_museum.data.convert

import com.example.mobdev21_night_at_the_museum.common.Mapper
import com.example.mobdev21_night_at_the_museum.data.model.CollectionDTO
import com.example.mobdev21_night_at_the_museum.data.model.CollectionMainDTO
import com.example.mobdev21_night_at_the_museum.domain.model.CollectionMain
import com.example.mobdev21_night_at_the_museum.domain.model.MCollection

class CollectionMainDTOConvertToCollectionMain : IConverter<CollectionMainDTO, CollectionMain> {

    private val mapper = Mapper(CollectionMainDTO::class, CollectionMain::class).apply {
        addNameMapper(CollectionMain::collectionsList) {
            return@addNameMapper CollectionMainDTO::collectionsList
        }
    }

    private val collectionDTOConvertToMCollectionMapper = Mapper(CollectionDTO::class, MCollection::class).apply {
        addNameMapper(MCollection::key) {
            return@addNameMapper CollectionDTO::id
        }
        addNameMapper(MCollection::name) {
            return@addNameMapper CollectionDTO::name
        }
        addNameMapper(MCollection::thumbnail) {
            return@addNameMapper CollectionDTO::thumbnail
        }
    }

    override fun convert(source: CollectionMainDTO): CollectionMain {
        mapper.register(
            CollectionMainDTO::collectionsList,
            Mapper.listMapper(collectionDTOConvertToMCollectionMapper)
        )
        return mapper.invoke(source)
    }
}
