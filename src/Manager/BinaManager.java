package Manager;

import Varliklar.Binalar.KlasikBinalar;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class BinaManager {
    private final ArrayList<KlasikBinalar> observableBinalar;

    // Constructor - Klasik binalar listesi ObservableList'e dönüştürülüyor
    public BinaManager(ArrayList<KlasikBinalar> klasikBinalar) {
        this.observableBinalar = klasikBinalar;
    }

    // Binaları listeleyen ve güncelleme işlemini yöneten method
    public void listeleVeGuncelle(Stage stage) {
        // Yeni sahne oluşturuluyor
        Stage stage1 = new Stage();
        stage1.setTitle("Bina Listesi ve Güncelleme");
        stage1.initModality(Modality.WINDOW_MODAL);
        stage1.initOwner(stage);
        stage1.setResizable(false);

        VBox vbox = new VBox(20); // Dikey düzen
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: #F0F0F0;");

        // Bina bilgilerinin listelendiği tablo oluşturuluyor
        TableView<KlasikBinalar> tableView = tabloOlustur();

        // ArrayList'i ObservableList olarak tabloya set ediyoruz
        tableView.setItems(FXCollections.observableArrayList(observableBinalar));

        Label lblStatus = new Label("Lütfen güncellemek istediğiniz binayı seçiniz.");
        lblStatus.setStyle("-fx-text-fill: #333; -fx-font-size: 14px;");

        // Güncelle düğmesi
        Button btnGuncelle = new Button("🔄 Güncelle");
        btnGuncelle.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");

        // Butonun aksiyonu
        btnGuncelle.setOnAction(event -> {
            KlasikBinalar secilenBina = tableView.getSelectionModel().getSelectedItem();
            if (secilenBina == null) {
                lblStatus.setText("❌ Lütfen bir bina seçin!");
                return;
            }
            // Güncelleme ekranını aç
            guncellemeEkrani(stage1, secilenBina, tableView);
        });

        // Arayüz elemanları VBox düzenine ekleniyor
        vbox.getChildren().addAll(tableView, lblStatus, btnGuncelle);

        // Sahne oluşturuluyor
        Scene scene = new Scene(vbox, 400, 400);
        stage1.setScene(scene);
        stage1.show();
    }

    // Bina tablosunu oluşturan method
    private TableView<KlasikBinalar> tabloOlustur() {
        TableView<KlasikBinalar> tableView = new TableView<>();
        tableView.setStyle("-fx-font-size: 14px;");

        TableColumn<KlasikBinalar, Integer> colId = new TableColumn<>("Bina ID");
        colId.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getId()));

        TableColumn<KlasikBinalar, String> colTur = new TableColumn<>("Bina Türü");
        colTur.setCellValueFactory(data -> data.getValue().turProperty());

        TableColumn<KlasikBinalar, Integer> colKapasite = new TableColumn<>("Kapasite");
        colKapasite.setCellValueFactory(data -> data.getValue().katSayisiProperty().asObject());

        // Sütunlar tabloya ekleniyor
        tableView.getColumns().addAll(colId, colTur, colKapasite);

        return tableView;
    }

    // Güncelleme ekranını açan ve güncellemeyi gerçekleştiren method
    private void guncellemeEkrani(Stage parentStage, KlasikBinalar secilenBina, TableView<KlasikBinalar> tableView) {
        // Yeni sahne
        Stage stage2 = new Stage();
        stage2.setTitle("Bina Güncelleme");
        stage2.initModality(Modality.WINDOW_MODAL);
        stage2.initOwner(parentStage);
        stage2.setResizable(false);

        VBox vbox = new VBox(15); // Dikey düzen
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: #F0F0F0;");

        // Etiketler ve metin alanları
        Label lblTur = new Label("Bina Türü:");
        TextField txtTur = new TextField(secilenBina.getBinaTuru());

        Label lblKapasite = new Label("Kapasite:");
        TextField txtKapasite = new TextField(String.valueOf(secilenBina.getKatSayisi()));

        // Kaydet düğmesi
        Button btnSave = new Button("💾 Kaydet");
        btnSave.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        // Düğme aksiyonu: Güncelle ve sahneyi kapat
        btnSave.setOnAction(event -> {
            try {
                // Yeni değerleri al ve modelde güncelle
                String yeniTur = txtTur.getText().trim();
                int yeniKapasite = Integer.parseInt(txtKapasite.getText().trim());

                secilenBina.setBinaTuru(yeniTur);
                secilenBina.setKatSayisi(yeniKapasite);

                // Tabloyu yenile
                tableView.refresh();

                // Güncelleme işleminden sonra bilgi mesajı ve sahneyi kapatma
                Alert bilgiMesaji = new Alert(Alert.AlertType.INFORMATION);
                bilgiMesaji.setTitle("Güncelleme Başarılı!");
                bilgiMesaji.setHeaderText(null);
                bilgiMesaji.setContentText("Bina bilgileri başarıyla güncellendi!");
                bilgiMesaji.showAndWait();

                // Güncelleme ekranını kapat
                stage2.close();
            } catch (NumberFormatException ex) {
                // Hatalı giriş yapıldıysa uyarı mesajı göster
                Alert hataMesaji = new Alert(Alert.AlertType.ERROR);
                hataMesaji.setTitle("Güncelleme Hatası");
                hataMesaji.setHeaderText(null);
                hataMesaji.setContentText("Lütfen geçerli bir kapasite giriniz.");
                hataMesaji.showAndWait();
            }
        });

        // VBox düzenine elemanları ekle
        vbox.getChildren().addAll(lblTur, txtTur, lblKapasite, txtKapasite, btnSave);

        // Sahne oluşturuluyor
        Scene scene = new Scene(vbox, 300, 200);
        stage2.setScene(scene);
        stage2.show();
    }

    // ArrayList olarak saklanan binaları döndürür
    public ArrayList<KlasikBinalar> getObservableBinalar() {
        return observableBinalar;
    }
}