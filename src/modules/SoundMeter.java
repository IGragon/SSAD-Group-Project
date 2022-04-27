package modules;

import utils.Data;

public class SoundMeter implements Module {
    @Override
    public Data getData() {
        Data data = new Data();
        data.put("soundLevel", 123); // whatever it means
        return null;
    }

    void enableAlarm() {

    }

    void disableAlarm() {

    }
}
