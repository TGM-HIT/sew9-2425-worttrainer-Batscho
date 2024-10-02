import bgogoladze.Model.WortListe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

    @BeforeEach
    public void setup() {
        this.wortListe = new WortListe();
    }

    @Test
    @DisplayName("U01 - Eine WortListe welche aus invaliden Zeichen besteht wirft eine Exception")
    public void notNullEmptyInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new WortListe(null, "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.vetline.de%2Fbitte-abdunkeln-so-schlafen-wellensittiche-besser&psig=AOvVaw2hwxvnhElmDjiWnhOSq5q-&ust=1727960711856000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCIjsvrjh74gDFQAAAAAdAAAAABAE"), "Es wurde versucht das Wort als null-Wert zu speichern");
        assertThrows(IllegalArgumentException.class, () -> new WortListe("Papagei", ""), "Es wurde versucht eine leere Url hinzuzufügen");
        assertThrows(IllegalArgumentException.class, () -> new WortListe("Papagei", "hhtp://www.google.com"), "Es wurde versucht invalide Url zum Papagei Bild als Value hinzuzufügen");
    }
    @Test
    @DisplayName("U01 - Eine WortListe welche aus invaliden Zeichen besteht wirft eine Exception")
    public void x() {
        assertThrows(IllegalArgumentException.class, () -> new WortListe(null, "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.vetline.de%2Fbitte-abdunkeln-so-schlafen-wellensittiche-besser&psig=AOvVaw2hwxvnhElmDjiWnhOSq5q-&ust=1727960711856000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCIjsvrjh74gDFQAAAAAdAAAAABAE"), "Es wurde versucht das Wort als null-Wert zu speichern");
        assertThrows(IllegalArgumentException.class, () -> new WortListe("Papagei", ""), "Es wurde versucht eine leere Url hinzuzufügen");
        assertThrows(IllegalArgumentException.class, () -> new WortListe("Papagei", "hhtp://www.google.com"), "Es wurde versucht invalide Url zum Papagei Bild als Value hinzuzufügen");
    }
}
