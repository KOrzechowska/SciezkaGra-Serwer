package com.mycompany.app;
        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.io.OutputStream;
        import java.io.PrintWriter;
        import java.net.Socket;
        import java.net.SocketTimeoutException;
        import java.util.ArrayList;

/**
 *
 *
 *Komunikacja z klientem - wysylanie odpowiedi
 */
public class Data extends Thread {
    /**tworzenie gniazda*/
    private Socket _socket;
    /**tworzenie zmiennej strumienia wejsciowego*/
    private BufferedReader _br;
    /**tworzenie zmiennej strumienia wyjsciowego*/
    private PrintWriter _pw;

    Data(Socket socket) throws IOException {

        _socket = socket;
        InputStream is = socket.getInputStream();
        _br = new BufferedReader(new InputStreamReader(is));
        OutputStream os = socket.getOutputStream();
        _pw = new PrintWriter(os, true);
        start();
    }

    /**
     * odbieranie komunikatwo od kilenta oraz wysylanie odpowiedzi*/
    public void run() {

        try {
            String command = _br.readLine();
            Panel.panel.addMessage("Od" + " "
                    + _socket.getInetAddress().getHostAddress() + ": "
                    + command, Config.RECEIVED, true);

            switch (command) {

                /**Kient chce plansze klockow*/
                case Protocol.GETPLANSZAKLOCKOW: {
                    String answer = null;
                    Panel.panel.addMessage("Czekam na numer poziomu", Config.INFO, true);
                    try {
                        _socket.setSoTimeout(Config.timeout);
                        String number = _br.readLine();
                        Panel.panel.addMessage("Do"+ " "
                                + _socket.getInetAddress().getHostAddress() + ": "
                                + number, Config.RECEIVED, true);

                        _socket.setSoTimeout(0);
                        answer = SciezkaConfig.dajStringaZPlanszy("plansza", number);
                    } catch (SocketTimeoutException e) {
                        _socket.setSoTimeout(0);
                        Panel.panel.addMessage("Zbyt długi czas oczekiwania", Config.ERROR,
                                true);
                    }
                    /**obsluga bledu*/
                    if (answer == null) {
                        _pw.println(Protocol.ERROR);
                        Panel.panel.addMessage("Nie można odczytać planszy kloców z pliku", Config.ERROR,
                                true);
                        Panel.panel.addMessage("Do" + " "
                                + _socket.getInetAddress().getHostAddress() + ": "
                                + Protocol.ERROR, Config.SENT, true);
                        _socket.close();
                        break;
                    }
                    /**odpowiedz serwera-wyslanie definicji planszy*/
                    Panel.panel.addMessage("Wysyłanie klockow planszy", Config.INFO, true);
                    _pw.println(answer);
                    Panel.panel.addMessage("Do" + " "
                            + _socket.getInetAddress().getHostAddress()
                            + ": 0x" + answer, Config.SENT, true);

                    _socket.close();
                    break;

                }

                /**Klient chce liczbe zyc*/
                case Protocol.GETZYCIA: {
                    String answer = SciezkaConfig.dajStringZPlanszy("lifes");
                    if (answer == null) {
                        _pw.println(Protocol.ERROR);
                        Panel.panel.addMessage("Nie można odczytać liczby żyć z pliku konfiguracyjnego", Config.ERROR,
                                true);
                        Panel.panel.addMessage("Do" + " "
                                + _socket.getInetAddress().getHostAddress() + ": "
                                + Protocol.ERROR, Config.SENT, true);
                        _socket.close();
                        break;
                    }
                    /**odpowiedz serwera- wyslanie liczby zyc*/
                    Panel.panel.addMessage("Wysyłanie liczby życ", Config.INFO, true);
                    _pw.println(answer);
                    Panel.panel.addMessage("Do" + " "
                            + _socket.getInetAddress().getHostAddress() + ": "
                            + answer, Config.SENT, true);
                    //}
                    _socket.close();
                    break;
                }


                /**Klient chce predkosc poruszania*/
                case Protocol.GETSPEED : {
                    String answer = null;
                    Panel.panel.addMessage("Czekam na numer poziomu trudnosci", Config.INFO, true);
                    try {
                        _socket.setSoTimeout(Config.timeout);
                        String number = _br.readLine();
                        Panel.panel.addMessage("Do"+ " "
                                + _socket.getInetAddress().getHostAddress() + ": "
                                + number, Config.RECEIVED, true);

                        _socket.setSoTimeout(0);
                        answer = String.valueOf(SciezkaConfig.dajIntZProperties("speed", number));
                    } catch (SocketTimeoutException e) {
                        _socket.setSoTimeout(0);
                        Panel.panel.addMessage("Zbyt długi czas oczekiwania", Config.ERROR,
                                true);
                    }
                    /**obsluga bledu*/
                    if (answer == null) {
                        _pw.println(Protocol.ERROR);
                        Panel.panel.addMessage("Nie można odczytać predkosci z pliku", Config.ERROR,
                                true);
                        Panel.panel.addMessage("Do" + " "
                                + _socket.getInetAddress().getHostAddress() + ": "
                                + Protocol.ERROR, Config.SENT, true);
                        _socket.close();
                        break;
                    }
                    /**odpowiedz serwera-wyslanie definicji planszy*/
                    Panel.panel.addMessage("Wysyłanie predkosc dla danego poziomu trudnosci", Config.INFO, true);
                    _pw.println(answer);
                    Panel.panel.addMessage("Do" + " "
                            + _socket.getInetAddress().getHostAddress()
                            + ": 0x" + answer, Config.SENT, true);

                    _socket.close();
                    break;

                }
                /**Klient wysyla swoj wynik*/
                case Protocol.SENDINGSCORE: {
                    Panel.panel.addMessage("Czekam na wynik", Config.INFO, true);
                    try {
                        _socket.setSoTimeout(Config.timeout);
                        String name = _br.readLine();
                        Panel.panel.addMessage("Od" + " "
                                + _socket.getInetAddress().getHostAddress() + ": "
                                + name, Config.RECEIVED, true);
                        String score = _br.readLine();
                        Panel.panel.addMessage("Od" + " "
                                + _socket.getInetAddress().getHostAddress() + ": "
                                + score, Config.RECEIVED, true);
                        _socket.setSoTimeout(0);
                        SciezkaConfig.saveScore(name, score);
                    } catch (SocketTimeoutException e) {
                        _socket.setSoTimeout(0);
                        Panel.panel.addMessage("Zbyt długi czas oczekiwania", Config.ERROR,
                                true);
                    }
                    _socket.close();
                    break;
                }
                /**Klient chce list najlepszych wynikow*/
                case Protocol.GETHIGHSCORES: {
                    ArrayList<HighScore> answer = SciezkaConfig.getHighScores();
                    if (answer == null) {
                        _pw.println(Protocol.ERROR);
                        Panel.panel.addMessage("Nie można odczytać najlepszych wyników z pliku", Config.ERROR,
                                true);
                        Panel.panel.addMessage("Do" + " "
                                + _socket.getInetAddress().getHostAddress() + ": "
                                + Protocol.ERROR, Config.SENT, true);
                        _socket.close();
                        break;
                    }
                    /**odpowiedz serwera- wyslanie listy wynikow*/
                    Panel.panel.addMessage("Wysyłanie najlepszych wyników", Config.INFO, true);
                    for (int i = 0; i < 20; i++) {
                        _pw.println(answer.get(i));
                        Panel.panel.addMessage("Do" + " "
                                + _socket.getInetAddress().getHostAddress() + ": "
                                + answer.get(i), Config.SENT, true);
                    }
                    _socket.close();
                    break;
                }

                /**obsluga bledu*/
                default:
                    _pw.println(Protocol.ERROR);
                    Panel.panel.addMessage("Nieznana komenda", Config.ERROR, true);
                    Panel.panel.addMessage("Do" + " "
                            + _socket.getInetAddress().getHostAddress() + ": "
                            + Protocol.ERROR, Config.SENT, true);
                    _socket.close();
                    break;
            }
            _socket.close();

        } catch (IOException e) {
            Panel.panel.addMessage("Bład wejcia/wyjcia", Config.ERROR, true);
        }


    }
}
