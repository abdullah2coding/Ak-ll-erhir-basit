package Varliklar.EnerjiKaynaklari;

public class NukleerEnerjisi extends EnerjiKaynaklari {

    // Constructor
    public NukleerEnerjisi(int id, double kapasitesi) {
        super("Nükleer Enerji", id, kapasitesi);
    }

    // Üretim işlemi
    //@Override
    public void enerjiUret() {
        System.out.println("Nükleer santralden enerji üretiliyor. Kapasite: " + getKapasitesi() + " kWh.");
    }
}