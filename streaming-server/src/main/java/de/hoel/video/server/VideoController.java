package de.hoel.video.server;

import java.nio.file.Paths;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/videos")
public class VideoController
{
  
  protected final Logger logger = LoggerFactory.getLogger(this.getClass());
  
  @RequestMapping(value = "/test", method = RequestMethod.GET)
  public void getTestVideo(HttpServletRequest request, HttpServletResponse response) throws Exception
  {
    
    MultipartFileSender.fromPath(Paths.get("C:\\Users\\0400630\\Desktop\\Hons\\video-streaming\\videos\\bbb.mp4"))
        .with(request)
        .with(response)
        .serveResource();
    
  }
}
