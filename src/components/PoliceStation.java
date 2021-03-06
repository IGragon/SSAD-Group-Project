package components;

import utils.Coordinates;

public class PoliceStation extends Component implements AccidentNotifiable {

    public PoliceStation(int id, Coordinates coordinates) {
        this.id = id;
        this.coordinates = coordinates;
    }


    public void getAccidentLocation(Coordinates coordinates) {
        // Do something with this information
        sendPatrol();
    }

    @Override
    public void getAccidentNotification() {
        preparePatrol();
    }

    private void preparePatrol() {
        // Prepare traffic police patrol
    }

    private void sendPatrol() {
        // Sending traffic police patrol
    }
}
