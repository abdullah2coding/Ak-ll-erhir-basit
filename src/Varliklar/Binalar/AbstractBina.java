package Varliklar.Binalar;

public abstract class AbstractBina implements Bina {
    private int id;
    private String binaTuru;
    private int katSayisi; // Kat sayısı
    private int enerjiVerimliligiYuzdesi; // Enerji verimliliği yüzdesi

    // Constructor
    public AbstractBina(int id, String binaTuru, int katSayisi, int enerjiVerimliligiYuzdesi) {
        this.id = id;
        this.binaTuru = binaTuru;
        this.katSayisi = katSayisi;
        this.enerjiVerimliligiYuzdesi = enerjiVerimliligiYuzdesi;
    }

    // Getter ve Setter metotları
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBinaTuru() {
        return binaTuru;
    }

    public void setBinaTuru(String binaTuru) {
        this.binaTuru = binaTuru;
    }

    public int getKatSayisi() {
        return katSayisi;
    }

    public void setKatSayisi(int katSayisi) {
        this.katSayisi = katSayisi;
    }

    public int getEnerjiVerimliligiYuzdesi() {
        return enerjiVerimliligiYuzdesi;
    }

    public void setEnerjiVerimliligiYuzdesi(int enerjiVerimliligiYuzdesi) {
        this.enerjiVerimliligiYuzdesi = enerjiVerimliligiYuzdesi;
    }

    // Ortak enerji raporu
    public void enerjiRaporu() {
        System.out.println("Bina Türü: " + binaTuru);
        System.out.println("Kat Sayısı: " + katSayisi);
        System.out.println("Enerji Verimliliği Yüzdesi: %" + enerjiVerimliligiYuzdesi);
    }

    // Ortak bir davranış olarak tüm binalar için binayı detaylı şekilde gösteren bir abstract metot
    public abstract void binaBilgisiGoster();
}