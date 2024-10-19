'use strict'

/** 画面ロード時の処理 */
jQuery(function($){
	/** 登録ボタンを押した時の処理 */
	$('#btn-event-form').click(function(event){
		//debugger
		//イベント登録
		insertEvent();
	});
	
	$.ajax({
		type: "GET",
		url: '/driver/api',
		dataType: 'json',
	}).done(function(data){
		for(var i in data){
			$('#driverSelectbox').append("<option value=" + data[i].driverId + ">" + data[i].driverId + "</option>");
		}
	}).fail(function(jqXHR, textStatus, errorThrown){
		alert("ドライバー情報の取得に失敗しました。ページをリロードしてもう一度お試しください")
	})
});

/** イベント登録処理 */
function insertEvent(){
	
	//バリデーション結果をクリア
	removeValidResult();
	
	//フォームの値を取得
	var formData = $('#event-insert-form').serializeArray()
	console.log(formData)
	//ajax通信
	$.ajax({
		type: "POST",
		cache: false,
		url: '/event/api',
		data: formData,
		dataType: 'json',
	}).done(function(data){
		//ajax通信成功時の処理
		if(data.result === 90){
			console.log(data.result)
			//validationエラー時の処理
			$.each(data.errors, function(key, value){
				reflectValidResult(key, value)
			});
		}else if(data.result === 0){
			alert('イベントを登録しました');
			//(/driver)にリダイレクト
			window.location.href = '/driver';
		}
		
	}).fail(function(jqXHR, textStatus, errorThrown){
		//ajax失敗時の処理
		alert('イベントの登録に失敗しました');
		
	}).always(function(){
		//常に実行する処理
	});
}

//バリデーション結果をクリア
function removeValidResult(){
	$('.is-invalid').removeClass('is-invalid');
	$('.invalid-feedback').remove();
	$('.text-danger').remove();
}

//バリデーション結果の反映
function reflectValidResult(key, value){
	
	//エラーメッセージ追加
	if(key === 'title'){
		$('input[name=' + key + ']').addClacc('is-invalid');
		//エラーメッセージ追加
		$('input[name=' + key + ']')
			.parent().parent()
			.append('<div class="text-danger">' + value + '</div>');
			
	}else{
		//CSS適用
		$('input[id=' + key + ']').addClass('is-invalid');
		//エラーメッセージ追加
		$('input[id=' + key + ']')
			.after('<div class="invalid-feedback">' + value + '</div>');
	}
}