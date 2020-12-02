/**
 * Represents a Product in the Production Line
 *
 * @author Paul Basso
 */

public abstract class Product implements Item {

  /**
   * product id
   */
  public int id;

  /**
   * type of product
   */
  public final ItemType type;

  /**
   * product's manufacturer
   */
  public String manufacturer;

  /**
   * product's name
   */
  public String name;

  public Product(String name, String manufacturer, ItemType type) {
    this.name = name;
    this.manufacturer = manufacturer;
    this.type = type;
  }

  // for creating Products for tableview from database
  public Product(int id, String name, String manufacturer, ItemType type) {
    this.id = id;
    this.name = name;
    this.manufacturer = manufacturer;
    this.type = type;
  }

  /**
   * Gets the id of the product.
   *
   * @return this Product's id.
   */
  public int getId() {
    return id;
  }

  /**
   * sets the name of the product
   *
   * @param name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the name of the product.
   *
   * @return this Product's name.
   */
  public String getName() {
    return name;
  }

  /**
   * sets the manufacturer of the product
   *
   * @param manufacturer
   */
  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  /**
   * Gets the manufacturer of the product.
   *
   * @return this Product's manufacturer.
   */
  public String getManufacturer() {
    return manufacturer;
  }

  /**
   * Gets the type of the product.
   *
   * @return this Product's type.
   */
  public ItemType getType() {
    return type;
  }

  /**
   * Returns a description of the Product
   *
   * @return formatted output containing product details
   */
  @Override
  public String toString() {
    return ("Name: " + name + "\nManufacturer: "
        + manufacturer + "\nType: " + type);
  }

}

/**
 * Implementation of Product class
 *
 * @author Paul Basso
 */
class Widget extends Product {

  public Widget(String name, String manufacturer, ItemType type) {
    super(name, manufacturer, type);
  }

  public Widget(int id, String name, String manufacturer, ItemType type) {
    super(id, name, manufacturer, type);
  }

}