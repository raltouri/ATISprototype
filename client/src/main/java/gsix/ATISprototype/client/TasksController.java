/**
 * Sample Skeleton for 'Tasks.fxml' Controller Class
 */

package gsix.ATISprototype.client;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gsix.ATISprototype.client.common.GuiCommon;
import gsix.ATISprototype.entities.Message;
import gsix.ATISprototype.entities.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class TasksController {

    private Stage stage;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="change_status_Btn"
    private Button change_status_Btn; // Value injected by FXMLLoader

    @FXML // fx:id="tasks_Lv"
    private ListView<String> tasks_Lv; // Value injected by FXMLLoader

    @FXML // fx:id="view_task_Btn"
    private Button view_task_Btn; // Value injected by FXMLLoader

    public static int extractTaskId(String taskInfoString) {
        try {
            // Split the string by comma, assuming consistent format
            String[] parts = taskInfoString.split(",");

            // Extract and trim the ID substring
            String idSubstring = parts[0].split(":")[1].trim();

            // Convert the ID substring to an integer
            return Integer.parseInt(idSubstring);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            // Handle potential errors during parsing
            return -1; // Or return any other value to indicate an error
        }
    }


    @FXML
    void ChangeTaskStatus(MouseEvent event) {
        try {
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            // Get the selected task information (assuming it's available in the ListView item)

            if (tasks_Lv.getSelectionModel().getSelectedItem() == null) {
                return; // Do nothing if no item is selected
            }

            String selectedTaskInfo = tasks_Lv.getSelectionModel().getSelectedItem();

            Integer selectedTaskID = extractTaskId(selectedTaskInfo);
            Message message = new Message(1, LocalDateTime.now(), "get task by id", selectedTaskID);
            System.out.println("before send to server ViewTaskInfo command");
            SimpleClient.getClient("",0).sendToServer(message);

        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately, e.g., display an error message
        }
    }

    @FXML
    void showTask(MouseEvent event) {


        try {
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            // Get the selected task information (assuming it's available in the ListView item)

            if (tasks_Lv.getSelectionModel().getSelectedItem() == null) {
                return; // Do nothing if no item is selected
            }

            String selectedTaskInfo = tasks_Lv.getSelectionModel().getSelectedItem();

            Integer selectedTaskID = extractTaskId(selectedTaskInfo);
            Message message = new Message(1, LocalDateTime.now(), "view task info", selectedTaskID);
            System.out.println("before send to server ViewTaskInfo command");
            SimpleClient.getClient("",0).sendToServer(message);

/*
            // Pass any necessary data to the new window's controller (e.g., task ID)
            taskController.setTaskInfo(selectedTaskInfo); // Assuming a method to set task info

            // Create a new stage for the window
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Task Details");
            stage.show();

 */
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately, e.g., display an error message
        }
    }

    private static List<String> getTasksInfo(List<Task> tasks) {
        List<String> tasks_info = new ArrayList<>();
        for (Task task : tasks) {
            tasks_info.add("Task ID:" + task.getTask_id() + ", Help Request Description: " + task.getRequested_operation());
        }
        return tasks_info;
    }

    @Subscribe
    public void handleTasksEvent(MessageEvent event) {
        //public void handleTasksEvent(List<Task> event) {

        if (event.getMessage().getMessage().equals("get all tasks: Done")) {
            List<Task> tasks = (List<Task>) event.getMessage().getData();
            List<String> tasks_info = getTasksInfo(tasks);
            System.out.println("handleTasksEvent");
            // Update ListView with received tasks
            Platform.runLater(() -> {
                tasks_Lv.getItems().clear(); // Clear existing items
                ObservableList<String> observableTasks = FXCollections.observableArrayList(tasks_info);
                tasks_Lv.setItems(observableTasks); // Add received tasks
            });
        } else if (event.getMessage().getMessage().equals("view task info: Done")) {


            Platform.runLater(() -> {

                //System.out.println("stam");
                GuiCommon guiCommon = GuiCommon.getInstance();
                TaskViewController taskController = (TaskViewController) guiCommon.displayNextScreen("Task.fxml","Task Info",stage,true);
                Task selectedTask = (Task) event.getMessage().getData();
                taskController.setTaskInfo(selectedTask,false);

                /*// Load the FXML file for the new window
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Task.fxml"));
                Parent root = loader.load();

                // Access the controller of the new window (if needed)
                TaskViewController taskController = loader.getController();

                // Pass any necessary data to the new window's controller (e.g., task ID)
                taskController.setTaskInfo(selectedTask); // Assuming a method to set task info

                // Create a new stage for the window
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Task Details");
                    stage.show();*/
            });
        }else if (event.getMessage().getMessage().equals("get task by id: Done")) {
            Platform.runLater(() -> {

                GuiCommon guiCommon = GuiCommon.getInstance();
                TaskViewController taskController = (TaskViewController) guiCommon.displayNextScreen("Task.fxml","Update Task",stage,true);
                Task selectedTask = (Task) event.getMessage().getData();
                taskController.setTaskInfo(selectedTask,true);

            });
        }
    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert change_status_Btn != null : "fx:id=\"change_status_Btn\" was not injected: check your FXML file 'Tasks.fxml'.";
        assert tasks_Lv != null : "fx:id=\"tasks_Lv\" was not injected: check your FXML file 'Tasks.fxml'.";
        assert view_task_Btn != null : "fx:id=\"view_task_Btn\" was not injected: check your FXML file 'Tasks.fxml'.";

        EventBus.getDefault().register(this);

        try {
            Message message = new Message(1, LocalDateTime.now(), "get all tasks", "no data");
            System.out.println("before send to server GetAllTasks command");
            SimpleClient.getClient("",0).sendToServer(message);
            System.out.println("after send to server GetAllTasks command");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
