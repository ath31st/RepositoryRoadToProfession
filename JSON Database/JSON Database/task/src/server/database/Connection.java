package server.database;


import server.model.Response;
import server.model.Task;

/**
 * Basic database operations
 */
public interface Connection {
    Response get(Task task);
    Response set(Task task);
    Response delete(Task task);
}