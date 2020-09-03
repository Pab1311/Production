import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class Controller {

  @FXML
  public void recordProduction(ActionEvent actionEvent) {
    System.out.println("Production recorded.");
  }

  public void addProduct(ActionEvent actionEvent) {
    System.out.println("Product added");
  }
}