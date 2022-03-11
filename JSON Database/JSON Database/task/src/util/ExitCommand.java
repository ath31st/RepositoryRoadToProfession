package util;

import server.Server;

public class ExitCommand implements Command{

    @Override
    public void execute() {
        Server.setIsServerActive(false);
    }
}
