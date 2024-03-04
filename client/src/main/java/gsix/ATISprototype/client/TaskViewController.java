/**
 * Sample Skeleton for 'Task.fxml' Controller Class
 */

package gsix.ATISprototype.client;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import gsix.ATISprototype.client.common.GuiCommon;
import gsix.ATISprototype.entities.Message;
import gsix.ATISprototype.entities.Task;
import gsix.ATISprototype.entities.TaskStatus;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class TaskViewController {

    private Stage stage;
    private Task task;
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="back_Btn"
    private Button back_Btn; // Value injected by FXMLLoader

    @FXML // fx:id="requested_op_Lbl"
    private Label requested_op_Lbl; // Value injected by FXMLLoader

    @FXML // fx:id="requster_id_Lbl"
    private Label requster_id_Lbl; // Value injected by FXMLLoader

    @FXML // fx:id="save_Btn"
    private Button save_Btn; // Value injected by FXMLLoader

    @FXML // fx:id="status_CmboBx"
    private ComboBox<String> status_CmboBx; // Value injected by FXMLLoader

    @FXML // fx:id="task_id_Lbl"
    private Label task_id_Lbl; // Value injected by FXMLLoader

    @FXML // fx:id="time_Lbl"
    private Label time_Lbl; // Value injected by FXMLLoader

    @FXML // fx:id="volunteer_id_Lbl"
    private Label volunteer_id_Lbl; // Value injected by FXMLLoader

    @FXML
    void backBtnClicked(MouseEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        GuiCommon.getInstance().displayNextScreen("Tasks.fxml", "Tasks Info", stage, true);
    }

    @FXML
    void saveBtnClicked(MouseEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        try {
            Task taskCpy = this.task;
            String selectedStatus = status_CmboBx.getValue();
            TaskStatus statusUpdated;
            if (selectedStatus.equals("Request") ){
                statusUpdated = TaskStatus.Request;
            } else if (selectedStatus.equals("Pending") ){
                statusUpdated = TaskStatus.Pending;
            }else{
                statusUpdated = TaskStatus.Done;
            }
            taskCpy.setStatus(statusUpdated);

            Message message = new Message(1, LocalDateTime.now(), "change task status", taskCpy);
            //System.out.println("before send to server GetAllTasks command");
            SimpleClient.getClient("",0).sendToServer(message);
            //System.out.println("after send to server GetAllTasks command");

            GuiCommon.getInstance().displayNextScreen("Tasks.fxml", "Tasks Info", stage, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void setTaskInfo(Task task, boolean enableUpdateFeature) {
        this.task=task;
        this.task_id_Lbl.setText(task.getTask_id() + "");
        this.requster_id_Lbl.setText(task.getRequester_id() + "");
        this.volunteer_id_Lbl.setText(task.getVolunteer_id() + "");
        this.time_Lbl.setText(task.getTime().toString());
        this.requested_op_Lbl.setText(task.getRequested_operation());
        this.status_CmboBx.setPromptText(task.getStatus());

        status_CmboBx.setDisable(!enableUpdateFeature);
        //status_CmboBx.setEditable(enableUpdateFeature);
        save_Btn.setDisable(!enableUpdateFeature);
        save_Btn.setVisible(enableUpdateFeature);
    }

    @Subscribe
    public void handleTasksEvent(MessageEvent event) {
        //public void handleTasksEvent(List<Task> event) {

         if (event.getMessage().getMessage().equals("change task status: Done")) {
            Platform.runLater(() -> {
                Task dbUpdatedTask = (Task) event.getMessage().getData();
                GuiCommon.popUp(dbUpdatedTask.toString());
            });
        }
    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert back_Btn != null : "fx:id=\"back_Btn\" was not injected: check your FXML file 'Task.fxml'.";
        assert requested_op_Lbl != null : "fx:id=\"requested_op_Lbl\" was not injected: check your FXML file 'Task.fxml'.";
        assert requster_id_Lbl != null : "fx:id=\"requster_id_Lbl\" was not injected: check your FXML file 'Task.fxml'.";
        assert save_Btn != null : "fx:id=\"save_Btn\" was not injected: check your FXML file 'Task.fxml'.";
        assert status_CmboBx != null : "fx:id=\"status_CmboBx\" was not injected: check your FXML file 'Task.fxml'.";
        assert task_id_Lbl != null : "fx:id=\"task_id_Lbl\" was not injected: check your FXML file 'Task.fxml'.";
        assert time_Lbl != null : "fx:id=\"time_Lbl\" was not injected: check your FXML file 'Task.fxml'.";
        assert volunteer_id_Lbl != null : "fx:id=\"volunteer_id_Lbl\" was not injected: check your FXML file 'Task.fxml'.";

        EventBus.getDefault().register(this);

        status_CmboBx.setItems(FXCollections.observableArrayList("Request", "Pending", "Done"));

    }

}
