package com.fujitsu.base.client;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

@ClientEndpoint
public class GasWebSocketClient {
	@OnOpen
    public void onOpen(Session session) {
 
    }
 
    @OnMessage
    public void onMessage(String message) {
        System.out.println("Client onMessage: " + message);
    }
 
    @OnClose
    public void onClose() {
 
    }
}
