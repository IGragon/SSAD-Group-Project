package system;

import components.Component;
import utils.Data;
import utils.Event;

public interface Mediator {
    void send(Component sender, Event event, Data data);
}
