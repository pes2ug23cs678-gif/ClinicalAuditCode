package FrontEnd;
public final class DummyInput{
    private String id;
    private String details;
    // This constructor must exist for the GUI code to work!
    public DummyInput(String id, String details) {
        this.id = id;
        this.details = details;
    }
    public String getInput() {
        return details; 
    }
}