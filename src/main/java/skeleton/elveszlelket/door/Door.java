package skeleton.elveszlelket.door;

import skeleton.elveszlelket.CleaningLady;
import skeleton.elveszlelket.Room;
import skeleton.elveszlelket.Student;
import skeleton.elveszlelket.Teacher;

public abstract class Door {
    boolean hidden = false;
    Room ownerRoom;

    public boolean isHidden() {
        return hidden;
    }

    /**
     * @return Szoba. Vissza adja, melyik eredeti szobába került le
     *         először a labirintus elkészítésekor. Az ownerRoom egy kiinduló pontja
     *         az ajtónak,
     *         ha egy szobának nem létezik ilyen tagváltozója, a szoba nem található
     *         meg a labirintusban.
     */
    public Room getOwnerRoom() {
        return ownerRoom;
    }

    /**
     * @return Szoba. Vissza adja, melyik (az eredeti szobán kívül) a másik
     *         feljegyzett
     *         szobája.
     */
    public Room getSecondRoom() {
        return null;
    }

    /*
     * Beállítja az szobáit, inicializáláskor kell.
     * 
     * @param r1 Egyik szoba
     * 
     * @param r2 Második szoba
     * OneWayDoor esetén az r1-nek nincs jelentősége.
     */
    public abstract void setRooms(Room r1, Room r2);

    public abstract Room getDest(Room r);

    /**
     * Megjeleníti vagy eltünteti az ajtót
     */
    public void changeVisibility() {
        if (hidden)
            hidden = false;
        else
            hidden = true;
    }

    /**
     * Elfogadja az observer hallgatót.
     * 
     * @param s A hallgató.
     */
    public abstract void accept(Student s);

    /**
     * Elfogadja az observer oktatót.
     * 
     * @param t Az oktató.
     */
    public abstract void accept(Teacher t);

    public abstract void accept(CleaningLady c);

    /**
     * Átrakja a hallgatót a másik szobába.
     * 
     * @param s A hallgató.
     * @return A művelet sikeressége: Igaz csakis akkor, ha sikeres.
     */
    public abstract void putMeThrough(Student s);

    /**
     * Átrakja az oktatót a másik szobába.
     * 
     * @param t Az oktató.
     * @return A művelet sikeressége: Igaz csakis akkor, ha sikeres.
     */
    public abstract void putMeThrough(Teacher t);

    public abstract void putMeThrough(CleaningLady c);

}
