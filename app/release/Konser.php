<?php defined('BASEPATH') or exit('no direct scripts are allowed');

class Konser extends CI_Controller
{
    function __construct()
    {
        parent::__construct();

        $this->load->model([
            'api/user/model_konser'
        ]);

    }

    public function get_active_konser()
    {
        if ($this->module_auth->check_auth_user('GET')) {
            $meta_status = 404;
            $meta_message = 'Tidak ada data konser';
            $data = '';

            $gt_konser = $this->model_konser->get_aktif_konser_API();

            if (!empty($gt_konser)) {
                $meta_status = 200;
                $meta_message = 'Berhasil mendapatkan data konser';

                $dt_jenis_tiket = [];

                $gt_jenis_tikets = $this->model_konser->get_jenis_tiket_konser_API($gt_konser['id']);
                $kategori = $this->model_konser->get_kategori_tiket();

                foreach ($gt_jenis_tikets AS $idx => $row) {
                    $dt_jenis_tiket[$idx] = [
                        'id' => (int)$row['id'],
                        'nama' => $row['nama'].' - Rp '.uang($this->model_konser->get_harga_tiket($row['id'])),
                        'harga' => (double)$this->model_konser->get_harga_tiket($row['id']),
                        'status_sold_out' => (int)$row['status_sold_out']
                    ];
                }

                $data = [
                    'konser' => [
                        'nama' => $gt_konser['nama'],
                        'gambar' => $gt_konser['host_gambar'] . $gt_konser['path_gambar'],
                        'denah' => site_url('assets/denah_konser.jpg')
                    ],
                    'jenis_tiket' => $dt_jenis_tiket,
                    'kategori' => $kategori
                ];  

            }

            response_api($meta_status, $meta_message, $data);

        }
    }


    public function get_history_beli()
    {
        if ($this->module_auth->check_auth_user('POST')) {
            date_default_timezone_set('Asia/Jakarta');
            setlocale(LC_ALL, 'id_ID.utf8');

            $uid_user = $this->input->get_request_header('Uid', true);

            $meta_status = 404;
            $meta_message = 'Tidak ada data pembelian';
            $data = '';

            $data_post = get_body_json();
            $start = isset($data_post['start']) ? $data_post['start'] : 0;
            $limit = isset($data_post['limit']) ? $data_post['limit'] : 10;

            // $count_gt_ticket = $this->model_konser->count_history_beli_ticket($uid_user);
            $gt_tickets = $this->model_konser->get_history_beli_tiket($uid_user, $start, $limit);


            // if ($count_gt_ticket > 0) {
            //     $meta_status = 200;
            //     $meta_message = 'Ada data pembelian tiket';
            // }

            if (!empty($gt_tickets)) {
                $meta_status = 200;
                $meta_message = 'Ada data pembelian tiket';

                $dt_tickets = [];

                foreach ($gt_tickets as $idx => $row) {

                    switch ($row['status_bayar']) {
                        case 2:
                            $fn_status = 'Dibayar';
                            $fn_is_download = 1;
                            $fn_ket_pembayaran = '';
                            break;

                        case 3:
                            $fn_status = 'Expired';
                            $fn_is_download = 0;
                            $fn_ket_pembayaran = '';
                            break;
                        
                        default:
                            $fn_status = 'Menunggu Pembayaran';
                            $fn_is_download = 0;
                            $fn_ket_pembayaran = "Silahkan lakukan pembayaran ke : "
                                            ."\n- BCA - 182-311-0999"
                                            ."\n- PT Mitra Inovasi Digital"
                                            ."\n sebelum ".strftime('%e %B %Y %H:%M', strtotime($row['expired_at']))." WIB";
                            break;
                    }

                    $str_id_url = strtotime($row['created_at']).$row['id'];

                    $dt_tickets[$idx] = [
                        'id' => (int)$str_id_url,
                        'nama_konser' => $row['nama_konser'],
                        'jenis_tiket' => $row['nama_tiket'],
                        'waktu_transaksi' => $row['created_at'],
                        'jumlah' => (int)$row['total_tiket'],
                        'total_harga' => (int)$row['harga_total'],
                        'status' => $fn_status,
                        'allow_download' => $fn_is_download,
                        'link_download' => ($fn_is_download == 1) ? site_url('/tiket/konser/download_qr/'.$str_id_url) : '#',
                        'kode_promo' => $row['kode_promo'],
                        'keterangan_pembayaran' => $fn_ket_pembayaran
                    ];
                }

                $data = [
                    'tickets' => $dt_tickets
                ];

            }

            response_api($meta_status, $meta_message, $data);
        }
    }


