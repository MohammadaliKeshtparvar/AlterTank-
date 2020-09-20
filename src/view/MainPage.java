package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Mahdi Mirfendereski
 * @version 0.0
 */
public class MainPage {
    private JFrame mainFrame;
    private JPanel buttonsPanel;
    private JButton setting ;
    private JButton single ;
    private JButton network ;
    private JButton logOut ;
    public MainPage() {

        mainFrame =new JFrame("Main Page");
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
        mainFrame.setSize(520,550);
        mainFrame.setLayout(new BorderLayout());
        prepareButton();
        mainFrame.add(addImage(),BorderLayout.CENTER);
        mainFrame.add(buttonsPanel,BorderLayout.SOUTH);
        //mainFrame.setVisible(true);

    }
    private JLabel addImage(){
        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File("1.jpg"));
        } catch (IOException e) {
            System.out.println("can't load image");
            e.printStackTrace();
        }
        Graphics2D g = (Graphics2D) myPicture.getGraphics();
        g.setStroke(new BasicStroke(3));
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, myPicture.getWidth() , myPicture.getHeight() );
        Image scaled = myPicture.getScaledInstance(500,400,Image.SCALE_SMOOTH);
        JLabel picLabel = new JLabel(new ImageIcon(scaled));
        return picLabel;
    }
    private void prepareButton(){
        buttonsPanel=new JPanel();
        buttonsPanel.setLayout(new BorderLayout());
        setting=new JButton("Setting");
        single=new JButton("single");
        network=new JButton("network");
        logOut=new JButton("log Out");
        JLabel label=new JLabel("Choose");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setOpaque(true);
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
        label.setBorder(border);
        int labelWidth = label.getPreferredSize().width + 20;
        int labelHeight = label.getPreferredSize().height + 10;
        //The preferred size of a component is the minimum component size
        //	that will allow the component to display normally.
        label.setPreferredSize(new Dimension(labelWidth, labelHeight));
        label.setBackground(Color.RED);
        buttonsPanel.add(label,BorderLayout.NORTH);
        buttonsPanel.add(setting,BorderLayout.CENTER);
        buttonsPanel.add(single,BorderLayout.WEST);
        buttonsPanel.add(network,BorderLayout.EAST);
        buttonsPanel.add(logOut,BorderLayout.SOUTH);
        addHover();
    }
    private void addHover(){
        setting.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setting.setBackground(Color.GREEN);
                setting.setRolloverEnabled(true);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                setting.setBackground(UIManager.getColor("control"));
            }
        });



        single.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                single.setBackground(Color.GREEN);
                single.setRolloverEnabled(true);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                single.setBackground(UIManager.getColor("control"));
            }
        });


        network.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                network.setBackground(Color.GREEN);
                network.setRolloverEnabled(true);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                network.setBackground(UIManager.getColor("control"));
            }
        });


        logOut.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logOut.setBackground(Color.GREEN);
                logOut.setRolloverEnabled(true);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                logOut.setBackground(UIManager.getColor("control"));
            }
        });

    }


    public JFrame getMainFrame() {
        return mainFrame;
    }

    public JPanel getButtonsPanel() {
        return buttonsPanel;
    }

    public JButton getSetting() {
        return setting;
    }

    public JButton getSingle() {
        return single;
    }

    public JButton getNetwork() {
        return network;
    }

    public JButton getLogOut() {
        return logOut;
    }
}
