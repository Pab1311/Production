/**
 * Author: Paul Basso Class: COP 3003 File: Main.java Description: Main method that creates the GUI
 * and adds the elements of a .css file to its style.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  /**
   * @param: primaryStage
   * @throws: Exception
   */

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
    Scene scene = new Scene(root, 663, 400);

    root.getStylesheets().add("productionStyle.css");

    primaryStage.setTitle("Production");
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
