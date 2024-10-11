package bgogoladze.View;
import javax.swing.*;
import java.awt.*;

/**
 * Diese Klasse namens WortFrame ist die ein Teil der View-Klassen in diesem Konzept und muss
 * jetzt codiert werden um die Aufgabe Worttrainer Reloaded abzuschließen.
 * Sie erbt von der Superklasse JFrame um auch alle Mehoden eines GUI-Frames benützen zu können.
 * Es wird ein Top-Level-Container dadurch kreiert in dem eine kleine Menübar und der Name als
 * Label zu sehen ist, wobei noch die Panel-Klasse aufgerufen wird.
 * @author Gogoladze Batschana
 * @version 11-10-2024
 */
public class WortFrame extends JFrame {
    /**
     * Konstruktor für das WortFrame.
     * Erstellt das Hauptfenster des Worttrainers mit einem Titel und einem Layout-Panel.
     * Initialisiert grundlegende Einstellungen wie Schließen-Verhalten, Menüleiste und
     * die Größe des Fensters.
     * @param layoutPanel Das Panel, das im Hauptframe angezeigt wird (z. B. das WortPanel).
     */
    public WortFrame(WortPanel layoutPanel) {
        super("[GK] 9a.1: Worttrainer Reloaded");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(createMenuBar());
        add(layoutPanel);
        setLocationRelativeTo(null);
        setSize(1000, 800);
        setVisible(true);
    }

    /**
     * Erstellt eine benutzerdefinierte Menüleiste (JMenuBar) für das Worttrainer-Fenster.
     * Die Menüleiste enthält ein zentriertes Label mit der Aufschrift "Worttrainer".
     * @return Die konfigurierte JMenuBar.
     */
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setOpaque(true);
        menuBar.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel label = new JLabel("[GK] 9a.1: Worttrainer Reloaded");
        label.setForeground(Color.WHITE);
        menuBar.add(label);
        menuBar.setBackground(new Color(11, 11, 161));
        menuBar.setPreferredSize(new Dimension(200, 30));
        return menuBar;
    }
    /**
     * Ausführen der Klasse WortFrame
     * @param args wird nicht verwendet
     */
    /*public static void main(String [] args) {
        try {
            WortPanel p = new WortPanel();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new WortFrame();
    }*/
}
