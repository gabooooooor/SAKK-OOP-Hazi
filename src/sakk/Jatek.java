/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sakk;

// One game: the board, whose turn it is, and the memory the special rules need
public class Jatek {

    private final Tabla tabla;
    private Szin soron;
    // The square a pawn just skipped with a double step (en passant target), or null
    private Mezo epCel;
    // true for every square a piece has left or arrived on (used for castling rights)
    private final boolean[] erintett = new boolean[64];

    // Standard starting position, white starts
    public Jatek()
    {
        this.tabla = new Tabla();
        this.soron = Szin.FEHER;
        kezdoAllas();
    }

    // Custom position, used by the tests
    public Jatek(Tabla tabla, Szin soron)
    {
        if (tabla == null || soron == null)
        {
            throw new IllegalArgumentException("Board and side to move are required");
        }
        this.tabla = tabla;
        this.soron = soron;
    }

    public Tabla getTabla() { return tabla; }

    public Szin getSoron() { return soron; }

    public boolean sakkban()
    {
        return Szabaly.sakkban(tabla, soron);
    }

    // Is the move legal for the side to move? (normal, castling or en passant)
    public boolean szabalyos(Mezo honnan, Mezo hova)
    {
        Figura f = tabla.figura(honnan);
        if (f == null || f.getSzin() != soron)
        {
            return false;
        }
        if (sancolasSzabalyos(honnan, hova) || enPassantSzabalyos(honnan, hova))
        {
            return true;
        }
        return Szabaly.szabalyos(tabla, soron, honnan, hova);
    }

    // True if this legal move brings a pawn to the last row (the caller must ask the choice)
    public boolean atvaltozasKell(Mezo honnan, Mezo hova)
    {
        return szabalyos(honnan, hova)
                && tabla.figura(honnan) instanceof Gyalog
                && hova.getSor() == utolsoSor();
    }

    // Plays the move if legal; atvaltozas is Q, R, B or N and only matters for promotion
    public boolean lep(Mezo honnan, Mezo hova, char atvaltozas)
    {
        if (!szabalyos(honnan, hova))
        {
            return false;
        }
        Figura f = tabla.figura(honnan);
        Mezo ujEp = null;

        // Castling: the king moves two squares, the rook jumps over it
        if (f instanceof Kiraly && Math.abs(hova.getOszlop() - honnan.getOszlop()) == 2)
        {
            boolean rovid = hova.getOszlop() > honnan.getOszlop();
            Mezo bastyaHonnan = Szabaly.mezo(rovid ? 7 : 0, honnan.getSor());
            Mezo bastyaHova = Szabaly.mezo(rovid ? 5 : 3, honnan.getSor());
            tabla.helyez(bastyaHova, tabla.figura(bastyaHonnan));
            tabla.helyez(bastyaHonnan, null);
            megjelol(bastyaHonnan);
            megjelol(bastyaHova);
        }

        if (f instanceof Gyalog)
        {
            // En passant: the captured pawn is beside us, not on the target square
            if (hova.equals(epCel) && honnan.getOszlop() != hova.getOszlop())
            {
                tabla.helyez(Szabaly.mezo(hova.getOszlop(), honnan.getSor()), null);
            }
            // Double step: remember the skipped square for the next move
            if (Math.abs(hova.getSor() - honnan.getSor()) == 2)
            {
                ujEp = Szabaly.mezo(honnan.getOszlop(), (honnan.getSor() + hova.getSor()) / 2);
            }
        }

        // The move itself (a captured piece is simply overwritten)
        tabla.helyez(hova, f);
        tabla.helyez(honnan, null);

        // Promotion
        if (f instanceof Gyalog && hova.getSor() == utolsoSor())
        {
            tabla.helyez(hova, ujFigura(atvaltozas, soron));
        }

        megjelol(honnan);
        megjelol(hova);
        epCel = ujEp;
        soron = soron.ellenfel();
        return true;
    }

    // Promotes to a queen by default
    public boolean lep(Mezo honnan, Mezo hova)
    {
        return lep(honnan, hova, 'Q');
    }

    // ---- special rules ----

