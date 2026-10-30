/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sakk;

public class Huszar extends Figura {

    private static final int[][] ELTOLASOK = {
        {1, 2}, {2, 1}, {-1, 2}, {-2, 1},
        {1, -2}, {2, -1}, {-1, -2}, {-2, -1}
    };

    public Huszar(Szin szin) {
        super(szin);
    }

    @Override
    public boolean lephet(Mezo honnan, Mezo hova, Tabla t) {
        return fixLepes(honnan, hova, t, ELTOLASOK);
    }

    @Override
    public char jel() {
        return 'N';
    }
}
