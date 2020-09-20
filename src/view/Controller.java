package view;

import view.Login;
import view.MainPage;
import view.Setting;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {

    private Login login ;
    private MainPage mainPage ;
    private Setting setting ;

    public Controller(Login login, MainPage mainPage, Setting setting) {
        this.login = login;
        this.mainPage = mainPage;
        this.setting = setting;
        loginControlling();
        mainPageControlling();
        settingControlling();
    }
    public Controller(Login login, MainPage mainPage) {
        this.login = login;
        this.mainPage = mainPage;
        loginControlling();
        mainPageControlling();
    }
    public Controller(Login login) {
        this.login = login;
        loginControlling();
    }
    private void loginControlling(){
        login.getSignUpButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(/*new ConfirmInformation(login.getUserTextField().getText(), login.getPasswordField().getPassword()).signUpUser()*/true){
                    JOptionPane.showMessageDialog(login.getMainFrame(), "Enrolled successfully");
                }
                else{
                    JOptionPane.showMessageDialog(login.getMainFrame(),"Username has been used.");
                }
                //new ConfirmInformation(login.getUserTextField().getText(), login.getPasswordField().getPassword()).signUpUser();
            }
        });
        login.getSignInButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(/*new ConfirmInformation(login.getUserTextField().getText(), login.getPasswordField().getPassword()).confirmInfo()*/true){
                    mainPage.getMainFrame().setVisible(true);
                }
                else{
                    JOptionPane.showMessageDialog(login.getMainFrame(),"try again");
                }
                //new ConfirmInformation(login.getUserTextField().getText(), login.getPasswordField().getPassword()).confirmInfo();
            }
        });
        login.getResetButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                login.getUserTextField().setText("");
                login.getPasswordField().setText("");
            }
        });
        login.getShowPassword().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (login.getShowPassword().isSelected()) {
                    login.getPasswordField().setEchoChar((char) 0);
                } else {
                    login.getPasswordField().setEchoChar('*');
                }
            }
        });

    }

    private void mainPageControlling(){
        mainPage.getLogOut().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        mainPage.getNetwork().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        mainPage.getSetting().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        mainPage.getSingle().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
    }
    private void settingControlling(){








    }


}
