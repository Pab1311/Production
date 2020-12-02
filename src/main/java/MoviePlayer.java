/**
 * Represents a product of type Visual
 *
 * @author Paul Basso
 */

public class MoviePlayer extends Product implements MultimediaControl {

  final Screen screen;
  final MonitorType monitorType;

  MoviePlayer(String name, String manufacturer, Screen screen, MonitorType monitorType) {
    super(name, manufacturer, ItemType.VISUAL);
    this.screen = screen;
    this.monitorType = monitorType;
  }

  /**
   * Simulates the action of hitting play on an MoviePlayer.
   */
  public void play() {
    System.out.println("Playing movie");
  }

  /**
   * Simulates the action of hitting stop on an MoviePlayer.
   */
  public void stop() {
    System.out.println("Stopping movie");
  }

  /**
   * Simulates the action of hitting previous movie on an MoviePlayer.
   */
  public void previous() {
    System.out.println("Previous movie");
  }

  /**
   * Simulates the action of hitting next movie on an MoviePlayer.
   */
  public void next() {
    System.out.println("Next movie");
  }

  /**
   * Returns a description of the MoviePlayer
   *
   * @return formatted output containing screen details and monitor type
   */
  @Override
  public String toString() {
    return (super.toString() + "\n" + screen.toString() + "\nMonitor Type: " + monitorType);
  }

}
