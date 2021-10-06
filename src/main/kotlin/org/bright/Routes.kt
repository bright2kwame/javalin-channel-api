package org.bright

object Routes {

    const val baseRoute = "/"
    const val addChannel = "/createChannel"
    const val channels = "/getChannels"
    const val channelId = "/channels/{channel-id}"
    const val channelName = "/channels/{channel-name}"
    const val user = "/users"
    const val message = "/messages"
    const val channelMembers = "/channel/members/{channel-id}"
    const val channelMessages = "/channel/messages/{channel-id}"
}