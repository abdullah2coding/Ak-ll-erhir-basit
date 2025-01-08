
import Manager.*;
import Varliklar.Araclar.Elektrikli;
import Varliklar.Binalar.AkilliBinalar;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import Login.AdminLogin;
import Login.KulaniciLogin;
import SistemKulanicilari.Admin;
import SistemKulanicilari.Vatandas;
import Varliklar.Araclar.Klasik;
import Varliklar.Binalar.KlasikBinalar;
import Varliklar.EnerjiKaynaklari.EnerjiKaynaklari;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {

    private static ArrayList<Admin> adminList = new ArrayList<>();
    private static ArrayList<Klasik> aracList = new ArrayList<>();
    private static ArrayList<KlasikBinalar> binaListesi = new ArrayList<>();
    private static ArrayList<EnerjiKaynaklari> enerjiListesi = new ArrayList<>();

    private static TrafficManager trafficManager = new TrafficManager();
    private static EnergyManager energyManager;
    private static WaterManager waterManager = new WaterManager(2500, 1000);
    private static BinaManager binaManager = new BinaManager(binaListesi);
    @Override
    public void start(Stage primaryStage) {
        // Enerji Kaynaklarını Ayarla
        setUpEnerjiKaynaklari();

        // Admin Başlangıç Verileri
        adminList.add(new Admin("ali", "delil", 10, 123));

        // Vatandaş Başlangıç Verisi
        ArrayList<KlasikBinalar> vatandasBinalarListesi = new ArrayList<>();
        ArrayList<Klasik> vatandasAracListesi = new ArrayList<>();

        KlasikBinalar n=new KlasikBinalar(1,"klasik",4,45);
        vatandasBinalarListesi.add(n);

        AkilliBinalar akilliBinalar=new AkilliBinalar(2,"Akilli",1,80,true);
        vatandasBinalarListesi.add(akilliBinalar);

        Elektrikli E=new Elektrikli("mavi",11,"mikra","nisan","Elektrikli",4,1,true);
        vatandasAracListesi.add(E);

        Klasik klasik=new Klasik("gri",12,"m2","volvo","klasik",6,1,false);
        vatandasAracListesi.add(klasik);
       binaListesi.add(n);
       binaListesi.add(akilliBinalar);

        //arac listesi oluşturma
        aracList.add(klasik);
        aracList.add(E);
        aracList.add(new Klasik("siyah",13,"sivic","honda","klasik",4,1,true));
        aracList.add(new Elektrikli("beyaz",14,"y","tesla","elektrikli",4,1,false));


        Vatandas vatandas = new Vatandas(123, "abdullah", "demir",vatandasBinalarListesi,vatandasAracListesi,primaryStage,trafficManager,energyManager,waterManager);
        ArrayList<Vatandas> vataList = new ArrayList<>();
        vataList.add(vatandas);

        // *** Ana Menü ***
        VBox mainMenu = new VBox(10);
        mainMenu.setStyle("-fx-padding: 20; -fx-alignment: center;");



        // Arka plan resmini ekleyin
        Image backgroundImage;
        try {
            // Resmin proje kaynak dizininden yüklenmesi
            backgroundImage = new Image(getClass().getResource("/resimler/0_sZ52rKKG2B_6vcNn.jpg").toExternalForm());
            if (backgroundImage.isError()) {
                throw new IllegalArgumentException("Resim yüklenemedi!");
            }
        } catch (Exception e) {
            System.out.println("Hata: " + e.getMessage());
            // Eğer resim yüklenemezse, fallback için null atanıyor
            backgroundImage = null;
        }

// Eğer arka plan resmi (backgroundImage) başarıyla yüklendi ise işlemler yapılır
        if (backgroundImage != null) {
            BackgroundImage bImage = new BackgroundImage(
                    backgroundImage,             // Kullanılacak resim
                    BackgroundRepeat.NO_REPEAT,  // Yatay tekrar etmeyecek
                    BackgroundRepeat.NO_REPEAT,  // Dikey tekrar etmeyecek
                    BackgroundPosition.CENTER,   // Merkeze hizalanacak
                    new BackgroundSize(1.0, 1.0, true, true, false, false) // Dinamik boyutlandırma
            );

            // Arka plan olarak ayarlanır
            mainMenu.setBackground(new Background(bImage));
        }
        Label welcomeLabel = new Label("Hoşgeldiniz! Giriş yapmak için bir seçeneği tıklayın:");
        welcomeLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold; -fx-background-color:#0000; -fx-text-fill: black; ");
        Button btnVatandasGiris = new Button("Vatandaş Girişi");
        btnVatandasGiris.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        btnVatandasGiris.setMinWidth(200);
        btnVatandasGiris.setMinHeight(50);

        Button btnAdminGiris = new Button("Admin Girişi");
        btnAdminGiris.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        btnAdminGiris.setMinWidth(200);
        btnAdminGiris.setMinHeight(50);
        Button btnCikis = new Button("Çıkış");
        btnCikis.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color: #FF0000; -fx-text-fill: white;");
        btnCikis.setMinWidth(200);
        btnCikis.setMinHeight(50);

        // Vatandaş Giriş Aksiyonu - Klasik Türde
        btnVatandasGiris.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                KulaniciLogin kl = new KulaniciLogin();
                Vatandas girisYapanVatandas = kl.vatandasGiris(vataList, new Stage());
                if (girisYapanVatandas != null) {
                    vatandasMenu(primaryStage, girisYapanVatandas,vatandasAracListesi);
                } else {
                    showAlert("Giriş Başarısız", "Lütfen kullanıcı bilgilerinizi kontrol edin.", Alert.AlertType.ERROR);
                }
            }
        });

        // Admin Giriş Aksiyonu
        btnAdminGiris.setOnAction(event -> {
            AdminLogin login = new AdminLogin();
            Admin girisYapanAdmin = login.showLoginScreen(primaryStage,adminList);
            if (girisYapanAdmin != null) {
                adminMenu(primaryStage, girisYapanAdmin);
            } else {
                showAlert("Giriş Başarısız", "Lütfen admin bilgilerinizi kontrol edin.", Alert.AlertType.ERROR);
            }
        });

        // Çıkış Aksiyonu
        btnCikis.setOnAction(event -> {
            primaryStage.close();
        });

        mainMenu.getChildren().addAll(welcomeLabel, btnVatandasGiris, btnAdminGiris, btnCikis);

        Scene scene = new Scene(mainMenu, 1200, 750);
        primaryStage.setTitle("Şehir Yönetim Sistemi");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Vatandaş Menüsü
    private void vatandasMenu(Stage primaryStage, Vatandas vatandas,ArrayList<Klasik> vatandasAracList1) {
        // Ana Layout (VBox)
        VBox vatandasBox = new VBox(20); // Boşluk artırıldı
        vatandasBox.setPadding(new Insets(30));
        vatandasBox.setAlignment(Pos.CENTER);
        vatandasBox.setStyle("-fx-background-color: #2d3447;");


        // Hoşgeldiniz Mesajı
        Label welcomeLabel = new Label("Hoşgeldiniz " + vatandas.getAd() + " " + vatandas.getSoyAd());
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #ffffff;"); // Modern font ve renkler

        // Düğmeler
        Button btnTrafikKontrol = createCustomButton("🚦 Trafik Kontrolü", "#64dd17");
        Button btnEnerjiRaporu = createCustomButton("⚡ Enerji Raporu Görüntüle", "#4fc3f7");
        Button btnSuRaporu = createCustomButton("💧 Su Rezervlerini Görüntüle", "#ffab00");
        Button btnSahipOlduklarin = createCustomButton("📂 Sahip Oldukların", "#ffa726");
        Button btnGeri = createCustomButton("🔙 Ana Menüye Dön", "#d50000");

        energyManager.enerjiKaynakSorgusuFX(primaryStage,enerjiListesi, 300.0);
        // Trafik Kontrolü Aksiyonu
        btnTrafikKontrol.setOnAction(event -> {
            trafficManager.trafigiGoster(aracList, primaryStage);
            showAlert("Trafik Kontrolü", "Trafik kontrolü gerçekleştirildi.", Alert.AlertType.INFORMATION);
        });

        // Enerji Raporu
        btnEnerjiRaporu.setOnAction(event -> {
            energyManager.enerjiRaporuGoster(primaryStage, "vatandas", enerjiListesi);
            showAlert("Enerji Raporu", "Enerji raporu başarılı bir şekilde görüntülendi.", Alert.AlertType.INFORMATION);
        });

        // Su Raporu
        btnSuRaporu.setOnAction(event -> {
            try {
                String rapor = waterManager.suRaporuGoster();
                waterManager.suSeviyesiKontrol();
                showAlert("Su Raporu", rapor, Alert.AlertType.INFORMATION);
            } catch (WaterLevelCriticalException e) {
                showAlert("Kritik Su Seviyesi", e.getMessage(), Alert.AlertType.WARNING);
            } catch (Exception e) {
                showAlert("Hata", "Bilinmeyen bir hata oluştu: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        });

        // Sahip Oldukların Paneli
        btnSahipOlduklarin.setOnAction(event -> {
            vatandas.VariklarimPaneli(vatandas, primaryStage, trafficManager,vatandasAracList1);
        });

        // Ana Menüye Geri Dön
        btnGeri.setOnAction(event -> {
            start(primaryStage);
        });

        // Layout'a düğmeleri ve başlığı ekle
        vatandasBox.getChildren().addAll(welcomeLabel, btnTrafikKontrol, btnEnerjiRaporu, btnSuRaporu, btnSahipOlduklarin, btnGeri);

        // Pencere boyutlandırması
        Scene scene = new Scene(vatandasBox, 1100, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Admin Menüsü (Genişletilmiş)
    private void adminMenu(Stage primaryStage, Admin admin) {
        // Ana Layout (VBox)
        VBox adminBox = new VBox(20);
        adminBox.setPadding(new Insets(30));
        adminBox.setAlignment(Pos.CENTER);
        adminBox.setStyle("-fx-background-color: #2d3447;"); // Daha modern bir arka plan

        // Hoşgeldiniz Etiketi
        Label welcomeLabel = new Label("Hoşgeldiniz Admin: " + admin.getIsim());
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #ffffff;");

        // Butonları oluştur
        Button btnVarlikEkle = createCustomButton("➕ Yeni Varlık Ekle", "#64dd17");
        Button btnVarlikGuncelle = createCustomButton("🔄 Varlık Güncelle", "#4fc3f7");
        Button btnSehirRaporu = createCustomButton("📊 Şehir Raporlarını Görüntüle", "#ffab00");
        Button btnTumAraclar = createCustomButton("🚗 Tüm Araçları Görüntüle", "#ff6f00");
        Button btnYardim = createCustomButton("❓ Yardım", "#0091ea");
        Button btnGeri = createCustomButton("🔙 Ana Menüye Dön", "#d50000");


        energyManager.enerjiKaynakSorgusuFX(primaryStage,enerjiListesi, 300.0);
        // Varlık Ekleme Aksiyonu
        btnVarlikEkle.setOnAction(event -> {
            admin.yeniVarlikEkleme(primaryStage, energyManager, trafficManager, aracList, enerjiListesi, binaListesi);
            showAlert("Varlık Ekleme", "Bu işlev, yeni bir varlık türü ekleme işlevini içerir (örneğin, bina veya araç).", Alert.AlertType.INFORMATION);
        });

        // Varlık Güncelleme Aksiyonu
        btnVarlikGuncelle.setOnAction(event -> {
            admin.varlikGuncelleme(primaryStage,binaManager, trafficManager, energyManager, waterManager, aracList, binaListesi, enerjiListesi);
            showAlert("Varlık Güncelleme", "Bu işlev, varlık türlerini güncelleme işlemini içerir.", Alert.AlertType.INFORMATION);
        });

        // Şehir Raporu Aksiyonu
        btnSehirRaporu.setOnAction(event -> {
            waterManager.adminSutakibi(primaryStage);

            admin.sehirRaporu(primaryStage, trafficManager, energyManager, waterManager, enerjiListesi, aracList);
        });

        // Tüm Araçları Görüntüleme Aksiyonu
        btnTumAraclar.setOnAction(event -> {
            admin.tumAraclariGoster(primaryStage, aracList);
        });

        // Yardım Menüsü Aksiyonu
        btnYardim.setOnAction(event -> {
            showAlert(
                    "Yardım",
                    """
                    1. 'Yeni Varlık Ekle': Yeni bir varlık ekleyebilirsiniz.
                    2. 'Şehir Raporlarını Görüntüle': Şehirdeki enerji, su ve trafik durumları hakkında rapor alabilirsiniz.
                    3. 'Varlıklar Güncelle': Mevcut varlıkları düzenleyebilirsiniz.
                    4. 'Tüm Araçları Görüntüle': Mevcut tüm araçların listesini ve özelliklerini görebilirsiniz.
                    5. 'Geri Dön': Ana Menüye hızlı dönüş yapabilirsiniz.
                    """,
                    Alert.AlertType.INFORMATION
            );
        });

        // Ana Menüye Dön
        btnGeri.setOnAction(event -> {
            start(primaryStage);
        });

        // Elemanları Layout'a ekle
        adminBox.getChildren().addAll(
                welcomeLabel,
                btnVarlikEkle,
                btnVarlikGuncelle,
                btnSehirRaporu,
                btnTumAraclar,
                btnYardim,
                btnGeri
        );

        // Animasyon: Fade (Pencere açıldığında)
        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), adminBox);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        // Sahne Ayarları
        Scene scene = new Scene(adminBox, 1100, 700);
        primaryStage.setScene(scene);
    }

    // Bir buton oluşturmak için yardımcı fonksiyon
    private Button createCustomButton(String text, String bgColor) {
        Button button = new Button(text);
        button.setPrefSize(300, 50);
        button.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #ffffff; " +
                "-fx-background-color: " + bgColor + "; -fx-background-radius: 10; -fx-border-radius: 10;");

        button.setOnMouseEntered(e -> button.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #ffffff; " +
                "-fx-background-color: " + darkenColor(bgColor) + "; -fx-background-radius: 10; -fx-border-radius: 10;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #ffffff; " +
                "-fx-background-color: " + bgColor + "; -fx-background-radius: 10; -fx-border-radius: 10;"));

        return button;
    }

    // Renk karanlıklaştırma fonksiyonu
    private String darkenColor(String color) {
        // Hex rengini bir miktar karartmak için basit bir yöntem
        int r = Integer.parseInt(color.substring(1, 3), 16);
        int g = Integer.parseInt(color.substring(3, 5), 16);
        int b = Integer.parseInt(color.substring(5, 7), 16);

        r = (int) (r * 0.85);
        g = (int) (g * 0.85);
        b = (int) (b * 0.85);

        return String.format("#%02x%02x%02x", r, g, b);
    }

    // Bildirimler için şık bir alert
    private void showAlert(String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("birşeyler ters gitti");
        alert.setContentText(content);
        alert.setHeaderText(null);
        alert.initStyle(StageStyle.UTILITY);
        alert.getDialogPane().setStyle("-fx-background-color: #37474f; -fx-text-fill: #ffffff;");
        alert.showAndWait();
    }
    // Enerji Kaynaklarını Ayarla
    private void setUpEnerjiKaynaklari() {
        enerjiListesi.add(new EnerjiKaynaklari("Güneş", 12, 1000.0));
        enerjiListesi.add(new EnerjiKaynaklari("Rüzgar", 11, 800.0));
        enerjiListesi.add(new EnerjiKaynaklari("Nükleer", 10, 500.0));
        enerjiListesi.add(new EnerjiKaynaklari("Fosil Yakıt", 9, 1200.0));
        double toplamKapasite = 0;
        for (EnerjiKaynaklari enerji : enerjiListesi) {
            toplamKapasite += enerji.getKapasitesi();
        }
        double tuketim = new Random().doubles(1, toplamKapasite - 1000.0, toplamKapasite + 1000.0)
                .findFirst()
                .getAsDouble();
        energyManager = new EnergyManager(toplamKapasite, tuketim);
    }

    // Bilgilendirme Penceresi
    private void showAlert(String baslik, String mesaj, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(baslik);
        alert.setContentText(mesaj);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}