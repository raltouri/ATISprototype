package gsix.ATISprototype.client;

import java.io.IOException;
import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        SimpleChatClient.setRoot("primary");
    }
}