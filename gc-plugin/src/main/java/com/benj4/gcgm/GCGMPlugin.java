package com.benj4.gcgm;

import com.benj4.gcgm.handlers.ServerTickHandler;
import com.benj4.gcgm.server.websocket.WebSocketServer;
import com.benj4.gcgm.utils.*;
import com.benj4.gcgm.utils.web.WebUtils;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.plugin.Plugin;
import emu.grasscutter.plugin.api.ServerHook;
import emu.grasscutter.server.dispatch.DispatchServer;
import emu.grasscutter.server.event.EventHandler;
import emu.grasscutter.server.event.HandlerPriority;
import emu.grasscutter.server.event.game.ServerTickEvent;
import emu.grasscutter.server.game.GameServer;
import emu.grasscutter.utils.Utils;
import express.Express;

import java.io.File;

public class GCGMPlugin extends Plugin {

    private static GCGMPlugin INSTANCE;
    EventHandler<ServerTickEvent> serverTickEventHandler;
    private WebSocketServer webSocketServer;
    private File webData;

    @Override
    public void onLoad() {
        INSTANCE = this;
        webData = new File(Utils.toFilePath(getDataFolder().getPath() + "/www"));

        serverTickEventHandler = new EventHandler<ServerTickEvent>(ServerTickEvent.class);
        serverTickEventHandler.listener(new ServerTickHandler());
        serverTickEventHandler.priority(HandlerPriority.HIGH);

        File pluginDataDir = getDataFolder();
        String zipFileLoc = Utils.toFilePath(getDataFolder().getPath() + "/DefaultWebApp.zip");

        if(!pluginDataDir.exists() && !pluginDataDir.mkdirs()) {
            Grasscutter.getLogger().error("[GCGM] Failed to create plugin data directory directory: " + pluginDataDir.getAbsolutePath());
            return;
        }

        if(!webData.exists()) {
            Grasscutter.getLogger().warn("[GCGM] The './plugins/GCGM/www' folder does not exist.");
            // Copy the the zip from resources to the plugin's data directory
            if(!new File(zipFileLoc).exists()) {
                if(GCGMUtils.CopyFile("DefaultWebApp.zip", zipFileLoc)) {
                    Grasscutter.getLogger().warn("[GCGM] Please extract the contents of 'DefaultWebApp.zip' from within './plugins/GCGM' to './plugins/GCGM/www");
                } else {
                    Grasscutter.getLogger().error("[GCGM] GCGM cannot start due to setup errors.");
                    return;
                }
            }
        }

        Grasscutter.getLogger().info("[GCGM] GCGM has now loaded...");
    }

    @Override
    public void onEnable() {
        if(webData.exists()) {
            WebUtils.addStaticFiles(webData);
            webSocketServer = new WebSocketServer();
            webSocketServer.start();

            serverTickEventHandler.register();

            Grasscutter.getLogger().info("[GCGM] GCGM Enabled");
            Grasscutter.getLogger().info("[GCGM] You can access your GM panel by navigating to " + GCGMUtils.GetDispatchAddress() + WebUtils.PAGE_ROOT);
        } else {
            Grasscutter.getLogger().error("[GCGM] GCGM could not find the 'www' folder inside its plugin directory");
            Grasscutter.getLogger().error("[GCGM] Please make sure a 'www' folder exists by extracting 'DefaultWebApp.zip' into a new 'www' or download a third-party dashboard");
            Grasscutter.getLogger().error("[GCGM] GCGM could not be enabled");
        }
    }

    @Override
    public void onDisable() {
        Grasscutter.getLogger().info("[GCGM] GCGM Disabled");
    }

    public static GCGMPlugin getInstance() {
        return INSTANCE;
    }

    public WebSocketServer getWebSocketServer() {
        return webSocketServer;
    }
    
    public static GameServer getGameServer() {
        return GCGMPlugin.getInstance().getServer();
    }

    public static DispatchServer getDispatchServer() {
        return ServerHook.getInstance().getDispatchServer();
    }
}
