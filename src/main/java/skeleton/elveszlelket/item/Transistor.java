package skeleton.elveszlelket.item;

import skeleton.elveszlelket.Room;
import skeleton.elveszlelket.Student;
import skeleton.elveszlelket.gui.ItemView;
import skeleton.elveszlelket.strategy.TeleportStrategy;

/**
 * Ez az osztály egy tranzisztor reprezentációja, amely képes párosítani egy
 * másik tranzisztort.
 * A tranzisztor használata lehetővé teszi a diákok teleportálását a párosított
 * tranzisztor helyére.
 */
public class Transistor extends Item {
    private Transistor pair;

    /**
     * Konstruktor, amely létrehoz egy új Transistor példányt.
     * Az alapértelmezett név 'Transistor' és a használati stratégia a teleportálási
     * stratégia.
     */
    public Transistor() {
        name = "Transistor";
        strategy = new TeleportStrategy();
    }

    /**
     * Használja a tranzisztort a megadott diákon.
     * A tranzisztor használatakor a diák a párosított tranzisztor helyére
     * teleportálódik.
     * 
     * @param student A diák, aki a tranzisztort használja.
     */
    public void use(Student student) {
        strategy.execute(student, this);
    }

    /**
     * Meghatározza, hogy a tranzisztorhoz van-e párosított tranzisztor.
     * 
     * @return boolean Igaz, ha van párja a tranzisztorhoz, egyébként hamis.
     */
    public boolean hasPair() {
        return pair != null;
    }

    /**
     * Párosítja ezt a tranzisztort egy másik tranzisztorral.
     * Ha egyik tranzisztorhoz sincs még párja, akkor ezeket összepárosítja.
     * 
     * @param other A tranzisztor, amellyel ez a tranzisztor párosításra kerül.
     */
    public void addPair(Transistor other) {
        if (!other.hasPair() && !hasPair()) {
            pair = other;
            other.addPair(this);
        } else if (other.getPair() == this) {
            pair = other;
        }
    }

    /**
     * Visszaadja a tranzisztor párját.
     * 
     * @return Transistor A tranzisztor párja, ha van, egyébként null.
     */
    public Transistor getPair() {
        return pair;
    }

    /**
     * Megszünteti a tranzisztor párosítását.
     */
    public void unPair() {
        pair = null;
    }

    @Override
    public void setFalse(boolean fals) {
        return;
    }

    @Override
    public void setView(float x, float y) {
        view = new ItemView(x, y, "file:textures/transistor.png");
    }

    public void connectRandom(Student s) {
        for (Item i : s.getInventory()) {
            if (i.getName().equals("Transistor")) {
                Transistor t = (Transistor) i;
                addPair(t);
            }
        }
    }
}
