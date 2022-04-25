package components;

import utils.Coordinates;
import utils.Data;

public class Ambulance extends Component {
    int patientId;
    int AttachedHospitalId;
    int hospitalDestinationId;
    Data patientData;
    Coordinates patientLocation;

    public Ambulance(int attachedHospitalId) {
        AttachedHospitalId = attachedHospitalId;
    }

    public void setHospitalDestination(int hospitalDestinationId) {
        this.hospitalDestinationId = hospitalDestinationId;
    }

    public void setPatient(int patient, Data patientData, Coordinates location){
        this.patientId = patient;
        this.patientData = patientData;
        this.patientLocation = location;
    }

    public int getPatientId() {
        return patientId;
    }

    public int getHospitalDestinationId() {
        return hospitalDestinationId;
    }

    public void removePatient(){
        this.patientId = -1;
        this.hospitalDestinationId = -1;
        this.patientData = null;
        this.patientLocation = null;
    }
}
