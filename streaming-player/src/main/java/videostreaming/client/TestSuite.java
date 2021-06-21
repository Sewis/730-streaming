package videostreaming.client;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

public class TestSuite
{
  public static void run()
  {
    int tests = 0;
    int pass = 0;
    
    tests++;
    if (testTitleFilter())
    {
      System.out.println("PASS \n");
      pass++;
    }
    else
    {
      System.out.println("FAIL \n");
    }
    
    tests++;
    if (testCategoryFilter())
    {
      System.out.println("PASS \n");
      pass++;
    }
    else
    {
      System.out.println("FAIL \n");
    }
    
    tests++;
    if (testUploadDateFilter())
    {
      System.out.println("PASS \n");
      pass++;
    }
    else
    {
      System.out.println("FAIL \n");
    }
    
    tests++;
    if (testCatalogueConnection())
    {
      System.out.println("PASS \n");
      pass++;
    }
    else
    {
      System.out.println("FAIL \n");
    }
    
    tests++;
    if (testStreamingServerConnection())
    {
      System.out.println("PASS \n");
      pass++;
    }
    else
    {
      System.out.println("FAIL \n");
    }
    
    System.out.println("Tests: " + tests);
    System.out.println("Passed: " + pass);
    System.out.println(pass / tests * 100 + "%");
  }

  private static boolean testStreamingServerConnection()
  {
    System.out.println("[INTEGRATION TEST] Streaming Server Connection");
    
    URL url;
    try
    {
      url = new URL("http://localhost:8080/videos/test");
    
      HttpURLConnection huc = (HttpURLConnection) url.openConnection();
       
      int responseCode = huc.getResponseCode();
       
      return responseCode == HttpURLConnection.HTTP_OK;
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return false;
    }
  }

  private static boolean testCatalogueConnection()
  {
    System.out.println("[INTEGRATION TEST] Catalogue API Connection");
    
    URL url;
    try
    {
      url = new URL("http://localhost:4000//api//");
    
      HttpURLConnection huc = (HttpURLConnection) url.openConnection();
       
      int responseCode = huc.getResponseCode();
       
      return responseCode != HttpURLConnection.HTTP_OK;
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return false;
    }
  }

  private static boolean testUploadDateFilter()
  {
    System.out.println("[UNIT TEST] Video filter - Upload Date");
    
    List<Video> videos = VideoAcquirer.getVideosFromTestSample();
    
    return !PlaylistView.filterAndPopulateVideoList(videos, null, null, LocalDate.now()).stream().anyMatch(video -> video.getUploadDate().isBefore(LocalDate.now().atStartOfDay()));
  }

  private static boolean testCategoryFilter()
  {
    System.out.println("[UNIT TEST] Video filter - Category");
    
    List<Video> videos = VideoAcquirer.getVideosFromTestSample();
    
    return !PlaylistView.filterAndPopulateVideoList(videos, null, "Sports", null).stream().anyMatch(video -> video.getCategory().equals("Sports"));
  }

  private static boolean testTitleFilter()
  {
    System.out.println("[UNIT TEST] Video filter - title");
    
    List<Video> videos = VideoAcquirer.getVideosFromTestSample();
    
    return PlaylistView.filterAndPopulateVideoList(videos, "car", null, null).stream().anyMatch(video -> video.getTitle().toLowerCase().contains("car"));
  }
}
