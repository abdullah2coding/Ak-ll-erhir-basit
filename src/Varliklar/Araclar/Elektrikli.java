package Varliklar.Araclar;

import Varliklar.Araclar.Klasik;

import java.util.Random;

public class Elektrikli extends Klasik implements  Arac{
    private int sarjSeviyesi;

    // Constructor
    public Elektrikli(String renk,int id,String model,String marka ,String aracTipi, int kapasite, int bulunanYer, boolean durum) {
        super(renk,id,model,marka,aracTipi, kapasite, bulunanYer, durum);
        Random random = new Random();
        this.sarjSeviyesi = random.nextInt(101); // 0-100 arasında rastgele bir şarj seviyesi
    }

    // Getter ve Setter
    public int getSarjSeviyesi() {
        return sarjSeviyesi;
    }

    public void setSarjSeviyesi(int sarjSeviyesi) {
        this.sarjSeviyesi = sarjSeviyesi;
    }

    // Şarj seviyesi kontrolü
    public void sarjKontrol() {
        try {
            System.out.println(getAracTipi() + " şarj seviyesi: %" + getSarjSeviyesi());
            while (sarjSeviyesi < 20) {
                System.out.println(getAracTipi() + " şarj seviyesi %20'nin altına düştü. Şarj istasyonuna yönlendiriliyor...");
                Thread.sleep(5000); // Bekleme simülasyonu
                sarjGuncelle();
            }
            System.out.println(getAracTipi() + " yeterli şarj seviyesine sahip (%" + getSarjSeviyesi() + ").");
        } catch (InterruptedException e) {
            System.out.println("Şarj kontrolü sırasında bir hata oluştu: " + e.getMessage());
            Thread.currentThread().interrupt(); // Thread'in kesildiğini işaretle
        } catch (Exception e) {
            System.out.println("Beklenmeyen bir hata oluştu: " + e.getMessage());
        }
    }

    // Şarj güncellemesi
    public void sarjGuncelle() {
        try {
            System.out.println("Şarj yükleniyor...");
            Thread.sleep(5000); // Şarj süresi simülasyonu
            this.sarjSeviyesi = 100;
            System.out.println("Şarj tamamlandı! Şarj seviyesi: %" + getSarjSeviyesi());
        } catch (InterruptedException e) {
            System.out.println("Şarj güncellemesi sırasında bir hata oluştu: " + e.getMessage());
            Thread.currentThread().interrupt(); // Thread'in kesildiğini işaretle
        } catch (Exception e) {
            System.out.println("Beklenmeyen bir hata oluştu: " + e.getMessage());
        }
    }
}
