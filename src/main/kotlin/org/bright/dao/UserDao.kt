package org.bright.dao

import org.bright.model.User
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.streams.toList

class UserDao {

   private val users = HashMap<Int, User>()

    //MARK: init the channel dao
    init {
        users.put(
            0,
            User(name = "SNR Bright", email = "bright@gmail.com")
        )
    }


    companion object {
        private var userDao: UserDao? = null
        fun instance(): UserDao {
            if (userDao == null) {
                userDao = UserDao()
            }
            return userDao as UserDao
        }
    }

    private var lastId: AtomicInteger = AtomicInteger(users.size - 1)

    //MARK: save user
    fun save(name: String, email: String): User {
        val id = lastId.incrementAndGet()
        users.put(id, User(name = name, email = email))
        return users[id]!!
    }

    //MARK: find a user by id
    fun findById(id: String): User? {
        return users[Integer.parseInt(id)]
    }

    //MARK: find a channel by the name
    fun findByEmail(name: String): User? {
        return users.values.stream()
            .filter { channel -> channel.name == name }
            .findFirst()
            .orElse(null)
    }

    //MARK: get all users
    fun getUsers(): List<User> {
        return users.values.stream()
            .toList()
    }

    //MARK: update a user
    fun update(id: String, user: User) {
        val idToInt = Integer.parseInt(id)
        users.put(
            idToInt,
            User(name = user.name, email = user.email)
        )
    }

    //MARK: delete some user
    fun delete(id: String) {
        val idToInt = Integer.parseInt(id)
        users.remove(idToInt)
    }
}