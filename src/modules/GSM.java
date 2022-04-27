package modules;

import components.Component;
import components.Hospital;
import components.PoliceStation;
import utils.Data;

public class GSM implements Module {
    @Override
    public Data getData() {
        return null;
    }

    public void notifyOrganization(Component organization) {
        switch (organization.getType()) {
            case POLICE -> ((PoliceStation) organization).getAccidentNotification();
            case HOSPITAL -> ((Hospital) organization).getAccidentNotification();
        }
    }
}
