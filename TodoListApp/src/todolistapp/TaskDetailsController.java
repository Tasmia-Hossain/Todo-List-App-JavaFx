
package todolistapp;

import java.io.IOException;
import java.net.URL;
import javafx.util.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TaskDetailsController implements Initializable {

    @FXML
    private Button FileButton;
    @FXML
    private TextField TaskTextField;
    @FXML
    private TextArea DetailsTextArea;
    @FXML
    private DatePicker Deadline;
    @FXML
    private Button SaveButton;
    @FXML
    private TextField successMessage;

    List<Task> list = new ArrayList<>(); 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        list = DataStorage.loadTasks();
        if (list == null) {
            list = new ArrayList<>();
        }
    }    
    
    @FXML
    private void saveInfo(ActionEvent event) throws IOException {
        
        String shortDisc = TaskTextField.getText().trim();
        String details = DetailsTextArea.getText().trim();
        LocalDate deadlineValue = Deadline.getValue();
        Task newItem = new Task(shortDisc,details,deadlineValue); 
        list.add(newItem);
        DataStorage.saveTasks(list);
        
        successMessage.setText("Task Added Successfully");
        successMessage.setVisible(true);
        
        TaskTextField.clear();
        DetailsTextArea.clear();
        Deadline.setValue(null);

        PauseTransition visiblePause = new PauseTransition(Duration.millis(2000));
        visiblePause.setOnFinished(e -> successMessage.setVisible(false));
        visiblePause.play();
         
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


    public void setItem(List<Task> l) {
        list=l; 
    }
}
