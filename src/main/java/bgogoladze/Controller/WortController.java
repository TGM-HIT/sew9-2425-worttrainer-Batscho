package bgogoladze.Controller;

import java.awt.*;
import java.awt.event.*;

import bgogoladze.Model.*;
import bgogoladze.View.WortFrame;
import bgogoladze.View.WortPanel;

import javax.swing.*;            // Für JOptionPane, JFrame, JPanel und JLabel
import java.awt.*;               // Für Image und ImageIcon
import java.awt.image.BufferedImage; // Für BufferedImage
import java.io.File;
import java.io.IOException;      // Für IOException
import java.net.MalformedURLException; // Für MalformedURLException
import java.net.URL;             // Für URL
import javax.imageio.ImageIO;   // Für ImageIO

/**
 * Diese Klasse namens WortController ist in MVC die sogenannte Control-Class, welche
 * dafür sorgt, dass Logik (Model-Class) und GUI-Oberfläche (View-Class) nicht direkt
 * miteinander kommunizieren und in der Controller Klasse wird der Ablauf gesteuert und
 * dort befindet sich auch ebenfalls die main-Methode.
 * @author Gogoladze Batschana
 * @version 11-10-2024
 */
public class WortController implements ActionListener, KeyListener, WindowListener {
    /**
     * wortTrainer
     */
    private WortTrainer wortTrainer;
    /**
     * wortView
     */
    private WortFrame wortView;
    /**
     * wortPanel
     */
    private WortPanel wortPanel;

    /**
     * Dies ist der Konstruktor der Control-Klasse und initialisiert
     * die Elemente der View und Model Klassen
     * @param wortTrainer Die Model-Klasse, die den Rechtschreibtrainer repräsentiert
     */
    public WortController(WortTrainer wortTrainer) {
        this.wortTrainer = wortTrainer;
        // Auswahl eines gültigen Wortpaares
        boolean ungueltigkeit = false;
        while (!ungueltigkeit) {
            ungueltigkeit = this.neuerWortEintrag();
        }
        // Hauptschleife des Spiels, basierend auf JOptionPane
        this.hauptSchleife();
    }

    /**
     * Diese Methode überprüft, ob ein Wort-Bild-Paar ausgewählt wurde und die URL gültig ist.
     * @return true, wenn ein gültiges Wortpaar gefunden wurde
     */
    private boolean neuerWortEintrag() {
        this.wortTrainer.randomWortEintrag();
        String imagePath = this.wortTrainer.ausgewaehlt()[1]; // Bild-Pfad oder URL

        // Debugging-Ausgabe
        System.out.println("Ausgewählte Bild-URL: " + imagePath);

        // Versuche, das Bild im Dialog anzuzeigen
        zeigeBildImDialog(imagePath);

        // Überprüfe, ob das Bild existiert
        if (!existence(imagePath)) {
            JOptionPane.showMessageDialog(null, "Das Bild " + imagePath + " existiert nicht!\nDer Worteintrag wird entfernt!", "Fehler", JOptionPane.WARNING_MESSAGE);
            this.wortTrainer.getWortListe().delWortEintrag(imagePath);
            return false;
        }
        return true;
    }

