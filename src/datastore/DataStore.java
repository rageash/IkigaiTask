package datastore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import exception.FilePathNotFound;
import exception.FileReadWriteException;
import exception.ObjectNotFoundException;

public class DataStore {

    private static DataStore instance = null;
    private static String dirPath = null;
    private static String filePath = null;

    private DataStore() {}

    public static DataStore getInstance() {
        if (dirPath == null || filePath == null) {
            throw new FilePathNotFound();
        }

        if (instance == null) {
            instance = new DataStore();
        }

        return instance;
    }

    public boolean writeData(List<? extends Object> datas) throws FileNotFoundException, FileReadWriteException {

        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        // Write data using object stream
        try (FileOutputStream fileOutputStream = new FileOutputStream(dir + "\\" + filePath);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(new ArrayList<>(datas));
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new FileReadWriteException();
        }

        return false;
    }

    public List<? extends Object> readData() throws FileReadWriteException, ObjectNotFoundException {

        // Read data using object stream
        try (FileInputStream fileInputStream = new FileInputStream(dirPath + "\\" + filePath);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            Object object = objectInputStream.readObject();
            return (ArrayList<?>) object;
        } catch (IOException e) {
            throw new FileReadWriteException();
        } catch (ClassNotFoundException e) {
            throw new ObjectNotFoundException();
        }
    }

    public static void setFilePath(String filePath) {
        DataStore.filePath = filePath;
    }

    public static void setDirPath(String dirPath) {
        DataStore.dirPath = dirPath;
    }
}
