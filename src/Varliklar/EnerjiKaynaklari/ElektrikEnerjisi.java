package Varliklar.EnerjiKaynaklari;

public class ElektrikEnerjisi extends EnerjiKaynaklari {

    // Constructor
    public ElektrikEnerjisi(int id, double kapasitesi) {
        super("Elektrik Enerjisi", id, kapasitesi);
    }

    // Üretim işlemi
   // @Override
    public void enerjiUret() {
        System.out.println("Direkt elektrik enerjisi sağlanıyor. Kapasite: " + getKapasitesi() + " kWh.");
    }
}