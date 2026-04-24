package infrastructure;
import java.io.FileWriter;
import java.io.IOException;
public class ReportFileWriter {
    public void saveToFile(String report, String auditId) {
        try (FileWriter fw = new FileWriter("report_" + auditId + ".txt")) {
            fw.write(report);
        } catch (IOException e) {
            System.out.println("Error saving report: " + e.getMessage());
        }
    }
}