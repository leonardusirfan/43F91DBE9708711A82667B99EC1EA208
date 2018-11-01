package id.net.gmedia.perkasaapp.Utils;

public class ServerURL {

    //private static final String baseUrl = "http://192.168.17.187/perkasa-sales/";
    //private static final String baseUrl = "http://36.66.177.165/sales/";
    //private static final String baseUrl = "http://api.myperkasa.com/sales/";
    private static final String baseUrl = "https://www.myperkasa.com/api/sales/";

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
    public static final String getJarakReseller = baseUrl + "Location/hitung_jarak/";
    public static final String getLokasiReseller = baseUrl + "Location/view_outlet/";
    public static final String getVersionApp = baseUrl + "version/";
    public static final String getResellerInfo = baseUrl + "customer/";
    public static final String getProvider = baseUrl + "provider/master_provider/";
    public static final String saveMarketSurvey = baseUrl + "Market_Survey/add_survey/";
    public static final String getMarketSurvey = baseUrl + "Market_Survey/view_survey/";
    public static final String logLocation = baseUrl + "location/log_location/";
    public static final String saveBrandingHeader = baseUrl + "branding/create_header/";
    public static final String saveBrandingDetail = baseUrl + "branding/create_detail/";
    public static final String getListBranding = baseUrl + "branding/view_branding/";
    public static final String getListMarketIntelligent = baseUrl + "Market_Intel/view_market/";
    public static final String saveMarketIntelligentHeader = baseUrl + "Market_Intel/create_header/";
    public static final String saveMarketIntelligentDetail = baseUrl + "Market_Intel/create_detail/";
    public static final String getPengajuanReseller = baseUrl + "customer/view_pengajuan/";
    public static final String savePengajuanReseller = baseUrl + "customer/pengajuan/";
    public static final String getResellerPelunasan = baseUrl + "pelunasan/view_customer/";
    public static final String getDataAccount = baseUrl + "pelunasan/list_akun/";
    public static final String getDataPiutang = baseUrl + "pelunasan/list_piutang/";
    public static final String savePelunasanPiutang = baseUrl + "pelunasan/simpan_pelunasan/";
    public static final String getPelunasanPiutang = baseUrl + "pelunasan/view_pelunasan/";
    public static final String getBarangDS = baseUrl + "Direct_Sale/get_barang/";
    public static final String logBalasanDS = baseUrl + "Direct_Sale/save_reply/";
    public static final String saveOrderDS = baseUrl + "Direct_Sale/direct_order/";
    public static final String getPerdanaDS = baseUrl + "Direct_Sale/get_perdana/";
    public static final String saveFinalMarketIntel = baseUrl + "Market_Intel/update_header/";
    public static final String saveFinalBranding = baseUrl + "Branding/update_header/";
    public static final String getRekapSetoran = baseUrl + "pelunasan/rekap_pelunasan/";
    public static final String getDetailRekapSetoran = baseUrl + "pelunasan/detail_pelunasan/";
    public static final String getHargaTcash = baseUrl + "tcash/get_harga/";
    public static final String saveTcash = baseUrl + "tcash/transaksi/";
    public static final String getResellerKunjungan = baseUrl + "kunjungan/view_reseller/";
    public static final String getKunjungan = baseUrl + "kunjungan/view_kunjungan/";
    public static final String saveKunjungan = baseUrl + "kunjungan/simpan_kunjungan/";
    public static final String getSales = baseUrl + "sales/index/";
    public static final String getTanggalTempo = baseUrl + "customer/tempo/";
    public static final String getPembayaranSetoran = baseUrl + "Pembayaran/index/";
    public static final String savePassword = baseUrl + "authentication/ganti_password/";
    public static final String getMutasiKonsinyasi = baseUrl + "Konsinyasi/index/";
    public static final String getBarangMutasiKonsinyasi = baseUrl + "Konsinyasi/view_barang/";
    public static final String getCCIDMutasiKonsinyasi = baseUrl + "Konsinyasi/list_ccid/";
    public static final String saveMutasiKonsinyasi = baseUrl + "Konsinyasi/simpan_konsinyasi/";

    public static final String getListRekonsinyasi = baseUrl + "Konsinyasi/view_penjualan/";
    public static final String getOutletKonsinyasi = baseUrl + "Konsinyasi/view_outlet/";
    public static final String getBarangRekonsinyasi = baseUrl + "Konsinyasi/view_barang_rekonsinyasi/";
    public static final String saveRekonsinyasi = baseUrl + "Konsinyasi/simpan_rekonsinyasi/";
    public static final String returRekonsinyasi = baseUrl + "Konsinyasi/retur_konsinyasi/";
    public static final String getReturKonsinyasi = baseUrl + "Konsinyasi/history_retur/";

}
