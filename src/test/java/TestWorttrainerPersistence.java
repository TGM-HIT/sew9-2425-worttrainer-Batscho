import bgogoladze.Model.WortListe;
import bgogoladze.Model.WortTrainer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Diese Testklasse namens TestWorttrainerPersistence ist zum Testen des Models (SpeichernUndLaden) und beinhaltet
 * mindestens eine Testmethode pro Methode aus der Klassen welche getestet werden soll und auch vielleicht zusätzliche
 * Kombinationen mehrerer Szenarien bezüglich Persistenz.
 * @author Gogoladze Batschana
 * @version 11-10-2024
 */

public class TestWorttrainerPersistence {
    private WortListe wortListe;
    private WortTrainer wortTrainer;

    @BeforeEach
    public void setup() {
        this.wortListe = new WortListe();
        this.wortListe.addWortEintrag("Papagei", "https://www.vetline.de/sites/default/files/2021-02/wellensittich.jpeg");
        this.wortListe.addWortEintrag("Fische", "https://wallpapers.com/images/hd/tropical-fish-with-corals-krz941d7wbb0jz08.jpg");
        this.wortListe.addWortEintrag("Hamster", "https://blog.wwf.de/wp-content/uploads/2021/12/Feldhamster-Futter-Wangen-0079476299h-1920x1080-c-IMAGO-blickwinkel.jpg");
        this.wortTrainer = new WortTrainer(this.wortListe);
    }

    @AfterEach
    public void deleteTmp() {
        // Löschen der temporären Testdatei nach jedem Test
       
    }
}
