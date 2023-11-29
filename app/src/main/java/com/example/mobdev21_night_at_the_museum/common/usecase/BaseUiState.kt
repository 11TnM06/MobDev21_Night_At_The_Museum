package com.example.mobdev21_night_at_the_museum.common.usecase

typealias UI_STATE = UiState.UI_STATE

abstract class BaseUiState {
    private var uiState: UiState = UiState()
    var throwable: Throwable? = null

    fun setUiState(state: UiState.UI_STATE, message: String = "") {
        this.uiState = UiState().apply {
            this.state = state
            this.message = message
        }
    }

    fun getUiState(): UI_STATE {
        return this.uiState.state
    }

    fun getMessage(): String {
        return this.uiState.message
    }
}

class UiState {
    var state = UI_STATE.INITIAL
    var message: String = ""

    enum class UI_STATE {
        INITIAL, LOADING, SUCCESS, FAILURE
    }
}
