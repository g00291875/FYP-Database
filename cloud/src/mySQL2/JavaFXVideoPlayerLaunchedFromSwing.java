package mySQL2;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;
import javafx.application.Platform;
import javafx.beans.value.*;
import javafx.embed.swing.JFXPanel;
import javafx.event.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.media.*;
import javafx.util.Duration;
import javax.swing.*;

/** Example of playing all mp3 audio files in a given directory
 * using a JavaFX MediaView launched from Swing
 */
public class JavaFXVideoPlayerLaunchedFromSwing {
    private static void initAndShowGUI() {



         DB db = new DB();
         Connection conn=db.dbConnect(
         "jdbc:mysql://localhost:3306/localsong","root","root");


         //   db.insertImage(conn,"C://theset//test3.mp3");
         db.getImageData(conn);

        /***********************/

        // This method is invoked on Swing thread
        JFrame frame = new JFrame("FX");
        final JFXPanel fxPanel = new JFXPanel();
        frame.add(fxPanel);
        frame.setBounds(200, 100, 800, 250);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        Platform.runLater(new Runnable() {
            @Override public void run() {
                initFX(fxPanel);
            }
        });
    }

//    private void connect() throws Exception
//    {
//        // Cloudscape database driver class name
//        String driver = "com.mysql.jdbc.Driver";
//
//        // URL to connect to addressbook database
//        String url = "jdbc:mysql://localhost:3306/addressbook";
//
//        // load database driver class
//        Class.forName( driver );
//
//        // connect to database
//        connection = DriverManager.getConnection(url, "root", "root");
//
//        // Require manual commit for transactions. This enables
//        // the program to rollback transactions that do not
//        // complete and commit transactions that complete properly.
//        connection.setAutoCommit( false );
//    }

    private static void initFX(JFXPanel fxPanel) {
        // This method is invoked on JavaFX thread
        Scene scene = new SceneGenerator().createScene();
        fxPanel.setScene(scene);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override public void run() {
                initAndShowGUI();
            }
        });
    }
}

class SceneGenerator {
    final Label currentlyPlaying = new Label();
    final ProgressBar progress = new ProgressBar();
    private ChangeListener<Duration> progressChangeListener;
    private Connection conn;

    public Scene createScene() {
        final StackPane layout = new StackPane();

        DB db = new DB();

        // determine the source directory for the playlist
       // final File dir = new File("C:\\theset");
        final File dir = new File("C:\\test");

        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("Cannot find video source directory: " + dir);
            Platform.exit();
            return null;
        }

        // create some media players.
        final List<MediaPlayer> players = new ArrayList<MediaPlayer>();
        for (String file : dir.list(new FilenameFilter() {
            @Override public boolean accept(File dir, String name) {
                return name.endsWith(".mp3");
            }
        }))

            players.add(createPlayer("file:///" + (dir + "\\" + file).replace("\\", "/").replaceAll(" ", "%20")));

            conn=db.dbConnect(
                    "jdbc:mysql://localhost:3306/localsong","root","root");

        //db.getDBData(conn);

        File file = new File("C:\\test\\toMediaObj.mp3");
        file.deleteOnExit();

//        try {
//           // FileOutputStream fos = new FileOutputStream(file);
//           // fos.write(db.getDBData(conn));
//          //  fos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

       // players.add(createPlayer(file);
        if (players.isEmpty()) {
            System.out.println("No audio found in " + dir);
            Platform.exit();
            return null;
        }

        // create a view to show the mediaplayers.
        final MediaView mediaView = new MediaView(players.get(0));
        final Button skip = new Button("Skip");
        final Button play = new Button("Pause");

        // play each audio file in turn.
        for (int i = 0; i < players.size(); i++) {
            final MediaPlayer player     = players.get(i);
            final MediaPlayer nextPlayer = players.get((i + 1) % players.size());
            player.setOnEndOfMedia(new Runnable() {
                @Override public void run() {
                    player.currentTimeProperty().removeListener(progressChangeListener);
                    mediaView.setMediaPlayer(nextPlayer);
                    nextPlayer.play();
                }
            });
        }

        // allow the user to skip a track.
        skip.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                final MediaPlayer curPlayer = mediaView.getMediaPlayer();
                MediaPlayer nextPlayer = players.get((players.indexOf(curPlayer) + 1) % players.size());
                mediaView.setMediaPlayer(nextPlayer);
                curPlayer.currentTimeProperty().removeListener(progressChangeListener);
                curPlayer.stop();
                nextPlayer.play();
            }
        });

        // allow the user to play or pause a track.
        play.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                if ("Pause".equals(play.getText())) {
                    mediaView.getMediaPlayer().pause();
                    play.setText("Play");
                } else {
                    mediaView.getMediaPlayer().play();
                    play.setText("Pause");
                }
            }
        });

        // display the name of the currently playing track.
        mediaView.mediaPlayerProperty().addListener(new ChangeListener<MediaPlayer>() {
            @Override public void changed(ObservableValue<? extends MediaPlayer> observableValue, MediaPlayer oldPlayer, MediaPlayer newPlayer) {
                setCurrentlyPlaying(newPlayer);
            }
        });

        // start playing the first track.
        mediaView.setMediaPlayer(players.get(0));
        mediaView.getMediaPlayer().play();
        setCurrentlyPlaying(mediaView.getMediaPlayer());

        // silly invisible button used as a template to get the actual preferred size of the Pause button.
        Button invisiblePause = new Button("Pause");
        invisiblePause.setVisible(false);
        play.prefHeightProperty().bind(invisiblePause.heightProperty());
        play.prefWidthProperty().bind(invisiblePause.widthProperty());

        // layout the scene.
        layout.setStyle("-fx-background-color: cornsilk; -fx-font-size: 20; -fx-padding: 20; -fx-alignment: center;");
        layout.getChildren().addAll(
                invisiblePause,
                VBoxBuilder.create().spacing(10).alignment(Pos.CENTER).children(
                        currentlyPlaying,
                        mediaView,
                        HBoxBuilder.create().spacing(10).alignment(Pos.CENTER).children(skip, play, progress).build()
                ).build()
        );
        progress.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(progress, Priority.ALWAYS);
        return new Scene(layout, 800, 600);
    }

    /** sets the currently playing label to the label of the new media player and updates the progress monitor. */
    private void setCurrentlyPlaying(final MediaPlayer newPlayer) {
        progress.setProgress(0);
        progressChangeListener = new ChangeListener<Duration>() {
            @Override public void changed(ObservableValue<? extends Duration> observableValue, Duration oldValue, Duration newValue) {
                progress.setProgress(1.0 * newPlayer.getCurrentTime().toMillis() / newPlayer.getTotalDuration().toMillis());
            }
        };
        newPlayer.currentTimeProperty().addListener(progressChangeListener);

        String source = newPlayer.getMedia().getSource();
        source = source.substring(0, source.length() - ".mp4".length());
        source = source.substring(source.lastIndexOf("/") + 1).replaceAll("%20", " ");
        currentlyPlaying.setText("Now Playing: " + source);
    }

    /** @return a MediaPlayer for the given source which will report any errors it encounters */
    private MediaPlayer createPlayer(String aMediaSrc) {
        System.out.println("Creating player for: " + aMediaSrc);
        final MediaPlayer player = new MediaPlayer(new Media(aMediaSrc));
        player.setOnError(new Runnable() {
            @Override public void run() {
                System.out.println("Media error occurred: " + player.getError());
            }
        });
        return player;
    }
}
