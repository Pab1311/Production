/**
 * Author: Paul Basso Class: COP 3003 File: Controller.java Description: Object created by FXML
 * which is used to initialize the UI elements and contains methods that are called when the user
 * interacts with the GUI.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
  private ChoiceBox<String> chbProType;

  @FXML
  private Label lblStatus;

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

  /**
   * Description: takes user input, verifies the user inputted something on all required fields, and
   * inserts the data into the PRODUCT table by accessing the database.
   */

  @FXML
  void addProduct(ActionEvent event) {

    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/PR";

    //  Database credentials
    final String USER = "";
    final String PASS = "";
    Connection conn = null;
    PreparedStatement stmt = null;

    if (!(txtProName.getText().isBlank()) && !(txtManufacturer.getText().isBlank())
        && !(chbProType.getSelectionModel().getSelectedItem() == null)) {
      try {
        // STEP 1: Register JDBC driver
        Class.forName(JDBC_DRIVER);
        //STEP 2: Open a connection
        // empty database password
        conn = DriverManager.getConnection(DB_URL, USER, PASS);

        //STEP 3: Execute an insertion of data
        String sql = "INSERT INTO Product(type, manufacturer, name) "
            + "VALUES (?, ?, ?)";

        // method may fail to clean up java.sql.Statement on checked exception
        stmt = conn.prepareStatement(sql);

        stmt.setString(1, chbProType.getSelectionModel().getSelectedItem());
        stmt.setString(2, txtManufacturer.getText());
        stmt.setString(3, txtProName.getText());
        stmt.executeUpdate();

        // STEP 4: Clean-up environment + notify user that product is added
        stmt.close();
        conn.close();

        lblStatus.setText("Product added.");
        txtManufacturer.clear();
        txtProName.clear();
        chbProType.getSelectionModel().clearSelection();

        // view list of current products
        showProductList();

      } catch (ClassNotFoundException e) {
        e.printStackTrace();

      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {
      lblStatus.setText("Please fill out all fields in order to add product.");
    }
  }

  /**
   * Description: records the production of the product, including quantity.
   */

  @FXML
  void recordProduction(ActionEvent event) {

  }

  /**
   * Description: initializes ComboBox and ChoiceBox
   */
  public void initialize() {
    for (int count = 1; count <= 10; count++) {
      cmbQuantity.getItems().add(String.valueOf(count));
    }

    cmbQuantity.getSelectionModel().selectFirst();
    cmbQuantity.setEditable(true);

    for (ItemType it : ItemType.values()) {
      chbProType.getItems().add(String.valueOf(it));
    }
  }

  /**
   * Description: displays current list of products in order of name, type, and manufacturer by
   * accessing the database.
   */

  public void showProductList() {
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
      // method may fail to clean up java.sql.Statement on checked exception
      // empty database password
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      stmt = conn.createStatement();

      //STEP 3: Execute an insertion of data
      // method may fail to clean up java.sql.ResultSet on checked exception
      String searchSql = "SELECT * FROM PRODUCT";

      ResultSet rs = stmt.executeQuery(searchSql);

      // found on stack overflow for accessing elements of each row
      while (rs.next()) {
        for (int i = 2; i <= 4; i++) {
          System.out.print(rs.getString(i) + " | ");
        }
        System.out.println();
      }
      System.out.println("__________________________");

      // STEP 4: Clean-up environment + notify user that product is added
      stmt.close();
      conn.close();

    } catch (ClassNotFoundException e) {
      e.printStackTrace();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
