package application;
import java.util.ArrayList;
import java.util.List;
import domain.Patient;
import domain.Doctor;
import domain.Prescription;
public final class AuditResult {
    private final boolean success;
    private final List<String> messages;
    private final double compliance;
    private AuditResult(boolean success,
                        List<String> messages,
                        double compliance) {
        this.success = success;
        this.messages = messages;
        this.compliance = compliance;
    }
    public static AuditResult failure(String reason) {
        return new AuditResult(false,
                List.of(reason),
                0.0);
    }
    public static AuditResult success(Patient p, Doctor d, Prescription pr, double compliance) {
        List<String> msg = new ArrayList<>();
        msg.add("Patient: " + p.getName());
        msg.add("Doctor: " + d.getName());
        msg.add("Prescription OK");
        return new AuditResult(true, msg, compliance);
    }
    public boolean isSuccess() {
        return success;
    }
    public List<String> getMessages() {
        return messages;
    }
    public double getCompliance() {
        return compliance;
    }
}