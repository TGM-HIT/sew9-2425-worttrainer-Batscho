package bgogoladze.Controller;

import java.awt.event.*;
import bgogoladze.Model.*;
import bgogoladze.View.WortFrame;
import bgogoladze.View.WortPanel;
import bgogoladze.Model.SpeichernUndLadenStrategie;
import bgogoladze.Model.SpeichernUndLaden;
import javax.swing.*;            // Für JOptionPane, JFrame, JPanel und JLabel
import java.io.File;
import java.net.MalformedURLException; // Für MalformedURLException
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Diese Klasse namens WortController ist in MVC die sogenannte Control-Class, welche
 * dafür sorgt, dass Logik (Model-Class) und GUI-Oberfläche (View-Class) nicht direkt
 * miteinander kommunizieren und in der Controller Klasse wird der Ablauf gesteuert und
 * dort befindet sich auch ebenfalls die main-Methode.
 * @author Gogoladze Batschana
 * @version 11-10-2024
 */
public class WortController implements ActionListener, DocumentListener, KeyListener, WindowListener {
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
     * speicherStrategie
     */
    private SpeichernUndLadenStrategie speicherStrategie;  // Speichern/Laden Strategie

    /**
     * Dies ist der Standardkonstruktor welcher einen Worttrainer als Parameter übernimmt und
     * alles managed was es im MVC Modell gibt
     * @param wortTrainer ist der Parameter welcher entscheidend für die Kontrolle ist
     */
    public WortController(WortTrainer wortTrainer) {
        this.wortTrainer = wortTrainer;
        this.wortPanel = new WortPanel(this, wortTrainer.getKorrekt(), wortTrainer.getAbgefragt());
        this.wortView = new WortFrame(this.wortPanel);
        this.speicherStrategie = new SpeichernUndLaden("src/main/resources/worttrainer_session.json");
        this.wortView.addWindowListener(this);
        updateImage();  // Initiales Bild setzen
    }

