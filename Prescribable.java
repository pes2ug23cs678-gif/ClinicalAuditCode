package domain;
interface Prescribable {
    Dosage getDosage();
    Medication getDetails();
    boolean containsMedication(String med);
}