package modules;

import components.AccidentNotifiable;
import components.Component;
import components.Hospital;
import components.PoliceStation;
import utils.Data;

public class GSM implements Module {
    @Override
    public Data getData() {
        return null;
    }

    public void notifyOrganization(AccidentNotifiable organization) {
        organization.getAccidentNotification();
    }
}
