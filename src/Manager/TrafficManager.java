package Manager;

import Varliklar.Araclar.Elektrikli;
import Varliklar.Araclar.Klasik;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class TrafficManager {
    private int trafikYoğunluğu; // Trafik yoğunluğu (0-100 arasında bir değer)

    public int getTrafikYoğunluğu() {
        return trafikYoğunluğu;
    }

    public void setTrafikYoğunluğu(int trafikYoğunluğu) {
        this.trafikYoğunluğu = trafikYoğunluğu;
    }

    // Genel trafik kontrolü
    public void trafikKontrol(ArrayList<Klasik> araclar) {
        int sayac = 0;
        for (Klasik arac : araclar) {
            if (arac.isDurum()) {
                sayac++;
            }
        }

        if (sayac >= 100) {
            this.trafikYoğunluğu = 90;
        } else if (sayac >= 50) {
            this.trafikYoğunluğu = 70;
        } else {
            this.trafikYoğunluğu = 40;
        }
    }

    // Araç durumu ve konumu kontrolü
    public void aracDurumKonum(ArrayList<Klasik> aracList, int secim) {
        int sayac = 0;

        for (Klasik arac : aracList) {
            if (arac.isDurum() && arac.getBulunanYernumarasi() == secim) {
                sayac++;
            }
        }

        if (sayac > 25) {
            this.trafikYoğunluğu = 90;
        } else if (sayac > 20) {
            this.trafikYoğunluğu = 80;
        } else if (sayac > 10) {
            this.trafikYoğunluğu = 60;
        } else {
            this.trafikYoğunluğu = 40;
        }
    }
    public void aracGuncelle(ArrayList<Klasik> aracList, Stage primaryStage) {
        // Yeni pencere oluştur
        Stage stage = new Stage();
        stage.initOwner(primaryStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Araç Güncelle");

        // Ana düzen
        VBox rootLayout = new VBox(15);
        rootLayout.setPadding(new Insets(15));
        rootLayout.setStyle("-fx-background-color: #f4f4f4;");

        // Başlık
        Label lblBaslik = new Label("🚗 Araç Güncelleme Paneli");
        lblBaslik.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        // Araç Listesi
        ListView<Klasik> listView = new ListView<>();
        listView.setPrefHeight(200);
        listView.getItems().addAll(aracList);

        // Seçilen Araç Bilgileri
        Label lblSeciliArac = new Label("Seçilen Araç:");
        lblSeciliArac.setStyle("-fx-font-size: 14px;");

        TextArea txtAracDetay = new TextArea();
        txtAracDetay.setEditable(false);
        txtAracDetay.setStyle("-fx-control-inner-background: #e8e8e8; -fx-text-fill: #333333;");
        txtAracDetay.setPrefHeight(100);

        // ListView'den seçili öğe değiştiğinde tetiklenir
        listView.getSelectionModel().selectedItemProperty().addListener((obs, eskiArac, yeniArac) -> {
            if (yeniArac != null) {
                txtAracDetay.setText("Araç ID: " + yeniArac.getId() + "\n"
                        + "Model: " + yeniArac.getModel() + "\n"
                        + "Renk: " + yeniArac.getRenk() + "\n")
                        ;
            }
        });

        // Güncelleme Butonu
        Button btnGuncelle = new Button("Güncelle");
        btnGuncelle.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 5;");
        btnGuncelle.setOnMouseEntered(e -> btnGuncelle.setStyle("-fx-background-color: #388e3c; -fx-text-fill: white;"));
        btnGuncelle.setOnMouseExited(e -> btnGuncelle.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white;"));

        btnGuncelle.setOnAction(event -> {
            Klasik seciliArac = listView.getSelectionModel().getSelectedItem();
            if (seciliArac != null) {
                TextInputDialog dialog = new TextInputDialog(seciliArac.getRenk());
                dialog.setTitle("Renk Güncelle");
                dialog.setHeaderText("Araç Rengini Güncelle");
                dialog.setContentText("Yeni Renk:");

                String yeniRenk = dialog.showAndWait().orElse(null);
                if (yeniRenk != null && !yeniRenk.isBlank()) {
                    seciliArac.setRenk(yeniRenk.trim());
                    txtAracDetay.setText("Araç ID: " + seciliArac.getId() + "\n"
                            + "Model: " + seciliArac.getModel() + "\n"
                            + "Renk: " + seciliArac.getRenk() + "\n"
                            );

                    new Alert(Alert.AlertType.INFORMATION, "Araç başarıyla güncellendi!").showAndWait();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Lütfen bir araç seçiniz!").showAndWait();
            }
        });

        // Çıkış Butonu
        Button btnKapat = new Button("Kapat");
        btnKapat.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 5;");
        btnKapat.setOnMouseEntered(e -> btnKapat.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white;"));
        btnKapat.setOnMouseExited(e -> btnKapat.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;"));
        btnKapat.setOnAction(e -> stage.close());

        // Bütün elemanları ekle
        HBox buttonLayout = new HBox(10, btnGuncelle, btnKapat);
        buttonLayout.setAlignment(Pos.CENTER);

        rootLayout.getChildren().addAll(lblBaslik, listView, lblSeciliArac, txtAracDetay, buttonLayout);

        // Sahne ve Pencere Ayarları
        Scene scene = new Scene(rootLayout, 450, 400);
        stage.setScene(scene);
        stage.showAndWait();
    }





    // Trafik Yönetim Arayüzü (JavaFX)
    // Trafik Yönetim Arayüzü (JavaFX)
    public void trafigiGoster(ArrayList<Klasik> aracList, Stage parentStage) {
        Stage stage = new Stage();
        stage.initOwner(parentStage);
        stage.initModality(Modality.WINDOW_MODAL);

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #263238; -fx-border-color: #1e88e5; -fx-border-width: 2px;");

        Label title = new Label("🚦 Trafik Yönetimi Paneli");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #ffffff; -fx-padding: 15px;");

        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefHeight(200);
        outputArea.setStyle("-fx-control-inner-background: #424242; -fx-text-fill: #ffffff; -fx-font-family: 'Arial';");

        // Trafik kontrol düğmesi
        Button btnGenelTrafikKontrol = new Button("🌍 Genel Trafik Kontrolü");
        btnGenelTrafikKontrol.setPrefWidth(275);
        btnGenelTrafikKontrol.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");
        btnGenelTrafikKontrol.setOnAction(e -> {
            trafikKontrol(aracList);
            Date date = new Date(); // Tarih log eklemek için
            String mesaj = "🛑 Genel Trafik Kontrolü Tamamlandı.\nTarih: " + date + "\nTrafik Yoğunluğu: " + getTrafikYoğunluğu() + "%";

            if (getTrafikYoğunluğu() > 80) {
                mesaj += "\n⚠️ Uyarı: Trafik oldukça sıkışık!";
            } else if (getTrafikYoğunluğu() > 50) {
                mesaj += "\nℹ️ Trafik orta derecede yoğun.";
            } else {
                mesaj += "\n✅ Trafik durumu rahat.";
            }

            // Loglama için
            loglamaYap("Genel Trafik Kontrolü gerçekleştirildi. Yoğunluk: " + getTrafikYoğunluğu() + "%");
            outputArea.setText(mesaj);
        });

        // Lokasyon bazlı trafik kontrol düğmesi
        Button btnOzelAracKontrol = new Button("📍 Lokasyon Bazlı Trafik Kontrolü");
        btnOzelAracKontrol.setPrefWidth(275);
        btnOzelAracKontrol.setStyle("-fx-background-color: #ff9800; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");
        btnOzelAracKontrol.setOnAction(e -> {
            ChoiceDialog<Integer> dialog = new ChoiceDialog<>(1, 1, 2, 3, 4, 5); // Daha fazla lokasyon
            dialog.setTitle("Lokasyon Seçimi");
            dialog.setHeaderText("📍 Kontrol etmek istediğiniz lokasyonu seçiniz:");
            dialog.setContentText("➡️ Lokasyon Numarası:");

            dialog.showAndWait().ifPresent(secim -> {
                aracDurumKonum(aracList, secim);
                String mesaj = "📍 Lokasyon " + secim + ": Trafik Yoğunluğu " + getTrafikYoğunluğu() + "%";

                if (getTrafikYoğunluğu() > 80) {
                    mesaj += "\n⚠️ Uyarı: Bu lokasyon oldukça sıkışık!";
                } else if (getTrafikYoğunluğu() > 50) {
                    mesaj += "\nℹ️ Orta düzeyde yoğunluk mevcut.";
                } else {
                    mesaj += "\n✅ Trafik akışı rahat.";
                }

                // Loglama
                loglamaYap("Lokasyon Bazlı Kontrol [Lokasyon: " + secim + "] Yoğunluk: " + getTrafikYoğunluğu() + "%");
                outputArea.setText(mesaj);
            });
        });

        // Kapat düğmesi
        Button btnKapat = new Button("❌ Kapat");
        btnKapat.setPrefWidth(275);
        btnKapat.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");
        btnKapat.setOnAction(e -> stage.close());

        root.getChildren().addAll(title, btnGenelTrafikKontrol, btnOzelAracKontrol, outputArea, btnKapat);

        Scene scene = new Scene(root, 600, 500);
        stage.setScene(scene);
        stage.setTitle("🚦 Trafik Yönetimi Paneli");
        stage.show();
    }

    // Loglama İşlemi
    private void loglamaYap(String mesaj) {
        try {
            FileWriter writer = new FileWriter("trafik_loglari.txt", true); // Dosya adı
            writer.write(mesaj + "\n"); // Logu ekle
            writer.close();
        } catch (IOException e) {
            System.out.println("Log dosyasına yazılamadı: " + e.getMessage());
        }
    }
    // Araç Ekleme Arayüzü (JavaFX)
    public void AracEkleme(Stage primaryStage, ArrayList<Klasik> aracList) {
        Stage stage = new Stage();
        stage.setTitle("Araç Ekleme");
        stage.initOwner(primaryStage);
        stage.initModality(Modality.WINDOW_MODAL);

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: linear-gradient(to bottom, #263238, #37474f);");

        // Elektrikli Araç Ekleme Butonu
        Button btnEkleElektrikli = new Button("Elektrikli Araç Ekle");
        stilUygulaButon(btnEkleElektrikli, "#4CAF50", 200);

        btnEkleElektrikli.setOnAction(e -> layout.getChildren().setAll(olusturulanAracFormu("Elektrikli", aracList, true, stage)));

        // Klasik Araç Ekleme Butonu
        Button btnEkleKlasik = new Button("Klasik Araç Ekle");
        stilUygulaButon(btnEkleKlasik, "#FF9800", 200);

        btnEkleKlasik.setOnAction(e -> layout.getChildren().setAll(olusturulanAracFormu("Klasik", aracList, false, stage)));

        // Geri Dön Butonu
        Button btnKapat = new Button("Geri Dön");
        stilUygulaButon(btnKapat, "#F44336", 200);
        btnKapat.setOnAction(e -> stage.close());

        layout.getChildren().addAll(btnEkleElektrikli, btnEkleKlasik, btnKapat);

        Scene scene = new Scene(layout, 1000, 700);
        stage.setScene(scene);
        stage.showAndWait();
    }

    // Araç Formu Oluşturma
    private VBox olusturulanAracFormu(String vehicleType, ArrayList<Klasik> aracList, boolean isElektrikli, Stage stage) {
        VBox formLayout = new VBox(15);
        formLayout.setAlignment(Pos.CENTER);
        formLayout.setPadding(new Insets(20));
        formLayout.setStyle("-fx-background-color: #eceff1; -fx-border-color: #90a4ae; -fx-border-width: 2px; -fx-border-radius: 10px;");

        // Araç Modeli
        Label lblModel = new Label("Araç Modeli:");
        stilUygulaLabel(lblModel);

        TextField txtModel = new TextField();
        stilUygulaMetinAlani(txtModel, "Modeli giriniz");

        // Araç Markası
        Label lblMarka = new Label("Araç Markası:");
        stilUygulaLabel(lblMarka);

        TextField txtMarka = new TextField();
        stilUygulaMetinAlani(txtMarka, "Markayı giriniz");

        // Araç Kapasitesi
        Label lblKapasite = new Label("Araç Kapasitesi (Kişi):");
        stilUygulaLabel(lblKapasite);

        TextField txtKapasite = new TextField();
        stilUygulaMetinAlani(txtKapasite, "Kapasiteyi giriniz");

        // Araç Konumu
        Label lblKonum = new Label("Araç Bulunduğu Konum:");
        stilUygulaLabel(lblKonum);

        TextField txtKonum = new TextField();
        stilUygulaMetinAlani(txtKonum, "Konum numarasını giriniz");

        // Araç Rengi
        Label lblRenk = new Label("Araç Rengi:");
        stilUygulaLabel(lblRenk);

        TextField txtRenk = new TextField();
        stilUygulaMetinAlani(txtRenk, "Rengi giriniz");

        // Araç ID'si
        Label lblId = new Label("Araç ID:");
        stilUygulaLabel(lblId);

        TextField txtId = new TextField();
        stilUygulaMetinAlani(txtId, "ID'yi giriniz");

        // Kaydet Butonu
        Button btnKaydet = new Button("Kaydet");
        stilUygulaButon(btnKaydet, "#4CAF50", 150);

        btnKaydet.setOnAction(e -> {
            try {
                // Kullanıcı Form Girdileri
                String model = txtModel.getText().trim();
                String marka = txtMarka.getText().trim();
                int kapasite = Integer.parseInt(txtKapasite.getText());
                int bulunanYer = Integer.parseInt(txtKonum.getText());
                String renk = txtRenk.getText().trim();
                int id = Integer.parseInt(txtId.getText());

                // Doğrulamalar
                if (model.isEmpty() || marka.isEmpty() || renk.isEmpty()) {
                    throw new IllegalArgumentException("Model, Marka ve Renk boş olamaz!");
                }
                if (kapasite <= 0 || bulunanYer <= 0 || id <= 0) {
                    throw new IllegalArgumentException("Kapasite, Konum ve ID pozitif bir değer olmalıdır!");
                }

                // Araç Ekleme
                if (isElektrikli) {
                    aracList.add(new Elektrikli(renk, id,model, marka, vehicleType, kapasite, bulunanYer,  true));
                } else {
                    aracList.add(new Klasik(renk, id,model, marka, vehicleType, kapasite, bulunanYer,  true));
                }

                // Başarı Mesajı
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Başarılı");
                alert.setContentText(vehicleType + " başarıyla eklendi!");
                alert.showAndWait();

                // Alanları Temizle
                txtModel.clear();
                txtMarka.clear();
                txtKapasite.clear();
                txtKonum.clear();
                txtRenk.clear();
                txtId.clear();
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Hata");
                alert.setContentText("Lütfen geçerli bir sayı giriniz!");
                alert.showAndWait();
            } catch (IllegalArgumentException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Hata");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        });

        // Geri Butonu
        Button btnGeri = new Button("Geri");
        stilUygulaButon(btnGeri, "#F44336", 150);
        btnGeri.setOnAction(e -> {
            formLayout.getChildren().clear();
            AracEkleme(stage, aracList);
        });

        // Form Elemanlarını Düzenleyip Eklemek
        formLayout.getChildren().addAll(
                lblMarka, txtMarka,
                lblModel, txtModel,
                lblKapasite, txtKapasite,
                lblKonum, txtKonum,
                lblRenk, txtRenk,
                lblId, txtId,
                btnKaydet, btnGeri
        );

        return formLayout;
    }
    private void stilUygulaButon(Button button, String renk, int genislik) {
        button.setStyle("-fx-background-color: " + renk + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 15px;");
        button.setPrefWidth(genislik);
    }

    private void stilUygulaMetinAlani(TextField textField, String placeholder) {
        textField.setPromptText(placeholder);
        textField.setStyle("-fx-control-inner-background: #ffffff; -fx-border-color: #78909C; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-padding: 5px;");
    }

    private void stilUygulaLabel(Label label) {
        label.setStyle("-fx-text-fill: #37474F; -fx-font-size: 14px; -fx-font-weight: bold;");
    }
}