package Varliklar.EnerjiKaynaklari;

public class RuzgarEnerjisi extends EnerjiKaynaklari {

    // Constructor
    public RuzgarEnerjisi(int id, double kapasitesi) {
        super("Rüzgar Enerjisi", id, kapasitesi);
    }

    // Üretim işlemi
    //@Override
    public void enerjiUret() {
        System.out.println("Rüzgar türbinlerinden enerji üretiliyor. Kapasite: " + getKapasitesi() + " kWh.");
    }
}