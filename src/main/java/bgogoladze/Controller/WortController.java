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
        try {
            this.wortPanel = new WortPanel(this, this.newImage(this.wortTrainer.ausgewaehlt()[1]), this.wortTrainer.getKorrekt(), this.wortTrainer.getAbgefragt());
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Fehler", JOptionPane.WARNING_MESSAGE);
        }
        this.wortView = new WortFrame(this.wortPanel);
        this.wortView.addWindowListener(this);
    }

    /**
     * Diese Methode neuer WortEintrag ruft einen random Worteintrag aus der Liste ab
     * @return gibt true zurück wenn ein neuer Eintrag abgerufen werden konnte,
     * wenn nicht wird der Eintrag gelöscht und false zurückgegeben
     */
    private boolean neuerWortEintrag() {
        this.wortTrainer.randomWortEintrag();
        String imagePath = this.wortTrainer.ausgewaehlt()[1];               // Bild-Pfad oder URL
        System.out.println("Ausgewählte Bild-URL: " + imagePath);           // Debugging-Ausgabe
        //zeigeBildImDialog(imagePath);                                       // Versuch, das Bild im Dialog anzuzeigen
        // Überprüfe, ob das Bild existiert
        if (!existence(imagePath)) {
            JOptionPane.showMessageDialog(null, "Das Bild " + imagePath + " existiert nicht!\nDer Worteintrag wird entfernt!", "Fehler beim Bild!", JOptionPane.WARNING_MESSAGE);
            this.wortTrainer.getWortListe().delWortEintrag(this.wortTrainer.ausgewaehlt()[0]);
            return false;
        }
        return true;
    }

    /*private void zeigeBildImDialog(String bildUrl) {
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
    }*/

    /**
     * Diese Methode existence überprüft ob der angegebene Pfad existiert
     * @param path ist der Pfad zum Überprüfen
     * @return true wenn der Pfad existiert und false wenn nicht
     */
    private boolean existence(String path) {
        // Überprüfe, ob der Pfad eine gültige URL ist
        try {
            new URL(path).openStream().close(); // Versuche, die URL zu öffnen
            return true; // Wenn erfolgreich, existiert die URL
        } catch (IOException e) {
            // Wenn IOException auftritt, ist es entweder keine URL oder die URL existiert nicht
            //File file = new File(path); // Überprüfe den lokalen Dateipfad
            //return file.exists(); // Rückgabe des Existenzstatus der lokalen Datei
            e.printStackTrace();
            return false;
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
     * Diese Methode newImage wird dann aufgerufen wenn ein neues Bild
     * zum Worttrainerhinzugefügt werden soll und das als Pfad zum Bild.
     * @param path gibt den Pfad des Bildes an
     * @return das Bild
     */
    private ImageIcon newImage(String path) {
        URL link = this.getClass().getResource(path);
        ImageIcon icon;
        if (link != null) {
            icon = new ImageIcon(link);
        } else {
            icon = new ImageIcon(path);
        }
        return icon;
        //return new ImageIcon(path);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /* Hinzufügen eines neuen Wort-Bild-Paares
        if ("add".equals(e.getActionCommand())) {
            String wort = JOptionPane.showInputDialog(null, "Bitte geben Sie ein neues Wort ein:");
            if (wort != null && !wort.isEmpty()) {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    String pfad = fileChooser.getSelectedFile().getAbsolutePath();
                    this.wortTrainer.getWortListe().addWortEintrag(wort, pfad);
                }
            }
        }*/
        String identify = e.getActionCommand();
        if(identify.equals("add")) {
            boolean gueltigkeit = true;
            String wort = JOptionPane.showInputDialog(null, "Bitte geben Sie ein Wort ein:");
            try {
                WortListe.checkWort(wort);
            } catch (Exception exception) {
                gueltigkeit = false;
                JOptionPane.showMessageDialog(null, exception.getMessage(), "Fehler beim Checken des Wortes!", JOptionPane.WARNING_MESSAGE);
            }
            if(gueltigkeit) {
                String path;
                JFileChooser choice = new JFileChooser();
                try {
                    if (choice.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                        path = choice.getSelectedFile().getAbsolutePath();
                        WortListe.checkUrl(path);
                        this.wortTrainer.getWortListe().addWortEintrag(wort, path);
                    }
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage(), "Fehler beim Checken der URL!", JOptionPane.WARNING_MESSAGE);
                }
            }
        } else if(identify.equals("reset")) {
            this.wortPanel.reset();
            this.wortTrainer.reset();
            try {
                this.wortPanel.setImage(this.newImage(this.wortTrainer.ausgewaehlt()[1]));
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage(), "Fehler beim Zurücksetzen!", JOptionPane.WARNING_MESSAGE);
            }

        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Die Eingabe wird geprüft, wenn ENTER gedrückt wird
        /*if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            this.hauptSchleife();
        }*/
    }

    /**
     * {@link KeyListener}
     * {@link KeyEvent}
     * Wird ausgelöst, wenn eine Taste freigegeben wurde, in diesem Fall ENTER.
     * Siehe die Klassenbeschreibung für KeyEvent für eine Definition des Ereignisses "Taste freigegeben".
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            boolean gueltigkeit = false;
            try {
                gueltigkeit = this.wortTrainer.check(this.wortPanel.getTextfield());
            }  catch(Exception ex){
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Fehler beim Checken der Eingabe!", JOptionPane.WARNING_MESSAGE);
            }
            if(gueltigkeit) {
                boolean ungueltigkeit = false;
                while(!ungueltigkeit) {
                    ungueltigkeit = neuerWortEintrag();
                }
                try {
                    this.wortPanel.refresh(true, this.wortTrainer.getKorrekt(), this.wortTrainer.getAbgefragt(), this.newImage(this.wortTrainer.ausgewaehlt()[1]));
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage(), "Fehler beim Aktualisieren der Statistik!", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                try {
                    this.wortPanel.refresh(false, this.wortTrainer.getKorrekt(), this.wortTrainer.getAbgefragt(), null);
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage(), "Fehler beim Aktualisieren!", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

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
                    JOptionPane.showMessageDialog(null, "Der Worttrainer konnte gespeichert werden!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,  ex.getMessage(), "Fehler beim Speichern!", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(null, "Der Worttrainer konnte nicht gespeichert werden!");
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
        int load = JOptionPane.showConfirmDialog(null, "Wollen Sie einen bereits erstellten Worttrainer laden?", "Worttrainer laden", JOptionPane.YES_NO_OPTION);
        if (load == JOptionPane.YES_OPTION) {
            WortTrainer wt = null;
            try {
                JFileChooser choice = new JFileChooser();
                if (choice.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    SpeichernUndLaden speichernLaden = new SpeichernUndLaden(choice.getSelectedFile().getAbsolutePath());          // Instanziieren der SpeichernUndLaden-Klasse mit dem ausgewählten Pfad
                    wt = speichernLaden.laden();                                                                                    // Laden des Worttrainers aus der Datei
                }
            } catch (IllegalArgumentException exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage(), "Fehler", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            WortListe wortListe = new WortListe("Papagei", "https://www.vetline.de/sites/default/files/2021-02/wellensittich.jpeg");
            wortListe.addWortEintrag("Hai", "https://naturdetektive.bfn.de/fileadmin/_processed_/f/f/csm_Weisser_Hai_Elias_Levy_cc-by-20_flach_b563f2725e.jpg");
            wortListe.addWortEintrag("Hamster", "https://blog.wwf.de/wp-content/uploads/2021/12/Feldhamster-Futter-Wangen-0079476299h-1920x1080-c-IMAGO-blickwinkel.jpg");
            wortListe.addWortEintrag("Hund", "https://s1.1zoom.me/b4452/667/Dogs_Golden_Retriever_Tongue_561568_1920x1080.jpg");
            wortListe.addWortEintrag("Katze", "https://s1.1zoom.me/b5050/687/Cats_Kittens_Cute_Glance_Bokeh_585449_1920x1080.jpg");
            wortListe.addWortEintrag("Fische", "https://wallpapers.com/images/hd/tropical-fish-with-corals-krz941d7wbb0jz08.jpg");
            new WortController(new WortTrainer(wortListe));
        }
        /*WortTrainer wt = null;
        int laden = JOptionPane.showConfirmDialog(null, "Möchten Sie einen bestehenden Rechtschreibtrainer laden?", "Rechtschreibtrainer laden", JOptionPane.YES_NO_OPTION);
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

        new WortController(wt);*/
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
