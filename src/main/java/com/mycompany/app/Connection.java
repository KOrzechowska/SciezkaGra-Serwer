package com.mycompany.app;
        import java.io.IOException;
        import java.net.ServerSocket;
        import java.net.SocketException;
/**
 *
 * @author Magda & Kasia
 *
 */
public class Connection extends Thread {

    /**Tworzenie gniazda serwera*/
    private ServerSocket _serverSocket;
    private int _port;
    private boolean _online;

    /**konstruktor - uruchonienie watku*/
    public Connection(int port) {
        _port = port;
        _online = true;
        start();
    }
    /**wlaczenie sertera, jest gotowy do pracy, nasluchuje czy kliient wysyla zapytanie
     * obsluba bledow gdzy jest poroblem z numerem portu*/
    public void run() {
        try {
            _serverSocket = new ServerSocket(_port);
            Panel.panel.addMessage("Serwer jest gotowy do pracy na porcie "+ " " + _port,
                    Config.INFO, true);
        } catch (IOException e) {
            Panel.panel.addMessage("Port" + " " + _port + " "
                    + "jest już używany", Config.ERROR, true);
            Panel.panel.setEnabled();
            return;
        } catch (IllegalArgumentException e) {
            Panel.panel.addMessage("Numer portu jest zbyt długi", Config.ERROR, true);
            Panel.panel.setEnabled();
            return;
        }

        while (_online) {
            try {
                new Data(_serverSocket.accept());
            } catch (SocketException e) {
            } catch (IOException e) {
                Panel.panel.addMessage("Blad serwera", Config.ERROR, true);
            }
        }
    }

    public void interrupt() {
        try {
            _online = false;
            _serverSocket.close();
        } catch (IOException e) {
        }
    }
}