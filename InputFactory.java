package presentation;
import java.util.Scanner;
import domain.Prescription;
import domain.Doctor;
import domain.Patient;
public abstract class InputFactory {
    protected final Scanner sc;
    public InputFactory(Scanner sc) {
        this.sc = sc;
    }
    public abstract Prescription getPrescription();
    public abstract Patient getPatient();
    public abstract Doctor getDoctor();
}
