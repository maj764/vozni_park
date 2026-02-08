package si.majkovac.client.ui;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import si.majkovac.client.dto.UporabnikDto;
import si.majkovac.client.net.ApiClient;

public class MainView extends BorderPane {

    public MainView(ApiClient api, UporabnikDto user) {
        TabPane tabs = new TabPane();

        Tab tVozila = new Tab("Vozila", new VozilaTab(api, user));
        tVozila.setClosable(false);

        // Later you add:
        // Tab tServisi = new Tab("Servisi", new ServisiTab(api, user));
        // Tab tStroski = new Tab("Stroški", new StroskiTab(api, user));
        // Tab tLogs = new Tab("Logs", new LogsTab(api));

        tabs.getTabs().addAll(tVozila);

        setCenter(tabs);
    }
}
