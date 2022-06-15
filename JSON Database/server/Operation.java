package server;

import server.model.Response;

@FunctionalInterface
public interface Operation {
    Response execute();
}