'use strict'

jQuery(function($){
	//wikipediaのpageIdをお入れておく変数
	var pageId;
	
	const driverUrl = `http://ergast.com/api/f1/drivers/${driverId}.json`;
	
	$.ajax({
		type: 'GET',
		url: driverUrl,
		dataType: 'json',
	}).done(function(data){
		insertInfoDriver(data.MRData.DriverTable.Drivers[0])
	}).fail(function(jqXHR, textStatus, errorThrown){
		alert('ドライバー情報の取得に失敗しました')
	})
	
	function insertInfoDriver(driverData){
		//取得したドライバーデータから足りない値をWikipediaAPIから取得
		selectInfoDriverPlus(driverData)
		
		//APIから取得した値を表示する処理
		$('#driverName').text(driverData.givenName + ' ' + driverData.familyName)
		$('#driverNumber').text(driverData.permanentNumber)
		$('#driverNationality').text(driverData.nationality)
		$('#driverBirth').text(driverData.dateOfBirth.replace(/-/g, '/'))
		$('#abbr').text(driverData.code)
		$('#driverLink').attr('href', driverData.url)
	}
	
	function selectInfoDriverPlus(driverData){
		const wikiUrl = `https://en.wikipedia.org/w/api.php?action=query&format=json&list=search&srsearch=${driverData.givenName}%20${driverData.familyName}&origin=*`
		
		$.ajax({
			type: 'GET',
			url: wikiUrl,
			dataType: 'json',
		}).done(function(data){
			pageId = data.query.search[0].pageid
			$.ajax({
				type: 'GET',
				url: `https://en.wikipedia.org/w/api.php?action=query&format=json&prop=images&pageids=${pageId}&origin=*`,
				dataType: 'json',
			}).done(function(data){
				insertInfoDriverPlus(data.query.pages[pageId].images[0])
			})
		})
	}
	
	function insertInfoDriverPlus(driverData){
		const imagePath = driverData.title
		$.ajax({
			type: 'GET',
			url: `https://en.wikipedia.org/w/api.php?action=query&titles=${imagePath}&prop=imageinfo&iiprop=url&format=json&origin=*`,
			dataType: 'json',
		}).done(function(data){
			const url = data.query.pages['-1'].imageinfo[0].url;
			$('#driverImg').attr('src', url)
		}).fail(function(){
			alet("ドライバーの画像取得に失敗しました")
		})
	}
	
	//ドライバーについて聞きたいことをChatGPTに聞く処理
	$('#send-button').click(function(){
		const message = $('#input-field').val()
		sendRequest(message)
	})
	
	function sendRequest(message){
		$.ajax({
			type: 'POST',
			url: "/openAiChat",
			dataType: 'json',
			data: JSON.stringify({
				message : message
			})
		}).done(function(response){
			$('.chat-response-area').html('<p>' + insertNewlines(response) + '</p>');
		}).fail(function(){
			alert("OpenAIとの接続に失敗しました")
		})
	}
	
	function insertNewlines(str){
		let result = ''
		
		for(let i = 0;i < str.length;i++){
			let substr = str.substring(i, i + 10)
			let idx = substr.lastIndexOf('。')
			if(idx !== -1){
				result += substr.substring(0, idx + 1) + '<br>'
				i += idx + 1
			}else{
				result += substr
				i += 10
			}
		}
		return result
	}
	
})