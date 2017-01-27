package com.mycompany.app;
        import java.awt.Color;
/**
 *
 * @author Magda& Kasia
 *przypisanie do zmiennych okersalniacych rozmiar czcionki, kolor, rozmiar pol detaultowych wartosci
 */
public class Config {
    public static int[] frameDimension;
    public static int[] buttonDimension;
    public static int[] textFieldDimension;
    public static Color[] fontColor;
    public static int[] fontSize;
    public static int port;
    public static int timeout;
    public static int INFO = 0;
    public static int SENT = 1;
    public static int RECEIVED = 2;
    public static int ERROR = 3;
    private static int[] _defaultFrameDimension = { 600, 700 };
    private static int[] _defaultbuttonDimension = { 80, 25 };
    private static int[] _defaultTextFieldDimension = { 7, 25 };
    private static Color[] _defaultfontColor = { new Color(0x0044AA),
            new Color(0x006600), new Color(0x8B008B), new Color(0xEE1111),
            new Color(0x0055BB) };
    private static int[] _defaultFontSize = { 12, 12 };
    private static int _defaultPort = 10001;
    private static int _defaultTimeout = 3000;
    static {
        frameDimension = _defaultFrameDimension;
        buttonDimension = _defaultbuttonDimension;
        textFieldDimension = _defaultTextFieldDimension;
        fontColor =  _defaultfontColor;
        fontSize =  _defaultFontSize;
        port =  _defaultPort;
        timeout =  _defaultTimeout;
    }
}