    public function download_qr_tiket($strcreatedandid = 0)
    {
        // if ($this->module_auth->check_auth_user('GET')) {
            $this->load->library('pdfgenerator');

            $str_created_at = substr($strcreatedandid, 0, 10);
            $id_header = substr($strcreatedandid, 10);

            $datetime_create = date('Y-m-d H:i:s', $str_created_at);

            $gt_header = $this->model_konser->get_tr_tiket_header_by_createdid($datetime_create, $id_header);

            if (!empty($gt_header)) {
                $this->load->library([
                    'ciqrcode',
                    'Pdfgenerator' => 'pdf_generator'
                ]);

                $gt_details = $this->model_konser->get_tr_tiket_detail_by_orderid($gt_header['order_id']);

                if (!empty($gt_details)) {
                    $qr_directory = './assets/qrcode_konser/user/';

                    foreach ($gt_details as $detail) {
                        $qr_data = $detail['kode_qr'];
                        $qr_image = md5($qr_data).'.png';

                        $qr_config = [
                            'data' => $qr_data,
                            'level' => 'H',
                            'size' => 8,
                            'savename' => $qr_directory.$qr_image
                        ];
    
                        // $qr_location = site_url('/assets/qrcode_konser/user/'.$qr_image);
                        $qr_path = $qr_directory.$qr_image;
    
                        if (!file_exists($qr_path)) {
                            $this->ciqrcode->generate($qr_config);
                        }
                    }
                }

                $data = [
                    'header' => $gt_header,
                    'details' => $gt_details
                ];


                $pdf_html = $this->load->view('/template_pdf/download_qr_tiket_konser', $data, true);
                $pdf_file_name = 'eTicket - '.$gt_header['nama_konser'];
                $pdf_paper = 'A4';
                $pdf_orientation = 'landscape';

                $this->pdf_generator->generate($pdf_html, $pdf_file_name, true, $pdf_paper, $pdf_orientation);

                // print_r($data);

            } else {
                show_404();

            }
    
        // }
    }


    public function process_prepayment_midtrans()
    {
        // if ($this->module_auth->check_auth_user('POST')) {
            $this->load->library([
                'Midtrans' => 'midtrans'
            ]);

            $json = get_body_json(); // data ini hasil dari sdk (android) midtrans

            $res_model = $this->model_konser->update_temp_trx_from_midtrans($json);

            $response = [
                'error_messages' => [
                    'Failed process prepayment'
                ]
            ];

            if ($res_model == '1') {
                /**
                 * post ke midtrans
                 */

                $gt_config_midtrans = $this->MyModel->get_config_midtrans();
                $mtr_is_production = $gt_config_midtrans['is_production'];
                $mtr_server_key = $gt_config_midtrans['server_key'];

                $this->midtrans->config([
                    'production' => $mtr_is_production,
                    'server_key' => $mtr_server_key
                ]);

                $mtr_snap_url = $this->midtrans->getSnapBaseUrl() . '/transactions';

                $response = $this->midtrans->post($mtr_snap_url, $mtr_server_key, $json);
            }
            
            json_output(200, $response);
        // }
    }