    /**
     * Diese Methode updateImage ist dazu da um das aktuelle Bild zu setzen und immer wieder zu ändern
     */
    private void updateImage() {
        try {
            String[] aktuellesWort = this.wortTrainer.ausgewaehlt();
            this.wortPanel.setImageUrl(aktuellesWort[1]);  // URL vom Wortpaar holen und im Panel setzen
        } catch (MalformedURLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Fehler", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Diese Methode checkUserInput dient zum Überprüfen der Eingabe im Textfield ob es Enabled sein soll oder nicht
     */
    private void checkUserInput() {
        String userInput = this.wortPanel.getTextfield();
        this.wortPanel.setButtonEnabled(!userInput.isBlank());
    }

    /**
     * Das ist der ActionListener wo die Action performed
     * wird mittels Steuerungsmethoden für Buttons
     * @param e ist das event welches performed wird
     */
    @Override
    // ActionListener-Implementierung für die Button-Steuerung
    public void actionPerformed(ActionEvent e) {
        String ac = e.getActionCommand();  // Dieser String nimmt die einzelnen internen Commands auf und führt bei Bedarf aus

        // Wenn auf den Speichern-Button geklickt wird:
        if (ac.equalsIgnoreCase("save")) {
            // Speicherlogik mit JFileChooser
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                try {
                    // Erstelle neue SpeichernUndLadenStrategie mit dem ausgewählten Dateipfad
                    this.speicherStrategie = new SpeichernUndLaden(selectedFile.getAbsolutePath());
                    this.speicherStrategie.speichern(this.wortTrainer);

                    JOptionPane.showMessageDialog(null, "Worttrainer erfolgreich gespeichert!",
                            "Speichervorgang der Worttrainer-Session", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Fehler beim Speichervorgang: " + ex.getMessage(),
                            "Speichervorgang fehlgeschlagen!", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        // Wenn auf den Laden-Button geklickt wird:
        else if (ac.equalsIgnoreCase("load")) {
            // Ladelogik mit JFileChooser
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                try {
                    // Erstelle neue SpeichernUndLadenStrategie mit dem ausgewählten Dateipfad
                    this.speicherStrategie = new SpeichernUndLaden(selectedFile.getAbsolutePath());
                    this.wortTrainer = this.speicherStrategie.laden();

                    // Aktualisiere das Bild und die Statistik
                    updateImage();
                    this.wortPanel.refresh(this.wortTrainer.getKorrekt(), this.wortTrainer.getAbgefragt(), this.wortTrainer.getFalsche());

                    JOptionPane.showMessageDialog(null, "Worttrainer erfolgreich geladen!",
                            "Ladevorgang der Worttrainer-Session", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Fehler beim Laden: " + ex.getMessage(),
                            "Ladevorgang fehlgeschlagen!", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        // Wenn auf den Nächste-Wort-Button geklickt wird:
        else if (ac.equalsIgnoreCase("next")) {
            this.wortTrainer.check(this.wortPanel.getTextfield());
            this.wortPanel.refresh(this.wortTrainer.getKorrekt(), this.wortTrainer.getAbgefragt(), this.wortTrainer.getFalsche());
            this.updateImage();
        }
    }

    /**
     * {@link DocumentListener}
     * {@link DocumentEvent}
     * Methoden für DocumentListener (zum Aktivieren/Deaktivieren des "Nächstes Wort"-Buttons)
     * @param e the event to be processed
     */
    @Override
    public void insertUpdate(DocumentEvent e) {
        this.wortPanel.setButtonEnabled(!this.wortPanel.getTextfield().isBlank());
    }

    /**
     * {@link DocumentListener}
     * {@link DocumentEvent}
     * Methoden für DocumentListener (zum Aktivieren/Deaktivieren des "Nächstes Wort"-Buttons)
     * @param e the event to be processed
     */
    @Override
    public void removeUpdate(DocumentEvent e) {
        wortPanel.setButtonEnabled(!wortPanel.getTextfield().isBlank());
    }

    /**
     * {@link DocumentListener}
     * {@link DocumentEvent}
     * Methoden für DocumentListener (zum Aktivieren/Deaktivieren des "Nächstes Wort"-Buttons)
     * @param e the event to be processed
     */
    @Override
    public void changedUpdate(DocumentEvent e) {
        wortPanel.setButtonEnabled(!wortPanel.getTextfield().isBlank());
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
        /*if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            this.wortTrainer.check(this.wortPanel.getTextfield());
            this.wortPanel.refresh(this.wortTrainer.getKorrekt(), this.wortTrainer.getAbgefragt(), this.wortTrainer.getFalsche());
            this.updateImage();
        }*/
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void windowOpened(WindowEvent e) {}

    /**
     * {@link WindowListener}
     * {@link WindowEvent}
     * Wird aufgerufen, wenn der Benutzer versucht, das Fenster
     * über das Systemmenü des Fensters zu schließen.
     * @param e the event to be processed
     */
    @Override
    public void windowClosing(WindowEvent e) {
        /*int save = JOptionPane.showConfirmDialog(null, "Möchten Sie Ihren Worttrainer sicher nicht speichern?", "Speichervorgang der Worttrainer-Session", JOptionPane.YES_NO_OPTION);
        if(save == JOptionPane.YES_OPTION) {
            JFileChooser choice = new JFileChooser();
            if(choice.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                try {
                    this.speicherStrategie.speichern(this.wortTrainer);
                    JOptionPane.showMessageDialog(null, "Speichervorgang des Worttrainers erfolgreich!");
                } catch (IllegalArgumentException exc) {
                    JOptionPane.showMessageDialog(null, exc.getMessage(), "Fehler", JOptionPane.WARNING_MESSAGE);
                    JOptionPane.showMessageDialog(null, "Speichervorgang des Worttrainers fehgeschlagen!");
                }
            }
        }*/
    }

    @Override
    public void windowClosed(WindowEvent e) {}
    @Override
    public void windowIconified(WindowEvent e) {}
    @Override
    public void windowDeiconified(WindowEvent e) {}
    @Override
    public void windowActivated(WindowEvent e) {}
    @Override
    public void windowDeactivated(WindowEvent e) {}

    /**
     * Das ist die main-Methode in der die WortController Instanz erzeugt wird
     * @param args wird nicht verwendet
     */
    public static void main(String[] args) {
        // Look And Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        // Erstelle neuen WortTrainer mit einer Liste von Wörtern
        WortListe wortListe = new WortListe("Papagei", "https://www.vetline.de/sites/default/files/2021-02/wellensittich.jpeg");
        wortListe.addWortEintrag("Hamster", "https://images5.alphacoders.com/577/thumb-1920-577864.jpg");
        wortListe.addWortEintrag("Fische", "https://wallpapers.com/images/hd/tropical-fish-with-corals-krz941d7wbb0jz08.jpg");
        wortListe.addWortEintrag("Hai", "https://s1.1zoom.me/b4850/625/Sharks_Underwater_world_564459_1920x1080.jpg");
        wortListe.addWortEintrag("Hund", "https://s1.1zoom.me/b4452/667/Dogs_Golden_Retriever_Tongue_561568_1920x1080.jpg");
        wortListe.addWortEintrag("Katze", "https://s1.1zoom.me/b5050/687/Cats_Kittens_Cute_Glance_Bokeh_585449_1920x1080.jpg");
        WortTrainer wortTrainerNew = new WortTrainer(wortListe);
        new WortController(wortTrainerNew);
    }
}
