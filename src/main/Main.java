package main;

import javax.swing.SwingUtilities;

import gui.LibraryGUI;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibraryGUI());
    }
}