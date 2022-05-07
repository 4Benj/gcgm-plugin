package com.benj4.gcgm.handlers;

import com.benj4.gcgm.GCGMPlugin;
import com.benj4.gcgm.server.websocket.json.TickData;
import com.benj4.gcgm.server.websocket.json.WSData;
import com.benj4.gcgm.utils.GCGMUtils;
import com.google.gson.JsonObject;
import emu.grasscutter.server.event.game.ServerTickEvent;
import emu.grasscutter.utils.EventConsumer;

import java.time.Instant;

public class ServerTickHandler implements EventConsumer<ServerTickEvent> {
    private static Instant firstTick;
    private static Instant lastTick;

    @Override
    public void consume(ServerTickEvent serverTickEvent) {
        if(lastTick != null) {
            Instant now = Instant.now();
            TickData data = new TickData();
            data.tickTimeElapsed = now.toEpochMilli() - lastTick.toEpochMilli();
            data.serverUptime = lastTick.toEpochMilli() - firstTick.toEpochMilli();
            data.getFreeMemory = GCGMUtils.GetFreeJVMMemory();
            data.getAllocatedMemory = GCGMUtils.GetAllocatedJVMMemory();
            data.playerCount = GCGMPlugin.getGameServer().getPlayers().size();

            GCGMPlugin.getInstance().getWebSocketServer().broadcast(new WSData("tick", data));
            lastTick = now;
        } else {
            lastTick = Instant.now();
            firstTick = Instant.now();
        }
    }
}
