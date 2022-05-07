package com.benj4.gcgm.server.websocket.json;

public class WSData {
    public String eventName;
    public Object data;

    public WSData(String eventName, Object data) {
        this.eventName = eventName;
        this.data = data;
    }
}
