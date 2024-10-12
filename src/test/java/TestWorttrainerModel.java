import bgogoladze.Model.WortListe;
import bgogoladze.Model.WortTrainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Diese Testklasse namens TestWorttrainerModel ist zum Testen des Models (WortListe und WortTrainer) und beinhaltet
 * mindestens eine Testmethode pro Methode aus den Klassen welche getestet werden sollen und auch vielleicht zusätzliche
 * Kombinationen mehrerer Szenarien.
 * @author Gogoladze Batschana
 * @version 01-10-2024
 */
public class TestWorttrainerModel {
    private WortListe wortListe;
    private WortListe wortListe2 = new WortListe("Hamster", "https://blog.wwf.de/wp-content/uploads/2021/12/Feldhamster-Futter-Wangen-0079476299h-1920x1080-c-IMAGO-blickwinkel.jpg");
    private WortListe wortListe3 = new WortListe();
    private WortTrainer wortTrainer;

    @BeforeEach
    public void setup() {
        this.wortListe = new WortListe();
        this.wortListe3.addWortEintrag("Papagei", "https://www.vetline.de/sites/default/files/2021-02/wellensittich.jpeg");
        this.wortListe3.addWortEintrag("Fische", "https://wallpapers.com/images/hd/tropical-fish-with-corals-krz941d7wbb0jz08.jpg");
        this.wortListe3.addWortEintrag("Hamster", "https://blog.wwf.de/wp-content/uploads/2021/12/Feldhamster-Futter-Wangen-0079476299h-1920x1080-c-IMAGO-blickwinkel.jpg");
        this.wortTrainer = new WortTrainer(this.wortListe3);
    }

