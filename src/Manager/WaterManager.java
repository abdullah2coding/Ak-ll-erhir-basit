package Manager;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

import java.util.Random;

public class WaterManager {

    private double suRezervi;
    private double suTuketimi;

    public WaterManager(double suRezervi, double suTuketimi) {
        Random random = new Random();
        this.suRezervi = suRezervi*random.nextInt(101)/100.0;
        this.suTuketimi = suTuketimi*random.nextInt(101)/100.0;
    }

    public double getSuRezervi() {
        return suRezervi;
    }

    public void setSuRezervi(double suRezervi) {
        this.suRezervi = suRezervi;
    }

    public double getSuTuketimi() {
        return suTuketimi;
    }

    public void setSuTuketimi(double suTuketimi) {
        this.suTuketimi = suTuketimi;
    }

    public String suRaporuGoster() {
        StringBuilder rapor = new StringBuilder();
        rapor.append("💧 Su Raporu:\n");
        rapor.append("📦 Şehirdeki Su Rezervi: ").append(suRezervi).append(" litre\n");
        rapor.append("🏙️ Şehirdeki Su Tüketimi: ").append(suTuketimi).append(" litre\n");

        if (suTuketimi > suRezervi) {
            rapor.append("⚠️ Uyarı: Su tüketimi rezervi aşmış durumda!\n");
        } else if (suRezervi == 0) {
            rapor.append("❗ Uyarı: Su rezervi tükenmiştir! Acil önlem alınmalı.\n");
        } else {
            rapor.append("✅ Su durumu dengeli.\n");
        }

        return rapor.toString();
    }

    public void suSeviyesiKontrol() throws WaterLevelCriticalException {
        if (suRezervi < 10) {
            throw new WaterLevelCriticalException("⚠️ Su seviyesi kritik seviyeye düştü! Mevcut seviye: " + suRezervi + " litre");
        }
    }

    public void suSeviyesiDüşür(double miktar) {
        if (miktar > suRezervi) {
            miktar = suRezervi;
        }
        suRezervi -= miktar;
    }

    public void suSeviyesiArttir(double miktar) {
        suRezervi += miktar;
    }

    public void adminSutakibi(Stage stage) {
        Label titleLabel = new Label("🌊 Su Yönetim Paneli");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label suRezervLabel = new Label("💧 Mevcut Su Rezervi: " + suRezervi + " litre");
        suRezervLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #2980b9;");

        Label suTuketimLabel = new Label("🏙️ Günlük Su Tüketimi: " + suTuketimi + " litre");
        suTuketimLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #c0392b;");

        Button btnSuRaporu = new Button("📄 Su Raporu Göster");
        btnSuRaporu.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");

        TextArea raporTextArea = new TextArea();
        raporTextArea.setEditable(false);
        raporTextArea.setWrapText(true);
        raporTextArea.setStyle("-fx-control-inner-background: #ecf0f1; -fx-font-size: 14px; -fx-border-color: #3498db;");

        Button btnSuAzalt = new Button("⬇️ Su Seviyesi Azalt");
        btnSuAzalt.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        TextField txtAzaltMiktar = new TextField();
        txtAzaltMiktar.setPromptText("Azaltılacak miktar (litre)");

        Button btnSuArttir = new Button("⬆️ Su Seviyesi Arttır");
        btnSuArttir.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold;");
        TextField txtArttirMiktar = new TextField();
        txtArttirMiktar.setPromptText("Arttırılacak miktar (litre)");

        Button btnKontrol = new Button("⚡ Su Seviyesini Kontrol Et");
        btnKontrol.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-font-weight: bold;");

        btnSuRaporu.setOnAction(event -> raporTextArea.setText(suRaporuGoster()));

        btnSuAzalt.setOnAction(event -> {
            try {
                double miktar = Double.parseDouble(txtAzaltMiktar.getText());
                suSeviyesiDüşür(miktar);
                suRezervLabel.setText("💧 Mevcut Su Rezervi: " + suRezervi + " litre");
                raporTextArea.setText("✔️ Su seviyesi " + miktar + " litre azaltıldı.");
            } catch (NumberFormatException e) {
                raporTextArea.setText("❌ Hata: Lütfen geçerli bir sayı giriniz.");
            }
        });

        btnSuArttir.setOnAction(event -> {
            try {
                double miktar = Double.parseDouble(txtArttirMiktar.getText());
                suSeviyesiArttir(miktar);
                suRezervLabel.setText("💧 Mevcut Su Rezervi: " + suRezervi + " litre");
                raporTextArea.setText("✔️ Su seviyesi " + miktar + " litre arttırıldı.");
            } catch (NumberFormatException e) {
                raporTextArea.setText("❌ Hata: Lütfen geçerli bir sayı giriniz.");
            }
        });

        btnKontrol.setOnAction(event -> {
            try {
                suSeviyesiKontrol();
                raporTextArea.setText("✔️ Su seviyesi normal.");
            } catch (WaterLevelCriticalException e) {
                raporTextArea.setText(e.getMessage());
                suSeviyesiArttir(20.0);
                suRezervLabel.setText("💧 Mevcut Su Rezervi: " + suRezervi + " litre");
            }
        });

        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(
                titleLabel,
                suRezervLabel,
                suTuketimLabel,
                btnSuRaporu,
                raporTextArea,
                new Label("Azaltmak için bir miktar girin:"),
                txtAzaltMiktar,
                btnSuAzalt,
                new Label("Arttırmak için bir miktar girin:"),
                txtArttirMiktar,
                btnSuArttir,
                btnKontrol
        );
        layout.setStyle("-fx-padding: 25; -fx-background-color: #ecf0f1;");

        Scene scene = new Scene(layout, 500, 650, Color.WHITESMOKE);
        stage.setTitle("💧 Su Yönetim Sistemi");
        stage.setScene(scene);
        stage.show();
    }
}