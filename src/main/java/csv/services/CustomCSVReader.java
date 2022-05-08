package csv.services;

import java.io.FileNotFoundException;
import java.util.List;
import java.io.FileReader;
import com.opencsv.bean.CsvToBeanBuilder;


public class CustomCSVReader {
    public static CustomCSVReader instance = null;

    public static CustomCSVReader getInstance() {
        if (instance == null)
            instance = new CustomCSVReader();
        return instance;
    }

    private CustomCSVReader(){}

    public <T> List<T> readAll(Class<T> type, String fileName) throws FileNotFoundException {
        List<T> beans = new CsvToBeanBuilder(new FileReader(fileName))
                .withType(type)
                .build()
                .parse();
        return beans;
    }

}
