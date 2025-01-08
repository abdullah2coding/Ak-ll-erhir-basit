package Varliklar.EnerjiKaynaklari;

public class GunesEnerjisi extends EnerjiKaynaklari {

    // Constructor
    public GunesEnerjisi(int id, double kapasitesi) {
        super("Güneş Enerjisi", id, kapasitesi);
    }

    // Üretim işlemi
    //@Override
    public void enerjiUret() {
        System.out.println("Güneş panellerinden enerji üretiliyor. Kapasite: " + getKapasitesi() + " kWh.");
    }
}