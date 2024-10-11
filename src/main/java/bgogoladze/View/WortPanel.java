package bgogoladze.View;

import bgogoladze.Controller.WortController;

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
    private WortController wortController;
    private final JButton bAdd = new JButton("Wort hinzufügen");
    private final JButton bReset = new JButton("Zurücksetzen");
    private final JTextField textField = new JTextField();
    private final JLabel image = new JLabel();
    private final JLabel question = new JLabel("Welches Wort wird unten dargestellt (Eingabe zum Überprüfen)?");
    private final JLabel correct = new JLabel("Richtige Wörter:");
    private final JLabel amount = new JLabel("Anzahl Wörter:");
    private final JLabel correctCounter = new JLabel("0");
    private final JLabel amountCounter = new JLabel("0");

    /**
     * Dieser Konstruktor ist der Standardkonstruktor der keine Parameter
     * enthält und ein einfaches GUI erstellt mit meinem Namen als Titel
     * im Frame, und dem Inhalt.
     */
    public WortPanel(WortController wortController, ImageIcon picture, int correct, int amount) throws IOException {
        this.wortController = wortController;
        setLayout(new BorderLayout(20, 0));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

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
        this.image.setHorizontalAlignment(JLabel.CENTER);

        // Unterer Bereich der GUI
        JPanel unten = new JPanel(new GridLayout(2, 3, 0, 10));
        unten.add(correct);
        unten.add(this.correctCounter);
        unten.add(this.bReset);
        unten.add(amount);
        unten.add(this.amountCounter);
        unten.add(this.bAdd);

        // Hinzufügen der Container zum Top-Level-Container
        add(oben, BorderLayout.PAGE_START);
        add(this.image, BorderLayout.CENTER);
        add(unten, BorderLayout.PAGE_END);
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
}
