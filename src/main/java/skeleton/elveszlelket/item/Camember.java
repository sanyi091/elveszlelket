package skeleton.elveszlelket.item;

import skeleton.elveszlelket.Student;
import skeleton.elveszlelket.strategy.GasStrategy;

public class Camember extends Item {
    private Boolean opened;

    /**
     * A Camembert konstruktora, beállítja
     * a nevét Camembert-re,
     * a strategy-t GasStrategy-re,
     * a opened állapotát pedig false-ra.
     */
    public Camember() {
        name = "Camembert";
        strategy = new GasStrategy();
        opened = false;
    }

    public boolean isOpened() {
        return opened;
    }

    /**
     * A Camembert használatát implementáló függvény.
     * Meghívja a saját strategy-jét.
     * 
     * @param student A tárgyat használó hallgató
     */
    public void use(Student student) {
        strategy.execute(student, this);
    }

    @Override
    public void setFalse(boolean fals) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setFalse'");
    }
}
