public class AudioPlayer extends Product implements MultimediaControl {

  String supportedAudioFormats;
  String supportedPlaylistFormats;

  AudioPlayer(String name, String manufacturer, String supportedAudioFormats,
      String supportedPlaylistFormats) {
    super(name, manufacturer, ItemType.AUDIO);
    this.supportedAudioFormats = supportedAudioFormats;
    this.supportedPlaylistFormats = supportedPlaylistFormats;
  }

  public void play() {
    System.out.println("Playing");
  }

  public void stop() {
    System.out.println("Stopping");
  }

  public void previous() {
    System.out.println("Previous");
  }

  public void next() {
    System.out.println("Next");
  }

  public String toString() {
    return (super.toString() + "\nSupported Audio Formats: " + supportedAudioFormats
        + "\nSupported Playlist Formats: " + supportedPlaylistFormats);
  }
}
