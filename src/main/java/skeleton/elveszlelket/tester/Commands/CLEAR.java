package skeleton.elveszlelket.tester.Commands;

import skeleton.elveszlelket.tester.Tester;

public class CLEAR implements Command {

    @Override
    public void execute(String[] params, Tester t) {
        t.clear();
        System.out.println("Az osszes objektum torolve. A rendszer alapallapotban van.");
    }

}
