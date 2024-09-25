'use strict'

var driverData = null;
var table = null; 

/** 画面ロード時の処理 */
jQuery(function($){
	//DataTablesの初期化
	createDataTables();
	
	/** 検索ボタンを押したときの処理 */
	$('#btn-search').click(function(event){
		
		//検索
		search();
	});
});

/** git config --global http.postBuffer 524288000 */
/** 検索処理 */
function search(){
	//formの値を取得
	var formData = $('#driver-search-form').serialize();
	//ajax通信
	$.ajax({
		url: 'http://ergast.com/api/f1/2024/drivers.json',
		data: formData,
		dataType: 'json',
	}).done(function(data){
		//ajax成功時の処理
		console.log(data);
		//JSONを変数に入れる
		driverData = data;
		//DataTablesを作成
		createDataTables();
		
	}).fail(function(jqXHR, textStatus, errorThrow){
		//ajax失敗時の処理
		alert('検索処理に失敗しました');
	}).always(function(){
		//常に実行する処理（特になし）
	});
}

/** DataTables作成 */
function createDataTables(){
	//すでにDataTablesが作成されている場合
	if(table !== null){
		table.destroy();
	}
	
	table = $('#driver-list-table').DataTable({
		language:{
			url: '/webjars/datatables-plugins/i18n/Japanese.json'
		},
		//データのセット
		data: driverData,
		//データと列のマッピング
		columns:[
			{ data: 'driverId'},
			{ data: 'name'},
			{
				data: 'dataOfBirth',
				render: function(data, type, row){
					var date = new Date(data);
					var year = date.getFullYear();
					var month = date.getMonth() + 1;
					var date = date.getDate();
					return year + '/' + month + '/' + date;
				}
			},
			{
				data: 'driverId',
				render: function(data, type, row){
					var url = '<a href="/driver/' + data + '">ドライバー詳細</a>';
					return url;
				}
			},
		]
	});
}