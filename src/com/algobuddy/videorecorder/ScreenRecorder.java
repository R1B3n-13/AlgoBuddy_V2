package com.algobuddy.videorecorder;

/**
 *
 * @author nebir, nazrul
 */
import com.algobuddy.gui.GraphBoard;
import com.algobuddy.gui.MainFrame;
import org.jcodec.api.awt.AWTSequenceEncoder;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;

/**
 *
 * @author enyason
 */
public class ScreenRecorder extends TimerTask {

    AWTSequenceEncoder encoder;
    BufferedImage image;
    JComponent comp;
    File f;
    boolean isRecording = false;
    public static Timer timerRecord;

    public ScreenRecorder(JComponent comp, String fileName) {
        int delay = 1000 / 24;
        f = new File(fileName + ".mp4");
        try {
            encoder = AWTSequenceEncoder.createSequenceEncoder(f, 24 / 8);
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        timerRecord = new Timer("Thread TimerRecord");
        timerRecord.scheduleAtFixedRate(this, 0, delay);
        this.comp = comp;
    }

    @Override
    public void run() {
        if (GraphBoard.isPlaying()) {
            image = ComponentImage.capture(comp);

            try {
                encoder.encodeImage(image);
                System.out.println("encoding...");
            } catch (IOException ex) {
                Logger.getLogger(ScreenRecorder.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            this.cancel();
            try {
                encoder.finish();
                System.out.println("encoding finish ");
            } catch (IOException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
