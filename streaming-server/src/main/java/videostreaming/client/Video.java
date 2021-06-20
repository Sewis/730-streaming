package videostreaming.client;

import java.time.LocalDateTime;


public class Video
{
  private String title;
  private LocalDateTime uploadDate;
  private String category;
  private double length;
  private String urlID;
  
  public Video(String title, LocalDateTime uploadDate, String category, double length, String urlID)
  {
    super();
    this.title = title;
    this.uploadDate = uploadDate;
    this.category = category;
    this.length = length;
    this.urlID = urlID;
  }
  
  public String getTitle()
  {
    return title;
  }
  
  public void setTitle(String title)
  {
    this.title = title;
  }
  
  public LocalDateTime getUploadDate()
  {
    return uploadDate;
  }
  
  public void setUploadDate(LocalDateTime uploadDate)
  {
    this.uploadDate = uploadDate;
  }
  
  public String getCategory()
  {
    return category;
  }
  
  public void setCategory(String category)
  {
    this.category = category;
  }
  
  public double getLength()
  {
    return length;
  }
  
  public void setLength(double length)
  {
    this.length = length;
  }
  
  public String getUrlID()
  {
    return urlID;
  }
  
  public void setUrlID(String urlID)
  {
    this.urlID = urlID;
  }
}
