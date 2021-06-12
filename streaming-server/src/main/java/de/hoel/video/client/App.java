package de.hoel.video.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayer;


public class App extends JFrame
{
  private static final long serialVersionUID = 1L;
  private static final String TITLE = "My First Media Player";
  private final EmbeddedMediaPlayerComponent mediaPlayerComponent;
  private JButton playButton;
  private JButton pauseButton;
  private JButton rewindButton;
  private JButton skipButton;
  
  public App(String title)
  {
    super(title);
    mediaPlayerComponent = new EmbeddedMediaPlayerComponent()
    {
      private static final long serialVersionUID = 1L;
      
      @Override
      public void mouseClicked(MouseEvent e)
      {
        super.mouseClicked(e);
        System.out.println("Mouse Clicked. (" + e.getX() + "," + e.getY() + ")");
      }
      
      @Override
      public void mouseWheelMoved(MouseWheelEvent e)
      {
        super.mouseWheelMoved(e);
        System.out.println("Mouse wheel moved. " + e.getScrollAmount());
      }
      
      @Override
      public void keyPressed(KeyEvent e)
      {
        super.keyPressed(e);
        System.out.println("Key pressed. " + e.getKeyCode());
      }
      
      @Override
      public void playing(MediaPlayer mediaPlayer)
      {
        super.playing(mediaPlayer);
        System.out.println("Media Playback started.");
      }
      
      @Override
      public void finished(MediaPlayer mediaPlayer)
      {
        super.playing(mediaPlayer);
        System.out.println("Media Playback finished.");
      }
      
      @Override
      public void error(MediaPlayer mediaPlayer)
      {
        SwingUtilities.invokeLater(new Runnable()
        {
          public void run()
          {
            System.out.println("Failed to load Media.");
          }
        });
      }
    };
  }
  
  public void initialize()
  {
    this.setBounds(100, 100, 600, 400);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.addWindowListener(new WindowAdapter()
    {
      @Override
      public void windowClosing(WindowEvent e)
      {
        mediaPlayerComponent.release();
        System.exit(0);
      }
    });
    JPanel contentPane = new JPanel();
    contentPane.setLayout(new BorderLayout());
    contentPane.add(mediaPlayerComponent, BorderLayout.CENTER);
    
    Border videoBorder = BorderFactory.createTitledBorder("Video Controls");
    
    JPanel videoControlsPane = new JPanel();
    videoControlsPane.setBorder(videoBorder);
    playButton = new JButton("Play");
    videoControlsPane.add(playButton);
    pauseButton = new JButton("Pause");
    videoControlsPane.add(pauseButton);
    rewindButton = new JButton("Rewind");
    videoControlsPane.add(rewindButton);
    skipButton = new JButton("Skip");
    videoControlsPane.add(skipButton);
    JPanel controlsPane = new JPanel();
    controlsPane.add(videoControlsPane);
    contentPane.add(controlsPane, BorderLayout.SOUTH);
    
    playButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        mediaPlayerComponent.getMediaPlayer().play();
      }
    });
    pauseButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        mediaPlayerComponent.getMediaPlayer().pause();
      }
    });
    rewindButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        mediaPlayerComponent.getMediaPlayer().skip(-4000);
      }
    });
    skipButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        mediaPlayerComponent.getMediaPlayer().skip(4000);
      }
    });
    this.setContentPane(contentPane);
    this.setVisible(true);
  }
  
  public void loadVideo(String path)
  {
    mediaPlayerComponent.getMediaPlayer().playMedia(path);
  }
  
  public static void main(String[] args)
  {
    new NativeDiscovery().discover();
    
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    Mat mat = Mat.eye(3, 3, CvType.CV_8UC1);
    System.out.println("Mat: " + mat.dump());
    
    try
    {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch (Exception e)
    {
      System.out.println(e);
    }
    App application = new App(TITLE);
    application.initialize();
    application.setVisible(true);
    application.loadVideo("http://localhost:8080/videos/test");
  }
}
