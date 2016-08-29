package com.messagehub.work;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

public class MessageList {
    private ArrayList<String> messages;

    MessageList(String messages[]) {
        this.messages = new ArrayList<String>();

        if (messages != null && messages.length > 0) {
            for (int i = 0; i < messages.length; i++) {
                push(messages[i]);
            }
        }
    }

    MessageList(ArrayList<String> messages) {
        this.messages = new ArrayList<String>();

        if (messages != null && messages.size() > 0) {
            for (int i = 0; i < messages.size(); i++) {
                push(messages.get(i));
            }
        }
    }

    public MessageList() {
        this.messages = new ArrayList<String>();
    }

    public void push(String message) {
        this.messages.add(message);
    }

    /**
     * Build message list dependent on the format Message Hub requires. The
     * message list is in the form: [{ "value": base_64_string }, ...]
     *
     * @return {String} String representation of a JSON object.
     * @throws IOException
     */
    public String build() throws IOException {
        final JsonFactory jsonFactory = new JsonFactory();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JsonGenerator jsonGenerator = null;

        jsonGenerator = jsonFactory.createGenerator(outputStream);

        jsonGenerator.writeStartArray();

        // Write each message as a JSON object in
        // the form:
        // { "value": base_64_string }
        for (int i = 0; i < this.messages.size(); i++) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeFieldName("value");
            jsonGenerator.writeObject(this.messages.get(i));
            jsonGenerator.writeEndObject();
        }

        jsonGenerator.writeEndArray();

        // Close underlying streams and return string representation.
        jsonGenerator.close();
        outputStream.close();

        return new String(outputStream.toByteArray());
    }
}
