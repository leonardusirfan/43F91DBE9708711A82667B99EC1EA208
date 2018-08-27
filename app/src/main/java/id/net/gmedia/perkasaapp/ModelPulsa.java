package id.net.gmedia.perkasaapp;

public class ModelPulsa {
    private int pulsa;
    private int harga;

    public ModelPulsa(int pulsa, int harga){
        this.pulsa = pulsa;
        this.harga = harga;
    }

    public int getHarga() {
        return harga;
    }

    public int getPulsa() {
        return pulsa;
    }
}
