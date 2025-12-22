package main;

import gui.LibraryGUI;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new LibraryGUI().setVisible(true);
        });
    }
}