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
            senderU.changeCondition(Condition.IN_ACCIDENT);

            Hospital closestHospital = hospitals.getClosest(senderU);
            Ambulance closestAmbulance = ambulances.getClosest(senderU);
            PoliceStation closestPoliceStation = policeStations.getClosest(senderU);


            senderU.notifyByGsm(closestHospital);
            senderU.notifyByGsm(closestAmbulance);
            senderU.notifyByGsm(closestPoliceStation);


            closestHospital.addPatient((Integer) data.get("id"), (Data) data.get("personalData"));
            closestAmbulance.setPatient(
                    (Integer) data.get("id"),
                    (Data) data.get("personalData"),
                    (Coordinates) data.get("coordinates")
            );
            closestAmbulance.setHospitalDestination(closestHospital.getId());
            closestPoliceStation.getAccidentLocation((Coordinates) data.get("coordinates"));

        }else if (sender.getType() == ComponentType.AMBULANCE && event == Event.PATIENT_RESCUED){
            Ambulance senderA = (Ambulance) sender;
            User patient = users.get((Integer) data.get("id"));
            patient.changeCondition(Condition.IN_AMBULANCE);
        }
        else if (sender.getType() == ComponentType.AMBULANCE && event == Event.PATIENT_DROPPED){
            Ambulance senderA = (Ambulance) sender;
            User patient = users.get((Integer) data.get("id"));
            patient.changeCondition(Condition.DROPPED_TO_HOSPITAL);
            senderA.removePatient();
        }else if (sender.getType() == ComponentType.HOSPITAL && event == Event.PATIENT_ADMITTED){
            Hospital senderH = (Hospital) sender;
            User patient = users.get((Integer) data.get("id"));
            patient.changeCondition(Condition.ADMITTED_TO_HOSPITAL);
        }else if (sender.getType() == ComponentType.HOSPITAL && event == Event.PATIENT_DISCHARGED){
            Hospital senderH = (Hospital) sender;
            User patient = users.get((Integer) data.get("id"));
            patient.changeCondition(Condition.OK);
        }
    }
}
