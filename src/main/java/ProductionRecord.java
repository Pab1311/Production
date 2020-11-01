import java.util.Date;

public class ProductionRecord {

  int productionNumber;
  int productID;
  String serialNumber;
  Date dateProduced;

  ProductionRecord(int productID) {
    this.productID = productID;
    this.productionNumber = 0;
    this.serialNumber = "0";
    this.dateProduced = new Date();
  }

  ProductionRecord(int productionNumber, int productID, String serialNumber, Date dateProduced) {
    this.productionNumber = productionNumber;
    this.productID = productID;
    this.serialNumber = serialNumber;
    this.dateProduced = dateProduced;
  }

  ProductionRecord(Product product, int count) {
    // need to add five digits for item types
    serialNumber = product.getManufacturer().substring(0, 3) + product.type.code + String
        .format("%05d", count);
    productID = 0;
    productionNumber = 0;
    dateProduced = new Date();
  }

  public void setProductionNum(int productionNumber) {
    this.productionNumber = productionNumber;
  }

  public void setProductID(int productID) {
    this.productID = productID;
  }

  public void setSerialNum(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public void setProdDate(Date dateProduced) {
    this.dateProduced = dateProduced;
  }

  public int getProductionNum() {
    return productionNumber;
  }

  public int getProductID() {
    return productID;
  }

  public String getSerialNum() {
    return serialNumber;
  }

  public Date getProdDate() {
    return dateProduced;
  }

  public String toString() {
    return ("Prod. Num: " + productionNumber + " Product ID: " + productID + " Serial Num: "
        + serialNumber + " Date: " + dateProduced);
  }

}