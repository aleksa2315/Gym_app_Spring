package org.example.view;

import org.example.MyApp;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KlijentToolPanel extends JPanel {

    private JPanel toolPanel;
    private JPanel actionPanel;
    private String podaci = "";
    private JButton izmeniPodatke;
    private JButton logOut;
    private JButton promeniLozinku;
    private JButton priaziZauzeteTermine;
    private JButton prikaziSlobodneTermine;
    private JButton zakaziTrening;
    private JButton otkaziTrening;
    private JLabel label;
    private JButton prikaziNotifikacije;
    private JButton filtrirajPoTipu;
    private JButton filtrirajPoNazivu;
    private JButton filtrirajPoDanu;


    public KlijentToolPanel(String podaci){
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

        filtrirajPoDanu = new JButton("Filtriraj po danu");
        filtrirajPoNazivu = new JButton("Filtriraj po nazivu treninga");
        filtrirajPoTipu = new JButton("Filtriraj po tipu");
        promeniLozinku = new JButton("Promeni lozinku");
        izmeniPodatke = new JButton("Izmeni podatke");
        prikaziNotifikacije = new JButton("Prikazi notifikacije");
        priaziZauzeteTermine = new JButton("Prikazi zakazane termine");
        prikaziSlobodneTermine = new JButton("Prikazi slobodne termine");
        zakaziTrening = new JButton("Zakazi trening");
        otkaziTrening = new JButton("Otkazi trening");
        logOut = new JButton("Log out");


        actionPanel.add(label);

        actionPanel.add(promeniLozinku);
        actionPanel.add(prikaziNotifikacije);
        actionPanel.add(izmeniPodatke);
        actionPanel.add(priaziZauzeteTermine);
        actionPanel.add(prikaziSlobodneTermine);
        actionPanel.add(filtrirajPoNazivu);
        actionPanel.add(filtrirajPoDanu);
        actionPanel.add(filtrirajPoTipu);
        actionPanel.add(zakaziTrening);
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
            MyApp.getInstance().getKlijentView().izmenaPodataka();
        });
        this.promeniLozinku.addActionListener(e -> {
            MyApp.getInstance().getKlijentView().promeniSifru();
        });
        this.prikaziNotifikacije.addActionListener(e -> {
            MyApp.getInstance().getKlijentView().initNotifikacijeListTable();
        });
        this.priaziZauzeteTermine.addActionListener(e -> {
            MyApp.getInstance().getKlijentView().initZauzetiTerminiListTable();
        });
        this.prikaziSlobodneTermine.addActionListener(e -> {
            MyApp.getInstance().getKlijentView().initSlobodniTerminiListTable();
        });
        this.zakaziTrening.addActionListener(e -> {
            MyApp.getInstance().getKlijentView().zakaziTrening();
        });
        this.otkaziTrening.addActionListener(e -> {
            MyApp.getInstance().getKlijentView().otkaziTrening();
        });
        this.filtrirajPoNazivu.addActionListener(e -> {
            MyApp.getInstance().getKlijentView().filtrirajPoNazivu();
        });
        this.filtrirajPoDanu.addActionListener(e -> {
            MyApp.getInstance().getKlijentView().filtrirajPoDanu();
        });
        this.filtrirajPoTipu.addActionListener(e -> {
            MyApp.getInstance().getKlijentView().filtrirajPoTipu();
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
