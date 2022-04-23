package components;

import utils.Coordinates;

public class PoliceStation extends Component implements AccidentNotifiable{


    public void getAccidentLocation(Coordinates coordinates){
        // do something with this information
        sendPatrol();
    }

    @Override
    public void getAccidentNotification(){
        preparePatrol();
    }

    private void preparePatrol(){
        // prepare traffic police patrol
    }
    private void sendPatrol(){
        // sending traffic police patrol
    }
}
