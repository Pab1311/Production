public abstract class Product implements Item {

  public int id;
  public String type;
  public String manufacturer;
  public String name;

  public int getId() {
    return id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public Product(int id, String type, String manufacturer, String name) {
    this.id = id;
    this.type = type;
    this.manufacturer = manufacturer;
    this.name = name;
  }

  public String toString() {
    return ("Name: " + name + "\nManufacturer: "
        + manufacturer + "\nType: " + type);
  }


}