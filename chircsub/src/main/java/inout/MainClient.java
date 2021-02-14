package inout;

import actor.ActorSystem;
import actor.Writer;

public class MainClient {
    public static void main(String[] args){
        Writer socket = ActorSystem.actorSelection("localhost",8080,false);
        socket.tell("Viktor",socket.self());
    }

}
