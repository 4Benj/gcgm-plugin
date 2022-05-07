import React, { useContext, useCallback, useState } from 'react'
import { useWebSocket as Websocket, ReadyState } from "react-use-websocket/dist/lib/use-websocket";
import { ReactIsInDevelopmentMode } from '../util/util';

const WebsocketContext = React.createContext();

const protocol = window.location.protocol === "https:" ? "wss" : "wss"
const host = window.location.host;
const port = window.location.port;

function getWebsocketURL() {
    if(ReactIsInDevelopmentMode) {
        return "wss://localhost:443/gm";
    } else {
        return protocol + "://"+ host +":" + port + "/gm";
    }
}

export function useWebsocket() {
    return useContext(WebsocketContext);
}

export function WebsocketProvider({ children }) {
	const { sendMessage, lastMessage, readyState } = Websocket(getWebsocketURL());

    const connectionStatus = useCallback(() => {
        if(readyState === ReadyState.CONNECTING) {
            return "Connecting";
        } else if(readyState === ReadyState.OPEN) {
            return "Open";
        } else if(readyState === ReadyState.CLOSING) {
            return "Closing";
        } else if(readyState === ReadyState.CLOSED) {
            return "Closed";
        } else if(readyState === ReadyState.UNINSTANTIATED) {
            return "Uninstantiated";
        }
	}, [readyState]);

    const getLastMessage = useCallback(() => {
        if(lastMessage != null) {
            if(lastMessage.data != null) {
                return JSON.parse(lastMessage.data);
            }
        }

        return {};
    }, [lastMessage]);

    const send = useCallback((data) => {
        sendMessage(data, false);
    }, [sendMessage]);

    return (
        <WebsocketContext.Provider value={{lastMessage: getLastMessage, connectionStatus, sendMessage: send}}>
            {children}
        </WebsocketContext.Provider>
    )
}

export default WebsocketContext;