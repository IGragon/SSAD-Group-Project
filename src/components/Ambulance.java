package components;

import utils.Coordinates;
import utils.Data;

public class Ambulance extends Component {
    int patientId;
    Data patientData;
    Coordinates location;
    Hospital hospitalDestination;

    public void setHospitalDestination(Hospital hospitalDestination) {
        this.hospitalDestination = hospitalDestination;
    }

    public void setPatient(int patient, Data patientData, Coordinates location){
        this.patientId = patient;
        this.patientData = patientData;
        this.location = location;
    }

    public void removePatient(){
        this.patientId = -1;
        this.patientData = null;
        this.location = null;
    }
}
