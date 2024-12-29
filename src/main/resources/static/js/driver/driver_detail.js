'use strict'

jQuery(function($){
	//wikipediaのpageIdをお入れておく変数
	var pageId;
	var driverName;
	
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
		
		driverName = driverData.givenName + ' ' + driverData.familyName
	}
	
	//driverIdを元に取得したドライバーの名前を元にドライバーのWikiepdia情報を取得
	function selectInfoDriverPlus(driverData){
		const wikiUrl = `https://en.wikipedia.org/w/api.php?action=query&format=json&list=search&srsearch=${driverData.givenName}%20${driverData.familyName}&origin=*`
		
		//wikiUrl変数で取得したドライバーの検索結果配列1つ目のページのimage情報を取得
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
	
	$('#chatGptBtn').click(function(event){
		let formData = $('#ChatGptForm').serialize()
		$('#genMessage').append("<p>生成中です。少々お待ちください</p>")
		
		$.ajax({
			type: 'GET',
			url: '/driver/chatgpt',
			data: formData,
			dataType: 'text',
		}).done(function(data){
			renderChatGPTResponse(data)
		}).fail(function(jqXHR, textStatus, errorThrown){
			$('#genMessage').children().remove()
			alert("ChatGPTへのリクエストに失敗しました")
		})
	})
	
	function renderChatGPTResponse(data){
		if(data === "" || data === null){
			$('#chatGptResponse').text("こんにちは")
		}
		$('#genMessage').children().remove()
		$('#chatGptResponse').text(data)
	}
	
	setTimeout(() => {
		generateTemplateBtn()
	}, "1000")
	
	function generateTemplateBtn(){
		var templateHtml = `
			<button type="button" class="btn btn-outline-dark templateText" data-id="template1" id="template1">${driverName}の名レースを教えて</button>
			<button type="button" class="btn btn-outline-dark templateText" data-id="template2" id="template2">${driverName}最大のライバルは？</button>
			<button type="button" class="btn btn-outline-dark templateText" data-id="template3" id="template3">${driverName}と他のドライバーと比較して</button>
		`
		$('#askTemplate').append(templateHtml)
	}
	
	$(document).on('click', '.templateText', function(){
		let btnId = $(this).data('id')
		let btnText = $(`#${btnId}`).text()
		$('#userMessage').val(btnText)
	})
	
})