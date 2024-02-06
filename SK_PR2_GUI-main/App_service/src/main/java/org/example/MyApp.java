package org.example;

import org.example.view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MyApp extends JFrame {
    private static MyApp instance = null;
    public static String apiUrl = "http://localhost:8080/";
    private String token;
    private LoginView loginView;
    private KlijentView klijentView;
    private AdminView adminView;
    private MenadzerView menadzerView;
    private RegisterView registerView;

    private JPanel jPanel;

    private MyApp(){
        this.setTitle("App");
        this.setSize(1200, 1200);
        this.jPanel = new JPanel();
        this.setLayout(new BorderLayout());

        loginView = new LoginView();
        registerView = new RegisterView();

        jPanel.add(loginView);
        this.add(jPanel,BorderLayout.CENTER);

        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                refreshPanel();
            }
        });
    }

    public void refreshPanel() {
        this.revalidate();
        this.repaint();
    }

    public void intView(String role) {
        if (role.equals("ADMIN")) {
            adminView = new AdminView();
            jPanel.remove(loginView);
            jPanel.add(adminView);
            jPanel.updateUI();
        } else if (role.equals("MENADZER")) {
            menadzerView = new MenadzerView();
            jPanel.remove(loginView);
            jPanel.add(menadzerView);
            jPanel.updateUI();
        } else if (role.equals("LOGOUT")) {
            if(klijentView != null) {
                jPanel.remove(klijentView);
                klijentView = null;
            } else if (adminView != null) {
                jPanel.remove(adminView);
                adminView = null;
            } else if (menadzerView != null) {
                jPanel.remove(menadzerView);
                menadzerView = null;
            }
            jPanel.add(loginView);
            jPanel.updateUI();
            loginView.setVisible(true);
            this.token = null;
        } else {
            klijentView = new KlijentView();
            jPanel.remove(loginView);
            jPanel.add(klijentView);
            jPanel.updateUI();
        }
    }

    public static MyApp getInstance(){
        if (instance == null){
            instance = new MyApp();
        }
        return instance;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LoginView getLoginView() {
        return loginView;
    }

    public void setLoginView(LoginView loginView) {
        this.loginView = loginView;
    }

    public KlijentView getKlijentView() {
        return klijentView;
    }

    public void setTerminiView(KlijentView klijentView) {
        this.klijentView = klijentView;
    }

    public void setKlijentView(KlijentView klijentView) {
        this.klijentView = klijentView;
    }

    public AdminView getAdminView() {
        return adminView;
    }

    public void setAdminView(AdminView adminView) {
        this.adminView = adminView;
    }

    public MenadzerView getMenadzerView() {
        return menadzerView;
    }

    public void setMenadzerView(MenadzerView menadzerView) {
        this.menadzerView = menadzerView;
    }

    public RegisterView getRegisterView() {
        return registerView;
    }

    public void setRegisterView(RegisterView registerView) {
        this.registerView = registerView;
    }

    public JPanel getjPanel() {
        return jPanel;
    }

    public void setjPanel(JPanel jPanel) {
        this.jPanel = jPanel;
    }
}
