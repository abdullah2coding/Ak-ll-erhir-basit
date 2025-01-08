package Varliklar.Binalar;

public interface Bina {
    // Binanı türünü almak ve ayarlamak için
    String getBinaTuru();
    void setBinaTuru(String binaTuru);

    // Kat sayısını almak ve ayarlamak için
    int getKatSayisi();
    void setKatSayisi(int katSayisi);

    // Enerji verimliliği yüzdesini almak ve ayarlamak için
    int getEnerjiVerimliligiYuzdesi();
    void setEnerjiVerimliligiYuzdesi(int enerjiVerimliligiYuzdesi);

    // Enerji raporu metodu
    void enerjiRaporu();
}