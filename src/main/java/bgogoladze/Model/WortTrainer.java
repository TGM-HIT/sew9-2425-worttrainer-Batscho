package bgogoladze.Model;

import java.util.Random;

/**
 * Diese Klasse namens WortTrainer beinhaltet einen einen Standartkonstruktor, Attribute für die WortListe (Wort-Bild-Pairs),
 * den aktuellen Worteintrag, die Anzahl an abgefragten Fragen und korrekt beanworteten Fragen, sowie ein abgeleitetes Attribut
 * welches die Statistik bildet, sowie Methoden für den ausgewählten Worteintrag, eine Methode zum randomizen der ausgewählten
 * Einträge, eine Methode Check zum Checken der korrekten Antworten mit und ohne Case-sensetivity, Zugriffsmethoden
 * für die Attribute (getter und setter) und auch eine reset-Methode zum zurücksetzen der ganzen Statistik
 * Nun diese Klasse ist der Rechtschreibtrainer und wird dann in das MVC Konzept eingebunden.
 * Der Worttrainer ist ebenso eine Logikklasse zum verwalten der anderen Logik Komponenten.
 * @author Gogoladze Batschana
 * @version 11-10-2024
 */
public class WortTrainer {
    private WortListe wortListe;        //Das Attribut welches mehrere Worteinträge speichert
    private String[] aktuell;           //Der aktuelle Worteintrag
    private int abgefragt;              //Die Anzahl an Wörtern die abgefragt wurden
    private int korrekt;                //Die Anzahl der abgefragten Wörter die erraten wurden
    private StringBuilder statistic;    //Ein abgeleitetes Attribut welches die Statistik angibt

    /**
     * Dies ist der Konstruktor bei dem die übergebenen Parameter in die Attrribute gespeichert werden
     * @param wortListe ist die Wortliste die gesetzt wird um die Wort-Bild Paare zu verwalten
     */
    public WortTrainer(WortListe wortListe) {
        if(wortListe != null) {
            this.wortListe = wortListe;
        } else {
            throw new NullPointerException("Die übergebene WortListe darf nicht null sein!");
        }
    }

    /**
     * Diese Methode randomWortEintrag liefert einen zufälligen Worteintrag welcher drüber aufgerufen werden kann
     * @return den zufällig ausgewählten WortEintrag
     */
    public String[] randomWortEintrag() {
        Random zufall = new Random();
        String[] all = this.wortListe.getWortListe().keySet().toArray(new String[0]);
        this.aktuell = wortListe.getWortEintrag(all[zufall.nextInt(all.length)]);
        return this.aktuell;
        /*do {
            this.aktuell = this.wortListe.getWortEintrag((int) (Math.random() * this.wortListe.getWortEinträge().length));
        } while(this.aktuell == null);
        return this.aktuell;*/
    }
}