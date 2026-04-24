package app;
import presentation.OutputService;
import application.AuditResult;
public final class OutputPresenter {
    private final OutputService output;
    public OutputPresenter(OutputService output) {
        this.output = output;
    }
    public void present(AuditResult result) {
        output.display(result.getMessages());
    }
}