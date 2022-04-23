package components;

import utils.Coordinates;

public class PoliceStation extends Component implements AccidentNotifiable{


    public void getAccidentLocation(Coordinates coordinates){
        // do something with this information
    }

    @Override
    public void getAccidentNotification(){
        sendPatrol();
    }

    private void sendPatrol(){
        // sending traffic police patrol
    }
}
