package SistemKulanicilari;

import Manager.EnergyManager;
import Manager.TrafficManager;
import Manager.WaterManager;
import Varliklar.Araclar.Klasik;
import Varliklar.Binalar.KlasikBinalar;
import Varliklar.EnerjiKaynaklari.EnerjiKaynaklari;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Objects;

public class Vatandas {
    private int numara;
    private String ad;
    private String soyAd;
    private ArrayList<KlasikBinalar> vatandasBinalarListesi = new ArrayList<>();
    private ArrayList<Klasik> vatandasAracListesi = new ArrayList<>();
    private VatandasUI vatandasUI; // VatandasUI'yi dahil etme

    public Vatandas(int numara, String ad, String soyAd, ArrayList<KlasikBinalar> vatandasBinalarListesi, ArrayList<Klasik> vatandasAracListesi,
                    Stage primaryStage, TrafficManager trafficManager, EnergyManager energyManager, WaterManager waterManager) {
        this.numara = numara;
        this.ad = ad;
        this.soyAd = soyAd;
        this.vatandasBinalarListesi = vatandasBinalarListesi;
        this.vatandasAracListesi = vatandasAracListesi;

        // VatandasUI nesnesini oluştur
        this.vatandasUI = new VatandasUI(primaryStage, this, trafficManager, energyManager, waterManager);
    }

    // VatandasUI'yi dışarıya açmak için bir getter
    public VatandasUI getVatandasUI() {
        return vatandasUI;
    }

    // Diğer getter ve setter'lar
    public int getNumara() {
        return numara;
    }

    public void setNumara(int numara) {
        this.numara = numara;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyAd() {
        return soyAd;
    }

    public void setSoyAd(String soyAd) {
        this.soyAd = soyAd;
    }

    public ArrayList<KlasikBinalar> getVatandasBinalarListesi() {
        return vatandasBinalarListesi;
    }

    public void setVatandasBinalarListesi(ArrayList<KlasikBinalar> vatandasBinalarListesi) {
        this.vatandasBinalarListesi = vatandasBinalarListesi;
    }

    public ArrayList<Klasik> getVatandasAracListesi() {
        return vatandasAracListesi;
    }

    public void setVatandasAracListesi(ArrayList<Klasik> vatandasAracListesi) {
        this.vatandasAracListesi = vatandasAracListesi;
    }

    // Eşitlik kontrolü
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vatandas vatandas = (Vatandas) o;
        return numara == vatandas.numara &&
                Objects.equals(ad, vatandas.ad) &&
                Objects.equals(soyAd, vatandas.soyAd);
    }

    // Trafik Kontrol Metodu
    public void trafikKontrol(TrafficManager trafficManager, ArrayList<Klasik> aracList, Stage pr) throws InterruptedException {
        trafficManager.trafigiGoster(aracList, pr);
    }

    // Enerji Raporu Göster
    public void enerjiRaporuGoster(Stage pr, EnergyManager energyManager, ArrayList<EnerjiKaynaklari> enerji) {
        energyManager.enerjiRaporuGoster(pr, "vatandas", enerji);
    }

