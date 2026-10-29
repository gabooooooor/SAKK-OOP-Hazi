/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sakk;

// Move legality: attack detection, check detection and the "own king must stay safe" rule
public final class Szabaly {

    private Szabaly() { }

    // Builds a square from column and row numbers (0..7), e.g. (4, 3) -> e4
    public static Mezo mezo(int oszlop, int sor)
    {
        return Mezo.of("" + (char) ('a' + oszlop) + (sor + 1));
    }

    // Finds the king of the given colour, or null if there is none (small test boards)
    public static Mezo kiralyHelye(Tabla t, Szin szin)
    {
        for (int o = 0; o < 8; o++)
        {
            for (int s = 0; s < 8; s++)
            {
                Mezo m = mezo(o, s);
                Figura f = t.figura(m);
                if (f instanceof Kiraly && f.getSzin() == szin)
                {
                    return m;
                }
            }
        }
        return null;
    }

    // True if a piece of colour "tamado" could move to the square "cel"
    public static boolean tamadja(Tabla t, Mezo cel, Szin tamado)
    {
        // A pawn only attacks diagonally onto an occupied square, so we put a dummy
        // enemy piece on the target of a copy and then ask every piece with lephet
        Tabla proba = t.masolat();
        proba.helyez(cel, new Gyalog(tamado.ellenfel()));

        for (int o = 0; o < 8; o++)
        {
            for (int s = 0; s < 8; s++)
            {
                Mezo m = mezo(o, s);
                Figura f = proba.figura(m);
                if (f == null || f.getSzin() != tamado)
                {
                    continue;
                }
                if (f.lephet(m, cel, proba))
                {
                    return true;
                }
            }
        }
        return false;
    }

    // True if the king of the given colour is attacked
    public static boolean sakkban(Tabla t, Szin szin)
    {
        Mezo kiraly = kiralyHelye(t, szin);
        if (kiraly == null)
        {
            return false;
        }
        return tamadja(t, kiraly, szin.ellenfel());
    }

    // Normal legality: right colour, the piece allows the move, own king safe afterwards
    public static boolean szabalyos(Tabla t, Szin soron, Mezo honnan, Mezo hova)
    {
        Figura f = t.figura(honnan);
        if (f == null || f.getSzin() != soron)
        {
            return false;
        }
        if (!f.lephet(honnan, hova, t))
        {
            return false;
        }

        // Try the move on a copy, the real board must not change
        Tabla proba = t.masolat();
        proba.helyez(hova, f);
        proba.helyez(honnan, null);
        return !sakkban(proba, soron);
    }
}

