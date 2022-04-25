package system;

import components.*;
import utils.Condition;
import utils.Coordinates;
import utils.Data;
import utils.Event;

import java.util.HashMap;


public class System implements Mediator {
    ComponentDatabase<User> users = new ComponentDatabase<>();
    ComponentDatabase<Hospital> hospitals = new ComponentDatabase<>();
    ComponentDatabase<Ambulance> ambulances = new ComponentDatabase<>();
    ComponentDatabase<PoliceStation> policeStations = new ComponentDatabase<>();

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
        }

        else if (sender.getType() == ComponentType.AMBULANCE && event == Event.PATIENT_RESCUED){
            Ambulance senderA = (Ambulance) sender;
            User patient = users.getId((Integer) data.get("id"));
            patient.changeCondition(Condition.IN_AMBULANCE);
        }

        else if (sender.getType() == ComponentType.AMBULANCE && event == Event.PATIENT_DROPPED){
            Ambulance senderA = (Ambulance) sender;
            User patient = users.getId((Integer) data.get("id"));
            patient.changeCondition(Condition.DROPPED_TO_HOSPITAL);
            senderA.removePatient();
        }

        else if (sender.getType() == ComponentType.HOSPITAL && event == Event.PATIENT_ADMITTED){
            Hospital senderH = (Hospital) sender;
            User patient = users.getId((Integer) data.get("id"));
            patient.changeCondition(Condition.ADMITTED_TO_HOSPITAL);
        }

        else if (sender.getType() == ComponentType.HOSPITAL && event == Event.PATIENT_DISCHARGED){
            Hospital senderH = (Hospital) sender;
            User patient = users.getId((Integer) data.get("id"));
            patient.changeCondition(Condition.OK);
        }
    }


//    public void addComponent(ComponentType T, int id, HashMap<Integer, Data> info_arr){
//        switch (T){
//
//            case USER -> users.addComponent(id, new User());
//
//            case HOSPITAL -> hospitals.addComponent(id, new Hospital(info_arr));
//
//            case AMBULANCE -> ambulances.addComponent(id, new Ambulance());
//
//            case POLICE -> policeStations.addComponent(id, new PoliceStation());
//        }
//    }


    public void addComponent(SystemTypes.user type, int id){
            users.addComponent(id, new User());
    }

    public void addComponent(SystemTypes.hospital type, int id, HashMap<Integer, Data> UserArr, Coordinates SelfCoord){
            hospitals.addComponent(id, new Hospital(UserArr, SelfCoord));
    }

    public void addComponent(SystemTypes.ambulance type, int id, int AttachedHospitalId){
            ambulances.addComponent(id, new Ambulance(AttachedHospitalId));
    }

    public void addComponent(SystemTypes.police type, int id, Coordinates SelfCoord){
            policeStations.addComponent(id, new PoliceStation(SelfCoord));
    }
}
