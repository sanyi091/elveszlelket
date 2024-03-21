package skeleton.elveszlelket;
import skeleton.elveszlelket.Player;
import skeleton.elveszlelket.item.*;

public interface Human {
    boolean use(OneWayDoor door);
    boolean use(TwoWayDoor door);
    boolean pickupItem(Item item);
    boolean dropItem(Item item);
    void stun(int duration);
    boolean decreaseStun(int amount);
    Room getRoom();
}