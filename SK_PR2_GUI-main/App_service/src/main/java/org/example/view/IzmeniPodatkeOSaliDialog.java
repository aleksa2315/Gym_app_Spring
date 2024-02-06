package org.example.view;

import org.example.MyApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class IzmeniPodatkeOSaliDialog extends JDialog {
    private JTextField brojPersonalnihTrenera;
    private JTextField ime;
    private JTextField kratak_opis;

    private static final Map<String, String> podaci = new HashMap<>();

        public IzmeniPodatkeOSaliDialog(JPanel owner) {
            setLayout(new GridLayout(4, 2));

            brojPersonalnihTrenera = new JTextField();
            ime = new JTextField();
            kratak_opis = new JTextField();

            add(new JLabel("Broj personalnih trenera:"));
            add(brojPersonalnihTrenera);
            add(new JLabel("Ime:"));
            add(ime);
            add(new JLabel("Kratak opis:"));
            add(kratak_opis);

            JButton confirmButton = new JButton("Potvrdi");
            confirmButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String brojPersonalnihTreneraText = brojPersonalnihTrenera.getText();
                    String imeText = ime.getText();
                    String kratak_opisText = kratak_opis.getText();
                    podaci.clear();

                    podaci.put("broj_personalnih_trenera", brojPersonalnihTreneraText);
                    podaci.put("ime", imeText);
                    podaci.put("kratak_opis", kratak_opisText);

                    dispose();
                    MyApp.getInstance().getMenadzerView().izmeniPodatkeOSali();
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