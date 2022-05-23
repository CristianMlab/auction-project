package csv.services;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditService {
    private static AuditService instance = null;

    public static AuditService getInstance() {
        if (instance == null)
            instance = new AuditService();
        return instance;
    }

    private AuditService(){}

    public void log(String action, String filename) throws IOException{
        FileWriter out = new FileWriter(filename, true);
        out.append(action + "," + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n");
        out.flush();
    }
}
