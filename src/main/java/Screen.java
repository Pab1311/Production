/**
 * Represents a Screen used by a Product
 *
 * @author Paul Basso
 */


public class Screen implements ScreenSpec {

  /**
   * Screen resolution
   */
  final String resolution;

  /**
   * Screen refresh rate
   */
  final int refreshrate;

  /**
   * Screen response time
   */
  final int responsetime;

  Screen(String resolution, int refreshrate, int responsetime) {
    this.resolution = resolution;
    this.refreshrate = refreshrate;
    this.responsetime = responsetime;
  }

  /**
   * Gets the resolution of the Screen.
   *
   * @return this Screen's resolution.
   */
  public String getResolution() {
    return resolution;
  }

  /**
   * Gets the refresh rate of the Screen.
   *
   * @return this Screen's refresh rate.
   */
  public int getRefreshRate() {
    return refreshrate;
  }

  /**
   * Gets the response time of the Screen.
   *
   * @return this Screen's response time.
   */
  public int getResponseTime() {
    return responsetime;
  }

  /**
   * Returns a description of the Screen
   *
   * @return formatted output containing Screen details
   */
  public String toString() {
    return ("Screen:\nResolution: " + resolution + "\nRefresh rate: " + refreshrate
        + "\nResponse time: " + responsetime);
  }

}