    @Test
    @DisplayName("U01 - Eine WortListe welche aus invaliden Zeichen besteht wirft eine Exception")
    public void notNullEmptyInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new WortListe(null, "https://www.vetline.de/sites/default/files/2021-02/wellensittich.jpeg"), "Es wurde versucht das Wort als null-Wert zu speichern");
        assertThrows(IllegalArgumentException.class, () -> new WortListe("Papagei", ""), "Es wurde versucht eine leere Url hinzuzufügen");
        assertThrows(IllegalArgumentException.class, () -> new WortListe("Papagei", "hhtp://www.google-com"), "Es wurde versucht invalide Url zum Papagei Bild als Value hinzuzufügen");
    }

    @Test
    @DisplayName("U02 - Testen, ob die Pretty Print Ausgabe mittels Gson der Map funktioniert")
    public void showTest() {
        this.wortListe.addWortEintrag("Papagei", "https://www.vetline.de/sites/default/files/2021-02/wellensittich.jpeg");

        assertEquals("{\n  \"Papagei\": \"https://www.vetline.de/sites/default/files/2021-02/wellensittich.jpeg\"\n}", this.wortListe.showWortListe(), "Die WortListe zeigt nicht denselben Output im Json-Format");
    }

    @Test
    @DisplayName("U03 - Testen, ob das Adden von neuen Einträgen auch funktioniert wie gewollt")
    public void addingMatchingTest() {
        this.wortListe.addWortEintrag("Hamster", "https://blog.wwf.de/wp-content/uploads/2021/12/Feldhamster-Futter-Wangen-0079476299h-1920x1080-c-IMAGO-blickwinkel.jpg");

        assertEquals(this.wortListe2.showWortListe(), this.wortListe.showWortListe(), "Die zweite Wortliste ist nicht dieselbe, obwohl zur leeren WortListe genau das eine Element abgegeben wurde");
    }

    @Test
    @DisplayName("U04 - Testen, ob der getter der WortListe auch einwandfrei funktioniert")
    public void getterTest() {
        assertEquals(this.wortListe2.getWortListe(), this.wortListe2.getWortListe(), "Die zweite Wortliste ist nicht dieselbe wie die vom getter zurückgegebene Liste");
    }

    @Test
    @DisplayName("U05 - Testen, ob der setter der WortListe auch einwandfrei funktioniert")
    public void setterTest() {
        this.wortListe.setWortListe(this.wortListe2.getWortListe());

        assertEquals(this.wortListe2.showWortListe(), this.wortListe.showWortListe(), "Die erste WortListe wurde auf die zweite gesetzt, jedoch sind diese im Vergleich nicht dieselben");
    }

    @Test
    @DisplayName("U06 - Testen, ob die korrekte Länge zurückgegeben werden kann bei Bedarf")
    public void lengthTest() {
        this.wortListe3.addWortEintrag("Hai", "https://naturdetektive.bfn.de/fileadmin/_processed_/f/f/csm_Weisser_Hai_Elias_Levy_cc-by-20_flach_b563f2725e.jpg");
        this.wortListe3.delWortEintrag("Hamster");

        assertEquals(3, this.wortListe3.length(), "Die dritte WortListe hatte 3 Einträge, wobei einer gelöscht wurde und ein weiterer geaddet wurde und hat nicht dieselbe Länge 3?");
    }

    @Test
    @DisplayName("U07 - Testen, ob die korrekte Länge zurückgegeben werden kann bei Bedarf")
    public void deleteTest() {
        this.wortListe3.addWortEintrag("Hai", "https://naturdetektive.bfn.de/fileadmin/_processed_/f/f/csm_Weisser_Hai_Elias_Levy_cc-by-20_flach_b563f2725e.jpg");
        this.wortListe3.delWortEintrag("Hamster");
        this.wortListe3.delWortEintrag("Hai");
        this.wortListe3.delWortEintrag("Fische");
        this.wortListe3.delWortEintrag("Papagei");

        assertEquals(0, this.wortListe3.length(), "Die WortListe hatte viele Einträge jedoch wurden alle gelöscht und sie ist trotzdem nicht leer?");
    }

    @Test
    @DisplayName("U08 - Testen, ob die Methode getWortEintrag zum Holen einzelner Einträge auch funktioniert")
    public void getEintragTest() {
        this.wortListe3.delWortEintrag("Papagei");

        assertThrows(IllegalArgumentException.class, () -> this.wortListe3.getWortEintrag("Papagei"), "Es wird keine Exception geworfen, obwohl der Eintrag Papagei nicht gerade verfügbar ist?");
    }

    @Test
    @DisplayName("U09 - Testen, ob auch das adden von neuen Einträgen möglich ist, wenn dieser schon vorhanden ist")
    public void alreadyInListTest() {
        assertDoesNotThrow(() -> this.wortListe3.addWortEintrag("Hai", "https://naturdetektive.bfn.de/fileadmin/_processed_/f/f/csm_Weisser_Hai_Elias_Levy_cc-by-20_flach_b563f2725e.jpg"),"Man addet einen neuen WortEintrag, aber der Prozess geht schief?");
        assertThrows(IllegalArgumentException.class, () -> this.wortListe3.addWortEintrag("Hai", "https://naturdetektive.bfn.de/fileadmin/_processed_/f/f/csm_Weisser_Hai_Elias_Levy_cc-by-20_flach_b563f2725e.jpg"), "Es wird keine Exception geworfen, obwohl der Eintrag Hai schon vorhanden ist?");
    }

    @Test
    @DisplayName("U10 - Testen, ob der Wort-Checker auch funktioniert")
    public void wortCheckerTest() {
        assertThrows(IllegalArgumentException.class, () -> WortListe.checkWort(""), "Das Wort ist invalide aber entspricht trotzdem der Gültigkeit?");
        assertThrows(IllegalArgumentException.class, () -> WortListe.checkWort(null), "Das Wort ist invalide aber entspricht trotzdem der Gültigkeit?");
        assertDoesNotThrow(() -> WortListe.checkWort("Papagei"), "Das Wort ist gültig aber es wird trotzdem eine Exception geworfen?");
    }

    @Test
    @DisplayName("U11 - Testen, ob der URL-Checker auch funktioniert")
    public void urlCheckerTest() {
        assertThrows(IllegalArgumentException.class, () -> WortListe.checkUrl(null), "Die URL ist invalide (null) aber entspricht trotzdem der Gültigkeit?");
        assertThrows(IllegalArgumentException.class, () -> WortListe.checkUrl(""), "Das Wort ist invalide (empty) aber entspricht trotzdem der Gültigkeit?");
        assertDoesNotThrow(() -> WortListe.checkUrl("https://www.vetline.de/sites/default/files/2021-02/wellensittich.jpeg"), "Die URL ist gültig aber es wird trotzdem eine Exception geworfen?");
    }

    //Ab hier beginnen die Tests vom WortTrainer

    @Test
    @DisplayName("U12 - Testen, der Randomizer für den WortTrainer funktioniert")
    public void randomWortEintragTest() {
        String[] eintrag = this.wortTrainer.randomWortEintrag();

        assertNotNull(eintrag, "Der Eintrag wurde random rausgepickt und es wurde für Null-Sicherheit gesorgt aber es ist trotzdem null?");
        assertTrue(this.wortTrainer.getWortListe().getWortListe().containsKey(eintrag[0]), "Der random Key aus dem WortTrainer ist nicht in der WortListe enhalten?");
        assertEquals(this.wortListe3.getWortEintrag(eintrag[0])[1], eintrag[1], "Die URL stimmt nicht mit dem zughörigen Key überein?");
    }

    @Test
    @DisplayName("U13 - Testen, ob der Randomizer augerufen wird wenn das aktuelle Wort null ist")
    public void ausgewaehltNullTest() {
        String[] eintrag = this.wortTrainer.ausgewaehlt();

        assertNotNull(eintrag, "Der ausgewählte Worteintrag ist null, wieeee?");
        assertTrue(this.wortListe3.getWortListe().containsKey(eintrag[0]), "Der randomisierte Key ist nicht in der WortListe entahlten?");
    }

    @Test
    @DisplayName("U14 - Testen, ob das Checken (auch ohne case-sensetivity) der korrekten und inkorrekten Wörter auch funktioniert für die Statistik")
    public void testCheckIgnoreCase() {
        String[] eintrag = this.wortTrainer.ausgewaehlt();
        boolean result = this.wortTrainer.check(eintrag[0]);

        assertTrue(result, "Das Wort sollte korrekt sein.");
        assertEquals(1, this.wortTrainer.getKorrekt(), "Die Anzahl der korrekten Antworten sollte doch 1 sein?");
        assertEquals(1, this.wortTrainer.getAbgefragt(), "Die Anzahl der abgefragten Wörter sollte doch 1 sein?");

        this.wortTrainer.setAktuell(new String[]{"Papagei", "https://www.vetline.de/sites/default/files/2021-02/wellensittich.jpeg"});
        boolean result2 = this.wortTrainer.checkIgnoreCase("pApAgEi");

        assertTrue(result2, "Das Wort sollte (case-insensitive) doch korrekt sein?");
        assertEquals(2, this.wortTrainer.getKorrekt(), "Die Anzahl der korrekten Antworten sollte doch bereits 2 sein?");
        assertEquals(2, this.wortTrainer.getAbgefragt(), "Die Anzahl der abgefragten Wörter sollte doch bereits2 sein.");
    }

    @Test
    @DisplayName("U15 - Testen, ob die Statistik auch eine korrekte Ausgabe liefert")
    public void statisticTest() {
        this.wortTrainer.setAbgefragt(48);
        this.wortTrainer.setKorrekt(47);
        assertEquals("{\n  \"Zweck\": \"Worttrainer Statistik\",\n  \"Abgefragt\": 48,\n  \"Korrekte\": 47,\n  \"Falsche\": 1,\n  \"Score\": \"97,92\",\n  \"Kommentar\": \"Super mach weiter so!\"\n}", this.wortTrainer.getStatistic(), "Die Statistik stimmt nicht überein?");
    }

    @Test
    @DisplayName("U16 - Testen, ob das reseten der Statistik auch funktioniert")
    public void resetTest() {
        this.wortTrainer.reset();
        assertNotNull(this.wortTrainer, "Vielleicht wurde die Statistik resetet aber nicht der Rechtschreibtrainer oder?");
        assertEquals(0, this.wortTrainer.getAbgefragt(), "Es wurde die Statistik resetet, aber die Anzahl an Fragen ist nicht 0?");
        assertEquals(0, this.wortTrainer.getKorrekt(), "Es wurde die Statistik resetet, aber die Anzahl an korrekten Antworten ist nicht 0?");
    }
}
