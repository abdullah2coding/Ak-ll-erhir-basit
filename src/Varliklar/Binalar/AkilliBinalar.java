/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license/default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Varliklar.Binalar;

import Varliklar.Araclar.Klasik;

/**
 *
 * @author Abdul
 */
public class AkilliBinalar extends KlasikBinalar{
    private boolean enerjiTasarrufu;

    // Constructor
    public AkilliBinalar(int id,String binaTuru, int katSayisi, int enerjiVerimliligiYuzdesi, boolean tasarruf) {
        super(id,binaTuru, katSayisi, enerjiVerimliligiYuzdesi);
        this.enerjiTasarrufu = tasarruf;
    }

    // Enerji tasarrufu modunu aktif etme metodu
    public void enerjiTasarrufuAc() {
        try {
            this.enerjiTasarrufu = true;
            System.out.println("Enerji tasarrufu modu aktif edildi.");
            Thread.sleep(10000); // 10 saniyelik bekleme simülasyonu
        } catch (InterruptedException e) {
            System.err.println("Enerji tasarrufu işlemi sırasında bir hata meydana geldi: " + e.getMessage());
        }
    }

    // Enerji raporu metodu
    @Override
    public void enerjiRaporu() {
        System.out.println("Enerji verimliliği raporu oluşturuluyor...");
        if (enerjiTasarrufu) {
            System.out.println("Enerji tasarrufu modunda çalışılıyor. Enerji verimliliği optimizasyonu sağlandı.");
        } else {
            System.out.println("Enerji tasarrufu modu kapalı. Enerji verimliliği raporu normal.");
        }
        try {
            System.out.println("Bina Tipi: " + getBinaTuru());
            System.out.println("Kat Sayısı: " + getKatSayisi());
            System.out.println("Enerji Verimliliği: %" + getEnerjiVerimliligiYuzdesi());
        } catch (Exception e) {
            System.err.println("Enerji raporu oluşturulurken bir hata meydana geldi: " + e.getMessage());
        }
    }


}
