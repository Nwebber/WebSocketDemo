package com.webber;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Dorchek
 */
@ServerEndpoint(
        value = "/endpoint",
        encoders = {MessageEncoder.class},
        decoders = {MessageDecoder.class}
)
public class MessageEndpoint {

    private static final Set<Session> subscribers = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("MessageEndpoint.onOpen(), session: " + session);
        subscribers.add(session);
    }

    @OnMessage
    public void onMessage(Message message, Session session) throws IOException, EncodeException {
        System.out.println("MessageEndpoint.onMessage(), message: " + message);
        for (Session subscriber : subscribers) {
            if (!subscriber.equals(session)) {
                subscriber.getBasicRemote().sendObject(message);
            }
        }
    }
    
    @OnClose
    public void onClose(Session session) {
        System.out.println("MessageEndpoint.onClose(), session: " + session);
        subscribers.remove(session);
    }

    @OnError
    public void onError(Throwable throwable) {
        System.err.println("ERROR: " + throwable.getMessage());
    }
}
