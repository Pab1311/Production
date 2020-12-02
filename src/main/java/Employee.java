import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents an Employee working in the company.
 *
 * @author Paul Basso
 */
public class Employee {

  /**
   * Full name of Employee
   */
  private final StringBuilder name;
  /**
   * Username of Employee generated using the employee's name
   */
  private String username;
  /**
   * Password of Employee
   */
  private final String password;
  /**
   * Email of Employee generated using the employee's name
   */
  private String email;

  Employee(String name, String password) {
    this.name = new StringBuilder(name);
    if (checkName(name)) {
      setUsername(name);
      setEmail(name);
    } else {
      this.username = "default";
      this.email = "user@oracleacademy.Test";
    }
    if (isValidPassword(password)) {
      this.password = password;
    } else {
      this.password = "pw";
    }
  }

  /**
   * Username of Employee generated using the employee's name
   *
   * @param name
   */
  public void setUsername(String name) {
    username =
        name.substring(0, 1).toLowerCase() + name.substring(name.indexOf(" ") + 1).toLowerCase();
  }

  /**
   * Check if employee sent in full name separated by a space
   *
   * @param name
   * @return boolean validName
   */
  public boolean checkName(String name) {
    return name.contains(" ");
  }

  /**
   * Email of Employee generated using the employee's name
   *
   * @param name
   */
  public void setEmail(String name) {
    email = name.substring(0, name.indexOf(" ")).toLowerCase() + "." + name
        .substring(name.indexOf(" ") + 1).toLowerCase() + "@oracleacademy.Test";
  }

  /**
   * Check if employee sent a valid password
   *
   * @param password
   * @return boolean validPassword
   */
  public boolean isValidPassword(String password) {
    Pattern pattern = Pattern.compile("[a-zA-Z0-9]*");
    Matcher matcher = pattern.matcher(password);

    return !password.equals(password.toLowerCase()) && !password.equals(password.toUpperCase())
        && !matcher.matches();
  }

  /**
   * Gets the first and last name of this Employee.
   *
   * @return this Employee's name.
   */
  public StringBuilder getName() {
    return name;
  }

  /**
   * Gets the username of this Employee.
   *
   * @return this Employee's username.
   */
  public String getUsername() {
    return username;
  }

  /**
   * Gets the password of this Employee.
   *
   * @return this Employee's password.
   */
  public String getPassword() {
    return password;
  }

  /**
   * Gets the email of this Employee.
   *
   * @return this Employee's email.
   */
  public String getEmail() {
    return email;
  }

  /**
   * Returns a description of the Employee
   *
   * @return formatted output containing employee details
   */
  @Override
  public String toString() {
    return ("Employee Details \nName : " + name + "\nUsername : " + username + "\nEmail : " + email
        + "\nInitial Password : " + password);
  }


}
