# javalin-channel-api

Made to fulfil the interview process requiring the use of a Java to create an API

# Platform and Framework
This application was built with Java/Kotlin with Maven using the very the light weight Java Framework called Javalin: https://javalin.io/


# Deployment 
App is deployed onn Heroku and api is available at https://message-channel-api.herokuapp.com/

#Routes and Params
main route baseRoute = "/"
adding channel route = "/createChannel"
getting all channels = "/getChannels"
get a particular channel by id = "/channels/{channel-id}"
get a channel by nname = "/channels/{channel-name}"
get all users = "/users"
get messaages route = "/messages"
get channel members = "/channel/members/{channel-id}"
get channnel messages = "/channel/messages/{channel-id}"


