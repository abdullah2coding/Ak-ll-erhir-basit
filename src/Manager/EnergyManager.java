package Manager;

import Varliklar.EnerjiKaynaklari.EnerjiKaynaklari;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EnergyManager {
    private double uretim;  // Enerji üretimi (birim)
    private double tuketim; // Enerji tüketimi (birim)
    private static final Random random = new Random(); // Rastgele değer için
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1); // Zamanlayıcı

    // Yapıcı metod
    public EnergyManager(double uretim, double tuketim) {
        this.uretim = uretim;
        this.tuketim = tuketim;
    }

    // Üretim ve tüketim için getter ve setter'lar
    public double getUretim() {
        return uretim;
    }

    public void setUretim(double uretim) {
        this.uretim = uretim;
    }

    public double getTuketim() {
        return tuketim;
    }

    public void setTuketim(double tuketim) {
        this.tuketim = tuketim;
    }

    // Enerji Raporu Göster
    public void enerjiRaporuGoster(Stage parentStage, String kullaniciTipi, ArrayList<EnerjiKaynaklari> enerjiListesi) {
        // Yeni pencere oluştur ve temel ayarlarını yap
        Stage stage = new Stage(StageStyle.UNDECORATED);
        stage.initOwner(parentStage);
        stage.initModality(Modality.WINDOW_MODAL);

        // Pencereyi taşımak için değişkenler
        final double[] xOffset = {0};
        final double[] yOffset = {0};

        // Başlık çubuğu (Özel başlık tasarımı)
        HBox header = new HBox();
        header.setPadding(new Insets(10));
        header.setStyle("-fx-background-color: #003366; -fx-background-radius: 10 10 0 0;");
        header.setAlignment(Pos.CENTER_RIGHT);

        Label lblBaslik = new Label("💡 Enerji Raporu");
        lblBaslik.setStyle("-fx-font-size: 20px; -fx-text-fill: white; -fx-font-weight: bold;");
        HBox.setHgrow(lblBaslik, Priority.ALWAYS);

        // Kapatma düğmesi
        Button closeButton = new Button("X");
        closeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 16px;");
        closeButton.setOnMouseEntered(event -> closeButton.setStyle("-fx-background-color: #ff6666; -fx-text-fill: white;"));
        closeButton.setOnMouseExited(event -> closeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white;"));
        closeButton.setOnAction(event -> stage.close());

        header.setOnMousePressed(event -> {
            xOffset[0] = event.getSceneX();
            yOffset[0] = event.getSceneY();
        });
        header.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset[0]);
            stage.setY(event.getScreenY() - yOffset[0]);
        });

        header.getChildren().addAll(lblBaslik, closeButton);

        // Enerji raporu içeriği
        VBox contentLayout = new VBox(15);
        contentLayout.setPadding(new Insets(20));
        contentLayout.setAlignment(Pos.TOP_CENTER);
        contentLayout.setStyle("-fx-background-color: #f4f4f4; -fx-background-radius: 0 0 10 10;");

        // Grafiksel Öğeler
        ProgressBar uretimProgress = new ProgressBar();
        ProgressBar tuketimProgress = new ProgressBar();
        uretimProgress.setStyle("-fx-accent: #4caf50;");
        tuketimProgress.setStyle("-fx-accent: #ff5722;");
        uretimProgress.setPrefWidth(300);
        tuketimProgress.setPrefWidth(300);

        // Dinamik olarak değer hesaplama
        uretimProgress.setProgress((uretim > 0) ? uretim / (tuketim + uretim) : 0);
        tuketimProgress.setProgress((tuketim > 0) ? tuketim / (tuketim + uretim) : 0);

        Label lblUretim = new Label("Enerji Üretimi: " + uretim + " birim");
        lblUretim.setStyle("-fx-font-size: 14px; -fx-text-fill: #333333;");

        Label lblTuketim = new Label("Enerji Tüketimi: " + tuketim + " birim");
        lblTuketim.setStyle("-fx-font-size: 14px; -fx-text-fill: #333333;");

        Label lblDurum = new Label();
        lblDurum.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        if (tuketim > uretim) {
            lblDurum.setText("⚠️ Uyarı: Enerji tüketimi, üretimi aşmış durumda!");
            lblDurum.setStyle("-fx-text-fill: red;");
        } else if (tuketim == uretim) {
            lblDurum.setText("➖ Enerji üretimi ve tüketimi eşit.");
            lblDurum.setStyle("-fx-text-fill: orange;");
        } else {
            lblDurum.setText("✅ Enerji durumu dengeli. Fazla üretim mevcut.");
            lblDurum.setStyle("-fx-text-fill: green;");
        }

        // Çözüm önerileri düğmesi
        Button btnCozumOnerileri = new Button("🛠 Çözüm Önerileri");
        btnCozumOnerileri.setStyle("-fx-background-color: #2196f3; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 5;");
        btnCozumOnerileri.setOnMouseEntered(e -> btnCozumOnerileri.setStyle("-fx-background-color: #1976d2; -fx-text-fill: white;"));
        btnCozumOnerileri.setOnMouseExited(e -> btnCozumOnerileri.setStyle("-fx-background-color: #2196f3; -fx-text-fill: white;"));
        btnCozumOnerileri.setOnAction(e -> enerjiCozumOnerisi(stage, kullaniciTipi, enerjiListesi));

        // Kapat düğmesi
        Button btnKapat = new Button("❌ Kapat");
        btnKapat.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 5;");
        btnKapat.setOnMouseEntered(e -> btnKapat.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white;"));
        btnKapat.setOnMouseExited(e -> btnKapat.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;"));
        btnKapat.setOnAction(e -> stage.close());

        // Tüm içeriği birleştir
        contentLayout.getChildren().addAll(lblUretim, uretimProgress, lblTuketim, tuketimProgress, lblDurum, btnCozumOnerileri, btnKapat);

        VBox root = new VBox();
        root.getChildren().addAll(header, contentLayout);

        // Sahne ve pencere ayarları
        Scene scene = new Scene(root, 450, 400);
        stage.setScene(scene);
        stage.show();
    }
    // Çözüm Önerileri
    public void enerjiCozumOnerisi(Stage parentStage, String kullaniciTipi, ArrayList<EnerjiKaynaklari> enerjiKaynaklariList) {
        Stage stage = new Stage();
        stage.initOwner(parentStage);

        // Ana layout (arka plan ve padding ayarları ile)
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-background-color: #2a2a2a; -fx-border-color: #00bcd4; -fx-border-width: 2px;");

        // Başlık
        Label lblBaslik = new Label("💡 Enerji Çözüm Önerileri");
        lblBaslik.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #ffffff; -fx-padding: 10px;");

        // Bilgilendirme metni (readonly TextArea)
        TextArea txtCozumler = new TextArea();
        txtCozumler.setEditable(false);
        txtCozumler.setWrapText(true);
        txtCozumler.setStyle("-fx-control-inner-background: #424242; -fx-text-fill: #e0e0e0; -fx-font-size: 14px;");

        if (kullaniciTipi.equalsIgnoreCase("vatandas")) {
            txtCozumler.setText(
                    "1️⃣ Daha fazla yenilenebilir enerji kaynağı eklemeyi düşünün.\n" +
                            "2️⃣ Enerji tüketimini azaltmak için verimli teknolojilere geçiş yapın.\n" +
                            "3️⃣ Enerji tasarrufu sağlayacak şehir içi uygulamaları devreye alın.\n");
        } else if (kullaniciTipi.equalsIgnoreCase("admin")) {
            txtCozumler.setText(
                    "1️⃣ Yeni enerji kaynağı ekle.\n" +
                            "2️⃣ Enerji tüketimini azaltıcı önlemler öner.\n" +
                            "3️⃣ Enerji tüketimini izlemek için yeni sistemler kur.\n" +
                            "4️⃣ Detaylı enerji raporu oluştur ve yönet.\n");

            // Seçim Alanı
            Label lblSecim = new Label("🔧 Lütfen bir işlem seçin:");
            lblSecim.setStyle("-fx-text-fill: #29b6f6; -fx-font-size: 16px; -fx-padding: 10px;");

            ComboBox<String> comboBox = new ComboBox<>();
            comboBox.getItems().addAll(
                    "Yeni enerji kaynağı ekle",
                    "Tüketim azaltıcı önlemler öner",
                    "Yeni sistem kur",
                    "Detaylı rapor oluştur"
            );
            comboBox.setStyle("-fx-background-color: #424242; -fx-text-fill: #ffffff; -fx-font-size: 14px;");

            // İşlem Yap Butonu
            Button btnSecim = new Button("İşlem Yap");
            btnSecim.setStyle("-fx-background-color: #81c784; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 15px; -fx-background-radius: 10px;");
            btnSecim.setOnAction(e -> {
                String selectedOption = comboBox.getValue();
                if (selectedOption != null) {
                    switch (selectedOption) {
                        case "Yeni enerji kaynağı ekle":
                            EnerjiKaynagiEkle(enerjiKaynaklariList, parentStage);
                            break;
                        case "Tüketim azaltıcı önlemler öner":
                            bilgiMesajiGoster("⚙️ Tüketim azaltıcı önlemler önerin", stage);
                            break;
                        case "Yeni sistem kur":
                            bilgiMesajiGoster("⚙️ Yeni bir enerji yönetim sistemi kurun", stage);
                            break;
                        case "Detaylı rapor oluştur":
                            bilgiMesajiGoster("📝 Detaylı rapor oluşturun ve yönetin", stage);
                            break;
                        default:
                            bilgiMesajiGoster("⚠️ Geçersiz işlem seçildi!", stage);
                            break;
                    }
                } else {
                    bilgiMesajiGoster("⚠️ Lütfen bir işlem seçiniz!", stage);
                }
            });

            layout.getChildren().addAll(lblSecim, comboBox, btnSecim);
        } else {
            txtCozumler.setText("❌ Geçersiz kullanıcı tipi!");
            txtCozumler.setStyle("-fx-text-fill: red;");
        }

        // Kapat Butonu
        Button btnKapat = new Button("Kapat");
        btnKapat.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 15px; -fx-background-radius: 10px;");
        btnKapat.setOnAction(e -> stage.close());

        // Tüm Bileşenleri Layout'una Ekle
        layout.getChildren().addAll(lblBaslik, txtCozumler, btnKapat);

        // Sahne Ayarları
        Scene scene = new Scene(layout, 500, 400);
        stage.setScene(scene);
        stage.setTitle("💡 Enerji Çözüm Önerileri");
        stage.show();
    }

    private void bilgiMesajiGoster(String tüketimAzaltıcıÖnlemlerÖnerin, Stage stage) {
    }

    public ArrayList<EnerjiKaynaklari> EnerjiKaynagiEkle(ArrayList<EnerjiKaynaklari> enerjiListesi, Stage parentStage) {
        // Yeni bir modal pencere oluştur (parentStage ile ilişkilendirilmiş).
        Stage stage = new Stage();
        stage.initOwner(parentStage);
        stage.initModality(Modality.WINDOW_MODAL); // Parent pencereyi bloke et

        // Ana düzen (VBox) ve Grid içerisinde Form
        VBox layout = new VBox(20); // Boşluklar artırıldı
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #2d3347;"); // Modern arka plan rengi (koyu)

        GridPane formLayout = new GridPane();
        formLayout.setHgap(10);
        formLayout.setVgap(15);
        formLayout.setAlignment(Pos.CENTER);

        // Form Etiketleri ve TextField'lar
        Label lblTur = new Label("🔋 Enerji Kaynağı Türü:");
        lblTur.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        TextField txtTur = createCustomTextField();

        Label lblId = new Label("🆔 Enerji Kaynağı ID:");
        lblId.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        TextField txtId = createCustomTextField();

        Label lblKapasite = new Label("⚡ Enerji Kapasitesi:");
        lblKapasite.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        TextField txtKapasite = createCustomTextField();

        // Form elemanlarını forma ekle
        formLayout.add(lblTur, 0, 0);
        formLayout.add(txtTur, 1, 0);
        formLayout.add(lblId, 0, 1);
        formLayout.add(txtId, 1, 1);
        formLayout.add(lblKapasite, 0, 2);
        formLayout.add(txtKapasite, 1, 2);

        // Uyarı/Bilgilendirme mesajı etiketi
        Label lblMessage = new Label();
        lblMessage.setStyle("-fx-text-fill: yellow;");

        // Kaydet düğmesi
        Button btnKaydet = createCustomButton("✅ Kaydet", "#64dd17");
        btnKaydet.setOnAction(e -> {
            try {
                // Kullanıcı girişlerini al ve doğrula
                String tur = txtTur.getText();
                int id = Integer.parseInt(txtId.getText());
                double kapasite = Double.parseDouble(txtKapasite.getText());

                // Yeni enerji kaynağı oluştur ve listeye ekle
                EnerjiKaynaklari yeniKaynak = new EnerjiKaynaklari(tur, id, kapasite);
                enerjiListesi.add(yeniKaynak);

                // Üretim kapasitesini güncelle
                setUretim(getUretim() + kapasite);

                // Başarılı giriş mesajı
                lblMessage.setText("✅ Yeni enerji kaynağı başarıyla eklendi: Tür: " + tur + ", ID: " + id + ", Kapasite: " + kapasite);
                lblMessage.setStyle("-fx-text-fill: lightgreen;");

                // TextField'ları temizle
                txtTur.clear();
                txtId.clear();
                txtKapasite.clear();

                // Pencereyi kapat
                stage.close();
            } catch (NumberFormatException ex) {
                // Geçersiz giriş mesajı
                lblMessage.setText("⚠️ Lütfen geçerli bir ID ve Kapasite girin!");
                lblMessage.setStyle("-fx-text-fill: red;");
            } catch (Exception ex) {
                // Genel hata mesajı
                lblMessage.setText("⚠️ Bir hata oluştu: " + ex.getMessage());
                lblMessage.setStyle("-fx-text-fill: red;");
            }
        });

        // Kapat düğmesi
        Button btnKapat = createCustomButton("❌ Kapat", "#d50000");
        btnKapat.setOnAction(e -> stage.close());

        // Düzenin düzenleyicilere eklenmesi
        layout.getChildren().addAll(formLayout, lblMessage, btnKaydet, btnKapat);

        // Pencere görünümü
        Scene scene = new Scene(layout, 450, 350);
        stage.setScene(scene);
        stage.setTitle("💡 Enerji Kaynağı Ekle");
        stage.showAndWait();

        // Enerji listesi yalnızca işlemler başarıyla tamamlandıysa geri döner
        return enerjiListesi;
    }

    // Özel düğme oluşturma fonksiyonu (modern stil için)
    private Button createCustomButton(String text, String bgColor) {
        Button button = new Button(text);
        button.setPrefSize(150, 30); // Düğme boyutu
        button.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-color: " + bgColor + "; -fx-text-fill: white; -fx-background-radius: 10; -fx-border-radius: 10;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-color: #555555; -fx-text-fill: white; -fx-background-radius: 10; -fx-border-radius: 10;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-color: " + bgColor + "; -fx-text-fill: white; -fx-background-radius: 10; -fx-border-radius: 10;"));
        return button;
    }

    // Modern bir TextField oluşturma fonksiyonu
    private TextField createCustomTextField() {
        TextField textField = new TextField();
        textField.setStyle("-fx-font-size: 12px; -fx-border-color: #bdbdbd; -fx-border-width: 1px; -fx-border-radius: 5; -fx-background-radius: 5;");
        textField.setPrefSize(200, 30);
        return textField;
    }
    // Enerji Durumu Hesapla
    public void enerjiDurumuHesapla(Stage parentStage) {
        // Yeni bir modal pencere oluştur
        Stage stage = new Stage();
        stage.initOwner(parentStage);
        stage.initModality(Modality.WINDOW_MODAL); // Modal pencere

        // Ana düzen
        VBox layout = new VBox(15); // Elemanlar arasına boşluk koyuldu
        layout.setPadding(new Insets(25));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #2d3347;"); // Koyu tema

        // Başlık etiketi
        Label lblBaslik = new Label("💡 Enerji Durumu Analizi");
        lblBaslik.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #ffffff;");

        // Durum etiketi
        Label lblDurum = new Label();
        lblDurum.setAlignment(Pos.CENTER);

        // Enerji durumu hesaplama
        double fark = uretim - tuketim;
        lblDurum.setText(getEnerjiDurumuMesaji(fark)); // Modüler hale getirildi
        lblDurum.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: " + getDurumRengi(fark) + ";");

        // Kapat düğmesi
        Button btnKapat = createCustomButton("❌ Kapat", "#d50000");
        btnKapat.setOnAction(e -> stage.close());

        // Layout içeriklerini ekle
        layout.getChildren().addAll(lblBaslik, lblDurum, btnKapat);

        // Sahne ayarları ve pencere gösterimi
        Scene scene = new Scene(layout, 450, 250);
        stage.setScene(scene);
        stage.setTitle("Enerji Durumu Analizi");
        stage.show();
    }

    // Enerji durumu mesajı metni
    private String getEnerjiDurumuMesaji(double fark) {
        if (fark < 0) {
            return "⚠️ Enerji üretimi yetersiz! Ek enerji kaynakları eklenmeli.";
        } else if (fark == 0) {
            return "🟧 Enerji üretimi ve tüketimi dengede.";
        } else {
            return "✅ Enerji fazlası var. Fazla enerjiyi depolamak veya dışa satmak mümkün.";
        }
    }

    // Enerji durumuna göre mesaj rengi
    private String getDurumRengi(double fark) {
        if (fark < 0) {
            return "red"; // Negatif üretim-tüketim farkında kırmızı
        } else if (fark == 0) {
            return "orange"; // Dengede turuncu
        } else {
            return "green"; // Pozitif durumda yeşil
        }
    }

    // Düğme oluşturma fonksiyonu
    private Button createCustomButton( String bgColor) {
        Button button = new Button("ya hızır");
        button.setPrefSize(120, 40);
        button.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-color: " + bgColor + "; -fx-text-fill: white; -fx-background-radius: 10; -fx-border-radius: 10;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-color: #555555; -fx-text-fill: white; -fx-background-radius: 10; -fx-border-radius: 10;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-color: " + bgColor + "; -fx-text-fill: white; -fx-background-radius: 10; -fx-border-radius: 10;"));
        return button;
    }

    public void enerjiGuncelle(Stage parentStage, ArrayList<EnerjiKaynaklari> enerjiListesi) {
        // Yeni pencere oluştur ve üst kenarlığı kapat
        Stage stage = new Stage(StageStyle.UNDECORATED); // Üst çubuğu gizler
        stage.initOwner(parentStage); // Ana pencereyle ilişkilendirilir
        stage.initModality(Modality.WINDOW_MODAL); // Modal pencere yapmak için

        // Pencereyi taşımak için değişkenler
        final double[] xOffset = {0};
        final double[] yOffset = {0};

        // Ana düzen (VBox)
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #2b2b30; -fx-background-radius: 15; -fx-border-radius: 15; -fx-border-color: #bbbbbb;");

        // Özel başlık çubuğu
        HBox header = new HBox();
        header.setPadding(new Insets(10, 15, 10, 15));
        header.setAlignment(Pos.CENTER_RIGHT);
        header.setStyle("-fx-background-color: #1e1e25; -fx-background-radius: 15 15 0 0;");

        Label headerTitle = new Label("Enerji Kaynağı Güncelle");
        headerTitle.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 16px; -fx-font-weight: bold;");
        HBox.setHgrow(headerTitle, Priority.ALWAYS);

        Button closeButton = new Button("X");
        closeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 16px;");
        closeButton.setOnMouseEntered(e -> closeButton.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white; -fx-font-size: 16px;"));
        closeButton.setOnMouseExited(e -> closeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 16px;"));
        closeButton.setOnAction(e -> stage.close());

        header.setOnMousePressed(event -> {
            xOffset[0] = event.getSceneX();
            yOffset[0] = event.getSceneY();
        });

        header.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset[0]);
            stage.setY(event.getScreenY() - yOffset[0]);
        });

        header.getChildren().addAll(headerTitle, closeButton);

        // Enerji Türü
        Label lblTur = new Label("Enerji Kaynağı Türü:");
        lblTur.setStyle("-fx-font-size: 14px; -fx-text-fill: #ffffff;");
        TextField txtTur = new TextField();
        txtTur.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-background-color: #3e4049; -fx-text-fill: white;");

        // Enerji ID
        Label lblId = new Label("Enerji Kaynağı ID:");
        lblId.setStyle("-fx-font-size: 14px; -fx-text-fill: #ffffff;");
        TextField txtId = new TextField();
        txtId.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-background-color: #3e4049; -fx-text-fill: white;");

        // Güncelle Butonu
        Button btnGuncelle = new Button("Güncelle");
        btnGuncelle.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 5;");
        btnGuncelle.setOnMouseEntered(event -> btnGuncelle.setStyle("-fx-background-color: #388e3c; -fx-text-fill: white;"));
        btnGuncelle.setOnMouseExited(event -> btnGuncelle.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white;"));

        // Güncelleme İşlemi
        btnGuncelle.setOnAction(event -> {
            try {
                // Kullanıcı girişlerini al
                String tur = txtTur.getText().trim();
                int id = Integer.parseInt(txtId.getText().trim());

                // Güncelleme işlemi
                boolean bulundu = false;
                for (EnerjiKaynaklari enerji : enerjiListesi) {
                    if (enerji.getId() == id && enerji.getTur().equalsIgnoreCase(tur)) {
                        // Yeni tür ve ID al
                        TextInputDialog turDialog = new TextInputDialog(enerji.getTur());
                        turDialog.setTitle("Tür Güncelle");
                        turDialog.setHeaderText("Yeni Tür Giriniz:");
                        String yeniTur = turDialog.showAndWait().orElse(null);

                        TextInputDialog idDialog = new TextInputDialog(String.valueOf(enerji.getId()));
                        idDialog.setTitle("ID Güncelle");
                        idDialog.setHeaderText("Yeni ID Giriniz:");
                        String yeniIdInput = idDialog.showAndWait().orElse(null);

                        if (yeniTur != null && yeniIdInput != null) {
                            int yeniId = Integer.parseInt(yeniIdInput.trim());
                            enerji.setTur(yeniTur.trim());
                            enerji.setId(yeniId);

                            // Başarı mesajı
                            Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Enerji kaynağı başarıyla güncellendi!");
                            successAlert.showAndWait();
                            bulundu = true;
                        }
                        break;
                    }
                }

                if (!bulundu) {
                    new Alert(Alert.AlertType.WARNING, "Enerji kaynağı bulunamadı!").showAndWait();
                }
            } catch (NumberFormatException ex) {
                // Hatalı girişlerde mesaj
                new Alert(Alert.AlertType.ERROR, "Lütfen doğru bir ID girin!").showAndWait();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Bir hata oluştu: " + ex.getMessage()).showAndWait();
            }
        });

        // İçeriği düzenle
        layout.getChildren().addAll(header, lblTur, txtTur, lblId, txtId, btnGuncelle);

        // Sahne ve pencere ayarları
        Scene scene = new Scene(layout, 400, 300);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void enerjiKaynakSorgusuFX(Stage parentStage, ArrayList<EnerjiKaynaklari> enerjiListesi, double minSeviye) {
        // Ana Pencere için Layout
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-background-color: #2d3347;");
        mainLayout.setAlignment(Pos.TOP_CENTER);

        // Başlık
        Label baslik = new Label("🔋 Enerji Kaynağı İzleme Sistemi");
        baslik.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
        HBox baslikLayout = new HBox(baslik);
        baslikLayout.setAlignment(Pos.CENTER);

        // Uyarılar için alan
        Label lblWarning = new Label();
        lblWarning.setStyle("-fx-text-fill: orange; -fx-font-size: 16px; -fx-padding: 10px;");

        // Tablo (Enerji Kaynaklarını göstermek için)
        TableView<EnerjiKaynaklari> tableView = new TableView<>();
        TableColumn<EnerjiKaynaklari, String> turColumn = new TableColumn<>("Tür");
        turColumn.setCellValueFactory(data -> data.getValue().turProperty());
        turColumn.setPrefWidth(150);

        TableColumn<EnerjiKaynaklari, Double> kapasiteColumn = new TableColumn<>("Kapasite");
        kapasiteColumn.setCellValueFactory(data -> data.getValue().kapasiteProperty().asObject());
        kapasiteColumn.setPrefWidth(150);

        tableView.getColumns().addAll(turColumn, kapasiteColumn);
        tableView.getItems().addAll(enerjiListesi);

        // Üretim seviyesi barı
        ProgressBar uretimProgressBar = new ProgressBar(0);
        uretimProgressBar.setStyle("-fx-accent: green;");
        uretimProgressBar.setPrefWidth(300);

        // Üretim seviyesini gösteren etiket
        Label lblUretimSeviyesi = new Label("Üretim Seviyesi: 0");
        lblUretimSeviyesi.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");

        // Güncellemeleri başlat
        startEnergyMonitoringFX(enerjiListesi, minSeviye, lblWarning, lblUretimSeviyesi, uretimProgressBar, tableView);

        // Kapat Butonu
        Button btnKapat = new Button("❌ Kapat");
        btnKapat.setStyle("-fx-background-color: #f44336; -fx-font-size: 14px; -fx-text-fill: white; -fx-padding: 8px 15px; -fx-background-radius: 5px;");
        btnKapat.setOnAction(e -> {
            stopEnergyMonitoring(); // İzlemeyi durdur
            parentStage.close(); // Pencereyi kapat
        });

        // Layout'a bileşenleri ekle
        mainLayout.getChildren().addAll(baslikLayout, tableView, lblUretimSeviyesi, uretimProgressBar, lblWarning, btnKapat);

        // Sahne ve Stage ayarları
        Scene scene = new Scene(mainLayout, 520, 480);
        parentStage.setScene(scene);
        parentStage.setTitle("Enerji İzleme Sistemi");
        parentStage.show();
    }

    // Arka planda enerji kaynaklarını izleme işlemi
    private void startEnergyMonitoringFX(ArrayList<EnerjiKaynaklari> enerjiListesi, double minSeviye,
                                         Label lblWarning, Label lblUretimSeviyesi,
                                         ProgressBar uretimProgressBar, TableView<EnerjiKaynaklari> tableView) {
        scheduler.scheduleAtFixedRate(() -> {
            // Kapasiteleri güncelle
            updateEnergySourceCapacities(enerjiListesi);

            // Toplam üretimi hesapla
            double totalUretim = enerjiListesi.stream().mapToDouble(EnerjiKaynaklari::getKapasitesi).sum();

            // JavaFX bileşenlerini güncellemek için Platform.runLater kullanılır
            Platform.runLater(() -> {
                lblUretimSeviyesi.setText("Üretim Seviyesi: " + String.format("%.2f", totalUretim));
                uretimProgressBar.setProgress(Math.min(totalUretim / minSeviye, 1.0)); // Bar doluluğu

                if (totalUretim < minSeviye) {
                    lblWarning.setText("⚠️ Uyarı: Enerji seviyesi düşük! Mevcut seviye: " + totalUretim);
                    lblWarning.setStyle("-fx-text-fill: red;");
                } else {
                    lblWarning.setText("✅ Enerji durumu yeterli.");
                    lblWarning.setStyle("-fx-text-fill: green;");
                }

                // Tabloyu yenile
                tableView.refresh();
            });
        }, 0, 10, TimeUnit.SECONDS); // 10 saniyede bir çalışır
    }

    // Enerji kaynaklarının kapasitelerini rastgele güncelle
    private void updateEnergySourceCapacities(ArrayList<EnerjiKaynaklari> enerjiListesi) {
        for (EnerjiKaynaklari kaynak : enerjiListesi) {
            double kapasiteDegisiklikOrani = 0.2 * kaynak.getKapasitesi(); // %20 değişiklik aralığı
            double yeniDeger = kaynak.getKapasitesi() + (random.nextDouble() - 0.5) * 2 * kapasiteDegisiklikOrani;
            kaynak.setKapasitesi(Math.max(yeniDeger, 0)); // Kapasite sıfırın altına düşmesin
        }
    }

    // İzlemeyi durdur
    public void stopEnergyMonitoring() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
        }
    }
}


