package com.benj4.gcgm.server.websocket.json;

public class WSData {
    public String eventName;
    public Object object;

    public WSData(String eventName, Object object) {
        this.eventName = eventName;
        this.object = object;
    }
}
