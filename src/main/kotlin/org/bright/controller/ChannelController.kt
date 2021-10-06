package org.bright.controller


import io.javalin.http.Handler
import org.bright.dao.ChannelDao
import org.bright.dao.UserDao
import org.bright.model.User
import org.bright.util.ApiStatus
import org.bright.util.ResponseHandler
import java.util.*


class ChannelController(private val channelDao: ChannelDao, private val userDao: UserDao) {

    //MARK: present and respond to the welcome route
    var handleWelcome = Handler { ctx ->
        val welcomeMessage = HashMap<String, String>()
        welcomeMessage.put("status", ApiStatus.SUCCESS.name)
        welcomeMessage.put("message", "Welcome to the Message Channel API")
        ctx.json(welcomeMessage)
    }

    //MARK: handle all channels
    var handleAllChannels = Handler { ctx ->
        val allChannels = channelDao.channels.values
        ctx.json(ResponseHandler.formatResponse(allChannels, ApiStatus.SUCCESS))
    }

    //MARK: route to delete all channels
    var handleDeleteChannel = Handler { context ->
        channelDao.delete(context.pathParam("channel-id"))
        val data = HashMap<String, String>()
        data.put("message", "Channel deleted successfully")
        context.json(ResponseHandler.formatResponse(data, ApiStatus.FAILED))
        context.status(204)
    }

    //MARK: route to get channel by the name
    var handleGetChannelByName = Handler { context ->
        val channelName = context.pathParam("channel-name")
        val channel = channelDao.findByName(channelName)
        if (channel == null) {
            val data = HashMap<String, String>()
            data.put("message", "No channel with name: $channelName found")
            context.json(ResponseHandler.formatResponse(data, ApiStatus.FAILED))
        } else {
            context.json(ResponseHandler.formatResponse(channel, ApiStatus.SUCCESS))
        }
        context.status(204)
    }

    //MARK: route to get channel members
    var handleGetChannelMembers = Handler { context ->
        val channelId = context.pathParam("channel-id")
        val channel = channelDao.findById(channelId)
        if (channel == null) {
            val data = HashMap<String, String>()
            data.put("message", "No channel with id: $channelId found")
            context.json(ResponseHandler.formatResponse(data, ApiStatus.FAILED))
        } else {
            context.json(ResponseHandler.formatResponse(channel.members!!, ApiStatus.SUCCESS))
        }
        context.status(204)
    }


    //MARK: route to get channel by the ID
    var handleGetChannelById = Handler { context ->
        val channelId = context.pathParam("channel-id")
        val channel = channelDao.findById(channelId)
        if (channel == null) {
            val data = HashMap<String, String>()
            data.put("message", "No channel with id: $channelId found")
            context.json(ResponseHandler.formatResponse(data, ApiStatus.FAILED))
        } else {
            context.json(ResponseHandler.formatResponse(channel, ApiStatus.SUCCESS))
        }

        context.status(204)
    }

    //MARK: route to add new channel
    var handleAddChannel = Handler { context ->
        val channelName = context.formParam("name")!!
        val channelDetail = context.formParam("description")!!
        val channel = channelDao.save(channelName, channelDetail, false)
        context.json(ResponseHandler.formatResponse(channel, ApiStatus.SUCCESS))
        context.status(201)
    }

    //MARK: route to add member and then proceed to add user to all default channels
    var handleCreateUser = Handler { context ->
        val userName = context.formParam("name")!!
        val userEmail = context.formParam("email")!!
        val user = User(userName, userEmail)
        channelDao.addMemberToDefaultChannel(user)
        val data = HashMap<String, String>()
        data.put("message", "User created successfully")
        context.json(ResponseHandler.formatResponse(data, ApiStatus.SUCCESS))
        context.status(201)
    }

    //MARK: route to add member to channel
    var handleAddUserToChannel = Handler { context ->
        val userEmail = context.formParam("email")!!
        val channelId = context.pathParam("channel-id")
        val user = userDao.findByEmail(userEmail)
        val data = HashMap<String, String>()
        if (user != null) {
            channelDao.addMemberToChanel(channelId, user)
            data.put("message", "User added successfully")
            context.json(ResponseHandler.formatResponse(data, ApiStatus.SUCCESS))
            context.status(201)
        } else {
            data.put("message", "No user with $userEmail found")
            context.json(ResponseHandler.formatResponse(data, ApiStatus.FAILED))
            context.status(404)
        }

    }

}