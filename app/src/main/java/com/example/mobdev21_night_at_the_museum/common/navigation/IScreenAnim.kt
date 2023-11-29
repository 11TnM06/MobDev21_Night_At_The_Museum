package com.example.mobdev21_night_at_the_museum.common.navigation

interface IScreenAnim {
    fun enter(): Int
    fun exit(): Int
    fun popEnter(): Int
    fun popExit(): Int
}
