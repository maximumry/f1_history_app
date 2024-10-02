'use strict'

var driverData = null;
var table = null; 
let num = 0;

/** 画面ロード時の処理 */
jQuery(function($){
	//すでにある情報の初期化
	//createSearchDriver();
	
	/** 検索ボタンを押したときの処理 */
	$('#btn-search').click(function(event){
		
		//検索
		search();
	});
});

/** 検索処理 */
function search(){
	//formの値を取得
	var formData = $('#driver-search-form').serialize();
	console.log(formData)
	//ajax通信
	$.ajax({
		url: `http://ergast.com/api/f1/2024/drivers.json?${formData}`,
		dataType: 'json',
	}).done(function(data){
		//ajax成功時の処理
		console.log(data);
		//JSONを変数に入れる
		driverData = data.MRData.DriverTable.Drivers;
		//DataTablesを作成
		createSearchDriver();
		
	}).fail(function(jqXHR, textStatus, errorThrow){
		//ajax失敗時の処理
		alert('検索処理に失敗しました');
	}).always(function(){
		//常に実行する処理（特になし）
	});
}
/**function createSearchDriver() {
    // テーブル本体（tbody）をクリアする
    $('#driver-list-table tbody').empty();

    // driverDataがnullまたは空の場合、何も表示しない
    if (!driverData || driverData.length === 0) {
        $('#driver-list-table tbody').append('<tr><td colspan="4">データがありません</td></tr>');
        return;
    }

    // driverDataをループして、各行をテーブルに追加する
    driverData.forEach(function(driver) {
        var date = new Date(driver.dateOfBirth);
        var formattedDate = date.getFullYear() + '/' + (date.getMonth() + 1) + '/' + date.getDate();

        var row = '<tr>' +
            '<td>' + driver.driverId + '</td>' +
            '<td>' + driver.givenName + ' ' + driver.familyName + '</td>' +
            '<td>' + formattedDate + '</td>' +
            '<td><a href="/driver/' + driver.driverId + '">ドライバー詳細</a></td>' +
            '</tr>';

        $('#driver-list-table tbody').append(row);
    });
}**/

function createSearchDriver(){
	if(num >= 1){
		driverData.destroy();
	}
	
	driverData.forEach((driver) => {
		const driverImgPath = "/img/" + driver.dateOfBirth + driver.driverId + driver.permanentNumber + ".jpg";
		const driverImg = document.getElementById("driverImg");
		const driverName = document.getElementById("driverName");
		const driverBirth = document.getElementById("driverBirth");
		const driverNationality = document.getElementById("driverNationality");
		const driverNumber = document.getElementById("driverNumber");
		const driverLink = document.getElementById("driverLink");
		const abbr = document.getElementById("abbr");
		
		driverImg.setAttribute("src", driverImgPath);
		driverName.textContent = `${driver.givenName} ${driver.familyName}`;
		driverBirth.textContent = driver.dateOfBirth.replace(/-/g, "/");
		driverNationality.textContent = driver.nationality;
		driverNumber.textContent = driver.permanentNumber;
		driverLink.setAttribute("href", driver.url);
		abbr.textContent = driver.code;
		
		driverLink.addEventListener("mouseover", function(event){
			setTimeout(() => {
				event.target.style.color = "red";
			}, 1);
		}, false);
		
		driverLink.addEventListener("mouseout", function(event){
			setTimeout(() => {
				event.target.style.color = "";
			}, 1);
		}, false);
		
	});
};

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