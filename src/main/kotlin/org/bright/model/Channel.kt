package org.bright.model

import java.util.*


data class Channel(
    val name: String,
    val description: String,
    val id: Int,
    val isDefault: Boolean = false,
    var members: ArrayList<User>? = ArrayList<User>()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other != Channel::class) return false
        other as Channel
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id
    }
}