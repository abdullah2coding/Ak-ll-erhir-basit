package Login;

import SistemKulanicilari.Admin;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class AdminLogin {

    public Admin showLoginScreen(Stage primaryStage, ArrayList<Admin> adminList) {
        // Giriş yapılacak admini tutacak olan değişken
        final Admin[] girisYapanAdmin = {null};

        // Yeni bir sahne oluştur
        Stage loginStage = new Stage();

        // Root (ana) düzenek
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #1E1E2E; -fx-border-color: #3E4451; -fx-border-width: 2px; -fx-border-radius: 10px;");

        // "ADMIN LOGIN" Başlığı
        Label lblTitle = new Label("ADMIN LOGIN");
        lblTitle.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #FFFFFF;");

        // Etiketler ve giriş alanları
        Label lblIsim = new Label("İsim:");
        lblIsim.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #C8D1E0;");

        TextField txtIsim = new TextField();
        txtIsim.setPromptText("İsminizi Girin");
        txtIsim.setStyle("-fx-font-size: 16px; -fx-background-color: #2A2D37; -fx-text-fill: #C1C8D1; -fx-border-color: #5A6270; -fx-border-radius: 12px; -fx-background-radius: 12px; -fx-padding: 10px;");

        Label lblSoyad = new Label("Soyad:");
        lblSoyad.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #C8D1E0;");

        TextField txtSoyad = new TextField();
        txtSoyad.setPromptText("Soyadınızı Girin");
        txtSoyad.setStyle("-fx-font-size: 16px; -fx-background-color: #2A2D37; -fx-text-fill: #C1C8D1; -fx-border-color: #5A6270; -fx-border-radius: 12px; -fx-background-radius: 12px; -fx-padding: 10px;");

        Label lblNumara = new Label("Numara:");
        lblNumara.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #C8D1E0;");

        TextField txtNumara = new TextField();
        txtNumara.setPromptText("Numaranızı Girin");
        txtNumara.setStyle("-fx-font-size: 16px; -fx-background-color: #2A2D37; -fx-text-fill: #C1C8D1; -fx-border-color: #5A6270; -fx-border-radius: 12px; -fx-background-radius: 12px; -fx-padding: 10px;");

        Label lblSifre = new Label("Şifre:");
        lblSifre.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #C8D1E0;");

        PasswordField txtSifre = new PasswordField();
        txtSifre.setPromptText("Şifrenizi Girin");
        txtSifre.setStyle("-fx-font-size: 16px; -fx-background-color: #2A2D37; -fx-text-fill: #C1C8D1; -fx-border-color: #5A6270; -fx-border-radius: 12px; -fx-background-radius: 12px; -fx-padding: 10px;");

        Label lblResult = new Label(); // Hata mesajları için etiket
        lblResult.setStyle("-fx-font-size: 14px; -fx-text-fill: red; -fx-font-weight: bold;");

        // Giriş Butonu
        Button btnGiris = new Button("Giriş");
        btnGiris.setStyle("-fx-font-size: 18px; -fx-background-color: #3A9A5D; -fx-text-fill: #FFFFFF; -fx-background-radius: 15px; -fx-padding: 12px;");

        // İptal Butonu
        Button btnIptal = new Button("İptal");
        btnIptal.setStyle("-fx-font-size: 18px; -fx-background-color: #D9534F; -fx-text-fill: #FFFFFF; -fx-background-radius: 15px; -fx-padding: 12px;");

        // Giriş Buton Aksiyonu
        btnGiris.setOnAction(event -> {
            String isim = txtIsim.getText();
            String soyAd = txtSoyad.getText();
            String numaraStr = txtNumara.getText();
            String sifreStr = txtSifre.getText();

            try {
                if (isim.isEmpty() || soyAd.isEmpty() || numaraStr.isEmpty() || sifreStr.isEmpty()) {
                    showAlert("Hata", "Lütfen tüm alanları doldurun.", Alert.AlertType.ERROR);
                    return;
                }

                int numara = Integer.parseInt(numaraStr);
                int sifre = Integer.parseInt(sifreStr);

                for (Admin admin : adminList) {
                    if (admin.getIsim().equals(isim) &&
                            admin.getSoyAd().equals(soyAd) &&
                            admin.getNumara() == numara &&
                            admin.getSifre() == sifre) {
                        showAlert("Başarılı", "Giriş başarılı!", Alert.AlertType.INFORMATION);
                        girisYapanAdmin[0] = admin;
                        loginStage.close();
                        return;
                    }
                }
                lblResult.setText("Kullanıcı bilgileri hatalı, tekrar deneyin.");
            } catch (NumberFormatException e) {
                lblResult.setText("Hatalı giriş yapıldı. Numerik veri girilmeli.");
            }
        });

        btnIptal.setOnAction(event -> loginStage.close());

        // Butonları yatay düzenlemek için kutu
        HBox buttonBox = new HBox(15, btnGiris, btnIptal);
        buttonBox.setAlignment(Pos.CENTER);

        // Bütün bileşenleri root'a ekleyin
        root.getChildren().addAll(lblTitle, lblIsim, txtIsim, lblSoyad, txtSoyad, lblNumara, txtNumara, lblSifre, txtSifre, buttonBox, lblResult);

        // Sahneyi oluştur ve göster
        Scene scene = new Scene(root, 800, 600);
        loginStage.setTitle("Admin Girişi");
        loginStage.setScene(scene);
        loginStage.showAndWait();

        return girisYapanAdmin[0];
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}