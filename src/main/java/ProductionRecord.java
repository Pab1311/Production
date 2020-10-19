import java.util.Date;

public class ProductionRecord {

  int productionNumber;
  int productID;
  String serialNumber;
  Date dateProduced;

  ProductionRecord(int productionNumber, int productID, String serialNumber, Date dateProduced) {
    this.productionNumber = productionNumber;
    this.productID = productID;
    this.serialNumber = serialNumber;
    this.dateProduced = dateProduced;
  }

  public void setProductionNumber(int productionNumber) {
    this.productionNumber = productionNumber;
  }

  public void setProductID(int productID) {
    this.productID = productID;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public void setDateProduced(Date dateProduced) {
    this.dateProduced = dateProduced;
  }

  public int getProductionNumber() {
    return productionNumber;
  }

  public int getProductID() {
    return productID;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  public Date getDateProduced() {
    return dateProduced;
  }

  public String toString() {
    return ("Prod. Num: " + productionNumber + "\nProduct ID: " + productID + "\nSerial Num: "
        + serialNumber + "\n Date: " + dateProduced);
  }

}
