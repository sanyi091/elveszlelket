package skeleton.elveszlelket.tester.Commands;

import skeleton.elveszlelket.*;
import skeleton.elveszlelket.item.*;
import skeleton.elveszlelket.tester.Tester;

/**
 * Egy parancsot implementál, amely lehetővé teszi egy tárgy hozzáadását egy diákhoz vagy tanárhoz.
 */
public class ADDTOHUMAN implements skeleton.elveszlelket.tester.Commands.Command {
    /**
     * Végrehajtja a tárgy hozzáadását egy diákhoz vagy tanárhoz.
     *
     * @param params A parancs paraméterei, amelyek tartalmazzák a tárgy nevét és a diák/tanár nevét.
     * @param t A Tester objektum, amely a parancsot végrehajtja, és tartalmazza a diákok, tanárok és tárgyak gyűjteményét.
     */
    public void execute(String[] params, Tester t) {
        Item keresettItem = t.getItem(params[1]); 
        // Ha keresettItem nem null, akkor létezik params[1] nevű tárgy.
        if(keresettItem != null) {
            Teacher keresettOktato = t.getTeacher(params[2]); // hasonlóan ha keresettOktato nem null, akkor van már ilyen nevű
            Student keresettHallgato = t.getStudent(params[2]); // hasonlóan ha keresettHallgato nem null, akkor van már ilyen nevű

            if(keresettHallgato != null) {
                if(keresettHallgato.pickupItem(keresettItem)) {
                    System.out.println(params[1] + " tárgy felvétele sikeres.");
                } else {
                    System.out.println(params[1] + " tárgy felvétele sikertelen.");
                }
            } else if(keresettOktato != null) {
                if(keresettOktato.pickupItem(keresettItem)) {
                    System.out.println(params[1] + " tárgy felvétele sikeres.");
                } else {
                    System.out.println(params[1] + " tárgy felvétele sikertelen.");
                }
            } else {
                System.out.println("Parameterkent kapott human entitas nincs meg feljegyezve.");
            } 
        } else {
            System.out.println("Parameterkent kapott targy nincs meg feljegyezve.");
        } 
    }
}