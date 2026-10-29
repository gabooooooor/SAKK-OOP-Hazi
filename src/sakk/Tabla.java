/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sakk;

/**
 *
 * The 8x8 board. Holds the pieces and the last move (for en passant)
 */
public class Tabla {
    
    private final Figura[][] racs = new Figura[8][8];
    
    public Tabla(){}
    
    public Tabla(Tabla other)
    {
        for(int s = 0; s < 8; s++)
        {
            for(int o = 0; o < 8; o++)
                racs[s][o] = other.racs[s][o];
        }
    }
    
    public Figura figura(Mezo m)
    {
        return racs[m.getSor()][m.getOszlop()];
    }
    
    public boolean uresE(Mezo m)
    {
        return figura(m) == null;
    }
    
    public void helyez(Mezo m, Figura f)
    {
        racs[m.getSor()][m.getOszlop()] = f;
    }
    
    //Checks the squares strictly BETWEEN honnan and hova (straight or diagonal line)
    public boolean utbanVan(Mezo honnan, Mezo hova)
    {
        int dOszlop = Integer.signum(hova.getOszlop() - honnan.getOszlop());
        int dSor = Integer.signum(hova.getSor() - honnan.getSor());
        int o = honnan.getOszlop() + dOszlop;
        int s = honnan.getSor() + dSor;
        while(o != hova.getOszlop() || s != hova.getSor())
        {
            if(racs[s][o] != null)
            {
                return true;
            }
            o += dOszlop;
            s += dSor;
        }
        return false;
    }
    
    //Copy constructor style copy: the pieces are shared (they are stateless), the grid is new
    public Tabla masolat()
    {
        return new Tabla(this);
    }
}
