/**
 * Represents a product of type Audio
 *
 * @author Paul Basso
 */


public class AudioPlayer extends Product implements MultimediaControl {

  /**
   * The types of supported Audio formats
   */
  final String supportedAudioFormats;

  /**
   * The types of supported Playlist formats
   */
  final String supportedPlaylistFormats;

  AudioPlayer(String name, String manufacturer, String supportedAudioFormats,
      String supportedPlaylistFormats) {
    super(name, manufacturer, ItemType.AUDIO);
    this.supportedAudioFormats = supportedAudioFormats;
    this.supportedPlaylistFormats = supportedPlaylistFormats;
  }

  /**
   * Simulates the action of hitting play on an Audioplayer.
   */
  public void play() {
    System.out.println("Playing");
  }

  /**
   * Simulates the action of hitting stop on an Audioplayer.
   */
  public void stop() {
    System.out.println("Stopping");
  }

  /**
   * Simulates the action of hitting previous song on an Audioplayer.
   */
  public void previous() {
    System.out.println("Previous");
  }

  /**
   * Simulates the action of hitting next song on an Audioplayer.
   */
  public void next() {
    System.out.println("Next");
  }

  /**
   * Returns a description of the Audioplayer
   *
   * @return formatted output containing all supportedAudioFormats and all supportedPlayListFormats
   */
  @Override
  public String toString() {
    return (super.toString() + "\nSupported Audio Formats: " + supportedAudioFormats
        + "\nSupported Playlist Formats: " + supportedPlaylistFormats);
  }
}
