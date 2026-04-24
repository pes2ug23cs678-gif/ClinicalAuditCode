package domain;
import domain.ValidationResult;
import domain.Prescription;
public final class PrescriptionValidator {
    public ValidationResult validate(Prescription p, String ailment) {
        if (!ClinicalRules.isDosageValid(p.getDosage().getValue())) {
            return ValidationResult.fail("Invalid dosage");
        }
        return ValidationResult.ok();
    }
}