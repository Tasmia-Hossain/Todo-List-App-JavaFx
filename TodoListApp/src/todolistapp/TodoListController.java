
package todolistapp;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import java.util.Comparator;

public class TodoListController implements Initializable {

    @FXML
    private Button FileButton;
    @FXML
    private Button add;
    @FXML
    private Button TodayTaskButton;
    @FXML
    private Button AllTaskButton;

    @FXML
    private ListView<String> listView;

    List<Task> list = new ArrayList<>(); 
    List<String> info = new ArrayList<>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        listView.setOnMouseClicked(event
             ->  {             
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewTask.fxml"));
                Parent root = loader.load();
                
                ViewTaskController tc = loader.getController();
                int x = listView.getSelectionModel().getSelectedIndex();
                
                tc.setIndex(x);
                tc.setItemforDetails(list);
                
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(TodoListController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }); 
    }    

    @FXML
    private void addList(ActionEvent event) throws IOException {
        
     FXMLLoader loader = new FXMLLoader(getClass().getResource("TaskDetails.fxml"));
     Parent root = loader.load();
     Scene scene = new Scene(root);
 
    TaskDetailsController wc = loader.getController();
    wc.setItem(list);
 
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.setTitle("ToDo List");
    stage.show();       
    }
    
    public void setItemAgain(List<Task> l) {
        list = l;
        listView.getItems().clear();
        for (Task task : list) {
            listView.getItems().add(task.getTaskTitle() +"\n"+ task.getDeadline().toString());
        }
    }
   
    @FXML
    private void showTodaysTasks(ActionEvent event) {
        LocalDate today = LocalDate.now();

        List<String> todaysTaskInfo = list.stream()
            .filter(task -> task.getDeadline().isEqual(today))
            .map(task -> task.getTaskTitle() +"\n"+ task.getDeadline().toString())
            .collect(Collectors.toList());

        listView.getItems().setAll(todaysTaskInfo);
    }
    
    @FXML
    private void showAllTasks(ActionEvent event) {
        List<String> allTaskInfo = list.stream()
        .sorted(Comparator.comparing(Task::getDeadline))
        .map(task -> task.getTaskTitle() + "\n" + task.getDeadline().toString())
        .collect(Collectors.toList());

        listView.getItems().setAll(allTaskInfo);
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

}
