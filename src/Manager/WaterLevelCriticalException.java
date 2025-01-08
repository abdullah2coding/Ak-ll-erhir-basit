package Manager;

// Özel Su Seviyesi Kritik Hatası
public class WaterLevelCriticalException extends Exception {
    public WaterLevelCriticalException(String mesaj) {
        super(mesaj);
    }
}
