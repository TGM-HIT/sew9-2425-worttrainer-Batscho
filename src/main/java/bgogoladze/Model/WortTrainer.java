package bgogoladze.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.LinkedHashMap;
import java.util.Map;
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
    private int falsche;                //Die Anzahl der abgefragten Wörter die erraten wurden
    private StringBuilder statistic = new StringBuilder();    //Ein abgeleitetes Attribut welches die Statistik angibt

    /**
     * Dies ist der Konstruktor bei dem die übergebenen Parameter in die Attrribute gespeichert werden
     * @param wortListe ist die Wortliste die gesetzt wird um die Wort-Bild Paare zu verwalten
     */
    public WortTrainer(WortListe wortListe) {
        if(wortListe != null) this.wortListe = wortListe;
        else throw new NullPointerException("Die übergebene WortListe darf nicht null sein!");
    }

    /**
     * Diese Methode randomWortEintrag liefert einen zufälligen Worteintrag welcher drüber aufgerufen werden kann.
     * Die Methode keySet der Map gibt ein Set der Keys zurück, in deinem Fall aus Strings.
     * Außerdem wird die toArray()-Methode des Set verwendet, um die Elemente in ein Array zu kopieren.
     * Die new String[0]-Notation ist eine gebräuchliche Methode, um das Zielarray zu bestimmen, wobei der Vorteil
     * von new String[0] ist, dass das Array dynamisch erstellt wird, basierend auf der tatsächlichen Größe des Sets.
     * @return den zufällig ausgewählten WortEintrag
     */
    public String[] randomWortEintrag() {
        String[] all = this.wortListe.getWortListe().keySet().toArray(new String[0]);
        this.aktuell = wortListe.getWortEintrag(all[new Random().nextInt(all.length)]);
        return this.aktuell;
    }

    /**
     * Diese Methode ausgewählt liefert den aktuellen Wert vom Attribut aktuell
     * @return den aktuell ausgewählten Worteintrag in der Form eines String Arrays mit Key und Value
     */
    public String[] ausgewaehlt() {
        if(this.aktuell != null) {
            return this.aktuell;
        } else {
            return this.randomWortEintrag();
        }
    }

    /**
     * Diese Methode check checkt ob ein übergebenes Wort mit dem Wort vom aktuellen Eintrag exakt übereinstimmt
     * @param wort ist das Wort, welchs verglichen wird mit der korrekten Antwort
     * @return ob die Wörter übereinstimmen und somit die Statistik sich verändert
     */
    public boolean check(String wort) throws IllegalArgumentException{
        this.abgefragt++;
        WortListe.checkWort(wort);
        if(this.aktuell[0].equals(wort)) {
            this.korrekt++;
            return true;
        }
        return false;
    }

    /**
     * Diese Methode check checkt ob ein übergebenes Wort mit dem Wort vom aktuellen Eintrag übereinstimmt ohne Case-sensetivity
     * @param wort ist das Wort, welchs verglichen wird mit der korrekten Antwort
     * @return ob die Wörter übereinstimmen und somit die Statistik sich verändert
     */
    public boolean checkIgnoreCase(String wort) throws IllegalArgumentException{
        this.abgefragt++;
        WortListe.checkWort(wort);
        if(this.aktuell[0].equalsIgnoreCase(wort)) {
            this.korrekt++;
            return true;
        }
        return false;
    }

    /**
     * Das ist die getter-Methode die das Attribut statistic als String in einer schöneren und motivierenden Form darstellt
     * @return die Rückgabe des zusammengesetzten Strings aus dem ursprünglichen StringBuilder
     */
    public String getStatistic() {
        double score = this.abgefragt == 0 ? 0 : ((double) this.korrekt / this.abgefragt) * 100;  // Vermeidung der Division durch 0
        this.statistic.append("\nStatistik:\n")
                .append("Abgefragt: ").append(this.getAbgefragt()).append("\n")
                .append("Korrekte: ").append(this.getKorrekt()).append("\n")
                .append("Falsche: ").append(this.getFalsche()).append("\n")
                .append("Score: ").append(String.format("%.2f", score)).append("%\n");  // Score auf 2 Nachkommastellen formatiert
        if(score >= 89) this.statistic.append("Super mach weiter so!\n");
        else this.statistic.append("Das geht besser!\n");
        return this.statistic.toString();
        /*double score = this.abgefragt == 0 ? 0 : ((double) this.korrekt / this.abgefragt) * 100;
        String kommentar = score >= 89 ? "Super mach weiter so!" : "Das geht besser!";
        Map<String, Object> statistikMap = new LinkedHashMap<>();
        statistikMap.put("Zweck", "Worttrainer Statistik");
        statistikMap.put("Abgefragt", this.getAbgefragt());
        statistikMap.put("Korrekte", this.getKorrekt());
        statistikMap.put("Falsche", this.getFalsche());
        statistikMap.put("Score", String.format("%.2f", score));
        statistikMap.put("Kommentar", kommentar);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(statistikMap);*/
    }

    /**
     * Getter-Methode des Attributs abgefragt, welche den
     * aktuellen Wert für abgefragt liefert
     * @return den neuen Wert des Attributs abgefragt
     */
    public int getAbgefragt(){
        return this.abgefragt;
    }

    /**
     * Setter-Methode des Attributs abgefragt, welche den
     * neuen Wert für abgefragt setzt
     * @param abgefragt ist die zu setzende Anzahl an gestellten Fragen
     */
    public void setAbgefragt(int abgefragt) {
        if(abgefragt < 0) throw new IllegalArgumentException("Die Anzahl an gestellten Fragen kann nicht kleiner als 0 sein!");
        else this.abgefragt = abgefragt;
    }

    /**
     * Getter-Methode des Attributs korrekt, welche den
     * aktuellen Wert von korrekt liefert
     * @return den aktuellen Wert des Attributs korrekt
     */
    public int getKorrekt(){
        return this.korrekt;
    }

    /**
     * Setter-Methode des Attributs korrekt, welche den
     * neuen Wert für korrekt setzt
     * @param korrekt ist die zu setzende Anzahl an korrekten Antworten
     */
    public void setKorrekt(int korrekt) {
        if(korrekt < 0) throw new IllegalArgumentException("Die Anzahl an korrekten Antworten kann nicht kleiner als 0 sein!");
        else this.korrekt = korrekt;
    }

    /**
     * Getter-Methode des Attributs aktuell, welche den
     * derzeitigen Wert von aktuell liefert
     * @return den derzeitgen Wert des Attributs aktuell, also den aktuellen Worteintrag
     */
    public String[] getAktuell() {
        return this.aktuell;
    }

    /**
     * Setter-Methode des Attributs aktuell, welche den
     * neuen Wert für aktuell setzt - NUR FÜR TESTZWECKE
     * @param eintrag ist der zu setzende Parameter
     */
    public void setAktuell(String[] eintrag) {
        if(eintrag == null) throw new NullPointerException("Der zu setzende Eintrag darf nicht null sein!");
        else this.aktuell = eintrag;
    }

    /**
     * Getter-Methode des Attributs falsch, welche den
     * aktuellen Wert von falsch liefert
     * @return den aktuellen Wert des Attributs falsch
     */
    public int getFalsche(){
        return this.falsche = this.getAbgefragt() - this.getKorrekt();
    }

    /**
     * Getter-Methode des Attributs wortListe, welche die WortListe zurückgibt welche durch den WortTrainer verwendet wird
     * @return den derzeitgen Wert des Attributs wortListe
     */
    public WortListe getWortListe() {
        return this.wortListe;
    }

    /**
     * Diese Methode reset setzt wieder alles was die Statistik betrifft auf 0 und mischt nochmal alles
     */
    public void reset() {
        this.setKorrekt(0);
        this.setAbgefragt(0);
        this.randomWortEintrag();
    }
}