package id.net.gmedia.perkasaapp.Utils;

public class ServerURL {

    //private static final String baseUrl = "http://192.168.17.15/perkasa-sales/";
    private static final String baseUrl = "http://36.66.177.165/sales/";

    public static final String auth = baseUrl + "authentication/";
    public static final String getResellerMkios = baseUrl + "mkios/view_reseller/";
    public static final String getDetailResellerMkios = baseUrl + "mkios/segmentasi/";
    public static final String saveTransaksiMkios = baseUrl + "mkios/transaksi/";
    public static final String getResellerPerdana = baseUrl + "perdana/view_customer/";
    public static final String getSuratJalan = baseUrl + "perdana/list_surat_jalan/";
    public static final String getListCCID = baseUrl + "perdana/list_perdana/";
    public static final String saveTransaksiPerdana = baseUrl + "perdana/jual/";
    public static final String getTransaksiHariIni = baseUrl + "Transaksi/today/";
    public static final String getRiwayatTransaksi = baseUrl + "Transaksi/history/";
}
