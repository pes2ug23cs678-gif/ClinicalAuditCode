package domain;
public final class ClinicalRules {
    public static boolean isDosageValid(int dose) {
        return dose > 0 && dose <= 1000;
    }
}