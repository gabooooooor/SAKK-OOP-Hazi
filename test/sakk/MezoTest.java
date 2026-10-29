/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sakk;

import static org.junit.Assert.*;
import org.junit.Test;
/**
 *
 * @author ministar
 */
public class MezoTest {
    
    @Test
    public void ofParsesE4() {
        Mezo m = Mezo.of("e4");
        assertEquals(4, m.getOszlop());
        assertEquals(3, m.getSor());
    }

    @Test
    public void ofParsesA1() {
        Mezo m = Mezo.of("a1");
        assertEquals(0, m.getOszlop());
        assertEquals(0, m.getSor());
    }

    @Test(expected = IllegalArgumentException.class)
    public void ofRejectsInvalidColumn() {
        Mezo.of("i5");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ofRejectsInvalidRow() {
        Mezo.of("a9");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ofRejectsWrongLength() {
        Mezo.of("e44");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorRejectsOutOfRange() {
        new Mezo(8, 0);
    }

    @Test
    public void equalMezosAreEqual() {
        assertEquals(Mezo.of("c3"), new Mezo(2, 2));
        assertEquals(Mezo.of("c3").hashCode(), new Mezo(2, 2).hashCode());
    }

    @Test
    public void toStringGivesNotation() {
        assertEquals("e4", new Mezo(4, 3).toString());
    }
}
