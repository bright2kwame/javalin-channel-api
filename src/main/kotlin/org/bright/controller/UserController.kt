package org.bright.controller

import org.bright.dao.ChannelDao
import org.bright.dao.UserDao
import org.bright.util.ApiStatus
import org.bright.util.ResponseHandler

class UserController(private val userDao: UserDao) {

    //MARK: handle all channels
    var handleAllUsers = io.javalin.http.Handler { ctx ->
        val allUsers = userDao.getUsers()
        ctx.json(ResponseHandler.formatResponse(allUsers, ApiStatus.SUCCESS))
    }
}