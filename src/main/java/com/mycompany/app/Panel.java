package com.mycompany.app;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
/**
 *
 *
 *tworzenie panelu serwera
 */
@SuppressWarnings("serial")
public class Panel extends JPanel {

    public static Panel panel;
    private JPanel _info;
    private JButton _start;
    private JTextField _port;
    private JScrollPane _scroll;
    private boolean _online;
    private Connection _connection;

    public Panel() {
        panel = this;
        _info = new JPanel();
        /**tworzenie potrzebnych paneli*/
        JPanel button = new JPanel();
        JPanel north = new JPanel();
        JPanel south = new JPanel();
        JPanel port = new JPanel();
        /**utworzenie guzików Start i Wyjscie*/
        _start = new JButton("START");
        JButton exit = new JButton("WYJCIE");
        /**nadanie adresu IP oraz numeru portu*/
        JLabel ip;
        JLabel portText = new JLabel("Port: ");
        try {
            ip = new JLabel("IP Servera: "
                    + InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            addMessage("Nie można ustalić adresu IP serwera", Config.ERROR, true);
            ip = new JLabel("   " + "IP Serwera" + ":   "
                    + "Nieznany");
        }
        /**utworzenie pola tekstowego do wpisania numeru portu*/
        _port = new JTextField(Integer.toString(Config.port));
        /**możliwoć przewijania okna*/
        _scroll = new JScrollPane(_info);
        _online = false;
        setLayout(new BorderLayout());
        south.setLayout(new BorderLayout());
        north.setLayout(new BorderLayout());
        _info.setLayout(new BoxLayout(_info, BoxLayout.Y_AXIS));
        /**ustawienie koloru czcionki*/
        ip.setForeground(Config.fontColor[4]);
        portText.setForeground(Config.fontColor[4]);

        _start.setFocusable(false);
        exit.setFocusable(false);
        /**ustawienie rozmiaru wywietlanyych guzikow i pol*/
        _start.setPreferredSize(new Dimension(80,
                Config.buttonDimension[1]));
        exit.setPreferredSize(new Dimension(Config.buttonDimension[0],
                Config.buttonDimension[1]));
        _port.setPreferredSize(new Dimension(0, Config.textFieldDimension[1]));
        _port.setColumns(Config.textFieldDimension[0]);
        _port.setSize(new Dimension(0, Config.textFieldDimension[1]));
        /**ustawienie rozmiry czcionki*/
        ip.setFont(ip.getFont().deriveFont((float) Config.fontSize[0]));
        portText.setFont(ip.getFont().deriveFont((float) Config.fontSize[0]));
        /**dodanie gucikow oraz pola tekstowego do panelu*/
        button.add(exit);
        button.add(_start);
        port.add(portText);
        port.add(_port);
        /**ustawieni polozenia*/
        south.add(button, BorderLayout.EAST);
        north.add(ip, BorderLayout.WEST);
        north.add(port, BorderLayout.EAST);
        add(_scroll, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);
        add(north, BorderLayout.NORTH);

        /**Zamkniecie okna gdy wcisniety przycisk Wyjscie*/
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
        /**obsuluga przycisku start, wlaczenie nasluchiwania serwera oraz obsluga bledow*/
        _start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {

                if (_online) {

                    _connection.interrupt();
                    setEnabled();
                } else {
                    try {
                        int port = Integer.parseInt(_port.getText());
                        _start.setText("STOP");
                        _port.setEnabled(false);
                        _online = true;
                        try {
                            _connection = new Connection(port);
                        } catch (Exception e) {
                        }
                    } catch (NumberFormatException e) {
                        addMessage("Niepoprawny numer portu", Config.ERROR, true);
                    }
                }
            }
        });
        _info.addContainerListener(new ContainerListener() {
            public void componentAdded(ContainerEvent e) {
                _scroll.getVerticalScrollBar().setValue(
                        _scroll.getVerticalScrollBar().getMaximum());
            }

            public void componentRemoved(ContainerEvent e) {
            }
        });
    }
    /**
     * funkcja do wypisywania komunikatow
     * @param message - wyswietlana wiadomosc
     * @param color - kolor w zaleznosci jakiego typy jest wyswietlana wiadomoas
     * @param time - czas
     */
    public void addMessage(String message, int color, boolean time) {

        JLabel label;
        if (time) {
            label = new JLabel(" ["
                    + DateFormat.getDateTimeInstance().format(new Date())
                    + "] " + message);
        } else {
            label = new JLabel(message);
        }
        label.setFont(label.getFont().deriveFont((float) Config.fontSize[1]));
        label.setForeground(Config.fontColor[color]);
        _info.add(label);
        _info.revalidate();
    }


    /** ustawia mais start na guziku*/
    public void setEnabled() {
        _start.setText("START");
        _port.setEnabled(true);
        _online = false;
    }
}