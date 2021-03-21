package mx.com.charlyescaz.web.models

data class GenericResponseWS<T> (
        var success: Boolean = false,

        var data: T? = null,

        var code: Int? = 0,
)