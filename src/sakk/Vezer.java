/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sakk;

/**
 *
 * @author ministar
 */
public class Vezer extends Figura{
    
    public Vezer(Szin szin) {
        super(szin);
    }

    @Override
    public boolean lephet(Mezo honnan, Mezo hova, Tabla t) {
        return csuszik(honnan, hova, t, true, true);
    }

    @Override
    public char jel() {
        return 'Q';
    }
}
