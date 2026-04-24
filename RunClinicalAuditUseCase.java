package app;
import domain.*;
import infrastructure.AuditLogRepository;
import application.*;
import presentation.*;
public final class RunClinicalAuditUseCase {
    private final PrescriptionValidator validator;
    private final ClinicalRules rule;
    private final AuditEngine engine;
    private AuditLogRepository repo;
    public RunClinicalAuditUseCase(PrescriptionValidator validator, ClinicalRules rules, AuditEngine engine, AuditLogRepository repo) {
        this.validator = validator;
        this.rule = rules;
        this.engine = engine;
        this.repo = repo;
    }
    public AuditResult execute(InputFactory input) {
        Prescription prescription = input.getPrescription();
        Patient patient = input.getPatient();
        Doctor doctor = input.getDoctor();
        ValidationResult vr = validator.validate(prescription, patient.getAilments());
        if (!vr.isValid()) {
            return AuditResult.failure(vr.getReason());
        }
        double compliance = engine.compute(prescription, patient);
        AuditResult result = AuditResult.success(patient, doctor, prescription, compliance);
        repo.save(result, input.toString());
        return result;
    }
}