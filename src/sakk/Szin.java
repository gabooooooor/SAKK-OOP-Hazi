/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package sakk;

/**
 *
 * @author ministar
 */
public enum Szin {
    FEHER, FEKETE;

    public Szin ellenfel() {
        return this == FEHER ? FEKETE : FEHER;
    }
}
