package system;

import components.*;
import utils.*;


public class System implements Mediator {
    ComponentDatabase<User> users;
    ComponentDatabase<Hospital> hospitals;
    ComponentDatabase<Ambulance> ambulances;
    ComponentDatabase<PoliceStation> policeStations;

    @Override
    public void send(Component sender, Event event, Data data) {
        if (sender.getType() == ComponentType.USER && event == Event.ACCIDENT){
            User senderU = (User) sender;
            Data userData = senderU.collectAllData();


            Hospital closestHospital = hospitals.getClosest(senderU);
            Ambulance closestAmbulance = ambulances.getClosest(senderU);
            PoliceStation closestPoliceStation = policeStations.getClosest(senderU);


            senderU.notifyByGsm(closestHospital);
            senderU.notifyByGsm(closestAmbulance);
            senderU.notifyByGsm(closestPoliceStation);


            closestHospital.addPatient((Integer) userData.get("id"), (Data) userData.get("personalData"));
            closestAmbulance.setPatient(
                    (Integer) userData.get("id"),
                    (Data) userData.get("personalData"),
                    (Coordinates) userData.get("coordinates")
            );
            closestAmbulance.setHospitalDestination(closestHospital);

        }
    }
}
