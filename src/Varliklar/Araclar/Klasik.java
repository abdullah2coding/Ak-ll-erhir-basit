package Varliklar.Araclar;

import java.util.Random;

public class Klasik implements Arac{
    private String renk;
    private int id;
    private String model;
    private String marka;
    private String aracTipi;
    private int kapasite; // Kapasite, int olarak değiştirilmiştir.
    private int bulunanYernumarasi;
    private boolean durum;

    // Constructor
    public Klasik(String renk,int id,String model,String marka ,String aracTipi, int kapasite, int bulunanYer, boolean durum) {

      this.renk = renk;
      this.id = id;
       this.model = model;
       this.marka = marka;
        this.aracTipi = aracTipi;
        this.kapasite = kapasite;
        this.bulunanYernumarasi = bulunanYer;
        this.durum = durum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRenk() {
        return renk;
    }

    public void setRenk(String renk) {
        this.renk = renk;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    // Getter ve Setter Metodları
    public boolean isDurum() {
        return durum;
    }

    public void setDurum(boolean durum) {
        this.durum = durum;
    }

    public int getBulunanYernumarasi() {
        return bulunanYernumarasi;
    }

    public void setBulunanYernumarasi(int bulunanYernumarasi) {
        this.bulunanYernumarasi = bulunanYernumarasi;
    }

    public String getAracTipi() {
        return aracTipi;
    }

    public void setAracTipi(String aracTipi) {
        this.aracTipi = aracTipi;
    }

    public int getKapasite() { // Kapasite tipi düzeltilmiştir.
        return kapasite;
    }

    public void setKapasite(int kapasite) { // Tip uyumu sağlanmıştır.
        this.kapasite = kapasite;
    }
}
