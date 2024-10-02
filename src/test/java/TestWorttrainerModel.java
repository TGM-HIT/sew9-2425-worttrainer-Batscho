import bgogoladze.Model.WortListe;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

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
    public void notNullOrEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new WortListe(null, ""), "Es wurde versucht invalide Werte in das Wort-Bild Paar zu setzen");
    }

}
