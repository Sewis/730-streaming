package videostreaming.client;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class PlaylistView extends BorderPane
{
  List<Video> videoList;
  VBox playlistVBox;
  
  public PlaylistView()
  {
    Button a = new Button("Open Player");
    
    a.setOnAction(e ->
    {
      App.openVideoPlayer(VideoAcquirer.getVideosFromTestSample().get(0));
    });
    
    Application.setUserAgentStylesheet(Application.STYLESHEET_CASPIAN);
    
    videoList = VideoAcquirer.getVideosFromTestSample();
    
    playlistVBox = new VBox();
    setCenter(playlistVBox);
    playlistVBox.setPadding(new Insets(15));
    
    setTop(setupFilters());
    populateVideoList(videoList);
  }
  
  private Node setupFilters()
  {
    TextField titleFilter = new TextField();
    ComboBox<String> categoryFilter = new ComboBox<>();
    categoryFilter.getItems().add("");
    categoryFilter.getItems().addAll(videoList.stream().map(video -> video.getCategory()).collect(Collectors.toList()));
    DatePicker datePicker = new DatePicker();
    datePicker.setOnAction(t ->
    {
      filterAndPopulateVideoList(titleFilter.getText(), categoryFilter.getSelectionModel().getSelectedItem(), datePicker.getValue());
    });
    
    titleFilter.textProperty().addListener((obs, ov, nv) ->
    {
      filterAndPopulateVideoList(titleFilter.getText(), categoryFilter.getSelectionModel().getSelectedItem(), datePicker.getValue());
    });
    
    categoryFilter.setOnAction(e ->
    {
      filterAndPopulateVideoList(titleFilter.getText(), categoryFilter.getSelectionModel().getSelectedItem(), datePicker.getValue());
    });
    
    HBox filtersBox = new HBox();
    filtersBox.getChildren().addAll(new Label("Title:"), titleFilter, new Label("Category:"), categoryFilter, new Label("Uploaded After:"), datePicker);
    filtersBox.setPadding(new Insets(10, 10, 0, 10));
    filtersBox.setSpacing(10);
    filtersBox.setAlignment(Pos.CENTER);
    
    return filtersBox;
  }
  
  private void filterAndPopulateVideoList(String title, String category, LocalDate uploadDate)
  {
    List<Video> filteredVideoList = new ArrayList<>(videoList);
    
    if (title != null && !title.isEmpty())
    {
      filteredVideoList = filteredVideoList.stream()
          .filter(video -> video.getTitle().toLowerCase().contains(title.toLowerCase()))
          .collect(Collectors.toList());
    }
    
    if (category != null && !category.isEmpty())
    {
      filteredVideoList = filteredVideoList.stream()
          .filter(video -> video.getCategory().equals(category))
          .collect(Collectors.toList());
    }
    if (uploadDate != null)
    {
      filteredVideoList = filteredVideoList.stream()
          .filter(video -> video.getUploadDate().isAfter(uploadDate.atStartOfDay()))
          .collect(Collectors.toList());
    }
    
    populateVideoList(filteredVideoList);
  }
  
  private void populateVideoList(List<Video> videoListToPopulate)
  {
    playlistVBox.getChildren().clear();
    playlistVBox.getChildren().add(new Separator());
    
    videoListToPopulate.forEach(video ->
    {
      BorderPane videoPane = new BorderPane();
      videoPane.setPadding(new Insets(15));
      
      VBox infoBox = new VBox();
      Label title = new Label(video.getTitle());
      title.setStyle("-fx-font-weight:bold;");
      Label length = new Label("Playback Length: " + formatSeconds(video.getLength()));
      Label category = new Label("Category: " + video.getCategory());
      Label uploadDate = new Label("Upload Date: " + video.getUploadDate());
      
      infoBox.getChildren().addAll(title, length, category, uploadDate);
      infoBox.setPadding(new Insets(5));
      
      videoPane.setLeft(infoBox);
      videoPane.setRight(new HBox(new Button("Watch Now")
      {
        {
          setOnAction(e ->
          {
            App.openVideoPlayer(video);
          });
          setGraphic(new ImageView(PlaylistView.class.getResource("icons8-circled-play-30.png").toExternalForm()));
          getGraphic().resize(20, 20);
        }
      })
      {
        {
          setAlignment(Pos.CENTER);
        }
      });
      
      playlistVBox.getChildren().add(videoPane);
      playlistVBox.getChildren().add(new Separator());
    });
  }
  
  public static String formatSeconds(double timeInSeconds)
  {
    int hours = (int)(timeInSeconds / 3600);
    int secondsLeft = (int)(timeInSeconds - hours * 3600);
    int minutes = secondsLeft / 60;
    int seconds = secondsLeft - minutes * 60;
    
    String formattedTime = "";
    if (hours < 10)
      formattedTime += "0";
    formattedTime += hours + ":";
    
    if (minutes < 10)
      formattedTime += "0";
    formattedTime += minutes + ":";
    
    if (seconds < 10)
      formattedTime += "0";
    formattedTime += seconds;
    
    return formattedTime;
  }
}
