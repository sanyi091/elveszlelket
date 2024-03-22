package skeleton.elveszlelket.item;

import skeleton.elveszlelket.Student;
import skeleton.elveszlelket.strategy.ItemUseStrategy;

public abstract class Item {
    protected String name;
    protected ItemUseStrategy strategy;

    public Item() {
        
    }
    public abstract void use(Student student);
}
