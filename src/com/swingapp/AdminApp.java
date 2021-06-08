package com.swingapp;

import javax.swing.*;

import static com.swingapp.AdminGUI.createAndShowGUI;

public class AdminApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

}
