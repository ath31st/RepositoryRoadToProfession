package server.io;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import server.model.RecordInBase;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FileOperator implements FileOperations<RecordInBase> {

    private final String filename;
    private final Gson gson;
    private final ReadWriteLock lock;

    public FileOperator(String filename, Gson gson) {
        this.filename = filename;
        this.gson = gson;
        this.lock = new ReentrantReadWriteLock();

        try {
            File file = new File(filename);
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }
            new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<RecordInBase> read() {
        List<RecordInBase> list = null;
        Type type = new TypeToken<ArrayList<RecordInBase>>(){}.getType();
        lock.readLock().lock();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename))) {
            list = gson.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        lock.readLock().unlock();
        return list == null ? new ArrayList<>() : list;
    }

    @Override
    public void save(List<RecordInBase> list) {
        lock.writeLock().lock();
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename));) {
            gson.toJson(list, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        lock.writeLock().unlock();
    }
}