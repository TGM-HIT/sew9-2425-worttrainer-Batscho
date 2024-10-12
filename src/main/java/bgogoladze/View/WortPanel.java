package bgogoladze.View;

import bgogoladze.Controller.WortController;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;

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
     * bAdd
     */
    private final JButton bAdd = new JButton("Wort hinzufügen");
    /**
     * bReset
     */
    private final JButton bReset = new JButton("Zurücksetzen");
    /**
     * textField
     */
    private final JTextField textField = new JTextField();
    /**
     * image
     */
    private final JLabel image = new JLabel();
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
     * @param picture ist das zu setzende Bild
     * @param correct ist die Anzahl an korrekten Antworten
     * @param amount ist die Anzahl an gestellten Fragen
     */
    public WortPanel(WortController wortController, ImageIcon picture, int correct, int amount) {
        this.wortController = wortController;
        this.setLayout(new BorderLayout(20, 0));
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        this.textField.addKeyListener(this.wortController);
        this.bAdd.setActionCommand("add");
        this.bAdd.addActionListener(this.wortController);
        this.bReset.setActionCommand("reset");
        this.bReset.addActionListener(this.wortController);

        this.correctCounter.setText(String.valueOf(correct));
        this.amountCounter.setText(String.valueOf(amount));

        // Oberer Bereich der GUI
        JPanel oben = new JPanel(new GridLayout(2, 1, 0, 10));
        oben.add(this.question);
        oben.add(this.textField);

        // Mittlerer Bereich der GUI
        setImage(picture);
        this.image.setHorizontalAlignment(this.getWidth() / 2);

        // Unterer Bereich der GUI
        JPanel unten = new JPanel(new GridLayout(2, 3, 0, 10));
        unten.add(this.correct);
        unten.add(this.correctCounter);
        unten.add(this.bReset);
        unten.add(this.amount);
        unten.add(this.amountCounter);
        unten.add(this.bAdd);

        // Hinzufügen der Container zum Top-Level-Container
        super.add(oben, BorderLayout.PAGE_START);
        super.add(this.image, BorderLayout.CENTER);
        super.add(unten, BorderLayout.PAGE_END);
    }

    /**
     * Diese Methode setImage setzt das als Parameter übergebene Bild für das Attribut
     * image um dieses dann in ein JLabel zu setzen welcher wie ein "Placeholder" wirkt
     * @param picture setzt das Bild welches als Parameter gegeben ist auf das Attribut
     */
    public void setImage(ImageIcon picture) {
        Image img = picture.getImage().getScaledInstance(650, 450, Image.SCALE_SMOOTH);
        this.image.setIcon(new ImageIcon(img));
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
     * Diese Methode refresh (update) dient dazu um wichtige Attribute wie die Zähler immer
     * auf dem laufenden zu halten, was hier ermöglicht wird
     * @param isCorrect ist der Wahrheitswert der dafür sorgt ob ein Image gesettet werden soll
     * @param correctCounter ist der Zähler für die korrekte Anzahl an Wörtern
     * @param amountCounter ist der Zähler für die Anzahl an eingegebenen Wörtern
     * @param picture ist das Image welches hinzugefügt werden soll
     */
    public void refresh(boolean isCorrect, int correctCounter, int amountCounter, ImageIcon picture) {
        this.textField.setText("");
        if (isCorrect) setImage(picture);
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
    }
}
