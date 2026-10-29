/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sakk;

import java.io.PrintStream;
import java.util.Scanner;

// Console game: draws the board and asks for moves like "e2 e4"
public class Main {

    public static void main(String[] args) throws Exception
    {
        // UTF-8 output so the chess symbols show correctly on Windows
        PrintStream ki = new PrintStream(System.out, true, "UTF-8");
        Scanner be = new Scanner(System.in);
        Jatek jatek = new Jatek();

        while (true)
        {
            kirajzol(jatek.getTabla(), ki);
            String nev = (jatek.getSoron() == Szin.FEHER) ? "White" : "Black";
            ki.println(nev + " to move" + (jatek.sakkban() ? " (CHECK!)" : ""));
            ki.print("Move (e.g. e2 e4) or 'exit': ");

            String sor = be.nextLine().trim();
            if (sor.equalsIgnoreCase("exit"))
            {
                break;
            }
            String[] reszek = sor.split("\\s+");
            if (reszek.length != 2)
            {
                ki.println("Please type two squares, e.g. e2 e4");
                continue;
            }

            try
            {
                Mezo honnan = Mezo.of(reszek[0]);
                Mezo hova = Mezo.of(reszek[1]);
                char valasztas = 'Q';
                if (jatek.atvaltozasKell(honnan, hova))
                {
                    ki.print("Promote to (Q, R, B, N): ");
                    String s = be.nextLine().trim();
                    if (!s.isEmpty())
                    {
                        valasztas = s.charAt(0);
                    }
                }
                if (!jatek.lep(honnan, hova, valasztas))
                {
                    ki.println("Illegal move.");
                }
            }
            catch (IllegalArgumentException e)
            {
                ki.println("Invalid square: " + e.getMessage());
            }
        }
    }

    private static void kirajzol(Tabla t, PrintStream ki)
    {
        ki.println();
        for (int s = 7; s >= 0; s--)
        {
            StringBuilder sor = new StringBuilder((s + 1) + " ");
            for (int o = 0; o < 8; o++)
            {
                Figura f = t.figura(Szabaly.mezo(o, s));
                sor.append(f == null ? ". " : szimbolum(f) + " ");
            }
            ki.println(sor);
        }
        ki.println("  a b c d e f g h");
    }

    // Unicode chess symbol from the piece letter and colour
    private static String szimbolum(Figura f)
    {
        String feher = "\u2654\u2655\u2656\u2657\u2658\u2659";   // K Q R B N P
        String fekete = "\u265A\u265B\u265C\u265D\u265E\u265F";
        int index = "KQRBNP".indexOf(Character.toUpperCase(f.jel()));
        // On a dark terminal the filled symbols look white, so the sets are swapped
        String keszlet = (f.getSzin() == Szin.FEHER) ? fekete : feher;
        return String.valueOf(keszlet.charAt(index));
    }
}
