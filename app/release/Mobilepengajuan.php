<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Mobilepengajuan extends CI_Controller
{
    
    function __construct()
    {
        parent::__construct();
        $this->load->model('MobileauthModel', '', TRUE);
        $this->load->model('MobilepengajuanModel', '', TRUE);
        header('Access-Control-Allow-Origin: *');
		ini_set('max_execution_time', 0);
    }
	
	function get_pengajuan_dev(){
		
		$method = $_SERVER['REQUEST_METHOD'];
		if ($method == 'POST') 
        {
			$check_auth_client = $this->MobileauthModel->check_auth_client();
			if ($check_auth_client == true) 
			{
				$params = json_decode(file_get_contents('php://input'), TRUE);
				$keyword = $params['keyword'];
				$start = $params['start'];
				$count = $params['count'];
				
				$result = $this->MobilepengajuanModel->get_pengajuan_dev($keyword, $start, $count);
				
				if($result != ''){
					$response = $this->MobileauthModel->success($result);	
				}else{
					$response = $this->MobileauthModel->data_not_found();
				}
				
				json_output(200, $response);
			}else{
				$this->MobileauthModel->not_authorized();
			}
        }else{
			$this->MobileauthModel->bad_request();
		}
	}
	
	function get_pengajuan(){
		
		$method = $_SERVER['REQUEST_METHOD'];
		if ($method == 'POST') 
        {
			$check_auth_client = $this->MobileauthModel->check_auth_client();
			if ($check_auth_client == true) 
			{
				$params = json_decode(file_get_contents('php://input'), TRUE);
				$keyword = $params['keyword'];
				$start = $params['start'];
				$count = $params['count'];
				
				$result = $this->MobilepengajuanModel->get_pengajuan($keyword, $start, $count);
				
				if($result != ''){
					$response = $this->MobileauthModel->success($result);	
				}else{
					$response = $this->MobileauthModel->data_not_found();
				}
				
				json_output(200, $response);
			}else{
				$this->MobileauthModel->not_authorized();
			}
        }else{
			$this->MobileauthModel->bad_request();
		}
	}
	
	function get_history_dev(){
		
		$method = $_SERVER['REQUEST_METHOD'];
		if ($method == 'POST') 
        {
			$check_auth_client = $this->MobileauthModel->check_auth_client();
			if ($check_auth_client == true) 
			{
				$params 	= json_decode(file_get_contents('php://input'), TRUE);
				$keyword 	= $params['keyword'];
				$start 		= $params['start'];
				$count 		= $params['count'];
				$bulan 		= ISSET($params['bulan']) ? $params['bulan'] : '';
				$tahun 		= ISSET($params['tahun']) ? $params['tahun'] : '';
				
				$result = $this->MobilepengajuanModel->get_history_dev($keyword, $bulan, $tahun, $start, $count);
				
				if($result != ''){
					$response = $this->MobileauthModel->success($result);
				}else{
					$response = $this->MobileauthModel->data_not_found();
				}
				
				json_output(200, $response);
			}else{
				$this->MobileauthModel->not_authorized();
			}
        }else{
			$this->MobileauthModel->bad_request();
		}
	}
	
	function get_history(){
		
		$method = $_SERVER['REQUEST_METHOD'];
		if ($method == 'POST') 
        {
			$check_auth_client = $this->MobileauthModel->check_auth_client();
			if ($check_auth_client == true) 
			{
				$params 	= json_decode(file_get_contents('php://input'), TRUE);
				$keyword 	= $params['keyword'];
				$start 		= $params['start'];
				$count 		= $params['count'];
				$bulan 		= ISSET($params['bulan']) ? $params['bulan'] : '';
				$tahun 		= ISSET($params['tahun']) ? $params['tahun'] : '';
				
				$result = $this->MobilepengajuanModel->get_history($keyword, $bulan, $tahun, $start, $count);
				
				if($result != ''){
					$response = $this->MobileauthModel->success($result);
				}else{
					$response = $this->MobileauthModel->data_not_found();
				}
				
				json_output(200, $response);
			}else{
				$this->MobileauthModel->not_authorized();
			}
        }else{
			$this->MobileauthModel->bad_request();
		}
	}
	
	function get_detail_pengajuan($id = ''){
		
		$method = $_SERVER['REQUEST_METHOD'];
		if ($method == 'POST') 
        {
			$check_auth_client = $this->MobileauthModel->check_auth_client();
			if ($check_auth_client == true) 
			{
				$params = json_decode(file_get_contents('php://input'), TRUE);
				$id_header = ISSET($params['id_header']) ? $params['id_header'] : '';
				
				$result = $this->MobilepengajuanModel->get_detail_pengajuan($id, $id_header);
				
				if($result != ''){
					$response = $this->MobileauthModel->success($result);	
				}else{
					$response = $this->MobileauthModel->data_not_found();
				}
				
				json_output(200, $response);
			}else{
				$this->MobileauthModel->not_authorized();
			}
        }else{
			$this->MobileauthModel->bad_request();
		}
	}
	
	function get_barang_po(){
		
		$method = $_SERVER['REQUEST_METHOD'];
		if ($method == 'POST') 
        {
			$check_auth_client = $this->MobileauthModel->check_auth_client();
			if ($check_auth_client == true) 
			{
				$params = json_decode(file_get_contents('php://input'), TRUE);
				$id_po = ISSET($params['id_po']) ? $params['id_po'] : '';
				
				
				$header = $this->MobilepengajuanModel->get_po_header($id_po);
				$detail = $this->MobilepengajuanModel->get_po_detail($id_po);
				$result = array(
						'header' => $header, 
						'detail' => $detail 
				);
				
				if($result != ''){
					$response = $this->MobileauthModel->success($result);	
				}else{
					$response = $this->MobileauthModel->data_not_found();
				}
				
				json_output(200, $response);
			}else{
				$this->MobileauthModel->not_authorized();
			}
        }else{
			$this->MobileauthModel->bad_request();
		}
	}
	
	function update_pengajuan(){
		
		$method = $_SERVER['REQUEST_METHOD'];
		if ($method == 'POST') 
        {
			$check_auth_client = $this->MobileauthModel->check_auth_client();
			if ($check_auth_client == true) 
			{
				$params = json_decode(file_get_contents('php://input'), TRUE);
				$id 	= $params['id'];
				$data	= $params['data'];
				
				$result = $this->MobilepengajuanModel->update_pengajuan($id, $data);
				
				if($result){
					
					$hasil = array(
						'message' => 'Keputusan berhasil disimpan'
					);
					
					$response = $this->MobileauthModel->success($hasil);
				}else{
					$response = $this->MobileauthModel->failed(array(), 'Data tidak tersimpan, harap ulangi kembali');
				}
				
				json_output(200, $response);
			}else{
				$this->MobileauthModel->not_authorized();
			}
        }else{
			$this->MobileauthModel->bad_request();
		}
	}
	
	function update_detail_pengajuan(){
		
		$method = $_SERVER['REQUEST_METHOD'];
		if ($method == 'POST') 
        {
			$check_auth_client = $this->MobileauthModel->check_auth_client();
			if ($check_auth_client == true) 
			{
				$params = json_decode(file_get_contents('php://input'), TRUE);
				$id 	= $params['id'];
				$data	= $params['data'];
				
				$result = $this->MobilepengajuanModel->update_detail_pengajuan($id, $data);
				
				if($result){
					
					$hasil = array(
						'message' => 'Hasil telah disimpan'
					);
					
					$response = $this->MobileauthModel->success($hasil);	
				}else{
					$response = $this->MobileauthModel->failed(array(), 'Data tidak tersimpan, harap ulangi kembali');
				}
				
				json_output(200, $response);
			}else{
				$this->MobileauthModel->not_authorized();
			}
        }else{
			$this->MobileauthModel->bad_request();
		}
	}
	
	function get_tahun_header(){
		
		$method = $_SERVER['REQUEST_METHOD'];
		if ($method == 'GET') 
        {
			$check_auth_client = $this->MobileauthModel->check_auth_client();
			if ($check_auth_client == true) 
			{
				
				$result = $this->MobilepengajuanModel->get_tahun_header();
				
				if($result != ''){
					$response = $this->MobileauthModel->success($result);	
				}else{
					$response = $this->MobileauthModel->data_not_found();
				}
				
				json_output(200, $response);
			}else{
				$this->MobileauthModel->not_authorized();
			}
        }else{
			$this->MobileauthModel->bad_request();
		}
	}
}
