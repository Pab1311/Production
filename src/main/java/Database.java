import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Database {

  private static Connection conn;
  final String JDBC_DRIVER = "org.h2.Driver";
  final String DB_URL = "jdbc:h2:./res/PR";

  //  Database credentials
  final String USER = "";
  final String PASS = reverseString(readFile());

  public String reverseString(String password) {
    if (password.isEmpty()) {
      return password;
    }

    return reverseString(password.substring(1)) + password.charAt(0);
  }

  public String readFile() {
    String password = "";

    try {
      File myObj = new File("src/main/java/databasePassword.txt");
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        password = myReader.nextLine();
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

    return password;
  }

  public ArrayList<ProductionRecord> accessProductionRecord() {
    ArrayList<ProductionRecord> records = new ArrayList<>();
    Statement stmt;

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
      String searchSql = "SELECT * FROM PRODUCTIONRECORD";

      ResultSet rs = stmt.executeQuery(searchSql);

      // found on stack overflow for accessing elements of each row
      while (rs.next()) {
        int productNum = rs.getInt(1);
        int id = rs.getInt(2);
        String serialNumber = rs.getString(3);
        Timestamp date = rs.getTimestamp(4);

        ProductionRecord record = new ProductionRecord(productNum, id, serialNumber, date);
        records.add(record);
      }

      // STEP 4: Clean-up environment + notify user that product is added
      stmt.close();
      conn.close();

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();

    }

    return records;
  }

  public ObservableList<Product> accessProducts() {
    ObservableList<Product> products = FXCollections.observableArrayList();
    Statement stmt;

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
        int id = rs.getInt(1);
        String name = rs.getString(2);
        String manufacturer = rs.getString(4);
        ItemType type = null;
        for (ItemType it : ItemType.values()) {
          if (it.code.equals(rs.getString(3))) {
            type = it;
          }
        }
        Product product = new Widget(id, name, manufacturer, type);
        products.add(product);
      }

      // STEP 4: Clean-up environment + notify user that product is added
      stmt.close();
      conn.close();

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();

    }

    return products;
  }

  public Widget getProductByID(int id) {
    PreparedStatement stmt;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);
      //STEP 2: Open a connection
      // empty database password
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 3: Execute an insertion of data
      String sql = "SELECT * FROM Product "
          + "WHERE id = ?";

      // method may fail to clean up java.sql.Statement on checked exception
      stmt = conn.prepareStatement(sql);

      stmt.setInt(1, id);

      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        // get properties of product

        String name = rs.getString("name");
        ItemType type = null;
        for (ItemType it : ItemType.values()) {
          if (it.code.equals(rs.getString("type"))) {
            type = it;
          }
        }
        String manufacturer = rs.getString("manufacturer");

        return new Widget(id, name, manufacturer, type);
      }

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();

    }

    return null;


  }

  public void addProduct(Widget item) {
    PreparedStatement stmt;

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

      stmt.setString(1, item.getType().code);
      stmt.setString(2, item.getManufacturer());
      stmt.setString(3, item.getName());
      stmt.executeUpdate();

      // STEP 4: Clean-up environment + notify user that product is added
      stmt.close();
      conn.close();

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();

    }
  }

  public void recordProduction(ProductionRecord record, Employee employee) {
    PreparedStatement stmt;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);
      //STEP 2: Open a connection
      // empty database password
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 3: Execute an insertion of data
      String sql =
          "INSERT INTO ProductionRecord(product_id, serial_num, date_produced, employee_name) "
              + "VALUES (?, ?, ?, ?)";

      Timestamp time = new Timestamp(System.currentTimeMillis());
      // method may fail to clean up java.sql.Statement on checked exception
      stmt = conn.prepareStatement(sql);

      stmt.setInt(1, record.getProductId());
      stmt.setString(2, record.getSerialNum());
      stmt.setTimestamp(3, time);
      stmt.setString(4, String.valueOf(employee.getName()));
      stmt.executeUpdate();

      // STEP 4: Clean-up environment + notify user that product is added
      stmt.close();
      conn.close();

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();

    }
  }


  public int getMaxSerialNum(int productID) {
    PreparedStatement stmt;
    int number = -1;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);
      //STEP 2: Open a connection
      // empty database password
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 3: Execute an insertion of data
      String sql = "SELECT * FROM ProductionRecord "
          + "WHERE product_id = ?";

      // method may fail to clean up java.sql.Statement on checked exception
      stmt = conn.prepareStatement(sql);

      stmt.setInt(1, productID);

      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        String serialNum = rs.getString("serial_num");
        String nums = serialNum.substring(5);
        number = Integer.parseInt(nums);
      }

      if (number >= 0) {
        return number;
      }

      // STEP 4: Clean-up environment + notify user that product is added
      stmt.close();
      conn.close();


    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();

    }

    return number;

  }

  public void addEmployee(Employee user) {
    PreparedStatement stmt;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);
      //STEP 2: Open a connection
      // empty database password
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 3: Execute an insertion of data
      String sql = "INSERT INTO Employee(name, username, employee_password, email) "
          + "VALUES (?, ?, ?, ?)";

      // method may fail to clean up java.sql.Statement on checked exception
      stmt = conn.prepareStatement(sql);

      stmt.setString(1, String.valueOf(user.getName()));
      stmt.setString(2, user.getUsername());
      stmt.setString(3, user.getPassword());
      stmt.setString(4, user.getEmail());
      stmt.executeUpdate();

      // STEP 4: Clean-up environment + notify user that product is added
      stmt.close();
      conn.close();

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();

    }
  }

  public boolean checkIfEmployeeExists(String username, String password) {
    PreparedStatement stmt;
    boolean employeeExists = false;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);
      //STEP 2: Open a connection
      // empty database password
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 3: Execute an insertion of data
      String sql = "SELECT * FROM Employee "
          + "WHERE username = ? AND employee_password = ?";

      // method may fail to clean up java.sql.Statement on checked exception
      stmt = conn.prepareStatement(sql);

      stmt.setString(1, username);
      stmt.setString(2, password);

      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        employeeExists = true;
      }

      // STEP 4: Clean-up environment + notify user that product is added
      stmt.close();
      conn.close();


    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();

    }

    return employeeExists;

  }

  public boolean checkIfNameExists(String name) {
    PreparedStatement stmt;
    boolean nameExists = false;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);
      //STEP 2: Open a connection
      // empty database password
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 3: Execute an insertion of data
      String sql = "SELECT * FROM Employee "
          + "WHERE name = ?";

      // method may fail to clean up java.sql.Statement on checked exception
      stmt = conn.prepareStatement(sql);

      stmt.setString(1, name);

      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        nameExists = true;
      }

      // STEP 4: Clean-up environment + notify user that product is added
      stmt.close();
      conn.close();


    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();

    }

    return nameExists;

  }

  public boolean checkIfUsernameExists(String username) {
    PreparedStatement stmt;
    boolean usernameExists = false;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);
      //STEP 2: Open a connection
      // empty database password
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 3: Execute an insertion of data
      String sql = "SELECT * FROM Employee "
          + "WHERE username = ?";

      // method may fail to clean up java.sql.Statement on checked exception
      stmt = conn.prepareStatement(sql);

      stmt.setString(1, username);

      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        usernameExists = true;
      }

      // STEP 4: Clean-up environment + notify user that product is added
      stmt.close();
      conn.close();


    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();

    }

    return usernameExists;

  }

  public String getEmployeeName(String username) {

    PreparedStatement stmt;
    String name = "";

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);
      //STEP 2: Open a connection
      // empty database password
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 3: Execute an insertion of data
      String sql = "SELECT * FROM Employee "
          + "WHERE username = ?";

      // method may fail to clean up java.sql.Statement on checked exception
      stmt = conn.prepareStatement(sql);

      stmt.setString(1, username);

      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        name = rs.getString(1);
      }

      // STEP 4: Clean-up environment + notify user that product is added
      stmt.close();
      conn.close();

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();

    }

    return name;

  }

  public String getEmployeeByProdNum(int productNum) {
    PreparedStatement stmt;
    String name = "";

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);
      //STEP 2: Open a connection
      // empty database password
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 3: Execute an insertion of data
      String sql = "SELECT * FROM ProductionRecord "
          + "WHERE production_num = ?";

      // method may fail to clean up java.sql.Statement on checked exception
      stmt = conn.prepareStatement(sql);

      stmt.setInt(1, productNum);

      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        name = rs.getString(5);
      }

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();

    }

    return name;
  }
}



