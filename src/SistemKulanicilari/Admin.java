package SistemKulanicilari;
import Manager.BinaManager;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import Manager.TrafficManager;
import Manager.WaterManager;
import Manager.EnergyManager;
import Varliklar.Araclar.Klasik;
import Varliklar.Araclar.Elektrikli;
import Varliklar.Binalar.KlasikBinalar;
import Varliklar.Binalar.AkilliBinalar;
import Varliklar.EnerjiKaynaklari.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Optional;

public class Admin {
    private String isim;
    private String soyAd;
    private int numara;
    private int sifre;

    // Yapıcı metod
    public Admin(String isim, String soyAd, int numara, int sifre) {
        this.isim = isim;
        this.soyAd = soyAd;
        this.numara = numara;
        this.sifre = sifre;
    }

    // Getter ve Setter Metotlar
    public int getSifre() {
        return sifre;
    }

    public void setSifre(int sifre) {
        this.sifre = sifre;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getSoyAd() {
        return soyAd;
    }

    public void setSoyAd(String soyAd) {
        this.soyAd = soyAd;
    }

    public int getNumara() {
        return numara;
    }

    public void setNumara(int numara) {
        this.numara = numara;
    }
    //javafx ile yeni varlık ekleme yeri
    public void yeniVarlikEkleme(Stage primaryStage, EnergyManager energyManager, TrafficManager t,
                                 ArrayList<Klasik> klasiks, ArrayList<EnerjiKaynaklari> enerji,
                                 ArrayList<KlasikBinalar> binalars) {
        // Yeni bir sahne oluşturma
        Stage stage = new Stage();
        stage.setTitle("Varlık Ekleme Paneli");
        stage.initOwner(primaryStage);        // Parent sahne
        stage.initModality(Modality.WINDOW_MODAL); // Modality ayarı (ana sahneye bağımlı)

        // Dikey düzen (layout)
        VBox layout = new VBox(15); // Butonlar arasında 15px boşluk
        layout.setPadding(new Insets(30));  // Kenarlardan 30px boşluk
        layout.setAlignment(Pos.CENTER);    // Elemanların ortada hizalanması
        layout.setStyle("-fx-background-color: #f9f9f9; -fx-border-color: #d3d3d3; -fx-border-radius: 10; -fx-background-radius: 10;");

        // Başlık oluşturma
        Label baslikLabel = new Label("Yeni Varlık Ekleme");
        baslikLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #4caf50;");

        // "Enerji Ekleme" butonu
        Button btnEkle = new Button("Enerji Ekleme");
        btnEkle.setStyle("-fx-font-size: 15px; -fx-padding: 10px 20px; -fx-background-color: #4caf50; -fx-text-fill: white; -fx-border-radius: 5; -fx-background-radius: 5;");
        btnEkle.setOnMouseEntered(e -> btnEkle.setStyle("-fx-background-color: #45a049; -fx-font-size: 15px; -fx-text-fill: white;"));
        btnEkle.setOnMouseExited(e -> btnEkle.setStyle("-fx-background-color: #4caf50; -fx-font-size: 15px; -fx-text-fill: white;"));
        btnEkle.setOnAction(actionEvent -> {
            yeniEnerjiEkle(primaryStage, energyManager, enerji);
        });

        // "Araç Ekleme" butonu
        Button btnAracEkle = new Button("Araç Ekleme");
        btnAracEkle.setStyle("-fx-font-size: 15px; -fx-padding: 10px 20px; -fx-background-color: #2196f3; -fx-text-fill: white; -fx-border-radius: 5; -fx-background-radius: 5;");
        btnAracEkle.setOnMouseEntered(e -> btnAracEkle.setStyle("-fx-background-color: #1e88e5; -fx-text-fill: white;"));
        btnAracEkle.setOnMouseExited(e -> btnAracEkle.setStyle("-fx-background-color: #2196f3; -fx-text-fill: white;"));
        btnAracEkle.setOnAction(actionEvent -> {
            t.AracEkleme(primaryStage, klasiks);
        });

        // "Bina Ekleme" butonu
        Button btnBinaEkle = new Button("Bina Ekleme");
        btnBinaEkle.setStyle("-fx-font-size: 15px; -fx-padding: 10px 20px; -fx-background-color: #ff9800; -fx-text-fill: white; -fx-border-radius: 5; -fx-background-radius: 5;");
        btnBinaEkle.setOnMouseEntered(e -> btnBinaEkle.setStyle("-fx-background-color: #f57c00; -fx-text-fill: white;"));
        btnBinaEkle.setOnMouseExited(e -> btnBinaEkle.setStyle("-fx-background-color: #ff9800; -fx-text-fill: white;"));
        btnBinaEkle.setOnAction(actionEvent -> {
            binaYonetimEkrani(primaryStage, binalars);
        });

        // Geri dönüş/kapatma butonu
        Button btnClose = new Button("Geri Dön");
        btnClose.setStyle("-fx-font-size: 15px; -fx-padding: 10px 20px; -fx-background-color: #f44336; -fx-text-fill: white; -fx-border-radius: 5; -fx-background-radius: 5;");
        btnClose.setOnMouseEntered(e -> btnClose.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white;"));
        btnClose.setOnMouseExited(e -> btnClose.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;"));
        btnClose.setOnAction(actionEvent -> {
            stage.close(); // Sahneyi kapat
        });

        // Butonları düzen içerisine ekleme
        layout.getChildren().addAll(baslikLabel, btnEkle, btnAracEkle, btnBinaEkle, btnClose);

        // Sahne ve düzen oluşturma
        Scene scene = new Scene(layout, 450, 350); // 450x350 boyutlandırma ayarlandı
        stage.setScene(scene);

        // Sahneyi göster
        stage.showAndWait();
    }
    public void binaYonetimEkrani(Stage primaryStage, ArrayList<KlasikBinalar> binalar) {
        Stage stage = new Stage();
        stage.setTitle("Bina Yönetimi");
        stage.initOwner(primaryStage);
        stage.initModality(Modality.WINDOW_MODAL);

        // Solda Bina Listesi Tablosu
        TableView<KlasikBinalar> tableView = new TableView<>();
        TableColumn<KlasikBinalar, String> columnBinaTur = new TableColumn<>("Bina Türü");
        columnBinaTur.setCellValueFactory(new PropertyValueFactory<>("binaTuru")); // Getter ile eşleşmeli

        TableColumn<KlasikBinalar, Integer> columnKapasite = new TableColumn<>("Kat Sayısı");
        columnKapasite.setCellValueFactory(new PropertyValueFactory<>("katSayisi"));

        TableColumn<KlasikBinalar, Integer> columnYuzde = new TableColumn<>("Enerji Yüzdesi");
        columnYuzde.setCellValueFactory(new PropertyValueFactory<>("enerjiVerimliligiYuzdesi"));

        tableView.getColumns().addAll(columnBinaTur, columnKapasite, columnYuzde);

        // Listenin güncellenmesi
        ObservableList<KlasikBinalar> observableList = FXCollections.observableArrayList(binalar);
        tableView.setItems(observableList);

        // Sağ tarafta Bina Özellikleri ve Düzenleme Alanı
        VBox rightPane = new VBox(10);
        rightPane.setPadding(new Insets(10));
        rightPane.setAlignment(Pos.CENTER_LEFT);

        Label lblTitle = new Label("Bina Bilgileri");
        lblTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        TextField txtBinaid = new TextField();
        txtBinaid.setPromptText("Bina ID");

        TextField txtBinaAdi = new TextField();
        txtBinaAdi.setPromptText("Bina Türü");

        TextField txtBinaKapasite = new TextField();
        txtBinaKapasite.setPromptText("Kat Sayısı");

        TextField txtBinaYuzde = new TextField();
        txtBinaYuzde.setPromptText("Enerji Yüzdesi");

        // Kaydet Butonu (Yeni Bina Ekleme)
        Button btnKaydet = new Button("Kaydet");
        btnKaydet.setStyle("-fx-background-color: #0275d8; -fx-text-fill: white;");
        btnKaydet.setOnAction(event -> {
            try {
                String binaAdi = txtBinaAdi.getText();
                int kapasite = Integer.parseInt(txtBinaKapasite.getText());
                int yuzde = Integer.parseInt(txtBinaYuzde.getText());

                if (binaAdi.isEmpty() || kapasite <= 0 || yuzde <= 0 || yuzde > 100) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Hata");
                    alert.setHeaderText("Geçersiz Veri");
                    alert.setContentText("Lütfen tüm bilgileri doğru ve eksiksiz doldurun.");
                    alert.showAndWait();
                    return;
                }

                // Yeni bina ekleme
                KlasikBinalar yeniBina = new KlasikBinalar(binalar.getLast().getId()+1,binaAdi, kapasite, yuzde);
                binalar.add(yeniBina);
                observableList.add(yeniBina); // Tabloyu günceller

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Başarılı");
                alert.setHeaderText("Bina Eklendi");
                alert.setContentText("Yeni bina başarıyla eklendi!");
                alert.showAndWait();

                // Alanları temizle
                txtBinaAdi.clear();
                txtBinaKapasite.clear();
                txtBinaYuzde.clear();
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Hata");
                alert.setHeaderText("Geçersiz Veri");
                alert.setContentText("Lütfen sayı gerektiren alanlara yalnızca sayısal değerler girin.");
                alert.showAndWait();
            }
        });

        // Güncelle Butonu
        Button btnGuncelle = new Button("Güncelle");
        btnGuncelle.setStyle("-fx-background-color: #5cb85c; -fx-text-fill: white;");
        btnGuncelle.setOnAction(event -> {
            KlasikBinalar secilenBina = tableView.getSelectionModel().getSelectedItem();
            if (secilenBina != null) {
                try {
                    secilenBina.setBinaTuru(txtBinaAdi.getText());
                    secilenBina.setKatSayisi(Integer.parseInt(txtBinaKapasite.getText()));
                    secilenBina.setEnerjiVerimliligiYuzdesi(Integer.parseInt(txtBinaYuzde.getText()));

                    tableView.refresh();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Başarılı");
                    alert.setHeaderText("Bina Güncellendi");
                    alert.setContentText("Seçilen bina başarıyla güncellendi!");
                    alert.showAndWait();
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Hata");
                    alert.setHeaderText("Geçersiz Veri");
                    alert.setContentText("Lütfen doğru bir şekilde veri girin.");
                    alert.showAndWait();
                }
            }
        });

        // Sil Butonu
        Button btnSil = new Button("Sil");
        btnSil.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white;");
        btnSil.setOnAction(event -> {
            KlasikBinalar secilenBina = tableView.getSelectionModel().getSelectedItem();
            if (secilenBina != null) {
                // Onay penceresini oluştur
                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Silme İşlemi");
                confirmAlert.setHeaderText("Bina Silme");
                confirmAlert.setContentText(secilenBina.getBinaTuru() + " adlı binayı silmek istediğinize emin misiniz?");

                // Buton seçenekleri ("Evet", "Hayır")
                Optional<ButtonType> result = confirmAlert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) { // "Evet" seçilmişse
                    // Seçilen binayı listeden kaldır
                    binalar.remove(secilenBina);
                    observableList.remove(secilenBina);

                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Başarılı");
                    successAlert.setHeaderText("Bina Silindi");
                    successAlert.setContentText("Seçilen bina başarıyla silindi!");
                    successAlert.showAndWait();
                }
            } else {
                // Eğer hiçbir seçim yoksa uyarı göster
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Uyarı");
                alert.setHeaderText("Bina Seçilmedi");
                alert.setContentText("Lütfen bir bina seçin ve tekrar deneyin.");
                alert.showAndWait();
            }
        });
        // Listele Butonu
        Button btnListele = new Button("Listele");
        btnListele.setStyle("-fx-background-color: #5bc0de; -fx-text-fill: white;");
        btnListele.setOnAction(event -> tableView.setItems(FXCollections.observableArrayList(binalar)));

        // Tablo seçim dinleyicisi
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, eskiSecim, yeniSecim) -> {
            if (yeniSecim != null) {
                txtBinaAdi.setText(yeniSecim.getBinaTuru());
                txtBinaKapasite.setText(String.valueOf(yeniSecim.getKatSayisi()));
                txtBinaYuzde.setText(String.valueOf(yeniSecim.getEnerjiVerimliligiYuzdesi()));
            }
        });

