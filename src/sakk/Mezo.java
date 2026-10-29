/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sakk;

/**
 *
 * Immutable board square. Column and row are 0..7
 */
public final class Mezo {
    
    private final int oszlop;
    private final int sor;
    
    public Mezo(int oszlop, int sor)
    {
        if(oszlop < 0 || oszlop > 7 || sor < 0 || sor > 7)
        {
            throw new IllegalArgumentException("Square out of range: " + oszlop + "," + sor);
        }
        this.oszlop = oszlop;
        this.sor = sor;
    }
    
    public static Mezo of(String jeloles)
    {
        if(jeloles == null || jeloles.length() != 2)
        {
            throw new IllegalArgumentException("Invalid square: " + jeloles);
        }
        return new Mezo(jeloles.charAt(0) - 'a', jeloles.charAt(1) - '1');
    }
    
    public int getOszlop() { return oszlop; }
    public int getSor() { return sor; }
    
    @Override
    public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(!(o instanceof Mezo))
        {
            return false;
        }
        Mezo m = (Mezo) o;
        return oszlop == m.oszlop && sor == m.sor;
    }
    
    @Override
    public int hashCode()
    {
        return 8 * oszlop + sor;
    }
    
    @Override
    public String toString()
    {
        return "" + (char) ('a' + oszlop) + (char) ('1' + sor);
    }
}
