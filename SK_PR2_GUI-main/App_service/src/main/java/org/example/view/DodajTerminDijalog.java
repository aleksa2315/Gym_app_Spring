package org.example.view;

import org.example.MyApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class DodajTerminDijalog extends JDialog{
    private JTextField datum;
    private JTextField maksimalni_broj_ucesnika;
    private JTextField cena;
    private JTextField vreme;
    private JTextField idTreninga;
    private JTextField sala;

    private static final Map<String, String> podaci = new HashMap<>();

    public DodajTerminDijalog(JPanel owner) {
        setLayout(new GridLayout(8, 2));

        datum = new JTextField();
        maksimalni_broj_ucesnika = new JTextField();
        cena = new JTextField();
        vreme = new JTextField();
        idTreninga = new JTextField();
        sala = new JTextField();


        add(new JLabel("Datum:"));
        add(datum);
        add(new JLabel("Maksimalni broj učesnika:"));
        add(maksimalni_broj_ucesnika);
        add(new JLabel("Cena:"));
        add(cena);
        add(new JLabel("Vreme:"));
        add(vreme);
        add(new JLabel("Tip treninga:"));
        add(idTreninga);
        add(new JLabel("Sala:"));
        add(sala);



        JButton confirmButton = new JButton("Potvrdi");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String datumText = datum.getText();
                String maksimalni_broj_ucesnikaText = maksimalni_broj_ucesnika.getText();
                String cenaText = cena.getText();
                String vremeText = vreme.getText();
                String idTreningaText = idTreninga.getText();
                String salaText = sala.getText();
                podaci.clear();

                podaci.put("datum", datumText);
                podaci.put("maksimalanBrojUcesnika", maksimalni_broj_ucesnikaText);
                podaci.put("cena", cenaText);
                podaci.put("vremePocetka", vremeText);
                podaci.put("nazivTreninga", idTreningaText);
                podaci.put("idSale", salaText);

                dispose();
                MyApp.getInstance().getMenadzerView().dodajNoviTermin();
            }
        });
        add(confirmButton);

        setSize(300, 150);
        setLocationRelativeTo(owner);
        setVisible(true);
    }

    public static Map<String,String> getPodaci() {
        return podaci;
    }
}
