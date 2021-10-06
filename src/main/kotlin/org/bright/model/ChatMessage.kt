package org.bright.model

import java.util.*

data class ChatMessage(val message: String, val sender: User, var time: Date, val channel: Channel)