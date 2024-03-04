package gsix.ATISprototype.client;

import gsix.ATISprototype.entities.Message;

public class UpdateMessageEvent {
    private Message message;

    public Message getMessage() {
        return message;
    }

    public UpdateMessageEvent(Message message) {
        this.message = message;
    }
}
