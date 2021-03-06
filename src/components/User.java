package components;

import modules.GPS;
import modules.GSM;
import modules.Sensor;
import modules.SoundMeter;
import utils.Condition;
import utils.Coordinates;
import utils.Data;

public class User extends Component {
    private Sensor sensor;
    private SoundMeter soundMeter;
    private GPS gps;
    private GSM gsm;
    private Data personalData;
    Condition condition;

    public User(int id) {
        sensor = new Sensor();
        soundMeter = new SoundMeter();
        gps = new GPS();
        gsm = new GSM();
        personalData = new Data();
        condition = Condition.OK;
    }

    @Override
    public Coordinates getCoordinates() {
        return (Coordinates) gps.getData().get("coordinates");
    }

    public boolean isAccident() {
        return (Boolean) sensor.getData().get("isAccident");
    }

    public void changeCondition(Condition condition) {
        this.condition = condition;
    }

    public void notifyByGsm(AccidentNotifiable organization) {
        gsm.notifyOrganization(organization);
    }

    public Data collectAllData() {
        Data data = new Data();
        data.put("personalData", personalData);
        data.put("id", id);
        data
                .merge(soundMeter.getData())
                .merge(gps.getData());
        return data;
    }
}
