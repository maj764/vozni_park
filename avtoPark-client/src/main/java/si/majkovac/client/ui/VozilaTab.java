package si.majkovac.client.ui;

import com.fasterxml.jackson.core.type.TypeReference;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import si.majkovac.client.dto.*;
import si.majkovac.client.net.ApiClient;

import java.util.List;

public class VozilaTab extends BorderPane {

    private final ApiClient api;
    private final UporabnikDto user;
    private final TableView<VoziloDto> table = new TableView<>();

    // form fields
    private final TextField tfReg = new TextField();
    private final TextField tfLetnik = new TextField();
    private final TextField tfKw = new TextField();
    private final TextField tfKm = new TextField();
    private final TextField tfModel = new TextField();
    private final TextField tfZnamka = new TextField();
    private final TextField tfOpis = new TextField();
    private final TextField tfOdgovorniUserId = new TextField();

    private Integer editingId = null;

    public VozilaTab(ApiClient api, UporabnikDto user) {
        this.api = api;
        this.user = user;

        setPadding(new Insets(10));
        setCenter(buildCenter());
        setRight(buildForm());

        refresh();
    }

    private VBox buildCenter() {
        VBox box = new VBox(10);

        Label title = new Label("Seznam vozil");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // table columns
        TableColumn<VoziloDto, Integer> cId = new TableColumn<>("ID");
        cId.setCellValueFactory(d -> new javafx.beans.property.SimpleIntegerProperty(d.getValue().id()).asObject());

        TableColumn<VoziloDto, String> cReg = new TableColumn<>("Registrska");
        cReg.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().registrska()));

        TableColumn<VoziloDto, String> cZnamka = new TableColumn<>("Znamka");
        cZnamka.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().znamka()));

        TableColumn<VoziloDto, String> cModel = new TableColumn<>("Model");
        cModel.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().model()));

        TableColumn<VoziloDto, Integer> cKm = new TableColumn<>("Km");
        cKm.setCellValueFactory(d -> new javafx.beans.property.SimpleIntegerProperty(d.getValue().kilometri()).asObject());

        table.getColumns().addAll(cId, cReg, cZnamka, cModel, cKm);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        HBox buttons = new HBox(10);
        Button btnRefresh = new Button("Osveži");
        Button btnEdit = new Button("Uredi");
        Button btnDelete = new Button("Briši");

        btnRefresh.setOnAction(e -> refresh());
        btnEdit.setOnAction(e -> loadSelectedToForm());
        btnDelete.setOnAction(e -> deleteSelected());

        buttons.getChildren().addAll(btnRefresh, btnEdit, btnDelete);

        box.getChildren().addAll(title, table, buttons);
        return box;
    }

    private VBox buildForm() {
        VBox form = new VBox(8);
        form.setPadding(new Insets(10));
        form.setPrefWidth(340);
        form.setStyle("-fx-border-color: #ddd; -fx-border-width: 1;");

        Label title = new Label("Vnos / urejanje");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        tfReg.setPromptText("Registrska");
        tfLetnik.setPromptText("Letnik (npr. 2020)");
        tfKw.setPromptText("kW (lahko prazno)");
        tfKm.setPromptText("Kilometri");
        tfZnamka.setPromptText("Znamka");
        tfModel.setPromptText("Model");
        tfOpis.setPromptText("Opis (lahko prazno)");
        tfOdgovorniUserId.setPromptText("Odgovorni uporabnik ID (lahko prazno)");

        Button btnSave = new Button("Shrani");
        Button btnClear = new Button("Počisti");

        Label msg = new Label();

        btnSave.setOnAction(e -> {
            msg.setText("");
            try {
                if (editingId == null) {
                    // CREATE
                    VoziloCreateRequest req = new VoziloCreateRequest(
                            tfReg.getText(),
                            Integer.parseInt(tfLetnik.getText()),
                            parseIntOrNull(tfKw.getText()),
                            Integer.parseInt(tfKm.getText()),
                            emptyToNull(tfOpis.getText()),
                            parseIntOrNull(tfOdgovorniUserId.getText()),
                            tfModel.getText(),
                            tfZnamka.getText()
                    );
                    api.postNoBody("/api/vozila", req);
                    msg.setStyle("-fx-text-fill: green;");
                    msg.setText("Vozilo dodano.");
                } else {
                    // UPDATE
                    VoziloUpdateRequest req = new VoziloUpdateRequest(
                            tfReg.getText(),
                            Integer.parseInt(tfLetnik.getText()),
                            parseIntOrNull(tfKw.getText()),
                            Integer.parseInt(tfKm.getText()),
                            emptyToNull(tfOpis.getText()),
                            parseIntOrNull(tfOdgovorniUserId.getText()),
                            tfModel.getText(),
                            tfZnamka.getText()
                    );
                    api.put("/api/vozila/" + editingId, req);
                    msg.setStyle("-fx-text-fill: green;");
                    msg.setText("Vozilo posodobljeno.");
                }

                clearForm();
                refresh();
            } catch (Exception ex) {
                msg.setStyle("-fx-text-fill: red;");
                msg.setText("Napaka: preveri podatke (številke, prazna polja...).");
            }
        });

        btnClear.setOnAction(e -> {
            clearForm();
            msg.setText("");
        });

        form.getChildren().addAll(
                title,
                new Label("Registrska"), tfReg,
                new Label("Letnik"), tfLetnik,
                new Label("kW"), tfKw,
                new Label("Kilometri"), tfKm,
                new Label("Znamka"), tfZnamka,
                new Label("Model"), tfModel,
                new Label("Opis"), tfOpis,
                new Label("Odgovorni uporabnik ID"), tfOdgovorniUserId,
                new HBox(10, btnSave, btnClear),
                msg
        );

        return form;
    }

    private void refresh() {
        // vozila_list returns list - your backend should expose GET /api/vozila
        List<VoziloDto> data = api.get("/api/vozila", new TypeReference<>() {});
        table.setItems(FXCollections.observableArrayList(data));
    }

    private void loadSelectedToForm() {
        VoziloDto v = table.getSelectionModel().getSelectedItem();
        if (v == null) return;

        editingId = v.id();
        tfReg.setText(v.registrska());
        tfLetnik.setText(String.valueOf(v.letnik()));
        tfKw.setText(v.kw() == null ? "" : String.valueOf(v.kw()));
        tfKm.setText(String.valueOf(v.kilometri()));
        tfZnamka.setText(v.znamka());
        tfModel.setText(v.model());
        tfOpis.setText(v.opis() == null ? "" : v.opis());
        tfOdgovorniUserId.setText(v.odgovorniUporabnikId() == null ? "" : String.valueOf(v.odgovorniUporabnikId()));
    }

    private void deleteSelected() {
        VoziloDto v = table.getSelectionModel().getSelectedItem();
        if (v == null) return;

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Potrditev");
        confirm.setHeaderText("Brišem vozilo " + v.registrska());
        confirm.setContentText("Si prepričan?");
        if (confirm.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) return;

        api.delete("/api/vozila/" + v.id());
        refresh();
    }

    private void clearForm() {
        editingId = null;
        tfReg.clear();
        tfLetnik.clear();
        tfKw.clear();
        tfKm.clear();
        tfModel.clear();
        tfZnamka.clear();
        tfOpis.clear();
        tfOdgovorniUserId.clear();
    }

    private static Integer parseIntOrNull(String s) {
        if (s == null) return null;
        String t = s.trim();
        if (t.isEmpty()) return null;
        return Integer.parseInt(t);
    }

    private static String emptyToNull(String s) {
        if (s == null) return null;
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }
}