        // Sağ panel elemanlarını ekleme
        rightPane.getChildren().addAll(lblTitle, new Label("Bina Türü:"), txtBinaAdi,
                new Label("Kat Sayısı:"), txtBinaKapasite,
                new Label("Enerji Yüzdesi:"), txtBinaYuzde,
                btnKaydet, btnGuncelle, btnSil, btnListele);

        // Ana layout
        HBox mainLayout = new HBox(10);
        mainLayout.setPadding(new Insets(10));
        mainLayout.getChildren().addAll(tableView, rightPane);

        // Sahne ayarı
        Scene scene = new Scene(mainLayout, 900, 500);
        stage.setScene(scene);
        stage.showAndWait();
    }
    // JavaFX ile yeni enerji kaynağı ekleme
    public void yeniEnerjiEkle(Stage primaryStage, EnergyManager energyManager, ArrayList<EnerjiKaynaklari> enerji) {
        Stage stage = new Stage();
        stage.setTitle("Yeni Enerji Kaynağı Ekle");
        stage.initOwner(primaryStage); // Modal pencerenin sahibi olarak ana sahneyi belirtebiliriz
        stage.initModality(Modality.WINDOW_MODAL); // Yeni pencere modal olacak şekilde ayarlandı

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));


            try {
                // Enerji kaynağı ekleme işlemi
                energyManager.EnerjiKaynagiEkle(enerji, primaryStage);

                // Tüm enerji kaynaklarını göster
                tumEnerjiKaynaklariGoster(primaryStage, enerji, energyManager);

                // Pencereyi kapat
                stage.close();
            } catch (NumberFormatException e) {
                showAlert("Hata!", "Geçerli bir sayı giriniz.", Alert.AlertType.ERROR);
            }


        Button btnIptal = new Button("geri");
        btnIptal.setOnAction(event -> stage.close());

        HBox buttonBox = new HBox(10,  btnIptal);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));
        buttonBox.setSpacing(10);

        layout.getChildren().addAll(buttonBox);

        Scene scene = new Scene(layout, 300, 200);
        stage.setScene(scene);

        // Modal olarak göster ve kullanıcı etkileşimini bekle
        stage.showAndWait();
    }
    // JavaFX ile yeni su kaynağı ekleme
    public void yeniSuKaynagiEkle(Stage primaryStage, WaterManager waterManager) {
        Stage stage = new Stage();
        stage.setTitle("💧 Yeni Su Kaynağı Ekle");

        // Ana Layout: VBox
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #212121; -fx-border-radius: 15; -fx-background-radius: 15; " +
                "-fx-border-color: #64dd17; -fx-border-width: 3;");

        // Başlık Label
        Label lblTitle = new Label("Su Kaynağı Ekleme Formu");
        lblTitle.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #ffffff;");

        // Bilgilendirme Label
        Label lblMiktar = new Label("Eklemek istediğiniz su miktarını girin (litre):");
        lblMiktar.setStyle("-fx-font-size: 16px; -fx-text-fill: #b0bec5;");

        // Su Miktarı Girişi (TextField)
        TextField txtMiktar = new TextField();
        txtMiktar.setPromptText("Örn: 1500");
        txtMiktar.setStyle("-fx-background-color: #37474f; -fx-text-fill: #ffffff; -fx-font-size: 16px; -fx-padding: 10; " +
                "-fx-border-radius: 10; -fx-background-radius: 10; -fx-border-color: #64dd17;");
        txtMiktar.setAlignment(Pos.CENTER);

        // Ekle Butonu
        Button btnEkle = new Button("💾 Ekle");
        btnEkle.setStyle(getButtonStyle("#64dd17", "#ffffff", 16));
        btnEkle.setOnMouseEntered(event -> btnEkle.setStyle(getButtonStyle("#43a047", "#ffffff", 16)));
        btnEkle.setOnMouseExited(event -> btnEkle.setStyle(getButtonStyle("#64dd17", "#ffffff", 16)));

        btnEkle.setOnAction(event -> {
            String input = txtMiktar.getText();
            try {
                double miktar = Double.parseDouble(input);
                if (miktar <= 0) {
                    showAlert("❌ Hata", "Su miktarı pozitif bir değer olmalıdır.", Alert.AlertType.ERROR);
                } else {
                    waterManager.setSuRezervi(waterManager.getSuRezervi() + miktar);
                    showAlert("✅ Başarılı", "Su kaynağı başarıyla eklendi. Yeni su rezervi: " + waterManager.getSuRezervi(), Alert.AlertType.INFORMATION);
                    stage.close();
                }
            } catch (NumberFormatException e) {
                showAlert("❌ Geçersiz Giriş", "Lütfen geçerli bir sayı girin!", Alert.AlertType.ERROR);
            }
        });

        // İptal Butonu
        Button btnIptal = new Button("❌ İptal");
        btnIptal.setStyle(getButtonStyle("#d50000", "#ffffff", 16));
        btnIptal.setOnMouseEntered(event -> btnIptal.setStyle(getButtonStyle("#b71c1c", "#ffffff", 16)));
        btnIptal.setOnMouseExited(event -> btnIptal.setStyle(getButtonStyle("#d50000", "#ffffff", 16)));

        btnIptal.setOnAction(event -> stage.close());

        // Butonlar için HBox (Yatay Hizalama)
        HBox buttonBox = new HBox(15, btnEkle, btnIptal);
        buttonBox.setAlignment(Pos.CENTER);

        // Animasyon: Fade efekti eklenir (Pencere açılırken)
        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), layout);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        // Layout'a bileşenleri ekle
        layout.getChildren().addAll(lblTitle, lblMiktar, txtMiktar, buttonBox);

        // Sahne Ayarları
        Scene scene = new Scene(layout, 500, 350); // Daha ferah bir pencere
        stage.setScene(scene);
        stage.setResizable(false); // Pencere boyutunu sabitle
        stage.show();
    }

    // Buton için stilleri düzenli bir hale getiren yardımcı fonksiyon
    private String getButtonStyle(String bgColor, String textColor, int fontSize) {
        return "-fx-background-color: " + bgColor + "; " +
                "-fx-font-size: " + fontSize + "px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: " + textColor + "; " +
                "-fx-border-radius: 10; " +
                "-fx-background-radius: 10; " +
                "-fx-padding: 10;";
    }

    // Uyarı mesajlarındaki detayları profesyonelleştirdik
    private void showAlert( String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("yas ile");
        alert.setContentText(content);
        alert.setHeaderText(null); // Başlığı gereksiz bir metinle doldurmadık
        alert.initStyle(StageStyle.UNDECORATED); // Daha modern görünüm
        alert.getDialogPane().setStyle("-fx-background-color: #2e2e2e; -fx-text-fill: white;"); // Alert stili
        alert.showAndWait();
    }

    public void sehirRaporu(Stage primaryStage, TrafficManager trafficManager,
                            EnergyManager energyManager, WaterManager waterManager,
                            ArrayList<EnerjiKaynaklari> enerji, ArrayList<Klasik> ar) {

        // Yeni bir sahne oluştur
        Stage stage = new Stage();
        stage.setTitle("Şehir Raporu");

        // Ana Layout: Dikey düzen (VBox)
        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-background-color: #1e1e2f;");

        // Genel stil ayarları için CSS parametreleri
        String panelStyle = "-fx-border-radius: 10; -fx-background-radius: 10; -fx-border-color: #dddddd; -fx-border-width: 2;";
        String labelStyle = "-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #ffffff;";

        // *** SU PANELİ
        VBox suPanel = new VBox(15);
        suPanel.setPadding(new Insets(10));
        suPanel.setStyle("-fx-background-color: #4caf50;" + panelStyle);
        suPanel.setAlignment(Pos.CENTER);

        Label lblSu = new Label("Su Seviyesi");
        lblSu.setStyle(labelStyle);

        Canvas waterCanvas = new Canvas(300, 100); // Daha geniş ve profesyonel grafikleri desteklemek için genişlik artırıldı
        drawWaterLevel(waterCanvas.getGraphicsContext2D(), 70); // Su sıklığı örnek verisi (%70)

        suPanel.getChildren().addAll(lblSu, waterCanvas);

        // *** TRAFİK PANELİ
        VBox trafikPanel = new VBox(15);
        trafikPanel.setPadding(new Insets(10));
        trafikPanel.setStyle("-fx-background-color: #ffa726;" + panelStyle);
        trafikPanel.setAlignment(Pos.CENTER);

        Label lblTrafik = new Label("Trafik Yoğunluğu");
        lblTrafik.setStyle(labelStyle);

        Canvas trafficCanvas = new Canvas(300, 150); // Trafik için daha büyük ölçek
        drawTrafficCylinder(trafficCanvas.getGraphicsContext2D(), 50); // Trafik seviyesi %50

        trafikPanel.getChildren().addAll(lblTrafik, trafficCanvas);

        // *** ENERJİ PANELİ
        VBox enerjiPanel = new VBox(15);
        enerjiPanel.setPadding(new Insets(10));
        enerjiPanel.setStyle("-fx-background-color: #42a5f5;" + panelStyle);
        enerjiPanel.setAlignment(Pos.CENTER);

        Label lblEnerji = new Label("Enerji Seviyesi");
        lblEnerji.setStyle(labelStyle);

        Canvas energyCanvas = new Canvas(300, 150);
        drawEnergyCircle(energyCanvas.getGraphicsContext2D(), 85); // Enerji seviyesi %85

        enerjiPanel.getChildren().addAll(lblEnerji, energyCanvas);

        // *** KAPAT BUTONU
        Button btnKapat = new Button("Kapat");
        btnKapat.setStyle("-fx-background-color: #f44336; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white; "
                + "-fx-border-radius: 8; -fx-background-radius: 8;");
        btnKapat.setOnMouseEntered(e -> btnKapat.setStyle("-fx-background-color: #d32f2f; -fx-font-size: 16px; -fx-font-weight: bold;"));
        btnKapat.setOnMouseExited(e -> btnKapat.setStyle("-fx-background-color: #f44336; -fx-font-size: 16px;"));
        btnKapat.setOnAction(e -> stage.close());

        // Panelleri ve butonu ana layout'a ekle
        mainLayout.getChildren().addAll(suPanel, trafikPanel, enerjiPanel, btnKapat);

        // Sahne ve görsel alan ayarları
        Scene scene = new Scene(mainLayout, 600, 800); // Daha büyük ve ferah bir alan
        stage.setScene(scene);
        stage.show();
    }

    private void drawWaterLevel(GraphicsContext gc, int percentage) {
        double width = 300;
        double height = 100;

        // Arkaplan
        gc.setFill(Color.DARKBLUE);
        gc.fillRect(0, 0, width, height);

        // Doluluk (Su seviyesi)
        gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(0, height - (percentage / 100.0) * height, width, (percentage / 100.0) * height);

        // Çerçeve
        gc.setStroke(Color.WHITE);
        gc.strokeRect(0, 0, width, height);

        // Yüzde yazısı
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        gc.fillText(percentage + "%", width / 2 - 10, height / 2);
    }

    private void drawTrafficCylinder(GraphicsContext gc, int percentage) {
        double width = 60; // Trafik genişliği
        double height = 150;

        // Arkaplan çerçevesi
        gc.setFill(Color.GRAY);
        gc.fillRect(120, 0, width, height);

        // Doluluk (Trafik seviyesi)
        gc.setFill(Color.ORANGE);
        gc.fillRect(120, height - (percentage / 100.0) * height, width, (percentage / 100.0) * height);

        // Çerçeve
        gc.setStroke(Color.BLACK);
        gc.strokeRect(120, 0, width, height);

        // Yüzde yazısı
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        gc.fillText(percentage + "%", 110, height / 2);
    }

    private void drawEnergyCircle(GraphicsContext gc, int percentage) {
        double size = 120; // Çember boyutu
        double centerX = size / 2 + 90;
        double centerY = size / 2 + 20;

        // Çember (arka plan)
        gc.setStroke(Color.GRAY);
        gc.setLineWidth(10);
        gc.strokeOval(90, 20, size, size);

        // Doluluk oranı
        gc.setStroke(Color.LIGHTGREEN);
        gc.setLineWidth(10);
        gc.strokeArc(90, 20, size, size, 90, -percentage * 3.6, ArcType.OPEN);

        // Yüzde yazısı
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        gc.fillText(percentage + "%", centerX - 10, centerY + 5);
    }
    // Araçları gösterme
    public void tumAraclariGoster(Stage primaryStage, ArrayList<Klasik> aracList) {
        Stage stage = new Stage();
        stage.setTitle("Tüm Araçlar");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        if (aracList.isEmpty()) {
            Label lblUyari = new Label("Araç listesi boş!");
            layout.getChildren().add(lblUyari);
        } else {
            for (Klasik arac : aracList) {
                Label lblArac = new Label("- " + arac.getAracTipi() + " | Kapasite: " + arac.getKapasite() + " | Lokasyon: " + arac.getBulunanYernumarasi());
                layout.getChildren().add(lblArac);

                if (arac instanceof Elektrikli) {
                    layout.getChildren().add(new Label("  Şarj Seviyesi: %" + ((Elektrikli) arac).getSarjSeviyesi()));
                }
            }
        }

        Button btnKapat = new Button("Kapat");
        btnKapat.setOnAction(event -> stage.close());

        layout.getChildren().add(btnKapat);

        Scene scene = new Scene(layout, 400, 300);
        stage.setScene(scene);
        stage.show();
    }
    public void tumEnerjiKaynaklariGoster(Stage primaryStage, ArrayList<EnerjiKaynaklari> enerji, EnergyManager energyManager) {
        // Yeni bir Stage oluştur
        Stage stage = new Stage();
        stage.initOwner(primaryStage); // Ana pencereyi owner olarak ayarla
        stage.initModality(Modality.WINDOW_MODAL); // Modal pencere oluştur
        stage.setTitle("Enerji Kaynakları");

        // Ana Layout (VBox ayarları)
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER); // Öğeleri dikey merkezle
        layout.setStyle("-fx-background-color: #f9f9f9; -fx-border-color: #cccccc; -fx-border-radius: 10; -fx-background-radius: 10;");

        // Eğer enerji boş ise uyarı etiketi
        if (enerji.isEmpty()) {
            Label lblUyari = new Label("Liste boş, lütfen enerji kaynaklarını ekleyiniz.");
            lblUyari.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #d32f2f;"); // Kırmızı, büyük yazı
            layout.getChildren().add(lblUyari);

            // Yeni enerji ekleme çağrısı
            yeniEnerjiEkle(stage, energyManager, enerji);
        }
        // Enerji dolu olduğunda her bir kaynağı listeyen döngü
        else {
            // Başlık ekle
            Label baslik = new Label("Enerji Kaynakları Listesi:");
            baslik.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #4caf50;");
            layout.getChildren().add(baslik);

            for (EnerjiKaynaklari kaynak : enerji) {
                Label lblEnerji = new Label("- " + kaynak.getTur() + " | Kapasite: " + kaynak.getKapasitesi() + " | ID: " + kaynak.getId());
                lblEnerji.setStyle("-fx-font-size: 14px; -fx-text-fill: #333333;"); // Gri tonlarda açıklama
                layout.getChildren().add(lblEnerji);
            }
        }

        // Kapat butonu
        Button btnKapat = new Button("Kapat");
        btnKapat.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5; -fx-background-radius: 5;");
        btnKapat.setOnMouseEntered(e -> btnKapat.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white; -fx-font-size: 14px;"));
        btnKapat.setOnMouseExited(e -> btnKapat.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14px;"));
        btnKapat.setOnAction(event -> stage.close());
        layout.getChildren().add(btnKapat); // Butonu layout'a ekle

        // Scene oluştur ve sahne ayarlarını yap
        Scene scene = new Scene(layout, 450, 400); // Boyutlar genişletildi
        stage.setScene(scene);

        // Stage'i göster ve kullanıcı etkileşimini bekle
        stage.showAndWait();
    }

    // Uyarı mesajı gösterme (ortak metot)
    private void showAlert(String baslik, String mesaj, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(baslik);
        alert.setHeaderText(null);
        alert.setContentText(mesaj);
        alert.showAndWait();
    }


    public void varlikGuncelleme(Stage pr, BinaManager binaManager,TrafficManager trafficManager, EnergyManager energyManager, WaterManager waterManager, ArrayList<Klasik> klasikList, ArrayList<KlasikBinalar> binalar, ArrayList<EnerjiKaynaklari> eneji){
        Stage stage = new Stage();
        stage.setTitle("varlık gunceleme");
        stage.initOwner(pr);
        stage.initModality(Modality.WINDOW_MODAL);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: black");
        Button btnArac = new Button("Arac Guncelleme");
       Button btnBina=new Button("Bina Gunceleme");
       Button btnEnerji=new Button("Enerji Gunceleme");
       Button btnSuGuncelle=new Button("Su Guncelle");
        Button btnKapat = new Button("Kapat");

        btnBina.setOnAction(event -> {
         binaManager.listeleVeGuncelle(stage);
            //
        });

        btnEnerji.setOnAction(event -> {
          energyManager.enerjiGuncelle(stage,eneji);
          stage.close();
        });

        btnArac.setOnAction(event -> {
            trafficManager.aracGuncelle(klasikList,stage);
            stage.close();
        });

       btnSuGuncelle.setOnAction(event -> {
           waterManager.suRaporuGoster();
           waterManager.adminSutakibi(stage);
           //stage.close();
       });

       btnSuGuncelle.setStyle("fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #6666dd;");
        btnBina.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #af634c;");
        btnEnerji.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #4caf86;");
        btnArac.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #4c9daf;");
        btnKapat.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5; -fx-background-radius: 5;");
        btnKapat.setOnMouseEntered(e -> btnKapat.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white; -fx-font-size: 14px;"));
        btnKapat.setOnMouseExited(e -> btnKapat.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14px;"));
        btnKapat.setOnAction(event -> stage.close());

        layout.getChildren().addAll(btnArac,btnBina,btnEnerji,btnSuGuncelle,btnKapat);

        Scene scene = new Scene(layout, 400, 300);
        stage.setScene(scene);
        stage.showAndWait();

    }
}