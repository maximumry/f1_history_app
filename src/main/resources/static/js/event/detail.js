'use strict'

jQuery(function($){
	
	//eventIdを元にイベント詳細取得
	$.ajax({
		type: "GET",
		url: `/event/api/${eventId}`,
		dataType: "json",
	}).done(function(data){
		showEventDetails(data);
	}).fail(function(jqXHR, textStatus, errorThrown){
		alert("イベント取得に失敗しました")
		console.log(jqXHR + "イベント取得失敗");
	})
	
	//イベント詳細画面のセレクトボックスにドライバー情報を表示のための非同期処理
	$.ajax({
		type: "GET",
		url: '/driver/api',
		dataType: "json",
	}).done(function(data){
		for(var i in data){
			$('#driverSelectbox').append("<option value=" + data[i].driverId + ">" + data[i].driverId + "</option>");
		}
	}).fail(function(jqXHR, textStatus, errorThrow){
		alert("ドライバー情報の取得に失敗しました。ページをリロードしてもう一度お試しください");
	})
	
	//イベント詳細のアップデート実行のイベント発火処理
	$('#btn-event-form').click(function(event){
		updateEventDetail(event);
	})
})

function showEventDetails(data){
	// イベントタイトル要素に詳細データを表示
	$('#title').val(data.title);
	
	//日付を表示
	$('#date').val(data.date);
	
	//カテゴリーを表示
	$('#category').val(data.category);
	
	//天候状況を表示
	$('#weather-condition').val(data.weatherCondition);
	
	//YouTubeURLを表示
	$('#youtube-url').val(data.youtubeUrl);
	
	//イベント解説の表示
	$('#floatingTextarea2').val(data.description);
	
	//セレクトボックスにデータを表示(実行タイミングを遅くしてセレクトボックスにdriverIdを表示させてる)
	setTimeout(function(){
		$('#driverSelectbox').val([data.driver.driverId]);
	},500)
}

//イベント更新処理
function updateEventDetail(){
	//バリデーション結果をクリア
	removeValidResult();
	
	//更新フォームの値を取得
	var formData = $('#event-update-form').serializeArray();
	$.ajax({
		type: "PUT",
		url: `/event/api/${eventId}`,
		cache: false,
		data: formData,
		dataType: 'json',
	}).done(function(data){
		//ajax通信成功時の処理
		if(data.result === 90){
			//validationエラー時の処理
			$.each(data.errors, function(key, value){
				reflectValidResult(key, value);
			})
		}else if(data.result === 0){
			alert("イベントを更新しました");
			window.location.href = '/driver';
		}
	}).fail(function(jqXHR, textStatus, errorThrown){
		//ajax失敗した時の処理
		alert("イベント更新に失敗しました");
	});
}

function reflectValidResult(key, value){
	$('input[name=' + key + ']').addClass('is-invalid')
		.after('<div class="invalid-feedback">' + value + '</div>');
}

function removeValidResult(){
	$('.is-invalid').removeClass('is-invalid');
	$('.invalid-feedback').remove();
	$('.text-danger').remove();
}