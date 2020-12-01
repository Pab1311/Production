import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Employee {

  private final StringBuilder name;
  private String username;
  private final String password;
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

  public void setUsername(String name) {
    username =
        name.substring(0, 1).toLowerCase() + name.substring(name.indexOf(" ") + 1).toLowerCase();
  }

  public boolean checkName(String name) {
    return name.contains(" ");
  }

  public void setEmail(String name) {
    email = name.substring(0, name.indexOf(" ")).toLowerCase() + "." + name
        .substring(name.indexOf(" ") + 1).toLowerCase() + "@oracleacademy.Test";
  }

  public boolean isValidPassword(String password) {
    Pattern pattern = Pattern.compile("[a-zA-Z0-9]*");
    Matcher matcher = pattern.matcher(password);

    return !password.equals(password.toLowerCase()) && !password.equals(password.toUpperCase())
        && !matcher.matches();
  }

  public StringBuilder getName() {
    return name;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public String toString() {
    return ("Employee Details \nName : " + name + "\nUsername : " + username + "\nEmail : " + email
        + "\nInitial Password : " + password);
  }


}
