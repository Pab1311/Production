public abstract class Product implements Item {

  public int id;
  public ItemType type;
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

  public Product(String name, String manufacturer, ItemType type) {
    this.name = name;
    this.manufacturer = manufacturer;
    this.type = type;
  }

  public String toString() {
    return ("Name: " + name + "\nManufacturer: "
        + manufacturer + "\nType: " + type.code);
  }

}

class Widget extends Product {

  public Widget(String name, String manufacturer, ItemType type) {
    super(name, manufacturer, type);
  }
}