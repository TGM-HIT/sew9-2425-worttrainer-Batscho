package bgogoladze.View;

import bgogoladze.Controller.WortController;
import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Diese Klasse namens Wortpanel ist ein Panel und erbt daher von der Superklasse JPanel
 * um auch alle Eigenschaften und Methoden eines GUI-Panel ansich zu nehmen und zu verwenden.
 * Im Panel werden Sachen wie Buttons, Inputs, ... erstellt, da die Frame Klasse eine Grundstruktur
 * darstellt und ebenfalls ein Panel (Container) ist (Top-Level-Container) und hier genaueres geschieht.
 * @author Gogoladze Batschana
 * @version 11-10-2024
 */
public class WortPanel extends JPanel {
    /**
     * wortController
     */
    private WortController wortController;
    /**
     * bNext
     */
    private final JButton bNext = new JButton("Nächstes Wort");
    /**
     * bSave
     */
    private final JButton bSave = new JButton("Speichern");
    /**
     * bLoad
     */
    private final JButton bLoad = new JButton("Laden");
    /**
     * textField
     */
    private final JTextField textField = new JTextField();
    /**
     * imageLabel
     */
    private final JLabel imageLabel = new JLabel(this.image);
    /**
     * image
     */
    private ImageIcon image = new ImageIcon();
    /**
     * question
     */
    private final JLabel question = new JLabel("Welches Wort wird unten dargestellt (Eingabe zum Überprüfen)?");
    /**
     * correct
     */
    private final JLabel correct = new JLabel("Richtige Wörter:");
    /**
     * amount
     */
    private final JLabel amount = new JLabel("Anzahl Wörter:");
    /**
     * correctCounter
     */
    private final JLabel correctCounter = new JLabel("0");
    /**
     * amountCounter
     */
    private final JLabel amountCounter = new JLabel("0");

    /**
     * Dieser Konstruktor ist der Standardkonstruktor der keine Parameter
     * enthält und ein einfaches GUI erstellt mit meinem Namen als Titel
     * im Frame, und dem Inhalt.
     * @param wortController ist der Controller welcher alles verwaltet
     * @param correct ist die Anzahl an korrekten Antworten
     * @param amount ist die Anzahl an gestellten Fragen
     */
    public WortPanel(WortController wortController, int correct, int amount) {
        this.wortController = wortController;
        this.setLayout(new BorderLayout(20, 0));
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        this.textField.addKeyListener(this.wortController);
        this.bNext.setActionCommand("next");
        this.bNext.addActionListener(this.wortController);
        this.bSave.setActionCommand("save");
        this.bSave.addActionListener(this.wortController);
        this.bLoad.setActionCommand("load");
        this.bLoad.addActionListener(this.wortController);
        setButtonEnabled(!this.getTextfield().isBlank());
        this.textField.getDocument().addDocumentListener(this.wortController);

        this.correctCounter.setText(String.valueOf(correct));
        this.amountCounter.setText(String.valueOf(amount));

        // Oberer Bereich der GUI
        JPanel oben = new JPanel(new GridLayout(2, 2, 0, 10));
        oben.add(this.question);
        oben.add(new JLabel());
        oben.add(this.textField);
        oben.add(this.bNext);

        // Mittlerer Bereich der GUI
        JPanel mitte = new JPanel();
        this.imageLabel.setHorizontalAlignment(this.getWidth() / 2);
        mitte.add(this.imageLabel);


        // Unterer Bereich der GUI
        JPanel unten = new JPanel(new GridLayout(2, 3, 0, 10));
        unten.add(this.correct);
        unten.add(this.correctCounter);
        unten.add(this.bSave);
        unten.add(this.amount);
        unten.add(this.amountCounter);
        unten.add(this.bLoad);

        // Hinzufügen der Container zum Top-Level-Container
        super.add(oben, BorderLayout.PAGE_START);
        super.add(mitte, BorderLayout.CENTER);
        super.add(unten, BorderLayout.PAGE_END);
    }

    /**
     * Diese Methode setImage setzt das als Parameter übergebene Bild für das Attribut
     * image um dieses dann in ein JLabel zu setzen welcher wie ein "Placeholder" wirkt
     * @param url ist die URL des Bildes in der GUI
     */
    public void setImageUrl(String url) throws MalformedURLException {
        this.image.getImage().getScaledInstance(650, 450, Image.SCALE_SMOOTH);
        this.image = new ImageIcon(new URL(url));
        this.imageLabel.setIcon(this.image);
    }

    /**
     * Diese Methode getTextfield ist eine getter-Methode für den Input
     * des Textfeldes und liefert einen String zurück
     * @return den eingegebenen Input im Textfeld als String
     */
    public String getTextfield() {
        return this.textField.getText();
    }

    /**
     * Diese Methode setButtonEnabled setzt den Button für das nächste Wort auf enabled oder disabled
     * basierend auf den boolean Wert
     * @param enabled ist der boolean Wert der entscheidet
     */
    public void setButtonEnabled(boolean enabled) {
        this.bNext.setEnabled(enabled);
    }

    /**
     * Diese Methode refresh (update) dient dazu um wichtige Attribute wie die Zähler immer
     * auf dem laufenden zu halten, was hier ermöglicht wird
     //@param isCorrect ist der Wahrheitswert der dafür sorgt ob ein Image gesettet werden soll
     * @param correctCounter ist der Zähler für die korrekte Anzahl an Wörtern
     * @param amountCounter ist der Zähler für die Anzahl an eingegebenen Wörtern
     //@param url ist das Image welches hinzugefügt werden soll
     */
    public void refresh( int correctCounter, int amountCounter) {
        this.textField.setText("");
        //if (isCorrect) setImageUrl(url);
        this.correctCounter.setText(String.valueOf(correctCounter));
        this.amountCounter.setText(String.valueOf(amountCounter));
    }

    /**
     * Diese Methode reset dient dazu um die Attribute wie das Textfeld oder die Zähler
     * zu reseten um wieder neu anzufangen, was ganz einfach bedeutet, dass die Zähler zurück
     * auf 0 gehen und das Textfeld leer ist.
     */
    public void reset() {
        this.textField.setText("");
        this.correctCounter.setText("0");
        this.amountCounter.setText("0");
        this.image = new ImageIcon();
    }
}
