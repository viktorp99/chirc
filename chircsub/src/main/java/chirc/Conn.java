package chirc;

import actor.Writer;

public interface Conn extends Runnable {
    Writer getWriter();
}
