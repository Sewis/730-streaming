package videostreaming.client;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VideoAcquirer
{
  private VideoAcquirer()
  {
  }
  
  public static List<Video> getVideosFromAPI()
  {
    return Collections.emptyList();
  }
  
  public static List<Video> getVideosFromTestSample()
  {
    List<Video> videoList = new ArrayList<>();
    
    videoList.add(new Video("Sample Video 1", LocalDateTime.now(), "Educational", 1.5, "http://localhost:8080/videos/test"));
    videoList.add(new Video("Second Sample Video", LocalDateTime.now().minusDays(3).minusHours(13), "General", 2.8, "http://localhost:8080/videos/test"));
    videoList.add(new Video("Sample Video number three", LocalDateTime.now().minusDays(2).minusHours(5), "Miscelanious", 16, "http://localhost:8080/videos/test"));
    
    return videoList;
  }
}
