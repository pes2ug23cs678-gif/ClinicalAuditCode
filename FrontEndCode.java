package FrontEnd;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.effect.DropShadow;
import java.util.Scanner;
// Backend imports
import app.RunClinicalAuditUseCase;
import app.AuthUseCase;
import app.AuditEngine;
import infrastructure.*;
import application.AuditResult;
import domain.*;
import presentation.InputHandler;
import presentation.ReportGeneration;
public final class FrontEndCode extends Application {
    private AuthUseCase authUseCase;
    private SessionManager sessionManager;
    // Professional Color Palette
    private final String PRIMARY_BLUE = "#1976D2";
    private final String SUCCESS_GREEN = "#2E7D32";
    private final String BACKGROUND_LIGHT = "#F5F7FA";
    private final String CARD_WHITE = "#FFFFFF";
    @Override
    public final void init() {
        authUseCase = new AuthUseCase(new AuthService());
        sessionManager = new SessionManager();
    }
    @Override
    public final void start(Stage stage) {
        showLoginScreen(stage);
    }
    private final void showLoginScreen(Stage stage) {
        stage.setTitle("Clinical Audit System - Secure Login");
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: " + BACKGROUND_LIGHT + ";");
        root.setPadding(new Insets(40));
        VBox loginCard = new VBox(15);
        loginCard.setMaxWidth(350);
        loginCard.setPadding(new Insets(30));
        loginCard.setStyle("-fx-background-color: " + CARD_WHITE + "; -fx-background-radius: 10; -fx-border-radius: 10;");
        loginCard.setEffect(new DropShadow(10, Color.LIGHTGREY));
        Label title = new Label("HealthAudit Pro");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setTextFill(Color.web(PRIMARY_BLUE));
        TextField userField = new TextField();
        userField.setPromptText("Username");
        userField.setPrefHeight(35);
        PasswordField passField = new PasswordField();
        passField.setPromptText("Password");
        passField.setPrefHeight(35);
        Button loginBtn = new Button("SIGN IN");
        loginBtn.setMaxWidth(Double.MAX_VALUE);
        loginBtn.setPrefHeight(40);
        loginBtn.setStyle("-fx-background-color: " + PRIMARY_BLUE + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
        Label status = new Label();
        loginBtn.setOnAction(e -> {
            if (authUseCase.login(userField.getText(), passField.getText())) {
                Session session = new Session(userField.getText(), authUseCase.isSuspicious(userField.getText()));
                sessionManager.add(session);
                showAuditScreen(stage, session);
            } else {
                status.setText("Invalid credentials. Try again.");
                status.setTextFill(Color.FIREBRICK);
            }
        });
        loginCard.getChildren().addAll(title, new Label("Please sign in to continue"), userField, passField, loginBtn, status);
        root.getChildren().add(loginCard);
        stage.setScene(new Scene(root, 450, 500));
        stage.show();
    }
    private final void showAuditScreen(Stage stage, Session session) {
        stage.setTitle("Clinical Dashboard - " + session.getUsername());
        // Main Layout
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + BACKGROUND_LIGHT + ";");
        // Top Header
        HBox header = new HBox();
        header.setPadding(new Insets(15, 25, 15, 25));
        header.setStyle("-fx-background-color: " + PRIMARY_BLUE + ";");
        Label headerTitle = new Label("Clinical Audit Dashboard");
        headerTitle.setTextFill(Color.WHITE);
        headerTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Button logoutBtn = new Button("Logout");
        logoutBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-border-color: white; -fx-border-radius: 5;");
        logoutBtn.setOnAction(e -> showLoginScreen(stage));
        header.getChildren().addAll(headerTitle, spacer, logoutBtn);
        root.setTop(header);
        // Content Area
        VBox content = new VBox(20);
        content.setPadding(new Insets(25));
        // Form Section (White Card)
        GridPane formGrid = new GridPane();
        formGrid.setHgap(20);
        formGrid.setVgap(15);
        formGrid.setPadding(new Insets(20));
        formGrid.setStyle("-fx-background-color: " + CARD_WHITE + "; -fx-background-radius: 8;");
        formGrid.setEffect(new DropShadow(5, Color.LIGHTGREY));
        // Column Constraints for clean alignment
        ColumnConstraints col1 = new ColumnConstraints(120);
        ColumnConstraints col2 = new ColumnConstraints(200);
        formGrid.getColumnConstraints().addAll(col1, col2, col1, col2);
        // Row 1: Patient
        TextField pName = createStyledField("Patient Full Name");
        TextField pSSN = createStyledField("XXXX-XXXX-0000");
        addFormField(formGrid, "Patient Name", pName, 0, 0);
        addFormField(formGrid, "Patient SSN", pSSN, 2, 0);
        // Row 2: Medication
        TextField mName = createStyledField("e.g. Amoxicillin");
        TextField dose = createStyledField("Dosage (mg)");
        addFormField(formGrid, "Medication", mName, 0, 1);
        addFormField(formGrid, "Dosage", dose, 2, 1);
        // Row 3: Details
        TextField pAilment = createStyledField("Ailment");
        TextField dName = createStyledField("Attending Physician");
        addFormField(formGrid, "Ailment", pAilment, 0, 2);
        addFormField(formGrid, "Doctor", dName, 2, 2);
        TextField dSpec = createStyledField("Specialization");
        addFormField(formGrid, "Specialty", dSpec, 0, 3);
        // Action Button
        Button runBtn = new Button("GENERATE COMPLIANCE REPORT");
        runBtn.setPrefHeight(45);
        runBtn.setMaxWidth(Double.MAX_VALUE);
        runBtn.setStyle("-fx-background-color: " + SUCCESS_GREEN + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5; -fx-cursor: hand;");
        // Report Areadoctor
        TextArea logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setPromptText("Audit report details will appear here...");
        logArea.setStyle("-fx-font-family: 'Consolas'; -fx-font-size: 13;");
        runBtn.setOnAction(e -> {
            if (dose.getText().isEmpty() || pSSN.getText().isEmpty()) {
                logArea.setText("CRITICAL ERROR: Please fill in mandatory clinical fields.");
                return;
            }
            String combinedInput = dose.getText() + "\n" + mName.getText() + "\n" +
                                   pName.getText() + "\n" + pSSN.getText() + "\n" +
                                   pAilment.getText() + "\n" + dName.getText() + "\n" + dSpec.getText();
            Scanner guiScanner = new Scanner(combinedInput);
            InputHandler handler = new InputHandler(guiScanner);
            try {
                RunClinicalAuditUseCase auditUseCase = new RunClinicalAuditUseCase(
                        new PrescriptionValidator(), new ClinicalRules(), new AuditEngine(), new AuditLogRepository());
                AuditResult result = auditUseCase.execute(handler);
                ReportGeneration generator = new ReportGeneration();
                String report = generator.generate(
                    result,
                    pName.getText(),
                    pSSN.getText(),
                    pAilment.getText(),
                    mName.getText(),
                    Integer.parseInt(dose.getText()),
                    dName.getText(),
                    dSpec.getText(),
                    session.getUsername()
                );
                String auditID = java.util.UUID.randomUUID().toString();
                ReportFileWriter fw = new ReportFileWriter();
                fw.saveToFile(report, auditID);
                logArea.setText(report);
            } catch (Exception ex) {
                logArea.setText("SYSTEM ERROR: " + ex.getMessage());
            } finally {
                guiScanner.close();
            }
        });
        content.getChildren().addAll(formGrid, runBtn, logArea);
        root.setCenter(content);
        stage.setScene(new Scene(root, 800, 700));
    }
    private final TextField createStyledField(String prompt) {
        TextField f = new TextField();
        f.setPromptText(prompt);
        f.setPrefHeight(35);
        f.setStyle("-fx-background-color: #FAFAFA; -fx-border-color: #E0E0E0; -fx-border-radius: 4;");
        return f;
    }
    private final void addFormField(GridPane grid, String label, TextField field, int col, int row) {
        Label l = new Label(label);
        l.setFont(Font.font("System", FontWeight.BOLD, 12));
        grid.add(l, col, row);
        grid.add(field, col + 1, row);
    }
    public static void main(String[] args) {
        launch(args);
    }
}