    private void zeigeBildImDialog(String bildUrl) {
        BufferedImage originalImage;
        try {
            originalImage = ImageIO.read(new URL(bildUrl));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ungültige Bild-URL: " + bildUrl, "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Fehler beim Laden des Bildes: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Skaliere das Bild
        Image scaledImage = originalImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(scaledImage);

        // Erstelle ein Panel für die Anzeige
        JPanel panel = new JPanel();
        JLabel label = new JLabel(imageIcon);
        panel.add(label);

        // Zeige das Panel im JOptionPane
        JOptionPane.showMessageDialog(null, panel, "Bildanzeige", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Überprüft, ob ein Bild-Pfad existiert
     * @param path der zu prüfende Pfad
     * @return true, wenn der Pfad existiert, andernfalls false
     */
    private boolean existence(String path) {
        // Überprüfe, ob der Pfad eine gültige URL ist
        try {
            new URL(path).openStream().close(); // Versuche, die URL zu öffnen
            return true; // Wenn erfolgreich, existiert die URL
        } catch (IOException e) {
            // Wenn IOException auftritt, ist es entweder keine URL oder die URL existiert nicht
            File file = new File(path); // Überprüfe den lokalen Dateipfad
            return file.exists(); // Rückgabe des Existenzstatus der lokalen Datei
        }
    }

    /**
     * Hauptschleife des Spiels: Ein Wort-Bild-Paar wird angezeigt und die Eingabe des Nutzers geprüft.
     */
    private void hauptSchleife() {
        boolean weitermachen = true;
        while (weitermachen) {
            String eingabe = (String) JOptionPane.showInputDialog(null,
                    "Statistik: " + this.wortTrainer.getStatistic() + "\nBitte geben Sie das Wort ein:",
                    "Rechtschreibtrainer", JOptionPane.QUESTION_MESSAGE, this.newImage(this.wortTrainer.ausgewaehlt()[1]),
                    null, null);
            if (eingabe == null || eingabe.trim().isEmpty()) {
                weitermachen = false;
                break;
            }
            try {
                boolean korrekt = this.wortTrainer.check(eingabe);
                if (korrekt) {
                    JOptionPane.showMessageDialog(null, "Richtig!", "Ergebnis", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Falsch, versuchen Sie es nochmal!", "Ergebnis", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            }
            boolean ungueltigkeit = false;
            while (!ungueltigkeit) {
                ungueltigkeit = neuerWortEintrag();
            }
        }
    }

    /**
     * Erstellt ein Bild-Icon basierend auf dem Pfad
     * @param path der Pfad zum Bild
     * @return das Bild als ImageIcon
     */
    private ImageIcon newImage(String path) {
        return new ImageIcon(path);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Hinzufügen eines neuen Wort-Bild-Paares
        if ("add".equals(e.getActionCommand())) {
            String wort = JOptionPane.showInputDialog(null, "Bitte geben Sie ein neues Wort ein:");
            if (wort != null && !wort.isEmpty()) {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    String pfad = fileChooser.getSelectedFile().getAbsolutePath();
                    this.wortTrainer.getWortListe().addWortEintrag(wort, pfad);
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Die Eingabe wird geprüft, wenn ENTER gedrückt wird
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            this.hauptSchleife();
        }
    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {}

    /**
     * Invoked the first time a window is made visible.
     *
     * @param e the event to be processed
     */
    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) {
        int speichern = JOptionPane.showConfirmDialog(null, "Möchten Sie den Fortschritt speichern?", "Speichern", JOptionPane.YES_NO_OPTION);
        if (speichern == JOptionPane.YES_OPTION) {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                String pfad = chooser.getSelectedFile().getAbsolutePath();
                try {
                    SpeichernUndLaden speichernLaden = new SpeichernUndLaden(pfad);     // Instanziieren der neuen SpeichernUndLaden-Klasse mit dem ausgewählten Pfad
                    speichernLaden.speichern(this.wortTrainer);                         // Speichern der aktuellen WortTrainer-Session
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Fehler beim Speichern", "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     * Invoked when a window has been closed as the result
     * of calling dispose on the window.
     *
     * @param e the event to be processed
     */
    @Override
    public void windowClosed(WindowEvent e) {}

    /**
     * Invoked when a window is changed from a normal to a
     * minimized state. For many platforms, a minimized window
     * is displayed as the icon specified in the window's
     * iconImage property.
     *
     * @param e the event to be processed
     * @see Frame#setIconImage
     */
    @Override
    public void windowIconified(WindowEvent e) {}

    /**
     * Invoked when a window is changed from a minimized
     * to a normal state.
     *
     * @param e the event to be processed
     */
    @Override
    public void windowDeiconified(WindowEvent e) {}

    /**
     * Invoked when the Window is set to be the active Window. Only a Frame or
     * a Dialog can be the active Window. The native windowing system may
     * denote the active Window or its children with special decorations, such
     * as a highlighted title bar. The active Window is always either the
     * focused Window, or the first Frame or Dialog that is an owner of the
     * focused Window.
     *
     * @param e the event to be processed
     */
    @Override
    public void windowActivated(WindowEvent e) {}

    /**
     * Invoked when a Window is no longer the active Window. Only a Frame or a
     * Dialog can be the active Window. The native windowing system may denote
     * the active Window or its children with special decorations, such as a
     * highlighted title bar. The active Window is always either the focused
     * Window, or the first Frame or Dialog that is an owner of the focused
     * Window.
     *
     * @param e the event to be processed
     */
    @Override
    public void windowDeactivated(WindowEvent e) {}

    /**
     * Ausführen des Controllers zum Finale
     * @param args wird nicht verwendet
     */
    public static void main(String[] args) {
        WortTrainer wt = null;

        int laden = JOptionPane.showConfirmDialog(null, "Möchten Sie einen bestehenden Trainer laden?", "Trainer laden", JOptionPane.YES_NO_OPTION);
        if (laden == JOptionPane.YES_OPTION) {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                String pfad = chooser.getSelectedFile().getAbsolutePath();
                try {
                    SpeichernUndLaden speichernLaden = new SpeichernUndLaden(pfad);          // Instanziieren der SpeichernUndLaden-Klasse mit dem ausgewählten Pfad
                    wt = speichernLaden.laden();                                        // Laden des Worttrainers aus der Datei
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(null, "Fehler beim Laden", "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        if (wt == null) {
            WortListe wortListe = new WortListe("Papagei", "https://www.vetline.de/sites/default/files/2021-02/wellensittich.jpeg");
            wortListe.addWortEintrag("Hai", "https://naturdetektive.bfn.de/fileadmin/_processed_/f/f/csm_Weisser_Hai_Elias_Levy_cc-by-20_flach_b563f2725e.jpg");
            wortListe.addWortEintrag("Hund", "https://cdn.royalcanin-weshare-online.io/mOgVlYgBaPOZra8q1ABE/v3/pubertat-beim-hund-2");
            wortListe.addWortEintrag("Hamster", "https://blog.wwf.de/wp-content/uploads/2021/12/Feldhamster-Futter-Wangen-0079476299h-1920x1080-c-IMAGO-blickwinkel.jpg");
            wortListe.addWortEintrag("Fische", "https://wallpapers.com/images/hd/tropical-fish-with-corals-krz941d7wbb0jz08.jpg");
            wt = new WortTrainer(wortListe);
        }

        new WortController(wt);
    }

    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {}
}
