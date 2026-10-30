/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sakk;

public class Gyalog extends Figura {

    public Gyalog(Szin szin) {
        super(szin);
    }

    @Override
    public boolean lephet(Mezo honnan, Mezo hova, Tabla t) {
        int irany = getSzin() == Szin.FEHER ? 1 : -1;
        int kezdoSor = getSzin() == Szin.FEHER ? 1 : 6;
        int dO = hova.getOszlop() - honnan.getOszlop();
        int dS = hova.getSor() - honnan.getSor();

        if (dO == 0 && dS == irany) {
            return t.uresE(hova);
        }

        if (dO == 0 && dS == 2 * irany && honnan.getSor() == kezdoSor) {
            Mezo koztes = new Mezo(honnan.getOszlop(), honnan.getSor() + irany);
            return t.uresE(koztes) && t.uresE(hova);
        }

        if (Math.abs(dO) == 1 && dS == irany) {
            Figura cel = t.figura(hova);
            return cel != null && cel.getSzin() != getSzin();
        }

        return false;
    }

    @Override
    public char jel() {
        return 'P';
    }
}
