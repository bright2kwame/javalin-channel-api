package org.bright.util

import java.util.*

class ResponseHandler {
    companion object {
        fun formatResponse(data: Any, status: ApiStatus): HashMap<String, Any> {
            val welcomeMessage = HashMap<String, Any>()
            welcomeMessage.put("status", status.name)
            welcomeMessage.put("result", data)
            return welcomeMessage
        }
    }
}