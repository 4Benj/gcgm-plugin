package com.benj4.gcgm.server.websocket;

import com.benj4.gcgm.server.websocket.json.WSData;
import emu.grasscutter.Grasscutter;
import express.Express;
import io.javalin.websocket.WsContext;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketServer {

    //SocketIOServer socketIOServer;
    private static Map<WsContext, String> userUsernameMap = new ConcurrentHashMap<>();

    public void start(Express app) {
        app.ws("/gm", ws -> {
            ws.onConnect(ctx -> {
                String username = "Not logged in";
                userUsernameMap.put(ctx, username);
                Grasscutter.getLogger().info("[GCGM] User logged in to panel");
            });
        });
    }

    public void broadcast(WSData data) {
        userUsernameMap.keySet().stream().filter(ctx -> ctx.session.isOpen()).forEach(session -> {
            session.send(data);
        });
    }
}
