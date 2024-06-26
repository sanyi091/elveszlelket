package skeleton.elveszlelket.tester.Commands;

import skeleton.elveszlelket.*;
import skeleton.elveszlelket.door.*;
import skeleton.elveszlelket.tester.Tester;

/**
 * Parancs az ajtók összekapcsolására két szoba között.
 */
public class CONNECTROOM implements skeleton.elveszlelket.tester.Commands.Command {
    /**
     * Az ajtók összekapcsolását végző parancs végrehajtása.
     *
     * @param params A parancs paraméterei tömbként.
     *               A params[0] tartalmazza a parancs nevét.
     *               A params[1] tartalmazza az ajtó nevét.
     *               A params[2] és params[3] tartalmazza a két szoba nevét,
     *               amelyeket az ajtó összekapcsol.
     * @param t      A Tester objektum, amely tartalmazza a szimulációs
     *               objektumokat.
     */

    public void execute(String[] params, Tester t) {
        if (params.length != 4) {
            System.out.println("Parameterk szama nem megfelelo.");
            return;
        }
        Door keresettDoor = t.getDoor(params[1]);
        // Ha keresettDoor nem null, akkor létezik params[1] nevű ajtó.
        if (keresettDoor != null) {

            if (keresettDoor.getOwnerRoom() != null) {
                System.out.println(params[1]+" nem lett osszekapcsolva "+params[2]+" es "+params[3]+" kozott. Mivel mashol mar hasznalatban van.");
                return;
            }

            Room keresettRoom1 = t.getRoom(params[2]);
            Room keresettRoom2 = t.getRoom(params[3]);

            if (keresettRoom1 != null) {
                if (keresettRoom2 != null) {
                    if (!keresettRoom1.equals(keresettRoom2)) {
                        keresettRoom1.addDoor(keresettDoor);
                        keresettRoom2.addDoor(keresettDoor);
                        keresettDoor.setRooms(keresettRoom1, keresettRoom2);
                        System.out.println(params[1]+" osszekapcsolva "+params[2]+" es "+params[3]+" kozott.");
                    } else {
                        System.out.println("Ugyanazt a szobat sajat magaval nem lehet osszekotni egy ajton keresztul. Connect sikertelen.");
                    }
                } else {
                    System.out.println("Parameterul kapott "+params[3]+" nincs feljegyezve.");
                }
            } else {
                System.out.println("Parameterul kapott "+params[2]+" nincs feljegyezve.");
            }
        } else {
            System.out.println("Parameterul kapott "+params[1]+" nincs feljegyezve.");
        }
    }
}