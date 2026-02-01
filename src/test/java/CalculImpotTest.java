import com.calculimpot.CalculImpot;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class CalculImpotTest {

    private final CalculImpot service = new CalculImpot();

    @ParameterizedTest(name = "Test {index}: Marié={0}, Enfants={1} => Parts attendues={2}")
    @CsvSource({
            "false, 0, 1.5",   // Célibataire, 0 enfant : 1,5 part
            "false, 3, 3.0",   // Célibataire, 3 enfants : 1,5 + (3 * 0,5) = 3,0 parts
            "true,  3, 3.5",   // Marié, 3 enfants : 2 + (3 * 0,5) = 3,5 parts
            "true,  8, 5.0"    // Marié, 8 enfants : 2 + (8 * 0,5) = 6 -> Plafond 5,0
    })
    void testCalculNombreParts(boolean estMarie, int nbEnfants, double resultatAttendu) {
        assertEquals(resultatAttendu, service.calculerNombreParts(estMarie, nbEnfants));
    }

    @ParameterizedTest(name = "Salaire={0} => Abattement attendu={1}")
    @CsvSource({
            "100000, 30000",   // 30% de 100.000 = 30.000
            "1500000, 300000"  // 30% de 1.5M = 450.000 -> Plafond 300.000 appliqué
    })
    void testCalculAbattement(double salaireBrut, double abattementAttendu) {
        assertEquals(abattementAttendu, service.calculerAbattement(salaireBrut));
    }
    @ParameterizedTest
    @CsvSource({
            "50000",   // Trop petit
            "4000000"  // Trop grand
    })
    void testSalaireInvalide(double salaire) {
                service.calculerBaseImposable(salaire, false, 0);
    }
    @Test
    void testSalaireLimiteBasse() {
        assertDoesNotThrow(() -> service.calculerBaseImposable(60000, false, 0));
    }
    @ParameterizedTest
    @CsvSource({
            // salaire, marié, enfants, baseImposableAttendues
            "100000, false, 0, 46666.67",
            "300000, true, 2, 70000"
    })
    void testCalculBaseImposable(double salaire, boolean estMarie, int enfants, double attendu) {
        double resultat = service.calculerBaseImposable(salaire, estMarie, enfants);
        assertEquals(attendu, resultat, 0.01);
    }
}