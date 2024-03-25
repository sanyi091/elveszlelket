package skeleton.elveszlelket.strategy;

import skeleton.elveszlelket.App;
import skeleton.elveszlelket.Student;
import skeleton.elveszlelket.item.FFP2Mask;
import skeleton.elveszlelket.item.Item;

/**
 * A GasProtectionStrategy egy stratégia, amely növeli a diák gázvédelmét,
 * amikor egy adott tárgyat használnak.
 * Ezt a stratégiát FPP2-es maszkhoz tartozik.
 */
public class GasProtectionStrategy implements ItemUseStrategy {
    
    /**
     * Végrehajtja a gázvédelmi stratégiát egy adott diákon egy adott tárgy használatakor(ffp2maszk).
     * Ha a  maszknak, vannak még használatai, akkor növeli a diák
     * gázvédelmi szintjét.
     *
     * @param student A diák, aki a tárgyat használja.
     * @param item A tárgy, amelynek használata során a stratégia aktiválódik.
     */
    public void execute(Student student, Item item) {
        int result = App.t.askInt("Hanyszor lehet a maszkot hasznalni?");
        if(result > 0) {
            student.setGasProtection(30);
            FFP2Mask m = (FFP2Mask)item;
            m.decreaseUse();
            System.out.println("Maszk felszerelve.");
        } else {
            System.out.println("Maszk lejart.");
        }
    }
}