    // Su Raporu Göster
    public void suRaporuGoster(WaterManager waterManager) {
        waterManager.suRaporuGoster();
    }
    public void araclarimiGoster(Vatandas vatandas, Stage pr, TrafficManager tr) {
        if (vatandas.getVatandasAracListesi() == null || vatandas.getVatandasAracListesi().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Araçlarımı Göster");
            alert.setHeaderText("Araç Bulunamadı");
            alert.setContentText("Herhangi bir araca sahip değilsiniz.");
            alert.showAndWait();
            return;
        }

        // Yeni pencere oluştur
        Stage stage = new Stage();
        stage.setTitle("Araçlarımı Göster");
        stage.initOwner(pr.getOwner());
        stage.initModality(Modality.WINDOW_MODAL);

        // Vatandaşın araç listesi
        ObservableList<Klasik> aracListesi = FXCollections.observableList(vatandas.getVatandasAracListesi());

        // TableView ve Sütunlar
        TableView<Klasik> tableView = new TableView<>();
        TableColumn<Klasik, String> markaColumn = new TableColumn<>("Araç Markası");
        markaColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMarka()));

        TableColumn<Klasik, String> modelColumn = new TableColumn<>("Araç Modeli");
        modelColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getModel()));

        // Tabloya sütunları ve veri setini bağlama
        tableView.getColumns().addAll(markaColumn, modelColumn);
        tableView.setItems(aracListesi);

        // Layout Ayarları
        VBox layout = new VBox(10, tableView);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 600, 400);
        stage.setScene(scene);
        stage.show();
    }
    public void VariklarimPaneli(Vatandas vatandas, Stage pr, TrafficManager tr, ArrayList<Klasik> mevcutAraclar) {
        // Yeni pencere oluşturma
        Stage stage = new Stage();
        stage.setTitle("Varlıklar ve İşlemler Paneli");
        stage.initOwner(pr.getOwner());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);

        // Pencere başlığı - Ana Başlık
        Label title = new Label("Varlıklar ve İşlemler");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #1E88E5; -fx-padding: 10px;");
        title.setAlignment(Pos.CENTER);

        // Butonların tanımlanması
        Button btnArac = new Button("Araç İşlemleri");
        Button btnBina = new Button("Bina İşlemleri");
        Button btnKapat = new Button("Kapat");

        // Araç İşlemleri - Buton Stilleri
        btnArac.setStyle(
                "-fx-background-color: #4CAF50; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 10px; -fx-padding: 10px 15px;");
        btnArac.setOnMouseEntered(e -> btnArac.setStyle(
                "-fx-background-color: #45A049; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 10px; -fx-padding: 10px 15px;"));
        btnArac.setOnMouseExited(e -> btnArac.setStyle(
                "-fx-background-color: #4CAF50; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 10px; -fx-padding: 10px 15px;"));

        // Bina İşlemleri - Buton Stilleri
        btnBina.setStyle(
                "-fx-background-color: #2196F3; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 10px; -fx-padding: 10px 15px;");
        btnBina.setOnMouseEntered(e -> btnBina.setStyle(
                "-fx-background-color: #1976D2; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 10px; -fx-padding: 10px 15px;"));
        btnBina.setOnMouseExited(e -> btnBina.setStyle(
                "-fx-background-color: #2196F3; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 10px; -fx-padding: 10px 15px;"));

        // Kapatma Düğmesi - Buton Stilleri
        btnKapat.setStyle(
                "-fx-background-color: #F44336; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 10px; -fx-padding: 10px 15px;");
        btnKapat.setOnMouseEntered(e -> btnKapat.setStyle(
                "-fx-background-color: #D32F2F; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 10px; -fx-padding: 10px 15px;"));
        btnKapat.setOnMouseExited(e -> btnKapat.setStyle(
                "-fx-background-color: #F44336; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 10px; -fx-padding: 10px 15px;"));

        // İşlemleri butonlara bağlama
        btnArac.setOnAction(event -> AracIslenmleri(vatandas, stage, tr, mevcutAraclar));
        btnBina.setOnAction(event -> BinalarimPaneli(vatandas, stage, tr, vatandas.getVatandasBinalarListesi()));
        btnKapat.setOnAction(event -> stage.close());

        // Düğmeler için bir VBox düzeni oluşturma
        VBox buttonLayout = new VBox(20, btnArac, btnBina, btnKapat);
        buttonLayout.setAlignment(Pos.CENTER);

        // Ana düzen
        VBox mainLayout = new VBox(30, title, buttonLayout);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #E3F2FD, #ffffff); " +
                        "-fx-border-color: #1E88E5; -fx-border-radius: 10px; -fx-padding: 20px;");

        // Sahne ve pencere ayarları
        Scene scene = new Scene(mainLayout, 600, 400);
        stage.setScene(scene);
        stage.showAndWait();
    }
    public void BinalarimPaneli(Vatandas vatandas, Stage pr, TrafficManager tr,ArrayList<KlasikBinalar> mevcutBinalarlar) {
        Stage stage=new Stage();
        stage.setTitle("bina işlemleri");
        stage.initOwner(pr.getOwner());
        stage.initModality(Modality.WINDOW_MODAL);

        VBox layout=new VBox(10);
        layout.setPadding(new Insets(20));

        Button btnBinalarim=new Button("binaların");
        Button btnBinaAlSat=new Button("bina AL sat");
        Button btnKapat=new Button("Kapat");

        btnBinalarim.setOnAction(event -> {

        });
        btnBinaAlSat.setOnAction(event -> {

        });
        btnKapat.setOnAction(event -> stage.close());
        layout.getChildren().addAll(btnBinalarim,btnBinaAlSat,btnKapat);


        Scene scene = new Scene(layout, 600, 400);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void BinalarimGoster(Vatandas vatandas, Stage pr, TrafficManager tr,ArrayList<KlasikBinalar> mevcutBinalarlar) {
        Stage stage=new Stage();
        stage.setTitle("Binaların");
        stage.initOwner(pr.getOwner());
        stage.initModality(Modality.WINDOW_MODAL);

        TableView<KlasikBinalar> tableView=new TableView<>();

        TableColumn<KlasikBinalar, String> aracAdiColumn = new TableColumn<>("bina numarası");
       // aracAdiColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId())); // Varsayımsal getter




        //  Scene scene = new Scene(layout, 600, 400);
       // stage.setScene(scene);
       // stage.showAndWait();
    }


    public void AracIslenmleri(Vatandas vatandas, Stage pr, TrafficManager tr, ArrayList<Klasik> mevcutAraclar) {
        // Yeni bir pencere oluşturma
        Stage stage = new Stage();
        stage.setTitle("İşlem Sınıflandırma");
        stage.initOwner(pr.getOwner());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);

        // Pencere başlığı (Label)
        Label title = new Label("Araç İşlemleri");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #1E88E5; -fx-padding: 10px;");

        // Düğmelerin tanımlanması
        Button btnAracSat = new Button("Araçları Sat");
        Button btnAracAl = new Button("Araçları Al");
        Button btnAracGoster = new Button("Araçları Göster");

        // Butonların düzeni
        btnAracSat.setStyle(
                "-fx-background-color: #FF5722; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 10px; -fx-padding: 10px 15px;");
        btnAracSat.setOnMouseEntered(e -> btnAracSat.setStyle(
                "-fx-background-color: #E64A19; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 10px; -fx-padding: 10px 15px;"));
        btnAracSat.setOnMouseExited(e -> btnAracSat.setStyle(
                "-fx-background-color: #FF5722; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 10px; -fx-padding: 10px 15px;"));

        btnAracAl.setStyle(
                "-fx-background-color: #4CAF50; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 10px; -fx-padding: 10px 15px;");
        btnAracAl.setOnMouseEntered(e -> btnAracAl.setStyle(
                "-fx-background-color: #45A049; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 10px; -fx-padding: 10px 15px;"));
        btnAracAl.setOnMouseExited(e -> btnAracAl.setStyle(
                "-fx-background-color: #4CAF50; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 10px; -fx-padding: 10px 15px;"));

        btnAracGoster.setStyle(
                "-fx-background-color: #2196F3; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 10px; -fx-padding: 10px 15px;");
        btnAracGoster.setOnMouseEntered(e -> btnAracGoster.setStyle(
                "-fx-background-color: #1976D2; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 10px; -fx-padding: 10px 15px;"));
        btnAracGoster.setOnMouseExited(e -> btnAracGoster.setStyle(
                "-fx-background-color: #2196F3; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 10px; -fx-padding: 10px 15px;"));

        // İşlemleri düğmelere bağlama
        btnAracSat.setOnAction(event1 -> aracSat(vatandas,mevcutAraclar));
        btnAracGoster.setOnAction(event1 -> araclarimiGoster(vatandas, pr, tr));

        btnAracAl.setOnAction(event1 -> aracAl(mevcutAraclar, vatandas));
        /// ////
        Button btnKapat = new Button("Kapat");
        btnKapat.setOnAction(event1 -> {
          stage.close();
        });
        // Düğmeleri düzenleyen VBox
        VBox aracLayout = new VBox(15, btnAracSat, btnAracAl, btnAracGoster);
        aracLayout.setAlignment(Pos.CENTER);

        // Ana düzen
        VBox mainLayout = new VBox(20, title, aracLayout);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #E3F2FD, #ffffff); " +
                        "-fx-border-color: #1E88E5; -fx-border-radius: 10px; -fx-padding: 20px;");

        // Sahne ve pencere ayarları
        Scene scene = new Scene(mainLayout, 600, 400);
        stage.setScene(scene);
        stage.showAndWait();
    }
    // Araç Satışı İşlemi - Modern Tasarım
    private void aracSat(Vatandas vatandas, ArrayList<Klasik> mevcutAraclar) {
        try {
            // Araç bulunamadığı durumu kontrol et
            if (vatandas.getVatandasAracListesi() == null || vatandas.getVatandasAracListesi().isEmpty()) {
                // Araç olmadığı uyarısı
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Araç Satışı Uyarısı");
                alert.setHeaderText("Araç Bulunamadı");
                alert.setContentText("Satabilecek bir aracınız bulunmamaktadır.");
                alert.showAndWait();
                return;
            }

            // Yeni pencere oluştur
            Stage stage = new Stage();
            stage.setTitle("Araçlarınızı Sat");
            stage.setResizable(false);

            // TableView tanımı
            TableView<Klasik> tableView = new TableView<>();
            tableView.setStyle("-fx-border-color: transparent; -fx-border-width: 0;");

            // Araç Adı Kolonu
            TableColumn<Klasik, String> aracAdiColumn = new TableColumn<>("Araç Adı");
            aracAdiColumn.setCellValueFactory(data -> {
                try {
                    return new SimpleStringProperty(data.getValue().getMarka()); // Varsayımsal getter
                } catch (Exception e) {
                    e.printStackTrace(); // Hata detaylarını konsola yazdır
                    return new SimpleStringProperty("Bilinmiyor");
                }
            });
            aracAdiColumn.setStyle("-fx-alignment: CENTER; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: black;");

            // Araç Modeli Kolonu
            TableColumn<Klasik, String> aracModelColumn = new TableColumn<>("Araç Modeli");
            aracModelColumn.setCellValueFactory(data -> {
                try {
                    return new SimpleStringProperty(data.getValue().getModel()); // Varsayımsal getter
                } catch (Exception e) {
                    e.printStackTrace(); // Hata detaylarını konsola yazdır
                    return new SimpleStringProperty("Bilinmiyor");
                }
            });
            aracModelColumn.setStyle("-fx-alignment: CENTER; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: black;");

            // İşlem Kolonu (Sat Butonu)
            TableColumn<Klasik, Void> islemColumn = new TableColumn<>("İşlem");
            islemColumn.setCellFactory(column -> new TableCell<>() {
                private final Button button = new Button("Sat");

                {
                    button.setStyle(
                            "-fx-background-color: #4CAF50; " +
                                    "-fx-text-fill: white; " +
                                    "-fx-font-size: 14px; " +
                                    "-fx-font-weight: bold; " +
                                    "-fx-background-radius: 8px; -fx-padding: 6px;");
                    button.setOnMouseEntered(e -> button.setStyle(
                            "-fx-background-color: #45A049; " +
                                    "-fx-text-fill: white; " +
                                    "-fx-font-size: 14px; " +
                                    "-fx-font-weight: bold; " +
                                    "-fx-background-radius: 8px; -fx-padding: 6px;"));
                    button.setOnMouseExited(e -> button.setStyle(
                            "-fx-background-color: #4CAF50; " +
                                    "-fx-text-fill: white; " +
                                    "-fx-font-size: 14px; " +
                                    "-fx-font-weight: bold; " +
                                    "-fx-background-radius: 8px; -fx-padding: 6px;"));

                    button.setOnAction(event -> {
                        try {
                            int index = getIndex(); // Butonun bulunduğu satırı al
                            Klasik seciliArac = getTableView().getItems().get(index);

                            // Seçilen aracı vatandaşa ait araç listesinden kaldır
                            vatandas.getVatandasAracListesi().remove(seciliArac);

                            // Başarı mesajı
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Araç Satışı");
                            alert.setHeaderText("Başarılı");
                            alert.setContentText(
                                    seciliArac.getMarka() + " " + seciliArac.getModel() +
                                            " aracı başarıyla satıldı!");
                            alert.showAndWait();

                            // Seçilen aracı tablodan da kaldır
                            tableView.getItems().remove(seciliArac);

                            // Eğer listede araç kalmadıysa pencereyi kapat
                            if (vatandas.getVatandasAracListesi().isEmpty()) {
                                stage.close();
                            }
                        } catch (Exception e) {
                            // Eğer bir hata oluşursa logla
                            e.printStackTrace();
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Hata");
                            alert.setHeaderText("Bir hata oluştu!");
                            alert.setContentText("Hata mesajı: " + e.getMessage());
                            alert.showAndWait();
                        }
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(button);
                    }
                }
            });
            islemColumn.setStyle("-fx-alignment: CENTER; -fx-font-size: 14px;");

            // Tabloya kolonları ekleme
            tableView.getColumns().addAll(aracAdiColumn, aracModelColumn, islemColumn);
            tableView.setStyle(
                    "-fx-control-inner-background: linear-gradient(to bottom, #E3F2FD, #ffffff); " +
                            "-fx-table-cell-border-color: transparent; " +
                            "-fx-table-header-border-color: transparent;");



            // Mevcut araçları tabloya ekleme
            tableView.getItems().addAll(vatandas.getVatandasAracListesi());

            // Pencere düzeni
            VBox layout = new VBox(10, tableView);
            layout.setPadding(new Insets(10));
            layout.setStyle(
                    "-fx-background-color: linear-gradient(to bottom, #BBDEFB, #E3F2FD); " +
                            "-fx-border-color: #2196F3; -fx-border-radius: 10px; -fx-padding: 10px;");

            // Başlık Etiketi
            Label title = new Label("Araç Satış İşlemleri");
            title.setStyle(
                    "-fx-font-size: 24px; " +
                            "-fx-font-weight: bold; " +
                            "-fx-text-fill: #1E88E5; " +
                            "-fx-underline: true;");
            title.setPadding(new Insets(10, 0, 20, 0));

            VBox container = new VBox(20, title, layout);
            container.setAlignment(Pos.CENTER);
            container.setPadding(new Insets(20));

            // Sahneyi oluştur ve göster
            Scene scene = new Scene(container, 700, 500);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (Exception e) {
            // Genel hata durumunda logla
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Uygulama Hatası");
            alert.setHeaderText("Bir hata oluştu!");
            alert.setContentText("Hata mesajı: " + e.getMessage());
            alert.showAndWait();
        }
    }
    private void aracAl(ArrayList<Klasik> mevcutAraclar, Vatandas vatandas) {
        // Yeni pencere oluşturma
        Stage stage = new Stage();
        stage.setTitle("Araç Seçimi");
        stage.setResizable(false);

        // TableView tanımı
        TableView<Klasik> tableView = new TableView<>();
        tableView.setStyle("-fx-border-color: transparent; -fx-border-width: 0;");

        // Araç Marka Kolonu
        TableColumn<Klasik, String> aracMarkaColumn = new TableColumn<>("Araç Markası");
        aracMarkaColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMarka()));
        aracMarkaColumn.setStyle("-fx-alignment: CENTER; -fx-font-size: 16px; -fx-font-weight: bold;");

        // Araç Model Kolonu
        TableColumn<Klasik, String> aracModelColumn = new TableColumn<>("Araç Modeli");
        aracModelColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getModel()));
        aracModelColumn.setStyle("-fx-alignment: CENTER; -fx-font-size: 16px; -fx-font-weight: bold;");

        // İşlem Kolonu (Seç Butonu)
        TableColumn<Klasik, Void> islemColumn = new TableColumn<>("İşlem");
        islemColumn.setCellFactory(column -> new TableCell<>() {
            private final Button button = new Button("Seç");

            {
                // Butonlara stil ver
                button.setStyle(
                        "-fx-background-color: #4CAF50; " +
                                "-fx-text-fill: white; " +
                                "-fx-font-size: 14px; " +
                                "-fx-font-weight: bold; " +
                                "-fx-background-radius: 8px; -fx-padding: 6px;");

                // Hover efekti ekle
                button.setOnMouseEntered(e -> button.setStyle(
                        "-fx-background-color: #45A049; " +
                                "-fx-text-fill: white; " +
                                "-fx-font-size: 14px; " +
                                "-fx-font-weight: bold; " +
                                "-fx-background-radius: 8px; -fx-padding: 6px;"));
                button.setOnMouseExited(e -> button.setStyle(
                        "-fx-background-color: #4CAF50; " +
                                "-fx-text-fill: white; " +
                                "-fx-font-size: 14px; " +
                                "-fx-font-weight: bold; " +
                                "-fx-background-radius: 8px; -fx-padding: 6px;"));

                button.setOnAction(event -> {
                    Klasik seciliArac = getTableView().getItems().get(getIndex());
                    // Seçilen aracı vatandaşın araç listesine ekle
                    vatandas.getVatandasAracListesi().add(seciliArac);

                    // Bilgilendirme mesajı
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Araç Satın Alındı");
                    alert.setHeaderText("Başarılı!");
                    alert.setContentText(
                            "Araç: " + seciliArac.getMarka() + " " + seciliArac.getModel() +
                                    "\nKapasite: " + seciliArac.getKapasite() +
                                    "\nAraç Tipi: " + seciliArac.getAracTipi() + "\nBaşarıyla satın alındı!");
                    alert.showAndWait();

                    // Pencereyi kapatma
                    stage.close();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        });
        islemColumn.setStyle("-fx-alignment: CENTER;");

        // Tabloya kolonları ekleme
        tableView.getColumns().addAll(aracMarkaColumn, aracModelColumn, islemColumn);

        // Tablonun genel stili
        tableView.setStyle(
                "-fx-control-inner-background: linear-gradient(to bottom, #E3F2FD, #ffffff); " +
                        "-fx-table-cell-border-color: transparent; " +
                        "-fx-border-insets: 5px; " +
                        "-fx-table-header-border-color: transparent;");

        // Mevcut araçları tabloya ekleme
        tableView.getItems().addAll(mevcutAraclar);

        // Pencere başlığı tasarımı
        Label title = new Label("Satışa Sunulan Araçlar");
        title.setStyle(
                "-fx-font-size: 24px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-text-fill: #1E88E5; " +
                        "-fx-underline: true;");
        title.setPadding(new Insets(10, 0, 20, 0));

        // Esnek düzen tasarımı
        VBox layout = new VBox(15, title, tableView);
        layout.setPadding(new Insets(20));
        layout.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #BBDEFB, #E3F2FD); " +
                        "-fx-border-color: #1E88E5; -fx-border-radius: 10px; -fx-padding: 10px;");

        // Pencerenin sahnesini ayarla
        Scene scene = new Scene(layout, 800, 500);
        stage.setScene(scene);
        stage.showAndWait();
    }
}