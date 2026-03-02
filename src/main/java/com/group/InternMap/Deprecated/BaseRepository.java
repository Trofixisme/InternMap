package com.group.InternMap.Deprecated;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Deprecated
@SuppressWarnings("all")
public abstract class BaseRepository {
    protected final String path;

    //Constructor to create a data directory if it doesn't exist
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public BaseRepository(String path) {
        this.path = path;
        File file = new File("data");
        if(!file.exists()) file.mkdirs();
    }

    public void save(Object Object) {

        writeToFile(path, Object.toString(),true);
    }
    // Convert Object → text

    //Write to a file
    protected void writeToFile(String path, String line, boolean append) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, append))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Object> findAll() {
        List<String> lines = readFromFile(path);
        List<Object> Objects = new ArrayList<>();
        for(String line: lines){
            try {
                Objects.add(parseObject(line));
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return Objects;
    }

    //Parse text -> Object
    abstract Object parseObject(String line);

    private List<String> readFromFile(String path) {
        List<String> lines = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

}
