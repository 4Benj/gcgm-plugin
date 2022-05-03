package com.benj4.gcgm.handlers;

import com.benj4.gcgm.GCGMPlugin;
import com.benj4.gcgm.server.websocket.json.WSData;
import emu.grasscutter.server.event.game.ServerTickEvent;
import emu.grasscutter.utils.EventConsumer;

import java.time.Instant;

public class ServerTickHandler implements EventConsumer<ServerTickEvent> {
    private static Instant lastTick;

    @Override
    public void consume(ServerTickEvent serverTickEvent) {
        if(lastTick != null) {
            Instant now = Instant.now();
            long timeTaken = now.toEpochMilli() - lastTick.toEpochMilli();
            GCGMPlugin.getWebSocketServer().broadcast(new WSData("tick", timeTaken));
            lastTick = now;
        } else {
            lastTick = Instant.now();
        }
    }
}
