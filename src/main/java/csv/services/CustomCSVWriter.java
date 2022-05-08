package csv.services;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomCSVWriter {
    public static CustomCSVWriter instance = null;

    public static CustomCSVWriter getInstance() {
        if (instance == null)
            instance = new CustomCSVWriter();
        return instance;
    }

    private CustomCSVWriter(){}

    public <T> void appendObject(Class<T> type, T data, String fileName) throws IOException {
        String d = data.toString();
        String[] separated = d.split(",");
        FileWriter output = new FileWriter(fileName, true);
        CSVWriter writer = new CSVWriter(output);
        writer.writeNext(separated);
        writer.close();
    }

    public <T> void writeAll(Class<T> type, List<T> data, String fileName, String header) throws IOException{
        List<String[]> dataStrings = new ArrayList<>();
        dataStrings.add(header.split(","));
        for (T obj: data) {
            dataStrings.add(obj.toString().split(","));
        }
        FileWriter output = new FileWriter(fileName);
        CSVWriter writer = new CSVWriter(output);
        writer.writeAll(dataStrings);
        writer.close();
    }
}
