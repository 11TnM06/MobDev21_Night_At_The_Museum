package com.example.mobdev21_night_at_the_museum.common.usecase

class FlowResult<DATA> private constructor() : BaseUiState() {
    var data: DATA? = null
        private set

    companion object {
        @JvmStatic
        fun <T> newInstance(): FlowResult<T> {
            return FlowResult()
        }
    }

    fun initial() = apply {
        setUiState(UI_STATE.INITIAL, "initial !!")
        return this
    }

    fun loading(message: String? = null) = apply {
        setUiState(UI_STATE.LOADING, message ?: "loading !!")
        return this
    }

    fun success(data: DATA?) = apply {
        setUiState(UI_STATE.SUCCESS)
        this.data = data
        return this
    }

    fun failure(throwable: Throwable, data: DATA? = null) = apply {
        setUiState(UI_STATE.FAILURE, throwable.message ?: "error !!")
        this.throwable = throwable
        this.data = data
        return this
    }

    fun reset() = apply {
        initial()
        this.data = null
    }
}
