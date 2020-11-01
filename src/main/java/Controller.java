/**
 * Author: Paul Basso Class: COP 3003 File: Controller.java Description: Object created by FXML
 * which is used to initialize the UI elements and contains methods that are called when the user
 * interacts with the GUI.
 */

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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
  private TableView<Product> tblExistingPro;

  @FXML
  private TableColumn<Product, String> productName;

  @FXML
  private TableColumn<Product, String> manufacturer;

  @FXML
  private TableColumn<Product, ItemType> typeCol;

  @FXML
  private TextArea prodLog;

  @FXML
  private Tab tab2;

  @FXML
  private ListView<Product> lstProduct;

  @FXML
  private ComboBox<String> cmbQuantity;

  @FXML
  private Label lblRecord;

  @FXML
  private Tab tab3;

  // list of products
  private final ObservableList<Product> productLine = FXCollections.observableArrayList();

  // list of production records
  private final ObservableList<ProductionRecord> productRecords = FXCollections
      .observableArrayList();


  /**
   * Description: initializes ComboBox and ChoiceBox and sets up Product Line Table
   */
  public void initialize() {
    showProductList();
    setUpProductLineTable();
    setUpCmb();
    setUpChb();
    setUpLstProduct();
    setUpProductionRecord();

    testMultimedia();
  }

  /**
   * Description: initializes production log TextView
   */
  public void setUpProductionRecord() {
    // sample record to display until we access database next week (11/2/20)
    ProductionRecord test = new ProductionRecord(0);
    prodLog.setText(test.toString());

  }

  /**
   * Description: initializes ListView with current products
   */
  public void setUpLstProduct() {
    lstProduct.getItems().clear();
    lstProduct.setItems(productLine);
  }

  /**
   * Description: initializes ChoiceBox
   */
  public void setUpChb() {
    for (ItemType it : ItemType.values()) {
      chbProType.getItems().add(String.valueOf(it));
    }
  }

  /**
   * Description: initializes ComboBox
   */
  public void setUpCmb() {
    for (int count = 1; count <= 10; count++) {
      cmbQuantity.getItems().add(String.valueOf(count));
    }

    cmbQuantity.getSelectionModel().selectFirst();
    cmbQuantity.setEditable(true);
  }

  public void setUpProductLineTable() {
    productName.setCellValueFactory(new PropertyValueFactory("name"));
    manufacturer.setCellValueFactory(new PropertyValueFactory("manufacturer"));
    typeCol.setCellValueFactory(new PropertyValueFactory("type"));

    tblExistingPro.setItems(productLine);
  }

  public static void testMultimedia() {
    AudioPlayer newAudioProduct = new AudioPlayer("DP-X1A", "Onkyo",
        "DSD/FLAC/ALAC/WAV/AIFF/MQA/Ogg-Vorbis/MP3/AAC", "M3U/PLS/WPL");
    Screen newScreen = new Screen("720x480", 40, 22);
    MoviePlayer newMovieProduct = new MoviePlayer("DBPOWER MK101", "OracleProduction", newScreen,
        MonitorType.LCD);
    ArrayList<MultimediaControl> productList = new ArrayList<MultimediaControl>();
    productList.add(newAudioProduct);
    productList.add(newMovieProduct);
    for (MultimediaControl p : productList) {
      System.out.println(p);
      p.play();
      p.stop();
      p.next();
      p.previous();
    }
  }

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
      Widget item = new Widget(txtProName.getText(), txtManufacturer.getText(),
          ItemType.valueOf(chbProType.getSelectionModel().getSelectedItem()));
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

        stmt.setString(1, item.type.code);
        stmt.setString(2, item.getManufacturer());
        stmt.setString(3, item.getName());
        stmt.executeUpdate();

        // STEP 4: Clean-up environment + notify user that product is added
        stmt.close();
        conn.close();

        lblStatus.setText("Product added.");
        txtManufacturer.clear();
        txtProName.clear();
        chbProType.getSelectionModel().clearSelection();

        // update list of current products
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

    // clear list to add new products and current products
    productLine.clear();

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
        String name = rs.getString(2);
        String manufacturer = rs.getString(4);
        ItemType type = null;
        for (ItemType it : ItemType.values()) {
          if (it.code.equals(rs.getString(3))) {
            type = it;
          }
        }
        Product product = new Widget(name, manufacturer, type);
        productLine.add(product);
      }

      // STEP 4: Clean-up environment + notify user that product is added
      stmt.close();
      conn.close();

    } catch (ClassNotFoundException e) {
      e.printStackTrace();

    } catch (SQLException e) {
      e.printStackTrace();
    }

    setUpProductLineTable();

  }


  /**
   * Description: records production of existing product by accessing the database and adding to the
   * table.
   */
  // change date to TimeStamp next week (11/2/20)
  public void recordProduction(ActionEvent actionEvent) {
    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/PR";

    //  Database credentials
    final String USER = "";
    final String PASS = "";
    Connection conn = null;
    PreparedStatement stmt = null;

    if (!(lstProduct.getSelectionModel().getSelectedItem() == null)
        && !(cmbQuantity.getSelectionModel().getSelectedItem() == null)) {

      int itemCount = 0;
      int numProduced = Integer.parseInt(cmbQuantity.getSelectionModel().getSelectedItem());

      try {
        // STEP 1: Register JDBC driver
        Class.forName(JDBC_DRIVER);
        //STEP 2: Open a connection
        // empty database password
        conn = DriverManager.getConnection(DB_URL, USER, PASS);

        for (int productionRunProduct = 0; productionRunProduct < numProduced;
            productionRunProduct++) {
          ProductionRecord record = new ProductionRecord(
              lstProduct.getSelectionModel().getSelectedItem(), itemCount++);

          //STEP 3: Execute an insertion of data
          String sql =
              "INSERT INTO ProductionRecord(product_id, serial_num, date_produced) "
                  + "VALUES (?, ?, ?)";

          // method may fail to clean up java.sql.Statement on checked exception
          stmt = conn.prepareStatement(sql);

          stmt.setInt(1, record.getProductID());
          stmt.setString(2, record.getSerialNum());
          stmt.setDate(3, new java.sql.Date(record.getProdDate().getTime()));
          stmt.executeUpdate();

        }

        // STEP 4: Clean-up environment + notify user that product is added
        stmt.close();
        conn.close();

        lblRecord.setText("Production recorded.");
        lstProduct.getSelectionModel().clearSelection();

      } catch (ClassNotFoundException e) {
        e.printStackTrace();

      } catch (SQLException e) {
        e.printStackTrace();
      }


    } else {
      lblRecord.setText("Please fill out all fields \nin order to record production.");
    }
  }

}