    public function update_midtrans_status($type = 'notification')
    {
        $json = get_body_json();

        $transaction_time = isset($json['transaction_time']) ? $json['transaction_time']  : '';
        $transaction_status = isset($json['transaction_status']) ? $json['transaction_status'] : '';
        $transaction_id = isset($json['transaction_id']) ? $json['transaction_id'] : '';
        $status_message = isset($json['status_message']) ? $json['status_message'] : '';
        $status_code = isset($json['status_code']) ? $json['status_code'] : '';
        $signature_key = isset($json['signature_key']) ? $json['signature_key'] : '';
        $payment_type = isset($json['payment_type']) ? $json['payment_type'] : '';
        $order_id = isset($json['order_id']) ? $json['order_id'] : '';
        $masked_card = isset($json['masked_card']) ? $json['masked_card'] : '';
        $gross_amount = isset($json['gross_amount']) ? $json['gross_amount'] : '';
        $fraud_status = isset($json['fraud_status']) ? $json['fraud_status'] : '';
        $eci = isset($json['eci']) ? $json['eci'] : '';
        $channel_response_message = isset($json['channel_response_message']) ? $json['channel_response_message'] : '';
        $channel_response_code = isset($json['channel_response_code']) ? $json['channel_response_code'] : '';
        $bank = isset($json['bank']) ? $json['bank'] : '';
        $approval_code = isset($json['approval_code']) ? $json['approval_code'] : '';
        $va_number = isset($json['va_numbers'][0]['va_number']) ? $json['va_numbers'][0]['va_number'] : '';

        if ($bank == '') {
            $bank = isset($json['va_numbers'][0]['bank']) ? $json['va_numbers'][0]['bank'] : '';
        }


        $dt_store = [
            'order_id' => $order_id,
            'transaction_time' => $transaction_time,
            'transaction_status' => $transaction_status,
            'transaction_id' => $transaction_id,
            'status_message' => $status_message,
            'status_code' => $status_code,
            'signature_key' => $signature_key,
            'payment_type' => $payment_type,
            'masked_card' => $masked_card,
            'gross_amount' => $gross_amount,
            'fraud_status' => $fraud_status,
            'eci' => $eci,
            'channel_response_message' => $channel_response_message,
            'channel_response_code' => $channel_response_code,
            'bank' => $bank,
            'approval_code' => $approval_code,
            'va_number' => $va_number,
            'created_at' => date('Y-m-d H:i:s')
        ];


        switch ($type) {
            case 'notification':
                $this->MyModel->store_to_table_get_id('midtrans_log_trx', $dt_store);

                /**
                 * MIDTRANS DOCS : [https://snap-docs.midtrans.com/#best-practice-to-handle-notification]
                 * Always check all the following three fields for confirming success transaction
                 * - status code: Should be 200 for successful transactions
                 * - fraud status: ACCEPT
                 * - transaction status : settlement/capture
                 * - another transaction status : expire / pending
                 */

                if ($fraud_status == 'ACCEPT') {
                    switch ($transaction_status) {
                        case 'settlement':
                            $dt_update_tr_tiket = [
                                'status_bayar' => 2
                            ];
                            break;

                        case 'capture':
                            $dt_update_tr_tiket = [
                                'status_bayar' => 2
                            ];
                            break;

                        case 'expire';
                            $dt_update_tr_tiket = [
                                'status_bayar' => 3
                            ];
                            break;

                        case 'pending';
                            $dt_update_tr_tiket = [
                                'status_bayar' => 4
                            ];
                            break;
                        
                        default:
                            $dt_update_tr_tiket = [
                                'status_bayar' => 1
                            ];
                            break;
                    }

                    $this->MyModel->update_table_use_trans('tr_tiket_konser_h', [
                        'order_id' => $order_id
                    ], $dt_update_tr_tiket);

                }


                $dt_response = [
                    'status' => 200,
                    'message' => 'Success update'
                ];

                break;
            
            default:
                $dt_response = [
                    'status' => 201,
                    'message' => 'Nothing to do'
                ];
                break;
        }


        json_output(200, $dt_response);
    }


