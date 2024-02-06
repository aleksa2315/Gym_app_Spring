package org.example.view;

import javax.swing.*;

public class Toolbar extends JToolBar {

    public Toolbar() {
        super(HORIZONTAL);
        setFloatable(false);
        addSeparator();
    }
}
