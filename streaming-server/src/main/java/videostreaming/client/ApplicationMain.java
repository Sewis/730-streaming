package videostreaming.client;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.embed.swing.SwingNode;


public class ApplicationMain extends Application
{
  private final int SIZE_WIDTH = 700;
  private final int SIZE_HEIGHT = 500;
  
  public static void main(String[] args)
  {
    launch(args);
  }
  
  @Override
  public void start(Stage primaryStage) throws Exception
  {
    primaryStage.setTitle("Video Streaming");
    primaryStage.setScene(new Scene(new PlaylistView(), SIZE_WIDTH, SIZE_HEIGHT));
    primaryStage.show();
    
    primaryStage.getIcons().add(new ImageView(PlaylistView.class.getResource("icons8-circled-play-30.png").toExternalForm()).getImage());
  }
}
