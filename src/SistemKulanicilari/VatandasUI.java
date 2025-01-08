package SistemKulanicilari;

import Manager.EnergyManager;
import Manager.TrafficManager;
import Manager.WaterManager;
import Varliklar.Araclar.Klasik;
import Varliklar.EnerjiKaynaklari.EnerjiKaynaklari;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class VatandasUI {
    private final Stage pr;
    private final Vatandas vatandas; // Vatandaş bilgileri
    private final TrafficManager trafficManager;
    private final EnergyManager energyManager;
    private final WaterManager waterManager;

    public VatandasUI(Stage pr, Vatandas vatandas, TrafficManager trafficManager, EnergyManager energyManager, WaterManager waterManager) {
        this.pr = pr;
        this.vatandas = vatandas;
        this.trafficManager = trafficManager;
        this.energyManager = energyManager;
        this.waterManager = waterManager;
    }

    public void showScreen(ArrayList<Klasik> aracList, ArrayList<EnerjiKaynaklari>
                           enerji) {
        Stage stage = new Stage(); // Yeni bir sahne oluştur

        // Ana düzen
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        // Vatandaş bilgilerini göster
        Label lblTitle = new Label("Vatandaş Paneli");
        lblTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        Label lblName = new Label("Ad: " + vatandas.getAd());
        Label lblLastName = new Label("Soyad: " + vatandas.getSoyAd());
        Label lblNumber = new Label("Numara: " + vatandas.getNumara());

        // Trafik Kontrol Butonu
        Button btnTrafikKontrol = new Button("Trafik Kontrolü");
        btnTrafikKontrol.setOnAction(event -> {
            try {
                vatandas.trafikKontrol(trafficManager, aracList,pr);
                showAlert("Trafik Kontrolü", "Trafik kontrol işlemi başarıyla tamamlandı.", Alert.AlertType.INFORMATION);
            } catch (InterruptedException e) {
                showAlert("Hata", "Trafik kontrol işlemi sırasında bir hata oluştu: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        });

        // Enerji Raporu Butonu
        Button btnEnerjiRaporu = new Button("Enerji Raporu Göster");
        btnEnerjiRaporu.setOnAction(event -> {
            vatandas.enerjiRaporuGoster(pr,energyManager,enerji);
            showAlert("Enerji Raporu", "Enerji raporu başarıyla gösterildi.", Alert.AlertType.INFORMATION);
        });

        // Su Raporu Butonu
        Button btnSuRaporu = new Button("Su Raporu Göster");
        btnSuRaporu.setOnAction(event -> {
            vatandas.suRaporuGoster(waterManager);
            showAlert("Su Raporu", "Su raporu başarıyla gösterildi.", Alert.AlertType.INFORMATION);
        });

        // Geri Dön Butonu
        Button btnClose = new Button("Kapat");
        btnClose.setOnAction(event -> stage.close());

        // Tüm butonları yerleştirme
        VBox buttonBox = new VBox(10, btnTrafikKontrol, btnEnerjiRaporu, btnSuRaporu, btnClose);
        buttonBox.setAlignment(Pos.CENTER);

        // Tüm bileşenleri kök düzenine ekleme
        root.getChildren().addAll(lblTitle, lblName, lblLastName, lblNumber, buttonBox);

        // Sahne oluşturma ve gösterme
        Scene scene = new Scene(root, 400, 300);
        stage.setTitle("Vatandaş Paneli");
        stage.setScene(scene);
        stage.show();
    }

    // Uyarı göstermek için helper metod
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void araclarimiGoster(Vatandas vatandas, ArrayList<Klasik> aracList){

    }
}