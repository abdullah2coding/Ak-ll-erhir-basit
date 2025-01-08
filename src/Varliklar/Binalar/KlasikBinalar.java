package Varliklar.Binalar;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class KlasikBinalar implements Bina {
    private final IntegerProperty id; // JavaFX IntegerProperty kullanımı
    private final StringProperty binaTuru; // JavaFX StringProperty kullanımı
    private final IntegerProperty katSayisi; // Kat sayısı için IntegerProperty
    private final IntegerProperty enerjiVerimliligiYuzdesi; // Enerji verimliliği için IntegerProperty

    // Constructor
    public KlasikBinalar(int id, String binaTuru, int katSayisi, int enerjiVerimliligiYuzdesi) {
        this.id = new SimpleIntegerProperty(id);
        this.binaTuru = new SimpleStringProperty(binaTuru);
        this.katSayisi = new SimpleIntegerProperty(katSayisi);
        this.enerjiVerimliligiYuzdesi = new SimpleIntegerProperty(enerjiVerimliligiYuzdesi);
    }

    // ID Property
    public IntegerProperty idProperty() {
        return id;
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    // Bina Türü Property
    public StringProperty turProperty() {
        return binaTuru;
    }

    public String getBinaTuru() {
        return binaTuru.get();
    }

    public void setBinaTuru(String binaTuru) {
        this.binaTuru.set(binaTuru);
    }

    // Kat Sayısı Property
    public IntegerProperty katSayisiProperty() {
        return katSayisi;
    }

    public int getKatSayisi() {
        return katSayisi.get();
    }

    public void setKatSayisi(int katSayisi) {
        this.katSayisi.set(katSayisi);
    }

    // Enerji Verimliliği Property
    public IntegerProperty enerjiVerimliligiYuzdesiProperty() {
        return enerjiVerimliligiYuzdesi;
    }

    public int getEnerjiVerimliligiYuzdesi() {
        return enerjiVerimliligiYuzdesi.get();
    }

    public void setEnerjiVerimliligiYuzdesi(int enerjiVerimliligiYuzdesi) {
        this.enerjiVerimliligiYuzdesi.set(enerjiVerimliligiYuzdesi);
    }

    // Enerji raporu metodu
    public void enerjiRaporu() {
        System.out.println("Bina Türü: " + binaTuru.get());
        System.out.println("Kat Sayısı: " + katSayisi.get());
        System.out.println("Enerji Verimliliği Yüzdesi: %" + enerjiVerimliligiYuzdesi.get());
    }
}