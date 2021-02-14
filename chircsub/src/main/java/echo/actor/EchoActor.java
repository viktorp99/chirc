package echo.actor;

import actor.AbstractActor;
import actor.Actor;
import fpinjava.Result;

public abstract class EchoActor extends AbstractActor<String> {

    public EchoActor() {
        super("EchoActor", Actor.Type.SERIAL);
        //System.out.println("Ecjp Actor");
    }

    @Override
    public void onReceive(String message, Result<Actor<String>> sender) {
        //System.err.println("EcHOactorrrrrrrrrrr" + " " + message);
        //System.out.println(sender.successValue().toString());
        sender.forEach(actor -> actor.tell(message, self()));
    }

}
