package infrastructure;
import java.io.FileWriter;
import java.io.IOException;
import application.AuditResult;
public class AuditLogRepository implements AuditRepository{
	private static final String File_path = "audit_log.jsonl";
	@Override
	public void save(AuditResult result, String rawInput) {
		try(FileWriter fw = new FileWriter(File_path, true)){
			String json = convertToJson(result, rawInput);
			fw.write(json + "\n");
		}
		catch(Exception e) {
			System.out.println("Error writing audit log: " + e.getMessage());
		}
	}
	private String convertToJson(AuditResult result, String rawInput) {
		return "{ \"result\": \"" + result.getCompliance() +
	               "\", \"details\": \"" + result.getMessages().toString() +
	               "\", \"input\": \"" + rawInput.replace("\n", " | ") + "\" }";
	}
}