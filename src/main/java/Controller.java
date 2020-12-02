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

/**
 * Controller for Production Line GUI
 *
 * @author Paul Basso
 */
public class Controller {


  /**
   * TextField to enter product name
   */
  @FXML
  private TextField txtProName;

  /**
   * TextField to enter product manufacturer
   */
  @FXML
  private TextField txtManufacturer;

  /**
   * ChoiceBox to choose product type
   */
  @FXML
  private ChoiceBox<String> chbProType;

  /**
   * Label to indicate if creating product was successful
   */
  @FXML
  private Label lblStatus;

  /**
   * Tableview to show current existing products and their characteristics (id, manufacturer, type)
   */
  @FXML
  private TableView<Product> tblExistingPro;

  /**
   * Table column for product name
   */
  @FXML
  private TableColumn<Product, String> productName;

  /**
   * Table column for product manufacturer
   */
  @FXML
  private TableColumn<Product, String> manufacturer;

  /**
   * Table column for product type
   */
  @FXML
  private TableColumn<Product, ItemType> typeCol;

  /**
   * Table column for product id
   */
  @FXML
  private TableColumn<Product, Integer> productID;

  /**
   * Text Area to show all production line records
   */
  @FXML
  private TextArea prodLog;

  /**
   * ListView to show all products that can be created
   */
  @FXML
  private ListView<Product> lstProduct;

  /**
   * Combobox with values 1-10 and editable to allow user to choose quantity of a product to
   * produce
   */
  @FXML
  private ComboBox<String> cmbQuantity;

  /**
   * Label to indicate if production was successful
   */
  @FXML
  private Label lblRecord;

  /**
   * Employee tab
   */
  @FXML
  private Tab tab4;

  /**
   * TextField to enter employee full name separated by a space
   */
  @FXML
  private TextField empName;

  /**
   * TextField to enter a password for creating an Employee account (must meet requirements)
   */
  @FXML
  private TextField empNewPass;

  /**
   * TextField to enter employee username when logging in
   */
  @FXML
  private TextField empUser;

  /**
   * TextField to enter employee generated password when logging in
   */
  @FXML
  private TextField empPass;

  /**
   * Label to indicate if signup or login were successful
   */
  @FXML
  private Label loginError;

  /**
   * List of Products
   */
  private ObservableList<Product> productLine = FXCollections.observableArrayList();

  /**
   * Check if employee is logged in
   */
  boolean loggedIn = false;

  /**
   * Accessing database
   */
  final Database data = new Database();

  /**
   * Current employee signed in
   */
  Employee employee;

  /**
   * Description: initializes ComboBox, ChoiceBox, sets up Product Line Table, and Production Log
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

  /**
   * Description: initializes TableView for products
   */
  public void setUpProductLineTable() {
    productName.setCellValueFactory(new PropertyValueFactory<>("name"));
    manufacturer.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
    typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
    productID.setCellValueFactory(new PropertyValueFactory<>("id"));
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

  /**
   * @param event Description: adds an employee to the database by getting values from text fields
   *              and inserting them into a database along with generating an email and password.
   *              Disable Employee Tab afterwards.
   */
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

  /**
   * @param event Description: Checks text fields' values and searches through database to see if
   *              employee credentials exist. If so, log in employee and disable Employee tab.
   */
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


