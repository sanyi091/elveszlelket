package skeleton.elveszlelket.tester.Commands;

import skeleton.elveszlelket.*;
import skeleton.elveszlelket.door.*;
import skeleton.elveszlelket.tester.Tester;

/**
 * Az ajtón való áthaladást végző parancs.
 */
public class MOVE implements skeleton.elveszlelket.tester.Commands.Command {
    /**
     * Az ajtón való áthaladást végző parancs végrehajtása.
     *
     * @param params A parancs paraméterei tömbként.
     *               A params[0] tartalmazza a parancs nevét.
     *               A params[1] tartalmazza az ajtó nevét, amelyen át kell haladni.
     *               A params[2] tartalmazza a hallgató vagy tanár nevét, aki
     *               áthalad az ajtón.
     * @param t      A Tester objektum, amely tartalmazza a szimulációs
     *               objektumokat.
     */
    public void execute(String[] params, Tester t) {
        if (params.length != 3) {
            System.out.println("Parameterk szama nem megfelelo.");
            return;
        }

        Door keresettDoor = t.getDoor(params[1]);
        // Ha keresettDoor nem null, akkor létezik params[1] nevű ajtó.
        if (keresettDoor != null) {
            Student keresettStudent = t.getStudent(params[2]);
            Teacher keresettTeacher = t.getTeacher(params[2]);
            CleaningLady keresettCleaner = t.getCleaningLady(params[2]);

            if (keresettStudent != null) {
                if (t.movement.contains(params[2])) {
                    if (!keresettStudent.isDead()) {
                        if (keresettDoor.getOwnerRoom().equals(keresettStudent.getRoom()) || keresettDoor.getSecondRoom().equals(keresettStudent.getRoom())) {
                            t.movement.remove(params[2]);
                            keresettDoor.accept(keresettStudent);
                        } else {
                            System.out.println("Az ajto illetve az ember nem ugyanabban a szobaban vannak.");
                        }
                    } else {
                        System.out.println("Mozgatni kivant hallgato mar nem el.");
                    }
                }
                else {
                    System.out.println(params[2] + " már lépett a körben");
                    return;
                }
            } else if (keresettTeacher != null) {
                if (t.movement.contains(params[2])) {

                    if (keresettDoor.getOwnerRoom().equals(keresettTeacher.getRoom()) || keresettDoor.getSecondRoom().equals(keresettTeacher.getRoom())) {
                        keresettDoor.accept(keresettTeacher);
                        t.movement.remove(params[2]);
                    } else {
                        System.out.println("Az ajto illetve az ember nem ugyanabban a szobaban vannak.");
                    }
                }
                else {
                    System.out.println(params[2] + " már lépett a körben");
                    return;
                }
            } else if (keresettCleaner != null) {
                if (t.movement.contains(params[2])) {
                    if (keresettDoor.getOwnerRoom().equals(keresettCleaner.getRoom()) || keresettDoor.getSecondRoom().equals(keresettCleaner.getRoom())) {
                        keresettDoor.accept(keresettCleaner);
                        t.movement.remove(params[2]);
                    } else {
                        System.out.println("Az ajto illetve az ember nem ugyanabban a szobaban vannak.");
                    }
                }
                else {
                    System.out.println(params[2] + " már lépett a körben");
                    return;
                }
            } else {
                System.out.println("Parameterul kapott "+params[2]+" nincs feljegyezve.");
            }
        } else {
            System.out.println("Parameterul kapott "+params[1]+" nincs feljegyezve.");
        }
    }
}