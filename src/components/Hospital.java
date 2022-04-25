package components;

import utils.Coordinates;
import utils.Data;

import java.util.HashMap;

public class Hospital extends Component implements AccidentNotifiable {
    HashMap<Integer, Data> users;
    Coordinates SelfCoord;

    public Hospital(HashMap<Integer, Data> users, Coordinates SelfCoord) {
        this.users = users;
        this.SelfCoord = SelfCoord;
    }

    public void addPatient(int patientId, Data patientData){
        users.put(patientId, patientData);
    }

    @Override
    public void getAccidentNotification(){
        prepareForPatient();
    }

    public void updatePatientData(){

    }
    private void prepareForPatient(){
        // make some preparations
    }
}
