package domain;
import domain.ClinicalRules;
import domain.Dosage;
import domain.Medication;
public final class Prescription implements Prescribable {
    private final Dosage dosage;
    private final Medication details;
    public Prescription(Dosage dosage, Medication details) {
        this.dosage = dosage;
        this.details = details;
    }
    public Dosage getDosage() {
        return dosage;
    }
    public Medication getDetails() {
        return details;
    }
    public boolean containsMedication(String med) {
    	return details.getName().equalsIgnoreCase(med);
    }
}