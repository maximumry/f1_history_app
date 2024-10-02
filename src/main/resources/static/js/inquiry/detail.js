'use strict'

//画面ロード時の処理
jQuery(function($){
	//削除ボタンを押した時の処理
	$('#btn-delete').click(function(event){
		deleteUser();
	});
});

//ユーザー削除処理
function deleteUser(){
	//フォームの値を取得
	var formData = $('#inquiry-detail-form').serializeArray();
	
	//Ajax通信
	$.ajax({
		type: "DELETE",
		cache: false,
		url: '/inquiry/delete',
		data: formData,
		dataType: 'json', 
	}).done(function(data){
		//ajax成功時
		alert('問い合わせを完了にしました');
		//一覧にリダイレクト
		window.location.href='/inquiry';
	}).fail(function(jqXHR, textStatus, errorThrown){
		//ajax失敗時の処理
		alert('問い合わせ完了に失敗しました');
	}).always(function(){
		//常に実行する処理
	});
}