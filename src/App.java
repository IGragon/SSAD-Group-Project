import components.Component;
import components.User;
import system.System;
import utils.Coordinates;
import utils.Data;

import java.util.HashMap;

import static system.SystemTypes.ambulance.AMBULANCE;
import static system.SystemTypes.hospital.HOSPITAL;
import static system.SystemTypes.police.POLICE;
import static system.SystemTypes.user.USER;
import static utils.Event.ACCIDENT;


public class App {
    public static void main(String[] args) throws Exception {

    // Simulation of our project implementation
        System system = new System();

    // Create test components (supposedly parsed from DB)
        // Test user array for hospital with only one user
        HashMap<Integer, Data>  HospitalBelongUsers = new HashMap<>();
        HospitalBelongUsers.put(100000, new Data());

        // Declaring the components
        system.addComponent(USER, 100000);
        system.addComponent(HOSPITAL, 200000, HospitalBelongUsers, new Coordinates());
        system.addComponent(AMBULANCE, 300000, 200000);
        system.addComponent(POLICE, 400000, new Coordinates());


    // Test accident case
        Component UnluckyUser = new User();
        Data UserData = new Data();

        system.send(UnluckyUser, ACCIDENT, UserData);

    }
}
