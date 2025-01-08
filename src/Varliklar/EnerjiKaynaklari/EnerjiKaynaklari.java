package Varliklar.EnerjiKaynaklari;

import javafx.beans.property.*;

public class EnerjiKaynaklari {

    // JavaFX Property türleri
    private final StringProperty tur;
    private final IntegerProperty id;
    private final DoubleProperty kapasitesi;

    public EnerjiKaynaklari(String tur, int id, double kapasitesi) {
        this.tur = new SimpleStringProperty(tur);
        this.id = new SimpleIntegerProperty(id);
        this.kapasitesi = new SimpleDoubleProperty(kapasitesi);
    }

    // Getter ve Setter Metotları (dinamik özellik destekli)
    public StringProperty turProperty() {
        return tur;
    }

    public String getTur() {
        return tur.get();
    }

    public void setTur(String tur) {
        this.tur.set(tur);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public DoubleProperty kapasiteProperty() {
        return kapasitesi;
    }

    public double getKapasitesi() {
        return kapasitesi.get();
    }

    public void setKapasitesi(double kapasitesi) {
        this.kapasitesi.set(kapasitesi);
    }

    @Override
    public String toString() {
        return "EnerjiKaynaklari{" +
                "tur='" + getTur() + '\'' +
                ", id=" + getId() +
                ", kapasitesi=" + getKapasitesi() +
                '}';
    }
}