import java.util.Date;

/**
 * Represents a Production Record when a product is produced in the Production Line
 *
 * @author Paul Basso
 */

public class ProductionRecord {

  /**
   * the production's number (everytime a product is produced this is incremented by 1 in the
   * database)
   */
  int productionNumber;

  /**
   * the production's id which matches the product id
   */
  int productId;

  /**
   * the production's unique serial number containing the type, manufacturer, and 5 digits.
   */
  String serialNumber;

  /**
   * the date the production occurred
   */
  Date dateProduced;

  ProductionRecord(int productId) {
    this.productId = productId;
    this.productionNumber = 0;
    this.serialNumber = "0";
    this.dateProduced = new Date();
  }

  ProductionRecord(int productionNumber, int productId, String serialNumber, Date dateProduced) {
    this.productionNumber = productionNumber;
    this.productId = productId;
    this.serialNumber = serialNumber;
    this.dateProduced = dateProduced;
  }

  ProductionRecord(Product product, int count) {
    serialNumber = product.getManufacturer().substring(0, 3) + product.type.code + String
        .format("%05d", count);
    productId = product.getId();
    productionNumber = 0;
    dateProduced = new Date();
  }

  /**
   * sets the production number of the ProductionRecord
   *
   * @param productionNumber
   */
  public void setProductionNum(int productionNumber) {
    this.productionNumber = productionNumber;
  }

  /**
   * sets the production id of the ProductionRecord
   *
   * @param productId
   */
  public void setProductId(int productId) {
    this.productId = productId;
  }

  /**
   * sets the serial number of the ProductionRecord
   *
   * @param serialNumber
   */
  public void setSerialNum(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  /**
   * sets the date of the ProductionRecord
   *
   * @param dateProduced
   */
  public void setProdDate(Date dateProduced) {
    this.dateProduced = dateProduced;
  }

  /**
   * Gets the production number of the ProductionRecord.
   *
   * @return this ProductionRecord's production number.
   */
  public int getProductionNum() {
    return productionNumber;
  }

  /**
   * Gets the production id of the ProductionRecord.
   *
   * @return this ProductionRecord's product id.
   */
  public int getProductId() {
    return productId;
  }

  /**
   * Gets the serial number of the ProductionRecord.
   *
   * @return this ProductionRecord's serial number.
   */
  public String getSerialNum() {
    return serialNumber;
  }

  /**
   * Gets the date that the ProductionRecord was produced.
   *
   * @return this ProductionRecord's production date.
   */
  public Date getProdDate() {
    return dateProduced;
  }

  /**
   * Returns a description of the ProductionRecord
   *
   * @return formatted output containing ProductionRecord details
   */
  public String toString() {
    Database data = new Database();

    if (productId > 0) {
      Product prod = data.getProductByID(productId);
      String employeeName = data.getEmployeeByProdNum(productionNumber);

      if (prod != null) {
        return ("Prod. Num: " + productionNumber + " Product Name: " + prod.getName()
            + " Serial Num: "
            + serialNumber + " Date: " + dateProduced + " Employee: " + employeeName);
      }
    }
    return ("Prod. Num: " + productionNumber + " Product ID: " + productId + " Serial Num: "
        + serialNumber + " Date: " + dateProduced);

  }
}