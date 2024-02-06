package org.example.view;

import org.example.MyApp;

import javax.swing.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class AdminToolPanel extends JPanel {

    private JPanel toolPanel;
    private JPanel actionPanel;
    private String podaci = "";
    private JButton zabraniKorisnikuPristup;
    private JButton odobriKorisnikuPristup;
    private JButton izlistajKorisnike;
    private JButton izlistajSlobodneTermine;
    private JButton izlistajZakazaneTermine;
    private JButton izmeniPodatke;
    private JButton promeniLozinku;
    private JButton prikaziNotifikacije;
    private JButton dodajTipNotifikacije;
    private JButton logOut;
    private JLabel label;


    public AdminToolPanel(String podaci){
        setBoxLayout();
        this.podaci = podaci;
        this.actionPanel = new JPanel();
        this.add(actionPanel(podaci));
    }

    private void setBoxLayout(){
        BoxLayout boxLayout = new BoxLayout(this,BoxLayout.Y_AXIS);
        this.setLayout(boxLayout);
    }

    private JPanel actionPanel(String podaci){
        BoxLayout layout = new BoxLayout(actionPanel,BoxLayout.Y_AXIS);
        actionPanel.setLayout(layout);
        label = new JLabel(podaci);

        zabraniKorisnikuPristup = new JButton("Zabrani korisniku pristup");

        odobriKorisnikuPristup = new JButton("Odobri korisniku pristup");
        izlistajKorisnike = new JButton("Izlistaj korisnike");
        izlistajSlobodneTermine = new JButton("Izlistaj slobodne termine");
        izlistajZakazaneTermine = new JButton("Izlistaj zakazane termine");
        prikaziNotifikacije = new JButton("Prikazi notifikacije");
        dodajTipNotifikacije = new JButton("Dodaj tip notifikacije");
        izmeniPodatke = new JButton("Izmeni podatke");
        promeniLozinku = new JButton("Promeni lozinku");
        logOut = new JButton("Log out");


        actionPanel.add(label);
        actionPanel.add(zabraniKorisnikuPristup);
        actionPanel.add(odobriKorisnikuPristup);
        actionPanel.add(izlistajKorisnike);
        actionPanel.add(izlistajSlobodneTermine);
        actionPanel.add(izlistajZakazaneTermine);
        actionPanel.add(prikaziNotifikacije);
        actionPanel.add(dodajTipNotifikacije);
        actionPanel.add(izmeniPodatke);
        actionPanel.add(promeniLozinku);
        actionPanel.add(logOut);
        actions();
        return actionPanel;
    }

    public void actions(){
        this.izlistajKorisnike.addActionListener(e -> {
            MyApp.getInstance().getAdminView().KorisniciListTable();
        });
        this.izlistajZakazaneTermine.addActionListener(e -> {
            try {
                MyApp.getInstance().getAdminView().initZauzetiTerminListTable();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        this.prikaziNotifikacije.addActionListener(e -> {
            MyApp.getInstance().getAdminView().initNotifikacijeListTable();
        });
        this.zabraniKorisnikuPristup.addActionListener(e -> {
            MyApp.getInstance().getAdminView().zabraniPristup();
        });
        this.odobriKorisnikuPristup.addActionListener(e -> {
            MyApp.getInstance().getAdminView().odobriPristup();
        });
        this.izlistajSlobodneTermine.addActionListener(e -> {
            MyApp.getInstance().getAdminView().initSlobodniTerminiListTable();
        });
        this.logOut.addActionListener(e -> {
            MyApp.getInstance().intView("LOGOUT");
        });
        this.izmeniPodatke.addActionListener(e -> {
            MyApp.getInstance().getAdminView().izmenaPodataka();
        });
        this.promeniLozinku.addActionListener(e -> {
            MyApp.getInstance().getAdminView().promeniSifru();
        });
        this.dodajTipNotifikacije.addActionListener(e -> {
            MyApp.getInstance().getAdminView().tipNotifikacije();
        });


    }

    public JLabel getLabel() {
        return label;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }

    public void setPodaci(List<String> podaci){

    }
}
