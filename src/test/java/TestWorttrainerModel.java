import bgogoladze.Model.WortListe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * Diese Testklasse namens TestWorttrainerModel ist zum Testen des Models (bis jetzt die WortListe) und beinhaltet
 * mindestens eine Testmethode pro Methode aus der Klasse welche getestet werden soll und auch vielleicht zusätzliche
 * Kombinationen mehrerer Szenarien
 * @author Gogoladze Batschana
 * @version 01-10-2024
 */
public class TestWorttrainerModel {
    private WortListe wortListe;
    private WortListe wortListe2 = new WortListe("Hamster", "https://blog.wwf.de/wp-content/uploads/2021/12/Feldhamster-Futter-Wangen-0079476299h-1920x1080-c-IMAGO-blickwinkel.jpg");
    private WortListe wortListe3;

    @BeforeEach
    public void setup() {
        this.wortListe = new WortListe();
        this.wortListe3.addWortEintrag("Papagei", "https://www.vetline.de/sites/default/files/2021-02/wellensittich.jpeg");
        this.wortListe3.addWortEintrag("Fische", "https://wallpapers.com/images/hd/tropical-fish-with-corals-krz941d7wbb0jz08.jpg");
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
    public void show() {
        this.wortListe.addWortEintrag("Papagei", "https://www.vetline.de/sites/default/files/2021-02/wellensittich.jpeg");
        assertEquals("{\n  \"Papagei\": \"https://www.vetline.de/sites/default/files/2021-02/wellensittich.jpeg\"\n}", this.wortListe.showWortListe(), "Die WortListe zeigt nicht denselben Output im Json-Format");
    }

    @Test
    @DisplayName("U03 - Testen, ob das Adden von neuen Einträgen auch funktioniert wie gewollt")
    public void addingMatching() {
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
        assertEquals(this.wortListe2.showWortListe(), this.wortListe.showWortListe(), "Die erste WortListe wurde auf die zweite gesetzt, jedoch sin diese im Vergleich nicht dieselben");
    }

    @Test
    @DisplayName("U06 - Testen, ob der setter der WortListe auch einwandfrei funktioniert")
    public void t() {
        this.wortListe.setWortListe(this.wortListe2.getWortListe());
        assertEquals(this.wortListe2.showWortListe(), this.wortListe.showWortListe(), "Die erste WortListe wurde auf die zweite gesetzt, jedoch sin diese im Vergleich nicht dieselben");
    }

}
