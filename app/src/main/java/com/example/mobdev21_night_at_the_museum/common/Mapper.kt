package com.example.mobdev21_night_at_the_museum.common

import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

typealias Converter<S, D> = (S) -> D
typealias PropertyConverter<S, D> = (S, D) -> D
typealias NameMapper<SRC_PROPERTY> = () -> SRC_PROPERTY

class Mapper<S : Any, D : Any>(
    private val srcClass: KClass<S>,
    private val destClass: KClass<D>
) : Converter<S, D>, PropertyConverter<S, D> {

    companion object {
        fun <I : Any, O : Any> setMapper(mapper: Mapper<I, O>) = object :
            Converter<Set<I>, Set<O>> {
            override fun invoke(data: Set<I>): Set<O> = data.map(mapper).toSet()
        }

        fun <I : Any, O : Any> listMapper(mapper: Mapper<I, O>) = object :
            Converter<List<I>, List<O>> {
            override fun invoke(data: List<I>): List<O> = data.map(mapper).toMutableList()
        }
    }


    var afterConvertCallback: ((S, D) -> Unit)? = null
    val propertyMappers = mutableMapOf<String, PropertyConverter<Any, Any>>()
    val fieldMappers = mutableMapOf<String, Converter<Any?, Any?>>()
    private val propertyNameMapper = mutableMapOf<String, NameMapper<KProperty<*>>>()
    private val srcPropertyNames by lazy {
        srcClass.memberProperties.associateBy {
            it.name
        }
    }

    override fun invoke(src: S): D {
        val constructor = destClass.primaryConstructor!!
        val args = arrayListOf<Any?>()
        for (param in constructor.parameters) {
            args.add(null)
        }
        val destInstance = constructor.call(*args.toArray())
        destClass.memberProperties.forEach { prop ->
            if (prop is KMutableProperty<*>) {
                val srcVal = copyValue(prop, src)
                if (srcVal != null) {
                    prop.setter.call(destInstance, srcVal)
                }
            }
        }
        afterConvertCallback?.invoke(src, destInstance)
        return destInstance
    }

    override fun invoke(src: S, dest: D): D {
        destClass.memberProperties.forEach { prop ->
            if (prop is KMutableProperty<*>) {
                val srcVal = copyValue(prop, src)
                if (srcVal != null) {
                    prop.setter.call(dest, srcVal)
                }
            }
        }
        return dest
    }

    fun addNameMapper(destProperty: KProperty<*>, nameMapper: NameMapper<KProperty<*>>) {
        propertyNameMapper[destProperty.name] = nameMapper
    }

    inline fun <reified I : Any?, reified O : Any?> register(
        destProperty: KProperty<*>,
        crossinline converter: Converter<I, O>
    ) {
        fieldMappers[destProperty.name] = object : Converter<Any?, Any?> {
            override fun invoke(p1: Any?): Any? {
                return converter.invoke(p1 as I)
            }
        }
    }

    /*inline fun <reified I : Any, reified O : Any> register(destProperty: KProperty<*>, crossinline propertyConverter: PropertyConverter<I, O>) {
        propertyMappers[destProperty.name] = object : PropertyConverter<Any, Any> {
            override fun invoke(p1: Any, p2: Any): Any {
                return propertyConverter.invoke(p1 as I, p2 as O)
            }
        }
    }*/

    private fun copyValue(parameter: KProperty<*>, src: S): Any? {
        var property = srcPropertyNames[parameter.name]
        if (property == null) {
            val mappedName = propertyNameMapper[parameter.name]?.invoke()
            mappedName?.let {
                property = srcPropertyNames[it.name]
            }
        }

        if (property != null) {
            val value = property?.get(src) ?: return null
            return if (fieldMappers[parameter.name] != null) {
                fieldMappers[parameter.name]?.invoke(value)
            } else {
                value
            }
        } else {
            return fieldMappers[parameter.name]?.invoke(src)
        }
    }

    /*private fun copyPropertyValue(parameter: KProperty<*>, src: S, dest: D) {
        var property = srcPropertyNames[parameter.name]
        if (property == null) {
            val mappedName = propertyNameMapper[parameter.name]?.invoke()
            mappedName?.let {
                property = srcPropertyNames[it.name]
            }
        }

        val mapper = propertyMappers[parameter.name]
        if (property != null) {
            val value = property?.get(src)
            if (value != null) {
                if (mapper != null) {

                }
                val propertyMappers[parameter.name]?.invoke(value, dest)
            }
        } else {
            propertyMappers[parameter.name]?.invoke(src, dest)
        }
    }*/
}
