'use strict'

jQuery(function($){
	
	//ドライバーの登録関数を実行
	$('#driver-insert-btn').click(function(event){
		insertDriver();
	})
})

function insertDriver(){
	removeValidResult();
	
	//ドライバーフォームを取得
	var formData = $('#driver-insert-form').serializeArray();
	$.ajax({
		type: 'POST',
		url: '/driver/api',
		data: formData,
		dataType: 'json',
	}).done(function(data){
		//ajax通信成功時の処理
		if(data.result === 0){
			alert("ドライバーを追加しました")
			window.location.href = "/driver";
		}else{
			//バリデーションエラー時の処理
			$.each(data.errors, function(key, value){
				reflectValidResult(key, value);
			})
		}
	}).fail(function(jqXHR, textStatus, errorThrown){
		alert("ドライバー追加に失敗しました");
	})
}

//keyはDriverクラスのフィールド。valueはmessageSourceで取得したエラーメッセージ
function reflectValidResult(key, value){
	$('input[name=' + key + ']').addClass('is-invalid')
		.after('<div class="invalid-feedback">' + value + '</div>');
};

function removeValidResult(){
	$('.is-invalid').removeClass('is-invalid');
	$('.invalid-feedback').remove();
}