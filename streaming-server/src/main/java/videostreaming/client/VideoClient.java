package videostreaming.client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JFrame;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayer;


public class VideoClient
{
  
  public static void main(String[] args) throws InvocationTargetException, InterruptedException
  {
    new NativeDiscovery().discover();
    
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    Mat mat = Mat.eye(3, 3, CvType.CV_8UC1);
    System.out.println("Mat: " + mat.dump());
    
    JFrame player = new JFrame("Video Player");
    
    EmbeddedMediaPlayerComponent embeddedMediaPlayerComponent = new EmbeddedMediaPlayerComponent();
    MediaPlayer mediaPlayer = embeddedMediaPlayerComponent.getMediaPlayer();
    
    player.setSize(640, 480);
    player.setVisible(true);
    player.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    player.setContentPane(embeddedMediaPlayerComponent);
    
    mediaPlayer.playMedia("http://localhost:8080/videos/test");
    
    player.addKeyListener(new KeyAdapter()
    {
      @Override
      public void keyPressed(KeyEvent e)
      {
        switch (e.getKeyCode())
        {
          case KeyEvent.VK_SPACE:
            mediaPlayer.pause();
            break;
        }
      }
    });
    
    player.addWindowListener(new WindowAdapter()
    {
      @Override
      public void windowClosing(WindowEvent e)
      {
        mediaPlayer.release();
        System.exit(0);
      }
    });
  }
}
