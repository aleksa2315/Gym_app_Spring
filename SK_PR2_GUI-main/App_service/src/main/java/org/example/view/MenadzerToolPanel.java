package org.example.view;

import org.example.MyApp;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MenadzerToolPanel extends JPanel {

    private JPanel toolPanel;
    private JPanel actionPanel;
    private String podaci = "";
    private JButton izmeniPodatke;
    private JButton logOut;
    private JButton promeniLozinku;
    private JButton izmeniPodatkeOSali;
    private JButton prikaziZauzeteTermine;
    private JButton prikaziSlobodneTermine;
    private JLabel label;
    private JButton prikaziNotifikacije;
    private JButton otkaziTrening;
    private JButton dodajTermin;


    public MenadzerToolPanel(String podaci){
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

        promeniLozinku = new JButton("Promeni lozinku");
        izmeniPodatke = new JButton("Izmeni podatke");
        prikaziNotifikacije = new JButton("Prikazi notifikacije");
        prikaziZauzeteTermine = new JButton("Prikazi zakazane termine");
        prikaziSlobodneTermine = new JButton("Prikazi slobodne termine");
        otkaziTrening = new JButton("Otkazi trening");
        dodajTermin = new JButton("Dodaj termin");
        izmeniPodatkeOSali = new JButton("Izmeni podatke o sali");
        logOut = new JButton("Log out");


        actionPanel.add(label);

        actionPanel.add(promeniLozinku);
        actionPanel.add(prikaziNotifikacije);
        actionPanel.add(izmeniPodatke);
        actionPanel.add(izmeniPodatkeOSali);
        actionPanel.add(prikaziZauzeteTermine);
        actionPanel.add(prikaziSlobodneTermine);
        actionPanel.add(dodajTermin);
        actionPanel.add(otkaziTrening);
        actionPanel.add(logOut);
        actions();
        return actionPanel;
    }

    public void actions(){
        this.logOut.addActionListener(e -> {
            MyApp.getInstance().intView("LOGOUT");
        });
        this.izmeniPodatke.addActionListener(e -> {
            MyApp.getInstance().getMenadzerView().izmenaPodataka();
        });
        this.promeniLozinku.addActionListener(e -> {
            MyApp.getInstance().getMenadzerView().promeniSifru();
        });
        this.prikaziNotifikacije.addActionListener(e -> {
            MyApp.getInstance().getMenadzerView().initNotifikacijeListTable();
        });
        this.izmeniPodatkeOSali.addActionListener(e -> {
            MyApp.getInstance().getMenadzerView().izmenaPodatakaOSali();
        });
        this.otkaziTrening.addActionListener(e -> {
            MyApp.getInstance().getMenadzerView().otkaziTrening();
        });
        this.dodajTermin.addActionListener(e -> {
            MyApp.getInstance().getMenadzerView().dodajTermin();
        });
        this.prikaziZauzeteTermine.addActionListener(e -> {
            MyApp.getInstance().getMenadzerView().initZauzetiTerminiTable();
        });
        this.prikaziSlobodneTermine.addActionListener(e -> {
            MyApp.getInstance().getMenadzerView().initSlobodniTerminiTable();
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
