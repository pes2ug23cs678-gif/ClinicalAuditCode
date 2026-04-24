package infrastructure;
import application.AuditResult;
public interface AuditRepository {
	void save(AuditResult result, String rawInput);
}