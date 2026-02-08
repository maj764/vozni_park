package si.majkovac.client.ui;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import si.majkovac.client.dto.PrijavaRequest;
import si.majkovac.client.dto.UporabnikDto;
import si.majkovac.client.net.ApiClient;

import java.util.function.Consumer;

public class LoginView extends VBox {

    public LoginView(ApiClient api, Consumer<UporabnikDto> onSuccess) {
        setPadding(new Insets(20));
        setSpacing(12);

        Label title = new Label("Prijava");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TextField email = new TextField();
        email.setPromptText("email");

        PasswordField pass = new PasswordField();
        pass.setPromptText("geslo");

        Button loginBtn = new Button("Prijava");
        Label msg = new Label();

        loginBtn.setOnAction(e -> {
            msg.setText("");
            try {
                UporabnikDto u = api.post("/api/uporabniki/prijava",
                        new PrijavaRequest(email.getText(), pass.getText()),
                        UporabnikDto.class);

                onSuccess.accept(u);
            } catch (Exception ex) {
                msg.setStyle("-fx-text-fill: red;");
                msg.setText("Napačen email ali geslo.");
            }
        });

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.addRow(0, new Label("Email:"), email);
        form.addRow(1, new Label("Geslo:"), pass);

        getChildren().addAll(title, form, loginBtn, msg);
    }
}
