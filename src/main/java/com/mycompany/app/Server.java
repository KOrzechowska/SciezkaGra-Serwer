package com.mycompany.app;

import javax.swing.*;
import java.awt.*;

/**
 * Utworzenie okna Serwera.
 *Klasa zawierajaca funkcje main
 *
 */
@SuppressWarnings("serial")
public class Server extends JFrame {
    /**Wywołanie konstruktora klasy Panel*/
    public Server() {
        super("Sciezka - Server");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Panel panel = new Panel();
        setPreferredSize(new Dimension(500,600));
        add(panel);
    }

    /**Funkcja main - utworzenie serwera*/
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Server server = new Server();
                server.setVisible(true);
                server.pack();
                Dimension dm = server.getToolkit().getScreenSize();
                server.setLocation(
                        (int) (dm.getWidth() / 8 - server.getWidth() / 8),
                        (int) (dm.getHeight() / 4 - server.getHeight() / 4));
            }
        });
    }
}