    public function process_pembelian_tiket()
    {
        if ($this->module_auth->check_auth_user('POST')) {
            $uid_user = $this->input->get_request_header('Uid', true);

            $is_valid = 1;
            $meta_status = 400;
            $meta_message = 'Gagal memproses pembelian tiket';
            $data = '';

            $data_post = get_body_json();
            $id_jenis_tiket = isset($data_post['id_jenis_tiket']) ? $data_post['id_jenis_tiket'] : '';
            $jumlah = isset($data_post['jumlah']) ? $data_post['jumlah'] : '';
            $kode_promo = isset($data_post['kode_promo']) ? $data_post['kode_promo'] : '';
            $kode_referral = isset($data_post['kode_referral']) ? $data_post['kode_referral'] : '';

            if (empty($jumlah)) {
                $is_valid = 0;
                $meta_status = 400;
                $meta_message = 'Jumlah harus diisi';
            }

            if (empty($id_jenis_tiket)) {
                $is_valid = 0;
                $meta_status = 400;
                $meta_message = 'Jenis Tiket harus diisi';
            }

            if (!empty($kode_referral)) {
                
                $gt_referral = $this->MyModel->view_by_id('ms_kode_referral', [
                    'kode_referral' => $kode_referral
                ], 'row');

                if (empty($gt_referral)) {
                    $is_valid = 0;
                    $meta_status = 400;
                    $meta_message = 'Kode Referral tidak berlaku';
                }

            }

            # nambah jenis tiket
            $ms_tiket = $this->MyModel->view_by_id('jenis_tiket_konser', [
                'id' => $id_jenis_tiket
            ], 'row');
            $jenis_tiket = isset($ms_tiket->nama) ? $ms_tiket->nama : '';


            $persen_promo = 0;

            if (!empty($kode_promo)) {
                $exist_promo = $this->model_konser->get_kode_promo_tiket($kode_promo);

                $persen_promo = isset($exist_promo['nominal_persen']) ? $exist_promo['nominal_persen'] : 0;

                if (!empty($exist_promo)) {
                    $avail_jumlah = $this->model_konser->get_total_avail_promo_tiket($kode_promo);

                    if ($avail_jumlah['total_available'] == 0) {
                        $is_valid = 0;
                        $meta_status = 400;
                        $meta_message = 'Maaf, kuota promo sudah habis';

                        $persen_promo = 0;

                    } else {
                        $validasi_promo = $this->model_konser->validate_used_kode_promo($uid_user, $kode_promo);

                        if (!empty($validasi_promo)) {
                            $is_valid = 0;
                            $meta_status = 400;
                            $meta_message = 'Anda sudah menggunakan kode promo ini';

                            $persen_promo = 0;

                        } else {
                            if ($jumlah > 10) {
                                $is_valid = 0;
                                $meta_status = 400;
                                $meta_message = 'Maaf, pembelian dengan kode promo, maksimal 10 tiket';

                                $persen_promo = 0;
                            }

                        }

                    }

                } else {
                    $is_valid = 0;
                    $meta_status = 400;
                    $meta_message = 'Kode Promo tidak berlaku';

                }
            }


            if ($is_valid == 1) {
                $valid_user = 1;
                $meta_status = 400;
                $meta_message = 'Data User tidak berlaku';

                $gt_user = $this->MyModel->view_by_id('tb_user', [
                    'uid' => $uid_user,
                    'status' => 1
                ], 'row');

                if (!empty($gt_user)) {
                    $valid_user = 1;

                    $nama_user = isset($gt_user->profile_name) ? $gt_user->profile_name : '';
                    $email_user = isset($gt_user->email) ? $gt_user->email : '';
                    $nik_user = isset($gt_user->no_ktp) ? $gt_user->no_ktp : '';

                    if (empty($nik_user)) {
                        $valid_user = 0;
                        $meta_status = 400;
                        $meta_message = 'Harap isi NIK Anda';
                    }

                    if (empty($email_user)) {
                        $valid_user = 0;
                        $meta_status = 400;
                        $meta_message = 'Harap isi Email Anda';
                    }

                    if (empty($nama_user)) {
                        $valid_user = 0;
                        $meta_status = 400;
                        $meta_message = 'Harap isi Nama Anda';
                    }
                }

                
                if ($valid_user == 1) {
                    $param_model = [
                        'uid_user' => $uid_user,
                        'id_jenis_tiket' => $id_jenis_tiket,
                        'jumlah' => $jumlah,
                        'kode_promo' => $kode_promo,
                        'persen_promo' => $persen_promo,
                        'kode_referral' => $kode_referral
                    ];
    
                    $res_model = $this->model_konser->process_pembelian_tiket($param_model);
    
                    switch ($res_model['status']) {
                        case '1':
                            $meta_status = 200;
                            $meta_message = 'Berhasil memproses pembelian';
                            $data = [
                                'order_id' => $res_model['order_id'],
                                'discount_percent' => (int)$persen_promo,
                                'discount_amount' => (int)$res_model['discount_amount'],
                                'gross_amount' => (int)$res_model['gross_amount'],
                                'unique_code' => (int)$res_model['unique_code'],
                                'tax_amount' => (int)$res_model['tax_amount'],
                                'total_payment' => (int)$res_model['total_payment'],
                                'jenis_tiket' => $jenis_tiket,
                                'jumlah' => $jumlah,
                                'email' => $email_user,
                                'expired_at' => $res_model['expired_at']
                            ];
                            break;
    
                        case '2':
                            $meta_status = 200;
                            $meta_message = 'Pemesanan tiket berhasil, gagal mengirim Email Tagihan';
                            $data = [
                                'order_id' => $res_model['order_id'],
                                'discount_percent' => (int)$persen_promo,
                                'discount_amount' => (int)$res_model['discount_amount'],
                                'gross_amount' => (int)$res_model['gross_amount'],
                                'unique_code' => (int)$res_model['unique_code'],
                                'tax_amount' => (int)$res_model['tax_amount'],
                                'total_payment' => (int)$res_model['total_payment'],
                                'jenis_tiket' => $jenis_tiket,
                                'jumlah' => $jumlah,
                                'email' => $email_user,
                                'expired_at' => $res_model['expired_at']
                            ];
                            break;
                        
                        default:
                            $meta_status = 400;
                            $meta_message = 'Maaf, gagal memproses pembelian tiket';
                            break;
                    }
                }
                
            }

            response_api($meta_status, $meta_message, $data);

        }
    }


