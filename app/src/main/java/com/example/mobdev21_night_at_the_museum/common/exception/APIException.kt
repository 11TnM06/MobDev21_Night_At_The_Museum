package com.example.mobdev21_night_at_the_museum.common.exception

class APIException : BaseException {
    companion object {

        /**
         * code in app
         */
        const val NETWORK_ERROR = 20002
        const val TIME_OUT_ERROR = 20003
        const val SERVER_ERROR_CODE_UNDEFINE = 20004
        const val RESPONSE_BODY_ERROR = 20005
        const val CREATE_INSTANCE_SERVICE_ERROR = 20006
        const val EXPIRE_SESSION_ERROR = 20007
        const val CONVERT_JSON_FROM_RESPONSE_ERROR = 20008
        const val ACCOUNT_INFO_NOT_FOUND_IN_PREFERENCES_ERROR = 20009
        const val REFRESH_TOKEN_NOT_FOUND_IN_PREFERENCES_ERROR = 20010
        const val REPORT_CONTENT_LENGTH_VALIDATE_ERROR = 20011
        const val QUESTION_NO_EXIST = 20012
        const val NO_OLD_EXCHANGE_ADDRESS = 20013

        /**
         * code server
         */
        const val BAD_REQUEST = 4000
        const val AUTHORIZE_ERROR = 4001
        const val ACCOUNT_BLOCKED_ERROR = 4003
        const val ACCOUNT_NOT_FOUND_ERROR = 404

        const val EMAIL_EXPIRED = 8002
        const val EMAIL_FAIL = 8003
        const val EMAIL_VERIFIED = 8004
        const val EMAIL_OVER = 8005
        const val CONTENT_VIOLATE_BAD_WORD = 9000

        fun newInstDevDebugLocal(msg: String) : APIException {
            return APIException(DEV_DEBUG_LOCAL_ERROR, msg)
        }
    }

    constructor(code: Int) : super(code)

    constructor(message: String?) : super(message)

    constructor(code: Int, message: String?) : super(code, message)

    constructor(code: Int, message: String?, throwable: Throwable?) : super(code, message, throwable)

    constructor(code: Int, message: String?, throwable: Throwable?, payload: Any?) : super(code, message, throwable, payload)
}
