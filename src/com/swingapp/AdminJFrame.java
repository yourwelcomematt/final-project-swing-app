package com.swingapp;

import javax.swing.*;
import java.awt.*;

public class AdminJFrame extends JFrame {

    public AdminJFrame() {
        setVisible(true);
        setTitle("Admin App");
        setLocation(500, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container frameContainer = getContentPane();
        AdminJPanel frameContent = new AdminJPanel();
        frameContainer.add(frameContent);
        frameContent.setPreferredSize(new Dimension(300, 300));
        pack();
        frameContent.requestFocusInWindow();

    }

}
