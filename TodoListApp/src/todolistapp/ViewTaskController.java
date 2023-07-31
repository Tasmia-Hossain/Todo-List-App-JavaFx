
package todolistapp;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ViewTaskController implements Initializable {

    @FXML
    private Button FileButton;
    @FXML
    private TextField TaskField;
    @FXML
    private TextField DetailsField;
    @FXML
    private TextField DeadlineField;
    @FXML
    private Button DeleteButton;
    @FXML
    private Button BackButton;
    
    int x;
    List<Task> list = new ArrayList<>(); 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    @FXML
    private void deleteTask(ActionEvent event) {
        
        list.remove(x);
        DataStorage.saveTasks(list);
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Task Deletion");
        alert.setHeaderText(null);
        alert.setContentText("Task deleted successfully!");
        alert.showAndWait();
        
        setTaskDetails(null);
    }
    @FXML
    private void goBack(ActionEvent event) throws IOException {
       
     FXMLLoader loader = new FXMLLoader(getClass().getResource("TodoList.fxml"));
     Parent root = loader.load();
     Scene scene = new Scene(root);
 
    TodoListController wc = loader.getController();
    wc.setItemAgain(list);
 
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.setTitle("ToDo List");
    stage.show();
    }
    
    @FXML
    private void showFileMenu(ActionEvent event) {
        ContextMenu fileMenu = new ContextMenu();

        MenuItem homeItem = new MenuItem("Home");
        MenuItem exitItem = new MenuItem("Exit");

        homeItem.setOnAction(e -> {

            System.out.println("Home clicked!");
            goBackToToDoList(event);
        });

        exitItem.setOnAction(e -> {

            System.out.println("Exit clicked!");
            exitApplication();
        });

        fileMenu.getItems().addAll(homeItem, exitItem);

        Node sourceNode = (Node) event.getSource();

        double screenX = sourceNode.localToScreen(sourceNode.getBoundsInLocal()).getMinX();
        double screenY = sourceNode.localToScreen(sourceNode.getBoundsInLocal()).getMinY();

        fileMenu.show(sourceNode, screenX, screenY);
    }

    private void goBackToToDoList(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TodoList.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            TodoListController wc = loader.getController();
            wc.setItemAgain(list);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("ToDo List");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exitApplication() {
        Stage stage = (Stage) FileButton.getScene().getWindow();
        stage.close();
    }
    
    public void setIndex(int x){
        this.x=x;
    }
    
    public void setItemforDetails(List<Task> l) {
        
        list=l;  
        
        TaskField.setText(list.get(x).taskTitle);
        DetailsField.setText(list.get(x).details);
        DeadlineField.setText(list.get(x).deadline.toString());
        
    }
    
    private void setTaskDetails(Task task) {
        if (task != null) {
            TaskField.setText(task.getTaskTitle());
            DetailsField.setText(task.getDetails());
            DeadlineField.setText(task.getDeadline().toString());
        } else {
            // Clear the fields when there is no task selected
            TaskField.clear();
            DetailsField.clear();
            DeadlineField.clear();
        }
    }
}
