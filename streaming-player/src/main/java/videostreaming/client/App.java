package videostreaming.client;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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
        mediaPlayerComponent.getMediaPlayer().stop();
        mediaPlayerComponent.getMediaPlayer().release();
        mediaPlayerComponent.release();
        
        SwingUtilities.invokeLater(new Runnable()
        {
          public void run()
          {
            App.this.dispose();
          }
        });
      }
    });
    JPanel contentPane = new JPanel();
    contentPane.setLayout(new BorderLayout());
    contentPane.add(mediaPlayerComponent, BorderLayout.CENTER);
    
    Border videoBorder = BorderFactory.createTitledBorder("Video Controls");
    
    JPanel videoControlsPane = new JPanel();
    videoControlsPane.setBorder(videoBorder);
    
    playButton = new JButton("Play")
    {
      {
        try
        {
          Image img = ImageIO.read(getClass().getResource("icons8-play-30.png")).getScaledInstance(20, 20, Image.SCALE_DEFAULT);
          setIcon(new ImageIcon(img));
        }
        catch (IOException e1)
        {
          e1.printStackTrace();
        }
      }
    };
    
    videoControlsPane.add(playButton);
    pauseButton = new JButton("Pause")
    {
      {
        try
        {
          Image img = ImageIO.read(getClass().getResource("icons8-pause-30.png")).getScaledInstance(20, 20, Image.SCALE_DEFAULT);;
          setIcon(new ImageIcon(img));
        }
        catch (IOException e1)
        {
          e1.printStackTrace();
        }
      }
    };
    
    videoControlsPane.add(pauseButton);
    rewindButton = new JButton("Rewind")
    {
      {
        try
        {
          Image img = ImageIO.read(getClass().getResource("icons8-rewind-30.png")).getScaledInstance(20, 20, Image.SCALE_DEFAULT);
          setIcon(new ImageIcon(img));
        }
        catch (IOException e1)
        {
          e1.printStackTrace();
        }
      }
    };
    
    videoControlsPane.add(rewindButton);
    skipButton = new JButton("Forward")
    {
      {
        try
        {
          Image img = ImageIO.read(getClass().getResource("icons8-fast-forward-30.png")).getScaledInstance(20, 20, Image.SCALE_DEFAULT);;
          setIcon(new ImageIcon(img));
        }
        catch (IOException e1)
        {
          e1.printStackTrace();
        }
      }
    };
    
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
  
  public void loadVideo(Video video)
  {
    mediaPlayerComponent.getMediaPlayer().playMedia(video.getUrlID());
    setTitle(video.getTitle());
  }
  
  public static App init()
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
    application.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    try
    {
      application.setIconImage(ImageIO.read(App.class.getResource("icons8-circled-play-30.png")).getScaledInstance(20, 20, Image.SCALE_DEFAULT));
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    
    return application;
  }
  
  public static void main(String... args)
  {
    App app = init();
    
    app.loadVideo(VideoAcquirer.getVideosFromTestSample().get(0));
  }
  
  public static void openVideoPlayer(Video video)
  {
    init().loadVideo(video);
    
  }
}
