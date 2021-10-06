package org.bright.dao

import org.bright.model.Channel
import org.bright.model.User
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.streams.toList

class ChannelDao {

    val channels = HashMap<Int, Channel>()

    //MARK: init the channel dao
    init {
        channels.put(
            0,
            Channel(name = "Welcome Channel", description = "The Welcome Channel", id = 0, isDefault = true)
        )
    }


    companion object {
        private var channelDao: ChannelDao? = null
        fun instance(): ChannelDao {
            if (channelDao == null) {
                channelDao = ChannelDao()
            }
            return channelDao as ChannelDao
        }
    }

    private var lastId: AtomicInteger = AtomicInteger(channels.size - 1)

    //MARK: save channel
    fun save(name: String, message: String, isDefault: Boolean): Channel {
        val id = lastId.incrementAndGet()
        channels.put(id, Channel(name = name, description = message, id = id, isDefault = isDefault))
        return channels[id]!!
    }

    //MARK: find a channel by id
    fun findById(id: String): Channel? {
        return channels[Integer.parseInt(id)]
    }

    //MARK: add a member to the channel
    fun addMember(id: String, user: User) {
        channels[Integer.parseInt(id)]?.members?.add(user)
    }

    //MARK: find a channel by the name
    fun findByName(name: String): Channel? {
        return channels.values.stream()
            .filter { channel -> channel.name == name }
            .findFirst()
            .orElse(null)
    }

    //MARK: get all default channels
    fun getDefaultChannels(): List<Channel> {
        return channels.values.stream()
            .filter { channel -> channel.isDefault }
            .toList()
    }

    //MARK: add a member to default channel
    fun addMemberToDefaultChannel(user: User) {
        channels.values.stream().filter { it.isDefault }.forEach { it.members?.add(user) }
    }

    fun addMemberToChanel(channel: String, user: User) {
        channels[Integer.parseInt(channel)]?.members?.add(user)
    }

    //MARK: update a channel
    fun update(id: String, channel: Channel) {
        val idToInt = Integer.parseInt(id)
        channels.put(
            idToInt,
            Channel(name = channel.name, description = channel.description, id = idToInt, isDefault = channel.isDefault)
        )
    }

    //MARK: delete some channel
    fun delete(id: String) {
        val idToInt = Integer.parseInt(id)
        channels.remove(idToInt)
    }
}