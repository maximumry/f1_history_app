'use strict'

jQuery(function($){
	
	//eventIdを元にイベント詳細取得
	$.ajax({
		type: "GET",
		url: `/driver/api/${driverId}`,
		dataType: 'json',
	}).done(function(data){
		setDriverDetail(data);
	}).fail(function(jqXHR, textStatus, errorThrown){
		alert("ドライバーの取得に失敗しました")
	})
	
	//更新ボタンが押された時の処理
	$('#driver-edit-btn').click(function(event){
		updateDriver();
	})
	
})

//ドライバー情報の値を表示させる関数
function setDriverDetail(data){
	
	// レコメンドのドライバーを取得しているためforEachで繰り返しをして、
	// HTMLでサーバーサイドから取得したdriverIdと一致するドライバーのみを条件分岐で処理する。
	data.forEach((driver) => {
		
		if(driver.driverId === driverId){
			console.log(driver)
			$('#driverId').val(driver.driverId);
			
			$('#team').val(driver.team);
			
			$('#worldChampionships').val(driver.worldChampionships);
			
			$('#wins').val(driver.wins);
			
			$('#wikiUrl').val(driver.wikiUrl);
		}
		
	})
}

//ドライバー情報を更新する関数
function updateDriver(){
	//バリデーションをリセット
	removeValidResult
	
	//ドライバーフォームの値を取得
	var formData = $('#driver-edit-form').serializeArray();
	
	$.ajax({
		type: "PUT",
		url: `/driver/api/${driverId}`,
		data: formData,
		dataType: "json",
	}).done(function (data){
		//ajax成功時の処理
		if(data.result === 0){
			alert("ドライバー情報を更新しました");
			window.location.href = "/driver";
		}else if(data.result === 90){
			$.each(data.errors, function(key, value){
				reflectValidResult(key, value);
			})
		}
	}).fail(function(jqXHR, textStatus, errorThrown){
		alert("ドライバー情報の更新に失敗しました");
		window.location.href = "/driver";
	})
}

function reflectValidResult(key, value){
	$('input[name = ' + key + ']').addClass('is-invalid')
		.after('<div class="invalid-feedback">' + value + '</div>');
}

function removeValidResult(){
	$('.is-invalid').removeClass('is-invalid');
	$('.invalid-feedback').remove();
}