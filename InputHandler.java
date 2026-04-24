package presentation;
import java.util.Scanner;
import domain.*;
public final class InputHandler extends InputFactory {
    public InputHandler(Scanner sc) {
        super(sc);
    }
    private int readInt() {
        while (!sc.hasNextInt()) {
            System.out.println("Enter valid number:");
            sc.next();
        }
        int val = sc.nextInt();
        sc.nextLine();
        return val;
    }
    private String readStr(String msg) {
        System.out.println(msg);
        return sc.nextLine().trim();
    }
    public final Prescription getPrescription() {
        System.out.println("Enter dosage:");
        int dose = readInt();
        String details = readStr("Enter details:");
        Dosage dosage = new Dosage(dose);
        Medication medication = new Medication(details);
        return new Prescription(dosage, medication); // ✅ FIXED
    }
    public final Patient getPatient() {
        String name = readStr("Enter name:");
        String ssn = readStr("Enter SSN:");
        String ailment = readStr("Enter ailment:");
        return new Patient(name, ssn, ailment);
    }
    public final Doctor getDoctor() {
        String name = readStr("Enter doctor name:");
        String spec = readStr("Enter specialization:");
        return new Doctor(name, spec);
    }
}