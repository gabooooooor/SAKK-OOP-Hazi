/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sakk;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class SzabalyTest {

    private Tabla tabla;

    @Before
    public void setUp()
    {
        tabla = new Tabla();
    }

    private Mezo m(String s) { return Mezo.of(s); }

    private boolean feherLephet(String honnan, String hova)
    {
        return Szabaly.szabalyos(tabla, Szin.FEHER, m(honnan), m(hova));
    }

    // ---- one legal and one illegal move per piece ----

    @Test
    public void bastyaEgyenesenLephet()
    {
        tabla.helyez(m("a1"), new Bastya(Szin.FEHER));
        assertTrue(feherLephet("a1", "a5"));
    }

    @Test
    public void bastyaAtlosanNemLephet()
    {
        tabla.helyez(m("a1"), new Bastya(Szin.FEHER));
        assertFalse(feherLephet("a1", "c3"));
    }

    @Test
    public void bastyaNemUgorjaAtABabut()
    {
        tabla.helyez(m("a1"), new Bastya(Szin.FEHER));
        tabla.helyez(m("a3"), new Gyalog(Szin.FEKETE));
        assertFalse(feherLephet("a1", "a5"));
    }

    @Test
    public void futoAtlosanLephet()
    {
        tabla.helyez(m("c1"), new Futo(Szin.FEHER));
        assertTrue(feherLephet("c1", "f4"));
    }

    @Test
    public void futoEgyenesenNemLephet()
    {
        tabla.helyez(m("c1"), new Futo(Szin.FEHER));
        assertFalse(feherLephet("c1", "c4"));
    }

    @Test
    public void vezerEgyenesenLephet()
    {
        tabla.helyez(m("d1"), new Vezer(Szin.FEHER));
        assertTrue(feherLephet("d1", "d8"));
    }

    @Test
    public void vezerHuszarLepesreNemLephet()
    {
        tabla.helyez(m("d1"), new Vezer(Szin.FEHER));
        assertFalse(feherLephet("d1", "e3"));
    }

    @Test
    public void kiralyEgyetLephet()
    {
        tabla.helyez(m("e1"), new Kiraly(Szin.FEHER));
        assertTrue(feherLephet("e1", "e2"));
    }

    @Test
    public void kiralyKettotNemLephet()
    {
        tabla.helyez(m("e1"), new Kiraly(Szin.FEHER));
        assertFalse(feherLephet("e1", "e3"));
    }

    @Test
    public void huszarLLepesTud()
    {
        tabla.helyez(m("b1"), new Huszar(Szin.FEHER));
        assertTrue(feherLephet("b1", "c3"));
    }

    @Test
    public void huszarEgyenesenNemLephet()
    {
        tabla.helyez(m("b1"), new Huszar(Szin.FEHER));
        assertFalse(feherLephet("b1", "b3"));
    }

    @Test
    public void gyalogKezdoDuplaLepes()
    {
        tabla.helyez(m("e2"), new Gyalog(Szin.FEHER));
        assertTrue(feherLephet("e2", "e4"));
    }

    @Test
    public void gyalogNemLephetHaromat()
    {
        tabla.helyez(m("e2"), new Gyalog(Szin.FEHER));
        assertFalse(feherLephet("e2", "e5"));
    }

    // ---- moving into / leaving check ----

    @Test
    public void nemLehetSakkbaLepni()
    {
        tabla.helyez(m("e1"), new Kiraly(Szin.FEHER));
        tabla.helyez(m("d8"), new Bastya(Szin.FEKETE));
        assertFalse(feherLephet("e1", "d1"));
    }

    @Test
    public void kotottBabuNemLephetKi()
    {
        tabla.helyez(m("e1"), new Kiraly(Szin.FEHER));
        tabla.helyez(m("e2"), new Bastya(Szin.FEHER));
        tabla.helyez(m("e8"), new Bastya(Szin.FEKETE));
        assertFalse(feherLephet("e2", "a2"));
        assertTrue(feherLephet("e2", "e5"));
    }

    // ---- special moves ----

    @Test
    public void enPassantUtes()
    {
        tabla.helyez(m("e5"), new Gyalog(Szin.FEHER));
        tabla.helyez(m("d7"), new Gyalog(Szin.FEKETE));
        Jatek jatek = new Jatek(tabla, Szin.FEKETE);

        assertTrue(jatek.lep(m("d7"), m("d5")));
        assertTrue(jatek.lep(m("e5"), m("d6")));
        assertThat(tabla.figura(m("d5")), is(nullValue()));
        assertThat(tabla.figura(m("d6")), instanceOf(Gyalog.class));
    }

    @Test
    public void sancolasMukodik()
    {
        tabla.helyez(m("e1"), new Kiraly(Szin.FEHER));
        tabla.helyez(m("h1"), new Bastya(Szin.FEHER));
        Jatek jatek = new Jatek(tabla, Szin.FEHER);

        assertTrue(jatek.lep(m("e1"), m("g1")));
        assertThat(tabla.figura(m("g1")), instanceOf(Kiraly.class));
        assertThat(tabla.figura(m("f1")), instanceOf(Bastya.class));
    }

    @Test
    public void tamadottMezonNemLehetSancolni()
    {
        tabla.helyez(m("e1"), new Kiraly(Szin.FEHER));
        tabla.helyez(m("h1"), new Bastya(Szin.FEHER));
        tabla.helyez(m("f8"), new Bastya(Szin.FEKETE));
        Jatek jatek = new Jatek(tabla, Szin.FEHER);

        assertFalse(jatek.szabalyos(m("e1"), m("g1")));
    }

    @Test
    public void gyalogAtvaltozasVezerre()
    {
        tabla.helyez(m("a7"), new Gyalog(Szin.FEHER));
        Jatek jatek = new Jatek(tabla, Szin.FEHER);

        assertTrue(jatek.atvaltozasKell(m("a7"), m("a8")));
        assertTrue(jatek.lep(m("a7"), m("a8"), 'Q'));
        assertThat(tabla.figura(m("a8")), instanceOf(Vezer.class));
    }
}
