package org.example.view;

import org.example.MyApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class DodajTipNotifikacijeDialog extends JDialog {
    private JTextField type;
    private JTextField message;


    private static final Map<String, String> podaci = new HashMap<>();

    public DodajTipNotifikacijeDialog(JPanel owner) {
        setLayout(new GridLayout(3, 2));

        type = new JTextField();
        message = new JTextField();

        add(new JLabel("Tip:"));
        add(type);
        add(new JLabel("Poruka:"));
        add(message);


        JButton confirmButton = new JButton("Potvrdi");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tip = type.getText();
                String poruka = message.getText();
                podaci.clear();

                podaci.put("tip", tip);
                podaci.put("poruka", poruka);

                dispose();
                MyApp.getInstance().getAdminView().dodajTipNotifikacije();
            }
        });
        add(confirmButton);

        setSize(300, 100);
        setLocationRelativeTo(owner);
        setVisible(true);
    }

    public static Map<String,String> getPodaci() {
        return podaci;
    }
}
