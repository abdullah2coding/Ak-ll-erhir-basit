package Varliklar.Araclar;

public interface Arac {
    boolean isDurum(); // Araç çalışıyor mu?
    int getBulunanYernumarasi(); // Araç lokasyon numarası
    String getAracTipi(); // Araç tipi bilgisi (örn: Klasik, Elektrikli)
}