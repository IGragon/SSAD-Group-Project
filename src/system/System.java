package system;

import components.*;
import utils.Condition;
import utils.Coordinates;
import utils.Data;
import utils.Event;

import java.sql.*;
import java.util.HashMap;


public class System implements Mediator {

    boolean UsingDB;
    Connection connection;
    ComponentDatabase<User> usersDB = new ComponentDatabase<>();
    ComponentDatabase<Hospital> hospitalsDB = new ComponentDatabase<>();
    ComponentDatabase<Ambulance> ambulancesDB = new ComponentDatabase<>();
    ComponentDatabase<PoliceStation> policeStationsDB = new ComponentDatabase<>();


    /*
        This constructor is used only
       when you want to connect to DB
    */
    public System(String url, String user, String pass) throws Exception {
        UsingDB = true;

        try {
            connection = DriverManager.getConnection(url, user, pass);
            Class.forName("com.mysql.jdbc.Driver");
            Statement DBstatement = connection.createStatement();
            ResultSet UsersData = DBstatement.executeQuery("select * from users");
            ResultSet HospitalsData = DBstatement.executeQuery("select * from hospitals");
            ResultSet AmbulancesData = DBstatement.executeQuery("select * from ambulances");
            ResultSet PoliceData = DBstatement.executeQuery("select * from polices");

//          ----Some Parsing Algorithm----

            connection.close();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /*
        This constructor is used in
       default case
    */
    public System() {
        UsingDB = false;
    }


    /*
         Send() method provides connection
        between components by using mediator
        concept
    */
    @Override
    public void send(Component sender, Event event, Data data) {

        if (sender.getType() == ComponentType.USER && event == Event.ACCIDENT) {
            User senderU = (User) sender;
            senderU.changeCondition(Condition.IN_ACCIDENT);

//            Find and send notifications to the closest components
            Hospital closestHospital = hospitalsDB.getClosest(senderU);
            Ambulance closestAmbulance = ambulancesDB.getClosest(senderU);
            PoliceStation closestPoliceStation = policeStationsDB.getClosest(senderU);
            senderU.notifyByGsm(closestHospital);
            senderU.notifyByGsm(closestAmbulance);
            senderU.notifyByGsm(closestPoliceStation);

//            Plan and execute tasks for all the components
            closestHospital.addPatient((Integer) data.get("id"), (Data) data.get("personalData"));
            closestAmbulance.setPatient(
                    (Integer) data.get("id"),
                    (Data) data.get("personalData"),
                    (Coordinates) data.get("coordinates")
            );
            closestAmbulance.setHospitalDestination(closestHospital.getId());
            closestPoliceStation.getAccidentLocation((Coordinates) data.get("coordinates"));

        } else if (sender.getType() == ComponentType.AMBULANCE && event == Event.PATIENT_RESCUED) {
            Ambulance senderA = (Ambulance) sender;
            User patient = usersDB.getId((Integer) data.get("id"));
            patient.changeCondition(Condition.IN_AMBULANCE);

        } else if (sender.getType() == ComponentType.AMBULANCE && event == Event.PATIENT_DROPPED) {
            Ambulance senderA = (Ambulance) sender;
            User patient = usersDB.getId((Integer) data.get("id"));
            patient.changeCondition(Condition.DROPPED_TO_HOSPITAL);
            senderA.removePatient();

        } else if (sender.getType() == ComponentType.HOSPITAL && event == Event.PATIENT_ADMITTED) {
            Hospital senderH = (Hospital) sender;
            User patient = usersDB.getId((Integer) data.get("id"));
            patient.changeCondition(Condition.ADMITTED_TO_HOSPITAL);

        } else if (sender.getType() == ComponentType.HOSPITAL && event == Event.PATIENT_DISCHARGED) {
            Hospital senderH = (Hospital) sender;
            User patient = usersDB.getId((Integer) data.get("id"));
            patient.changeCondition(Condition.OK);
            senderH.removePatient(patient.getId());
        }
    }


    /*
         This function is used to execute database
        commands with interrupted
        connection (for not overloading DB).
    */
    private ResultSet ExecuteDB(String command) throws SQLException {
        Statement DBstatement = connection.createStatement();
        ResultSet result = DBstatement.executeQuery(command);
        connection.close();
        return result;
    }

    /*
        Here are multiple constructor variants
        to add different type Components to the system
    */
    public void addComponent(SystemTypes.user type, int id) throws SQLException {
        usersDB.addComponent(id, new User());

        if (UsingDB) {
            ExecuteDB(String.format("INSERT INTO users(id, data) " +
                    "VALUES(%d, %s)", id, "some_user_data"));
        }
    }

    public void addComponent(SystemTypes.hospital type, int id, HashMap<Integer, Data> UserArr, Coordinates SelfCoord) throws SQLException {
        hospitalsDB.addComponent(id, new Hospital(UserArr, SelfCoord));

        if (UsingDB) {
            ExecuteDB(String.format("INSERT INTO hospitals(id, data) " +
                    "VALUES(%d, %s)", id, "some_hospital_data"));
        }
    }

    public void addComponent(SystemTypes.ambulance type, int id, int AttachedHospitalId) throws SQLException {
        ambulancesDB.addComponent(id, new Ambulance(AttachedHospitalId));

        if (UsingDB) {
            ExecuteDB(String.format("INSERT INTO ambulances(id, data) " +
                    "VALUES(%d, %s)", id, "some_ambulance_data"));
        }
    }

    public void addComponent(SystemTypes.police type, int id, Coordinates SelfCoord) throws SQLException {
        policeStationsDB.addComponent(id, new PoliceStation(SelfCoord));

        if (UsingDB) {
            ExecuteDB(String.format("INSERT INTO polices(id, data) " +
                    "VALUES(%d, %s)", id, "some_police_data"));
        }
    }
}
