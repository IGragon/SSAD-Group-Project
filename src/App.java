import system.System;
import utils.Coordinates;

import static system.SystemTypes.ambulance.AMBULANCE;
import static system.SystemTypes.police.POLICE;
import static system.SystemTypes.user.USER;


public class App {
    public static void main(String[] args) {

        // simulation of our project implementation
        System system = new System();

        system.addComponent(USER, 100000);
//        system.addComponent(HOSPITAL, 200000, HashMap<Integer, Data >, new Coordinates());
        system.addComponent(AMBULANCE, 300000, 200000);
        system.addComponent(POLICE, 400000, new Coordinates());

    }
}
