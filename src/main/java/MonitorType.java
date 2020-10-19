public enum MonitorType {
  LCD("LCD"),
  LED("LED");

  public final String type;

  MonitorType(String type) {
    this.type = type;
  }

  private String type() {
    return type;
  }
}
