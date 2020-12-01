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
  private TableColumn<Product, Integer> productId;

  @FXML
  private TextArea prodLog;

  @FXML
  private ListView<Product> lstProduct;

  @FXML
  private ComboBox<String> cmbQuantity;

  @FXML
  private Label lblRecord;

  @FXML
  private Tab tab4;

  @FXML
  private TextField empName;

  @FXML
  private TextField empNewPass;

  @FXML
  private TextField empUser;

  @FXML
  private TextField empPass;

  @FXML
  private Label loginError;

  // list of products
  private ObservableList<Product> productLine = FXCollections.observableArrayList();

  // check if employee is logged in
  boolean loggedIn = false;

  // database
  final Database data = new Database();

  // current employee signed in
  Employee employee;

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
  }

  /**
   * Description: initializes production log TextView
   */
  public void setUpProductionRecord() {
    prodLog.clear();
    // list of production records
    ArrayList<ProductionRecord> productRecords = data.accessProductionRecord();
    for (ProductionRecord productRecord : productRecords) {
      prodLog.appendText(productRecord.toString() + "\n");
    }
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
    productName.setCellValueFactory(new PropertyValueFactory<>("name"));
    manufacturer.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
    typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
    productId.setCellValueFactory(new PropertyValueFactory<>("id"));
    tblExistingPro.setItems(productLine);
  }

  /**
   * Description: takes user input, verifies the user inputted something on all required fields, and
   * inserts the data into the PRODUCT table by accessing the database.
   */

  @FXML
  void addProduct(ActionEvent event) {

    if (!(txtProName.getText().isBlank()) && !(txtManufacturer.getText().isBlank())
        && !(chbProType.getSelectionModel().getSelectedItem() == null)) {
      Widget item = new Widget(txtProName.getText(), txtManufacturer.getText(),
          ItemType.valueOf(chbProType.getSelectionModel().getSelectedItem()));

      data.addProduct(item);
      lblStatus.setText("Product added.");
      txtManufacturer.clear();
      txtProName.clear();
      chbProType.getSelectionModel().clearSelection();

      // update list of current products
      showProductList();
      setUpLstProduct();

    } else {
      lblStatus.setText("Please fill out all fields in order to add product.");
    }
  }


  /**
   * Description: displays current list of products in order of name, type, and manufacturer by
   * accessing the database.
   */

  public void showProductList() {
    // clear list to add new products and current products
    productLine.clear();
    productLine = data.accessProducts();
    setUpProductLineTable();
  }


  /**
   * @param actionEvent Description: records production of existing product by accessing the
   *                    database and adding to the table.
   */
  public void recordProduction(ActionEvent actionEvent) {

    if (!(lstProduct.getSelectionModel().getSelectedItem() == null)
        && !(cmbQuantity.getSelectionModel().getSelectedItem() == null)) {
      if (loggedIn) {
        int currentCount = data
            .getMaxSerialNum(lstProduct.getSelectionModel().getSelectedItem().getId());
        int numProduced = Integer.parseInt(cmbQuantity.getSelectionModel().getSelectedItem());

        for (int productionRunProduct = 0; productionRunProduct < numProduced;
            productionRunProduct++) {
          // checking if serial num already exists (if so increment and continue from last point)
          if (currentCount == -1) {
            ProductionRecord record;
            record = new ProductionRecord(
                lstProduct.getSelectionModel().getSelectedItem(), ++currentCount);
            data.recordProduction(record, employee);
          } else {
            ProductionRecord record;
            record = new ProductionRecord(
                lstProduct.getSelectionModel().getSelectedItem(), ++currentCount);

            data.recordProduction(record, employee);
          }
        }

        lblRecord.setText("Production recorded.");
        lstProduct.getSelectionModel().clearSelection();

        setUpProductionRecord();
      } else {
        lblRecord.setText("You must be logged in as an employee\n in order to record production.");
      }
    } else {
      lblRecord.setText("Please fill out all fields \nin order to record production.");
    }
  }

  @FXML
  void enrollEmployee(ActionEvent event) {
    if (!(empName.getText().isBlank()) && !(empNewPass.getText().isBlank())
    ) {
      String name = empName.getText();
      String password = empNewPass.getText();
      if (data.checkIfNameExists(name)) {
        loginError.setText("An account for this person already exists.");
      } else {
        Employee user = new Employee(name, password);
        data.addEmployee(user);
        empName.clear();
        empNewPass.clear();
        loggedIn = true;
        employee = user;
        tab4.setDisable(true);
      }
    } else {
      loginError.setText("Please fill out all fields before attempting to sign up.");
    }
  }

  @FXML
  void loginEmployee(ActionEvent event) {
    if (!(empUser.getText().isBlank()) && !(empPass.getText().isBlank())
    ) {

      String username = empUser.getText();
      String password = empPass.getText();

      if (data.checkIfEmployeeExists(username, password)) {
        loggedIn = true;
        employee = new Employee(data.getEmployeeName(username), password);
        empUser.clear();
        empPass.clear();
        tab4.setDisable(true);
      } else if (data.checkIfUsernameExists(username)) {
        loginError.setText("Password is incorrect. Please try again.");
      } else {
        loginError.setText("Account does not exist. Please try again or \ncreate an account.");
      }
    } else {
      loginError.setText("Please fill out all fields before attempting to login.");
    }

  }
}