    public function send_qr_via_email($strcreatedandid = 0)
    {
        if ($this->module_auth->check_auth_user('GET')) {
            $meta_status = 400;
            $meta_message = 'Data tidak ditemukan';
            $data = '';

            $str_created_at = substr($strcreatedandid, 0, 10);
            $id_header = substr($strcreatedandid, 10);

            $datetime_create = date('Y-m-d H:i:s', $str_created_at);

            $gt_header = $this->model_konser->get_tr_tiket_header_by_createdid($datetime_create, $id_header);

            if (!empty($gt_header)) {
                $meta_status = 400;
                $meta_message = 'Gagal membuat file pdf';

                $this->load->library([
                    'ciqrcode',
                    'Pdfgenerator' => 'pdf_generator',
                    'Module_email' => 'module_email'
                ]);

                $gt_details = $this->model_konser->get_tr_tiket_detail_by_orderid($gt_header['order_id']);

                if (!empty($gt_details)) {
                    $qr_directory = './assets/qrcode_konser/user/';

                    foreach ($gt_details as $detail) {
                        $qr_data = $detail['kode_qr'];
                        $qr_image = md5($qr_data).'.png';

                        $qr_config = [
                            'data' => $qr_data,
                            'level' => 'H',
                            'size' => 8,
                            'savename' => $qr_directory.$qr_image
                        ];
    
                        // $qr_location = site_url('/assets/qrcode_konser/user/'.$qr_image);
                        $qr_path = $qr_directory.$qr_image;
    
                        if (!file_exists($qr_path)) {
                            $this->ciqrcode->generate($qr_config);
                        }
                    }
                }


                $pdf_data = [
                    'header' => $gt_header,
                    'details' => $gt_details
                ];


                $pdf_html = $this->load->view('template_pdf/download_qr_tiket_konser', $pdf_data, true);
                $pdf_filename = str_replace(' ', '_', 'eTicket_'.$gt_header['nama_konser'].'_'.$strcreatedandid);

                $pdf_eticket = $this->pdf_generator->gen_pdf_etiket($pdf_html, $pdf_filename);

                if (file_exists($pdf_eticket['path'])) {
                    $meta_status = 200;
                    $meta_message = 'Gagal mengirim Email';

                    
                    $pref_title = '[DEV] ';
                    $bcc_email = 'ibnu.halim@gmedia.co.id';
                    if ($_SERVER['SERVER_NAME'] != 'localhost') {
                        $pref_title = '';
                        $bcc_email = 'ibnu.halim@gmedia.co.id, edy.kristanto@gmedia.co.id, victor.fendi@gmedia.co.id';
                    }

                    $m_from = [
                        'email' => 'no-reply@semargres.gmedia.id',
                        'name' => '[SEMARGRES 2019]'
                    ];
                    $m_to = [
                        'to' => $gt_header['email_user'],
                        'bcc' => $bcc_email
                    ];
                    $m_subject = $pref_title . 'eTicket Konser '.$gt_header['nama_konser'];
                    $m_content = [
                        'url_tiket' => url_path($pdf_eticket['path'])
                    ];
                    $m_template = 'template_email/email_qr_tiket_konser';
                    $m_attachment[] = [
                        'name' => $pdf_eticket['file'],
                        'location' => url_path($pdf_eticket['path'])
                    ];

                    $res_send = $this->module_email->send_email($m_from, $m_to, $m_subject, $m_content, $m_template, $m_attachment);

                    if ($res_send == '1') {
                        $meta_status = 200;
                        $meta_message = 'Email eTicket terkirim';
                    }
                }

            }

            response_api($meta_status, $meta_message, $data);
        }
    }