    private boolean sancolasSzabalyos(Mezo honnan, Mezo hova)
    {
        Figura f = tabla.figura(honnan);
        int sor = (soron == Szin.FEHER) ? 0 : 7;
        if (!(f instanceof Kiraly) || honnan.getOszlop() != 4
                || honnan.getSor() != sor || hova.getSor() != sor)
        {
            return false;
        }
        int eltolas = hova.getOszlop() - 4;
        if (eltolas != 2 && eltolas != -2)
        {
            return false;
        }

        // The rook must be on its starting square, and neither piece may have moved
        int irany = (eltolas > 0) ? 1 : -1;
        int bastyaOszlop = (eltolas > 0) ? 7 : 0;
        Mezo bastyaMezo = Szabaly.mezo(bastyaOszlop, sor);
        Figura b = tabla.figura(bastyaMezo);
        if (!(b instanceof Bastya) || b.getSzin() != soron)
        {
            return false;
        }
        if (erintett[index(honnan)] || erintett[index(bastyaMezo)])
        {
            return false;
        }

        // Squares between the king and the rook must be empty
        for (int o = 4 + irany; o != bastyaOszlop; o += irany)
        {
            if (!tabla.uresE(Szabaly.mezo(o, sor)))
            {
                return false;
            }
        }

        // The king may not start in check, pass over or land on an attacked square
        for (int lepes = 0; lepes <= 2; lepes++)
        {
            if (Szabaly.tamadja(tabla, Szabaly.mezo(4 + irany * lepes, sor), soron.ellenfel()))
            {
                return false;
            }
        }
        return true;
    }

    private boolean enPassantSzabalyos(Mezo honnan, Mezo hova)
    {
        Figura f = tabla.figura(honnan);
        if (!(f instanceof Gyalog) || !hova.equals(epCel))
        {
            return false;
        }
        int irany = (soron == Szin.FEHER) ? 1 : -1;
        int oszlopEltolas = hova.getOszlop() - honnan.getOszlop();
        if ((oszlopEltolas != 1 && oszlopEltolas != -1) || hova.getSor() - honnan.getSor() != irany)
        {
            return false;
        }

        // Try it on a copy: move the pawn, remove the captured pawn, own king must be safe
        Tabla proba = tabla.masolat();
        proba.helyez(hova, f);
        proba.helyez(honnan, null);
        proba.helyez(Szabaly.mezo(hova.getOszlop(), honnan.getSor()), null);
        return !Szabaly.sakkban(proba, soron);
    }

    // ---- helpers ----

    private int utolsoSor()
    {
        return (soron == Szin.FEHER) ? 7 : 0;
    }

    private int index(Mezo m)
    {
        return m.getSor() * 8 + m.getOszlop();
    }

    private void megjelol(Mezo m)
    {
        erintett[index(m)] = true;
    }

    private Figura ujFigura(char jel, Szin szin)
    {
        switch (Character.toUpperCase(jel))
        {
            case 'R': return new Bastya(szin);
            case 'B': return new Futo(szin);
            case 'N': return new Huszar(szin);
            default: return new Vezer(szin);
        }
    }

    private void kezdoAllas()
    {
        for (int o = 0; o < 8; o++)
        {
            tabla.helyez(Szabaly.mezo(o, 1), new Gyalog(Szin.FEHER));
            tabla.helyez(Szabaly.mezo(o, 6), new Gyalog(Szin.FEKETE));
        }
        hatsoSor(0, Szin.FEHER);
        hatsoSor(7, Szin.FEKETE);
    }

    private void hatsoSor(int sor, Szin szin)
    {
        tabla.helyez(Szabaly.mezo(0, sor), new Bastya(szin));
        tabla.helyez(Szabaly.mezo(1, sor), new Huszar(szin));
        tabla.helyez(Szabaly.mezo(2, sor), new Futo(szin));
        tabla.helyez(Szabaly.mezo(3, sor), new Vezer(szin));
        tabla.helyez(Szabaly.mezo(4, sor), new Kiraly(szin));
        tabla.helyez(Szabaly.mezo(5, sor), new Futo(szin));
        tabla.helyez(Szabaly.mezo(6, sor), new Huszar(szin));
        tabla.helyez(Szabaly.mezo(7, sor), new Bastya(szin));
    }
}