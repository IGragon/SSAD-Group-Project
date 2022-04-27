package components;

import utils.Coordinates;
import utils.Data;
import java.util.HashMap;

// Hospital
public class Hospital extends Component implements AccidentNotifiable {
    HashMap<Integer, Data> users;

    public Hospital(int id, HashMap<Integer, Data> users, Coordinates coordinates) {
        this.id = id;
        this.users = users;
        this.coordinates = coordinates;
    }

    public void addPatient(int patientId, Data patientData) {
        users.put(patientId, patientData);
    }

    @Override
    public void getAccidentNotification() {
        prepareForPatient();
    }

    public void removePatient(int patientId) {
        users.remove(patientId);
    }

    private void prepareForPatient() {
        // Make some preparations
    }
}
