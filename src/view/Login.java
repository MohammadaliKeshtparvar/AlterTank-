package view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Mahdi Mirfendereski
 * @version 0.0
 */
public class Login {
    private JFrame mainFrame ;
    private JTextField userTextField ;
    private JPasswordField passwordField ;
    private JButton signInButton ;
    private JButton resetButton ;
    private JButton signUpButton ;
    private JCheckBox showPassword ;
    private JCheckBox rememberMe ;

    public Login() {
        mainFrame=new JFrame("Login");
        mainFrame.setLayout(new BorderLayout(5,5));
        mainFrame.setSize(300,200);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);
        userTextField = new JTextField();
        passwordField = new JPasswordField();
        signInButton = new JButton("SIGN IN");
        resetButton = new JButton("RESET");
        signUpButton = new JButton("SIGN UP");
        showPassword = new JCheckBox("Show Password");

        rememberMe=new JCheckBox("Remember Me");
        addHover();
        prepareGui();
        mainFrame.setVisible(true);
    }
    public JPanel fieldsPanel(){
        JPanel fields=new JPanel();
        fields.setLayout(new GridLayout(3,3,5,5));
        JLabel userLabel = new JLabel("Username :");
        JLabel passwordLabel = new JLabel("Password : ");
        fields.add(userLabel);
        fields.add(userTextField);
        fields.add(passwordLabel);
        fields.add(passwordField);
        fields.add(rememberMe);
        fields.add(showPassword);
        return fields ;
    }
    public JPanel buttonsPanel(){
        JPanel fieldsPanel=new JPanel();
        fieldsPanel.setLayout(new FlowLayout());
        fieldsPanel.add(signUpButton);
        fieldsPanel.add(signInButton);
        fieldsPanel.add(resetButton);
        return fieldsPanel;
    }
    public void prepareGui(){
        JLabel label = new JLabel(" Please Enter Your Information ");
        label.setBackground(Color.YELLOW);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setOpaque(true);
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
        label.setBorder(border);
        int labelWidth = label.getPreferredSize().width + 20;
        int labelHeight = label.getPreferredSize().height + 10;
        //The preferred size of a component is the minimum component size
        //	that will allow the component to display normally.
        label.setPreferredSize(new Dimension(labelWidth, labelHeight));
        mainFrame.add(label,BorderLayout.NORTH);
        mainFrame.add(fieldsPanel(),BorderLayout.CENTER);
        mainFrame.add(buttonsPanel(),BorderLayout.SOUTH);

    }
    private void addHover(){
        signUpButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                signUpButton.setBackground(Color.GREEN);
                signUpButton.setRolloverEnabled(true);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                signUpButton.setBackground(UIManager.getColor("control"));
            }
        });
        signInButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                signInButton.setBackground(Color.GREEN);
                signInButton.setRolloverEnabled(true);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                signInButton.setBackground(UIManager.getColor("control"));
            }
        });
        resetButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                resetButton.setBackground(Color.GREEN);
                resetButton.setRolloverEnabled(true);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                resetButton.setBackground(UIManager.getColor("control"));
            }
        });

        showPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (showPassword.isSelected()) {
                    passwordField.setEchoChar((char) 0);
                } else {
                    passwordField.setEchoChar('*');
                }
            }
        });
        //todo:action listener of rememberme

    }


    public JFrame getMainFrame() {
        return mainFrame;
    }

    public JTextField getUserTextField() {
        return userTextField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JButton getSignInButton() {
        return signInButton;
    }

    public JButton getResetButton() {
        return resetButton;
    }

    public JButton getSignUpButton() {
        return signUpButton;
    }

    public JCheckBox getShowPassword() {
        return showPassword;
    }

    public JCheckBox getRememberMe() {
        return rememberMe;
    }
}
