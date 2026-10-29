/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sakk;

/**
 *
 * Base class of all pieces. Each piece knows how it may move
 */
public abstract class Figura {

    private final Szin szin;

    protected Figura(Szin szin) {
        this.szin = szin;
    }

    public Szin getSzin() {
        return szin;
    }

    //Checks the piece's own movement rule only (no check detection)
    public abstract boolean lephet(Mezo honnan, Mezo hova, Tabla t);

    //Character of the piece, e.g. 'K'; the colour is not part of it
    public abstract char jel();

    //Shared sliding movement: straight/diagonal line, path must be free, target not own piece
    protected boolean csuszik(Mezo honnan, Mezo hova, Tabla t, boolean egyenes, boolean atlos) {
        int dO = hova.getOszlop() - honnan.getOszlop();
        int dS = hova.getSor() - honnan.getSor();
        if (dO == 0 && dS == 0) {
            return false;
        }
        boolean isEgyenes = (dO == 0 || dS == 0);
        boolean isAtlos = (Math.abs(dO) == Math.abs(dS));
        if (!(egyenes && isEgyenes) && !(atlos && isAtlos)) {
            return false;
        }
        if (t.utbanVan(honnan, hova)) {
            return false;
        }
        Figura cel = t.figura(hova);
        return cel == null || cel.getSzin() != getSzin();
    }

    //Shared fixed-offset movement (king, knight): the move must match one of the offsets.
    protected boolean fixLepes(Mezo honnan, Mezo hova, Tabla t, int[][] eltolasok) {
        int dO = hova.getOszlop() - honnan.getOszlop();
        int dS = hova.getSor() - honnan.getSor();
        for (int[] e : eltolasok) 
        {
            if (e[0] == dO && e[1] == dS) 
            {
                 Figura cel = t.figura(hova);
                 return cel == null || cel.getSzin() != getSzin();
            }
        }
        return false;
    }
}
