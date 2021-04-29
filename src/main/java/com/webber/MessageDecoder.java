package com.webber;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author Dorchek
 */
public class MessageDecoder implements Decoder.Text<Message> {

    @Override
    public Message decode(String string) throws DecodeException {
        Message result = null;
        if (willDecode(string)) {
            JsonObject jsonObject = Json.createReader(new StringReader(string)).readObject();
            result = new Message(jsonObject);
        }
        return result;
    }

    @Override
    public boolean willDecode(String string) {
        boolean result;
        try {
            JsonObject jsonObject = Json.createReader(new StringReader(string)).readObject();
            result = true;
        } catch (JsonException jex) {
            result = false;
        }
        return result;
    }

    @Override
    public void init(EndpointConfig config) {
        
    }

    @Override
    public void destroy() {
        
    }
}
