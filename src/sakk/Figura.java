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
    
    protected Figura(Szin szin)
    {
        this.szin = szin;
    }
    
    public Szin getSzin() { return szin; }
    
    //Checks the piece's own movement rule only (no check detection)
    public abstract boolean lephet(Mezo honnan, Mezo hova, Tabla t);
    
    //Character of the piece, e.g. 'K'; the colour is not part of it
    public abstract char jel();
}
