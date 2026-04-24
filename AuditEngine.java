package app;
import domain.Prescription;
import domain.Patient;
public final class AuditEngine {
    public double compute(Prescription p, Patient patient) {
        double score = 0.0;
        if (patient.isValid()) score += 0.5;
        if (p.getDosage().getValue() > 0) score += 0.5; // ✅ FIXED
        return score;
    }
}