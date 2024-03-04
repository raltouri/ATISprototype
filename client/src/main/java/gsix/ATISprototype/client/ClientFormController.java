/**
 * Sample Skeleton for 'ClientForm.fxml' Controller Class
 */

package gsix.ATISprototype.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static gsix.ATISprototype.client.SimpleChatClient.loadFXML;

public class ClientFormController {

    private static Scene scene;
    private SimpleClient client;

    @FXML
    private Stage stage;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="connectToServer_Btn"
    private Button connectToServer_Btn; // Value injected by FXMLLoader

    @FXML // fx:id="ip_TF"
    private TextField ip_TF; // Value injected by FXMLLoader

    @FXML // fx:id="port_TF"
    private TextField port_TF; // Value injected by FXMLLoader

    @FXML
    void ConnectToServer(ActionEvent event) throws IOException {
        String server=ip_TF.getText();
        String port=port_TF.getText();
        if(!server.isEmpty() && ! port.isEmpty()) {
            String ipStr=null;

            /*String[] address = {server,port};
            SimpleChatClient.main(address);*/ // connect to server

            stage = (Stage) connectToServer_Btn.getScene().getWindow();

            client = SimpleClient.getClient(server, Integer.parseInt(port));
            client.openConnection();
            scene = new Scene(loadFXML("Tasks"));
            stage.setScene(scene);
            stage.show();
            //ClientUI.connectToServer(server,Integer.parseInt(port));

            //GuiCommon.getInstance().displayNextScreen("/gui/login.fxml", "LoginCEMS", event,true);
        }
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert connectToServer_Btn != null : "fx:id=\"connectToServer_Btn\" was not injected: check your FXML file 'ClientForm.fxml'.";
        assert ip_TF != null : "fx:id=\"ip_TF\" was not injected: check your FXML file 'ClientForm.fxml'.";
        assert port_TF != null : "fx:id=\"port_TF\" was not injected: check your FXML file 'ClientForm.fxml'.";

    }

}
