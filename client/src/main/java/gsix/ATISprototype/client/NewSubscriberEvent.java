package gsix.ATISprototype.client;

import gsix.ATISprototype.entities.Message;

public class NewSubscriberEvent {
    private Message message;

    public Message getMessage() {
        return message;
    }

    public NewSubscriberEvent(Message message) {
        this.message = message;
    }
}
