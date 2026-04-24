package domain;
public final class Dosage {
    private final int value;
    public Dosage(int value) {
        if (value <= 0 || value > 1000)
            throw new IllegalArgumentException("Invalid dosage");
        this.value = value;
    }
    public int getValue() { return value; }
    public boolean isWithin(int min, int max) {
    	return value >= min && value <= max;
    }
}