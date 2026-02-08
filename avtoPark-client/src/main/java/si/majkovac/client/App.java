package si.majkovac.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import si.majkovac.client.config.AppConfig;
import si.majkovac.client.net.ApiClient;
import si.majkovac.client.ui.LoginView;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        ApiClient api = new ApiClient(AppConfig.BASE_URL);

        LoginView login = new LoginView(api, uporabnik -> {
            // after login success -> open main window
            stage.setScene(new Scene(new si.majkovac.client.ui.MainView(api, uporabnik), 1100, 700));
            stage.setTitle("Vozni park - Maj Kovač");
        });

        stage.setScene(new Scene(login, 450, 300));
        stage.setTitle("Prijava - Vozni park");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
