package view;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
/**
 *
 * @author Mahdi Mirfendereski
 * @version 0.0
 */
public class Setting {
    private JFrame mainFrame;
    private String playingTime;
    private String userName;
    private String computerStatus;
    private String networkStatus;
    private ImageIcon tankShape;
    private JSlider tankHealth;
    private JSlider ballPower;
    private JSlider wallHealth;

    public Setting(String playingTime,String userName,String computerStatus,String networkStatus){
        this.playingTime=playingTime;
        this.userName=userName;
        this.computerStatus =computerStatus;
        this.networkStatus = networkStatus;
        mainFrame=new JFrame("Setting");
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
        mainFrame.setSize(450,400);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.add(creatingLabel(userName,Color.cyan),BorderLayout.NORTH);
        JTabbedPane tabbedPane=new JTabbedPane();
        tabbedPane.add("User Info",userInfoPanel());
        tabbedPane.add("Game Setting",gameSettingPanel());
        mainFrame.add(tabbedPane,BorderLayout.CENTER);
        mainFrame.setVisible(true);
    }
    private JSlider getSlider(){
        JSlider slider=new JSlider();
        slider = new JSlider(0, 200, 120);

        // paint the ticks and tarcks
        slider.setPaintTrack(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        // set spacing
        slider.setMajorTickSpacing(50);
        slider.setMinorTickSpacing(5);

        // set orientation of slider
        slider.setOrientation(SwingConstants.VERTICAL);

        // set Font for the slider
        slider.setFont(new Font("Serif", Font.ITALIC, 20));
        return slider;

    }
    private JPanel userInfoPanel(){
        JPanel panel=new JPanel();
        panel.setLayout(new GridLayout(3,1));
        panel.add(rowOfTime());
        panel.add(rowOfComWins());
        panel.add(rowOfNetWins());
        return panel;
    }
    private JPanel gameSettingPanel(){
        JPanel panel=new JPanel();
        panel.setLayout(new BorderLayout());
        JPanel sliders=new JPanel();
        sliders.setLayout(new FlowLayout());
        tankHealth=getSlider();
        wallHealth=getSlider();
        ballPower=getSlider();
        sliders.add(tankHealth);
        sliders.add(wallHealth);
        sliders.add(ballPower);
        panel.add(sliders,BorderLayout.CENTER);
        JPanel names=new JPanel();
        names.setLayout(new FlowLayout());
        names.add(new JLabel("Tank Health"));
        names.add(new JLabel("Wall Health"));
        names.add(new JLabel("Ball  Power"));
        panel.add(names,BorderLayout.SOUTH);
        return panel;
    }
    private JPanel rowOfTime(){
        JPanel time=new JPanel();
        time.setLayout(new BorderLayout());
        time.add(creatingLabel("  playing   Time   ",Color.cyan),BorderLayout.WEST);
        time.add(creatingLabel(playingTime,Color.LIGHT_GRAY),BorderLayout.CENTER);
        return time;
    }
    private JPanel rowOfComWins(){
        JPanel time=new JPanel();
        time.setLayout(new BorderLayout());
        time.add(creatingLabel("Computer status",Color.GREEN),BorderLayout.WEST);
        time.add(creatingLabel(computerStatus,Color.white),BorderLayout.CENTER);
        return time;
    }
    private JPanel rowOfNetWins(){
        JPanel time=new JPanel();
        time.setLayout(new BorderLayout());
        time.add(creatingLabel("  Network status ",Color.ORANGE),BorderLayout.WEST);
        time.add(creatingLabel(networkStatus,Color.LIGHT_GRAY),BorderLayout.CENTER);
        return time;
    }
    private JLabel creatingLabel(String text,Color backGround){
        JLabel label=new JLabel(text);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setOpaque(true);
        Border border2 = BorderFactory.createLineBorder(Color.BLACK, 2);
        label.setBorder(border2);
        int labelWidth2 = label.getPreferredSize().width + 20;
        int labelHeight2 = label.getPreferredSize().height + 10;
        //The preferred size of a component is the minimum component size
        //	that will allow the component to display normally.
        label.setPreferredSize(new Dimension(labelWidth2, labelHeight2));
        label.setBackground(backGround);
        return label;
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(JFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public String getPlayingTime() {
        return playingTime;
    }

    public void setPlayingTime(String playingTime) {
        this.playingTime = playingTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComputerStatus() {
        return computerStatus;
    }

    public void setComputerStatus(String computerStatus) {
        this.computerStatus = computerStatus;
    }

    public String getNetworkStatus() {
        return networkStatus;
    }

    public void setNetworkStatus(String networkStatus) {
        this.networkStatus = networkStatus;
    }

    public ImageIcon getTankShape() {
        return tankShape;
    }

    public void setTankShape(ImageIcon tankShape) {
        this.tankShape = tankShape;
    }

    public JSlider getTankHealth() {
        return tankHealth;
    }

    public void setTankHealth(JSlider tankHealth) {
        this.tankHealth = tankHealth;
    }

    public JSlider getBallPower() {
        return ballPower;
    }

    public void setBallPower(JSlider ballPower) {
        this.ballPower = ballPower;
    }

    public JSlider getWallHealth() {
        return wallHealth;
    }

    public void setWallHealth(JSlider wallHealth) {
        this.wallHealth = wallHealth;
    }



}
