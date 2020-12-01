public class Screen implements ScreenSpec {

  final String resolution;
  final int refreshrate;
  final int responsetime;

  Screen(String resolution, int refreshrate, int responsetime) {
    this.resolution = resolution;
    this.refreshrate = refreshrate;
    this.responsetime = responsetime;
  }

  public String getResolution() {
    return resolution;
  }

  public int getRefreshRate() {
    return refreshrate;
  }

  public int getResponseTime() {
    return responsetime;
  }

  public String toString() {
    return ("Screen:\nResolution: " + resolution + "\nRefresh rate: " + refreshrate
        + "\nResponse time: " + responsetime);
  }

}
