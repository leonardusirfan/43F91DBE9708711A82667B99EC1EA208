package id.net.gmedia.perkasaapp.Utils;

public class ServerURL {

    private static final String baseUrl = "http://192.168.17.15/perkasa-sales/";

    public static final String auth = baseUrl + "authentication/";
    public static final String getResellerMkios = baseUrl + "mkios/view_reseller/";
    public static final String getDetailResellerMkios = baseUrl + "mkios/segmentasi/";
    public static final String saveTransaksiMkios = baseUrl + "mkios/transaksi/";
    public static final String getResellerPerdana = baseUrl + "perdana/view_customer/";
}
