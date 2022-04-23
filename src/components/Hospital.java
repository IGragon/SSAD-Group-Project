package components;

import utils.Data;

import java.util.HashMap;

public class Hospital extends Component implements AccidentNotifiable {
    HashMap<Integer, Data> users;
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