    public function check_kode_promo()
    {
        if ($this->module_auth->check_auth_user('POST')) {
            $uid_user = $this->input->get_request_header('Uid', true);

            $is_valid = 1;
            $meta_status = 400;
            $meta_message = 'Kode promo tidak berlaku';
            $data = '';

            $data_post = get_body_json();
            $id_jenis_tiket = isset($data_post['id_jenis_tiket']) ? $data_post['id_jenis_tiket'] : '';
            $jumlah = isset($data_post['jumlah']) ? $data_post['jumlah'] : '';
            $kode_promo = isset($data_post['kode_promo']) ? $data_post['kode_promo'] : '';

            if (empty($kode_promo)) {
                $is_valid = 0;
                $meta_status = 400;
                $meta_message = 'Kode promo harus diisi';
            }

            if (empty($jumlah)) {
                $is_valid = 0;
                $meta_status = 400;
                $meta_message = 'Jumlah harus diisi';
            }

            if (empty($id_jenis_tiket)) {
                $is_valid = 0;
                $meta_status = 400;
                $meta_message = 'Jenis Tiket harus diisi';
            }


            $persen_promo = 0;

            if (!empty($kode_promo)) {
                $exist_promo = $this->model_konser->get_kode_promo_tiket($kode_promo);

                $persen_promo = isset($exist_promo['nominal_persen']) ? $exist_promo['nominal_persen'] : 0;
    
                if (!empty($exist_promo)) {
                    $avail_jumlah = $this->model_konser->get_total_avail_promo_tiket($kode_promo);
    
                    if ($avail_jumlah['total_available'] == 0) {
                        $is_valid = 0;
                        $meta_status = 400;
                        $meta_message = 'Maaf, kuota promo sudah habis';
    
                        $persen_promo = 0;
    
                    } else {
                        $validasi_promo = $this->model_konser->validate_used_kode_promo($uid_user, $kode_promo);
    
                        if (!empty($validasi_promo)) {
                            $is_valid = 0;
                            $meta_status = 400;
                            $meta_message = 'Anda sudah menggunakan kode promo ini';
    
                            $persen_promo = 0;
    
                        } else {
                            if ($jumlah > 10) {
                                $is_valid = 0;
                                $meta_status = 400;
                                $meta_message = 'Maaf, pembelian dengan kode promo, maksimal 10 tiket';
    
                                $persen_promo = 0;
                            }
    
                        }
    
                    }
    
                } else {
                    $is_valid = 0;
                    $meta_status = 400;
                    $meta_message = 'Kode Promo tidak berlaku';
    
                }
            }

            if ($is_valid == 1) {
                $meta_status = 400;
                $meta_message = 'Gagal memproses pengecekan';

                $param_model = [
                    'id_jenis_tiket' => $id_jenis_tiket,
                    'jumlah' => $jumlah,
                    'persen_promo' => $persen_promo
                ];

                $res_model = $this->model_konser->check_pembelian_with_kode_promo($param_model);

                if ($res_model['status'] == '1') {
                    $meta_status = 200;
                    $meta_message = 'Kode promo berlaku';
                    $data = [
                        'discount_percent' => (int)$persen_promo,
                        'discount_amount' => (int)$res_model['discount_amount'],
                        'gross_amount' => (int)$res_model['gross_amount']
                    ];

                }
            }

            response_api($meta_status, $meta_message, $data);

        }
    }
}