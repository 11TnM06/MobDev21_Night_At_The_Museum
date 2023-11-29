package com.example.mobdev21_night_at_the_museum.common.exception

class DBException : BaseException {
    companion object {

    }

    constructor() : super()

    constructor(code: Int) : super(code)

    constructor(message: String?) : super(message)

    constructor(code: Int, message: String?) : super(code, message)

    constructor(code: Int, message: String?, throwable: Throwable?) : super(code, message, throwable)

    constructor(code: Int, message: String?, throwable: Throwable?, payload: Any?) : super(code, message, throwable, payload)
}
