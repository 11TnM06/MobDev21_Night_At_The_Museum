package com.example.mobdev21_night_at_the_museum.data.convert

interface IConverter<S, D> {
    fun convert(source: S): D
}
