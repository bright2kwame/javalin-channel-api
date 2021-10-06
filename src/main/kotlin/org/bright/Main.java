package org.bright;

import io.javalin.Javalin;
import org.bright.controller.ChannelController;
import org.bright.controller.UserController;
import org.bright.dao.ChannelDao;
import org.bright.dao.UserDao;
import org.bright.model.Channel;
import org.bright.util.ApiStatus;
import org.bright.util.ResponseHandler;

import java.util.HashMap;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Main {

    public static void main(String[] args) {
        Javalin app = Javalin.create()
                .start(getHerokuAssignedPort());

        ChannelDao channelDao = ChannelDao.Companion.instance();
        UserDao userDao = UserDao.Companion.instance();
        ChannelController appController = new ChannelController(channelDao, userDao);
        UserController userController = new UserController(userDao);

        app.routes(() -> {
//            before(Filters.stripTrailingSlashes);
//            before(Filters.handleLocaleChange);
//            before(LoginController.ensureLoginBeforeViewingBooks);
            get(Routes.baseRoute, appController.getHandleWelcome());
            get(Routes.channels, appController.getHandleAllChannels());
            get(Routes.channelId, appController.getHandleGetChannelById());
            get(Routes.channelName, appController.getHandleGetChannelByName());
            get(Routes.channelMembers, appController.getHandleGetChannelMembers());
            post(Routes.channelMembers, appController.getHandleAddUserToChannel());
            delete(Routes.channelId, appController.getHandleDeleteChannel());
            patch(Routes.channelId, context -> {
                Channel channel = context.bodyAsClass(Channel.class);
                channelDao.update(String.valueOf(channel.getId()), channel);
                context.status(204);
            });
            post(Routes.addChannel, appController.getHandleAddChannel());
            post(Routes.user, appController.getHandleCreateUser());
            get(Routes.user, userController.getHandleAllUsers());
        });

        app.error(404, context -> {
            HashMap<String, String> message = new HashMap<String, String>();
            message.put("message", "Sorry, the page you are looking for took a day off. Engineers are working on it.");
            context.json(ResponseHandler.Companion.formatResponse(message, ApiStatus.FAILED));
            context.status(404);
        });
    }

    private static int getHerokuAssignedPort() {
        String herokuPort = System.getenv("PORT");
        if (herokuPort != null) {
            return Integer.parseInt(herokuPort);
        }
        return 7000;
    }
}

