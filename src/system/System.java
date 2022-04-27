package system;

import components.*;
import utils.Condition;
import utils.Coordinates;
import utils.Data;
import utils.Event;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;


public class System implements Mediator {

    boolean UsingDB;
    ComponentDatabase<User> usersDB = new ComponentDatabase<>();
    ComponentDatabase<Hospital> hospitalsDB = new ComponentDatabase<>();
    ComponentDatabase<Ambulance> ambulancesDB = new ComponentDatabase<>();
    ComponentDatabase<PoliceStation> policeStationsDB = new ComponentDatabase<>();


//   This constructor is used only
//   when you want to connect to DB
    public System(String url, String user, String pass) throws Exception {
        UsingDB = true;

        try(Connection connection = DriverManager.getConnection(url, user, pass)){
            Class.forName("com.mysql.jdbc.Driver");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from users");
            connection.close();
        }

        catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

//   This constructor is used in
//   default case
    public System() {
        UsingDB = false;
    }


    @Override
    public void send(Component sender, Event event, Data data) {
        if (sender.getType() == ComponentType.USER && event == Event.ACCIDENT){
            User senderU = (User) sender;
            senderU.changeCondition(Condition.IN_ACCIDENT);

            Hospital closestHospital = hospitalsDB.getClosest(senderU);
            Ambulance closestAmbulance = ambulancesDB.getClosest(senderU);
            PoliceStation closestPoliceStation = policeStationsDB.getClosest(senderU);


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
            User patient = usersDB.getId((Integer) data.get("id"));
            patient.changeCondition(Condition.IN_AMBULANCE);
        }

        else if (sender.getType() == ComponentType.AMBULANCE && event == Event.PATIENT_DROPPED){
            Ambulance senderA = (Ambulance) sender;
            User patient = usersDB.getId((Integer) data.get("id"));
            patient.changeCondition(Condition.DROPPED_TO_HOSPITAL);
            senderA.removePatient();
        }

        else if (sender.getType() == ComponentType.HOSPITAL && event == Event.PATIENT_ADMITTED){
            Hospital senderH = (Hospital) sender;
            User patient = usersDB.getId((Integer) data.get("id"));
            patient.changeCondition(Condition.ADMITTED_TO_HOSPITAL);
        }

        else if (sender.getType() == ComponentType.HOSPITAL && event == Event.PATIENT_DISCHARGED){
            Hospital senderH = (Hospital) sender;
            User patient = usersDB.getId((Integer) data.get("id"));
            patient.changeCondition(Condition.OK);
        }
    }


//    Here are multiple constructor variants
//    to add different type Components to the system
    public void addComponent(SystemTypes.user type, int id){
            usersDB.addComponent(id, new User());
    }

    public void addComponent(SystemTypes.hospital type, int id, HashMap<Integer, Data> UserArr, Coordinates SelfCoord){
            hospitalsDB.addComponent(id, new Hospital(UserArr, SelfCoord));
    }

    public void addComponent(SystemTypes.ambulance type, int id, int AttachedHospitalId){
            ambulancesDB.addComponent(id, new Ambulance(AttachedHospitalId));
    }

    public void addComponent(SystemTypes.police type, int id, Coordinates SelfCoord){
            policeStationsDB.addComponent(id, new PoliceStation(SelfCoord));
    }
}
