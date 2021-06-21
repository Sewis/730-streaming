package videostreaming.server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.servlet.http.HttpServletResponse;
import videostreaming.server.MultipartFileSender.Range;

public class TestSuite
{
  public static void run()
  {
    int tests = 0;
    int pass = 0;
    
    tests++;
    if (testStorageAccess())
    {
      System.out.println("PASS \n");
      pass++;
    }
    
    tests++;
    if (testReadAccess())
    {
      System.out.println("PASS \n");
      pass++;
    }
    
    tests++;
    if (testValidVideo())
    {
      System.out.println("PASS \n");
      pass++;
    }
    
    tests++;
    if (testByteSegment())
    {
      System.out.println("PASS \n");
      pass++;
    }
    
    System.out.println("Tests: " + tests);
    System.out.println("Passed: " + pass);
    System.out.println(pass / tests * 100 + "%");
  }
  
  private static boolean testStorageAccess()
  {
    System.out.println("[INTEGRATION TEST] File Storage Access");
    
    return Files.exists(Paths.get("C:\\Users\\0400630\\Desktop\\Hons\\video-streaming\\videos\\boxing.mp4"));
  }
  
  private static boolean testReadAccess()
  {
    System.out.println("[INTEGRATION TEST] Read Access on System");
    
    try
    {
      Files.getLastModifiedTime(Paths.get("C:\\Users\\0400630\\Desktop\\Hons\\video-streaming\\videos\\boxing.mp4"));
    }
    catch (IOException e)
    {
      return false;
    }
    return true;
  }
  
  private static boolean testValidVideo()
  {
    System.out.println("[INTEGRATION TEST] Valid video file read");
    
    try
    {
      return Files.size(Paths.get("C:\\Users\\0400630\\Desktop\\Hons\\video-streaming\\videos\\boxing.mp4")) > 0;
    }
    catch (IOException e)
    {
      e.printStackTrace();
      return false;
    }
  }
  
  private static boolean testByteSegment()
  {
    System.out.println("[UNIT TEST] Video conversion into byte segments");
    
    try
    {
      Long length = Files.size(Paths.get("C:\\Users\\0400630\\Desktop\\Hons\\video-streaming\\videos\\boxing.mp4"));
      Range full = new Range(0, length - 1, length);
      return full.length > 0;
    }
    catch (IOException e)
    {
      e.printStackTrace();
      return false;
    }
  }
}
