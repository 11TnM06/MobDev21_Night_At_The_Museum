package com.example.mobdev21_night_at_the_museum.data.convert

import com.example.mobdev21_night_at_the_museum.common.Mapper
import com.example.mobdev21_night_at_the_museum.data.model.ModelDTO
import com.example.mobdev21_night_at_the_museum.data.model.ModelMainDTO
import com.example.mobdev21_night_at_the_museum.domain.model.Model3d
import com.example.mobdev21_night_at_the_museum.domain.model.ModelMain

class ModelMainDTOConvertToModelMain : IConverter<ModelMainDTO, ModelMain> {

    private val mapper = Mapper(ModelMainDTO::class, ModelMain::class).apply {
        addNameMapper(ModelMainDTO::modelsList) {
            return@addNameMapper ModelMain::modelsList
        }
    }

    private val modelDTOConvertToModel3d = Mapper(ModelDTO::class, Model3d::class).apply {
        addNameMapper(Model3d::id) {
            return@addNameMapper ModelDTO::id
        }
        addNameMapper(Model3d::name) {
            return@addNameMapper ModelDTO::name
        }
        addNameMapper(Model3d::thumbnail) {
            return@addNameMapper ModelDTO::thumbnail
        }
        addNameMapper(Model3d::description) {
            return@addNameMapper ModelDTO::description
        }
        addNameMapper(Model3d::path) {
            return@addNameMapper ModelDTO::path
        }
    }

    override fun convert(source: ModelMainDTO): ModelMain {
        mapper.register(
            ModelMainDTO::modelsList,
            Mapper.listMapper(modelDTOConvertToModel3d)
        )
        return mapper.invoke(source)
    }
}
