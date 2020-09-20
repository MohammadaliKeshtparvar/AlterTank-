package view;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Mahdi Mirfendereski
 * @version 0.0
 */
public class Main {
    private static void soundsPlay(){
        AudioInputStream audioInputStream =
                null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File("music.wav").getAbsoluteFile());

            // create clip reference
            Clip clip = AudioSystem.getClip();

            // open audioInputStream to the clip
            clip.open(audioInputStream);

            clip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (UnsupportedAudioFileException e) {
            System.out.println("Unsupported Audio File");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("opening file failed");
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            System.out.println("playing file failed");
            e.printStackTrace();
        }

    }
    public static void main(String[] args){
        soundsPlay();
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, fall back to cross-platform
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ex) {
                // not worth my time
            }
        }
        ;
        new Controller(new Login(),new MainPage());
        ;
        //Setting setting=new Setting(" 01h 25m 36s","user","03win 02lost","03win 02lost");
        //Scanner scanner=new Scanner(System.in);
        //if(scanner.nextInt()==0){
        //    setting.getMainFrame().setVisible(true);
        //}




    }
}
