package gsix.ATISprototype.client;

import gsix.ATISprototype.entities.Message;

import java.io.Serializable;

public class MessageEvent implements Serializable {
    private Message message;

    public Message getMessage() {
        return message;
    }

    public MessageEvent(Message message) {
        this.message = message;
    }
}
