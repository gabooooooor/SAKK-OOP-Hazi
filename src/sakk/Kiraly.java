/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sakk;

public class Kiraly extends Figura {

    private static final int[][] ELTOLASOK = {
        {1, 0}, {-1, 0}, {0, 1}, {0, -1},
        {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
    };

    public Kiraly(Szin szin) {
        super(szin);
    }

    @Override
    public boolean lephet(Mezo honnan, Mezo hova, Tabla t) {
        return fixLepes(honnan, hova, t, ELTOLASOK);
    }

    @Override
    public char jel() {
        return 'K';
    }
}
