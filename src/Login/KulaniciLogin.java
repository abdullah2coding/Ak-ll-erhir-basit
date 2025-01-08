package Login;

import SistemKulanicilari.Vatandas;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class KulaniciLogin {

    public Vatandas vatandasGiris(ArrayList<Vatandas> vatandasList, Stage primaryStage) {
        // AtomicReference kullanılarak bir Vatandas nesnesi referansı tutulur
        AtomicReference<Vatandas> sonuc = new AtomicReference<>(null);

        // ** JavaFX Form Tasarımı **
        VBox loginLayout = new VBox(15);
        loginLayout.setPadding(new Insets(40));
        loginLayout.setAlignment(Pos.CENTER);
        loginLayout.setStyle("-fx-background-color: linear-gradient(to bottom, #2B5876, #4E4376); -fx-border-color: #FFFFFF; -fx-border-radius: 15px;");

        // Gölge efekti
        DropShadow shadow = new DropShadow();
        shadow.setRadius(8.0);
        shadow.setOffsetX(3.0);
        shadow.setOffsetY(3.0);
        shadow.setColor(Color.color(0.3, 0.3, 0.3));

        // Başlık
        Label lblTitle = new Label("Vatandaş Giriş Sistemi");
        lblTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");
        lblTitle.setEffect(shadow);

        // Etiket ve Giriş Alanları
        Label lblIsim = new Label("Vatandaş İsmi:");
        lblIsim.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #E0E0E0;");

        TextField txtIsim = new TextField();
        txtIsim.setPromptText("İsminizi Girin");
        txtIsim.setStyle("-fx-font-size: 16px; -fx-background-color: rgba(255, 255, 255, 0.9); -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-padding: 8px; -fx-text-fill: #333333;");
        txtIsim.setEffect(shadow);

        Label lblNumara = new Label("Vatandaş Numaranız:");
        lblNumara.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #E0E0E0;");

        PasswordField txtNumara = new PasswordField();
        txtNumara.setPromptText("Numaranızı Girin");
        txtNumara.setStyle("-fx-font-size: 16px; -fx-background-color: rgba(255, 255, 255, 0.9); -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-padding: 8px; -fx-text-fill: #333333;");
        txtNumara.setEffect(shadow);

        Label lblStatus = new Label(); // Hata durumlarını göstermek için etiket
        lblStatus.setStyle("-fx-font-size: 14px; -fx-text-fill: yellow; -fx-font-weight: bold;");

        // Butonlar
        Button btnGiris = new Button("Giriş Yap");
        btnGiris.setStyle("-fx-font-size: 16px; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 15px; -fx-padding: 10px;");
        btnGiris.setEffect(shadow);

        Button btnIptal = new Button("İptal Et");
        btnIptal.setStyle("-fx-font-size: 16px; -fx-background-color: #FF0000; -fx-text-fill: white; -fx-background-radius: 15px; -fx-padding: 10px;");
        btnIptal.setEffect(shadow);

        // Kullanıcı Girişi Aksiyonu
        btnGiris.setOnAction(event -> {
            String isim = txtIsim.getText();
            String numaraStr = txtNumara.getText();

            if (isim.isEmpty() || numaraStr.isEmpty()) {
                lblStatus.setText("Lütfen tüm alanları doldurun!");
                return;
            }

            try {
                int numara = Integer.parseInt(numaraStr);

                // Giriş bilgileri kontrol edilir
                for (Vatandas v : vatandasList) {
                    if (v.getAd().equals(isim) && v.getNumara() == numara) {
                        lblStatus.setTextFill(Color.GREEN);
                        lblStatus.setText("Giriş Başarılı! Hoşgeldiniz :)");
                        sonuc.set(v); // Başarılı girişte sonucu ayarla
                        primaryStage.close(); // Login penceresini kapat
                        return;
                    }
                }
                lblStatus.setTextFill(Color.RED);
                lblStatus.setText("Geçersiz isim veya numara!");

            } catch (NumberFormatException e) {
                lblStatus.setTextFill(Color.RED);
                lblStatus.setText("Numara sadece sayılardan oluşabilir!");
            }
        });

        // İptal Aksiyonu
        btnIptal.setOnAction(event -> primaryStage.close());

        // Buton Kutusu
        HBox buttonBox = new HBox(20, btnGiris, btnIptal);
        buttonBox.setAlignment(Pos.CENTER);

        // JavaFX tasarımına tüm elementleri ekle
        loginLayout.getChildren().addAll(lblTitle, lblIsim, txtIsim, lblNumara, txtNumara, buttonBox, lblStatus);

        // Sahne tasarımı
        Scene loginScene = new Scene(loginLayout, 600, 400);

        primaryStage.setTitle("Vatandaş Giriş");
        primaryStage.setScene(loginScene);
        primaryStage.getIcons().clear(); // Gerekirse pencere ikonunu ayarla
        primaryStage.showAndWait();

        return sonuc.get(); // Giriş başarılıysa Vatandas nesnesini döner, değilse null
    }
}