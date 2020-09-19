import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class Controller {

  @FXML
  private Tab tab1;

  @FXML
  private TextField txtProName;

  @FXML
  private TextField txtManufacturer;

  @FXML
  private ChoiceBox<?> txtProType;

  @FXML
  private TableView<?> tblExistingPro;

  @FXML
  private Tab tab2;

  @FXML
  private ListView<?> lstProduct;

  @FXML
  private ComboBox<String> cmbQuantity;

  @FXML
  private Tab tab3;

  @FXML
  void addProduct(ActionEvent event) {
    connectToDB();
  }

  @FXML
  void recordProduction(ActionEvent event) {

  }

  public void connectToDB() {
    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/PR";

    //  Database credentials
    final String USER = "";
    final String PASS = "";
    Connection conn = null;
    Statement stmt = null;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);
      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 3: Execute an insertion of data
      stmt = conn.createStatement();
      String insertSql = "INSERT INTO PRODUCT(type, manufacturer, name) "
          + "VALUES ('AUDIO', 'Samsung', 'Headphones')";

      stmt.executeUpdate(insertSql);

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
