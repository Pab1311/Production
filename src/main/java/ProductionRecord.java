import java.util.Date;

public class ProductionRecord {

  int productionNumber;
  int productId;
  String serialNumber;
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

  public void setProductionNum(int productionNumber) {
    this.productionNumber = productionNumber;
  }

  public void setProductId(int productId) {
    this.productId = productId;
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

  public int getProductId() {
    return productId;
  }

  public String getSerialNum() {
    return serialNumber;
  }

  public Date getProdDate() {
    return dateProduced;
  }

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