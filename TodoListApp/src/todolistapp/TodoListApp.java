package todolistapp;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class TodoListApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TodoList.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        primaryStage.setTitle("Todo List App || Kashfia || 20210204038");
        primaryStage.setScene(scene);

        setAppIcon(primaryStage);
        
        TodoListController todoListController = loader.getController();
        todoListController.setItemAgain(DataStorage.loadTasks());
        
        primaryStage.show();
    }

    public void setAppIcon(Stage stage) {
        Image icon = new Image(getClass().getResourceAsStream("Icon.png"));
        stage.getIcons().add(icon);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
