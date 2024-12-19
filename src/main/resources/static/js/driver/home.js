'use strict';

jQuery(function($){
	//セレクトボックスで1950年から生成
	let dateY = new Date().getFullYear();
	let past = dateY - 1949
	let html = ''
	
	for(let i = 0;i < past;i++){
		html += '<option value="' + (dateY - i) + '">' + (dateY - i) + '</option>'
	}
	$('#year').html(html)
	
	//画面が読み込まれたら今年の年数を引数として関数を実行してAPIから値を取得、表示
	selectRaceData(dateY)
	function selectRaceData(year){
		const allRaceUrl = `https://ergast.com/api/f1/${year}.json`
		const winRaceUrl = `https://ergast.com/api/f1/${year}/results/1.json`
		
		try{
			async function allFetch(){
				const urls = [allRaceUrl, winRaceUrl]
				
				let response = await Promise.all(urls.map(url => fetch(url)))
				let data = await Promise.all(response.map(res => res.json()))
				let raceData;
				let raceWinData;
				//二つのAPIを呼び出したので配列を用意して代入する
				for(let i = 0;i < data.length;i++){
					$.each(data[i], function(index, value){
						if(i === 0){
							raceData = value.RaceTable
						}else{
							raceWinData = value.RaceTable.Races
						}
					})
				}
				insertRace(raceData, raceWinData);
			}
			allFetch()
		}catch(error){
			console.log("Asynchronous communication failure" + error)
		}
		
	}
	
	//セレクトボックスに変化があった時の処理
	$('#year').change(function(){
		selectRaceData($(this).val())
	})
	
	//insertRaceData関数で取得したレースデータをDOMに挿入
	function insertRace(raceData, raceWinData){
		//優勝者とコンストラクタの名前を入れておく変数
		let raceWinner = []
		let winConstructor = []
		let driverId = []
		
		//前回の値を削除する処理
		$('#raceDataTbody').empty()
		//選択されたシーズンを表示する
		let season = raceData.season
		$('#season').text("シーズン：" + season)
		
		//シーズンの優勝者の名前を繰り返し処理で取得
		$.each(raceWinData, function(index, value){
			$.each(value.Results, function(index, value){
				raceWinner.push(value.Driver.givenName + ' ' + value.Driver.familyName)
				winConstructor.push(value.Constructor.name)
				driverId.push(value.Driver.driverId)
			})
		})
		
		//選択されたシーズンのレースを表示
		$.each(raceData.Races, function(index, value){
					
			var raceDataHtml = `
				<tr>
			      <th scope="row" id="round">${value.round}</th>
			      <td id="raceName">${value.raceName}</td>
			      <td id="date">${value.date.replace(/-/g, "/")}</td>
			      <td id="circuitName">${value.Circuit.circuitName}</td>
			      <td id="wins" style="background-color: #e6b422"><a href="/driver/${driverId[index]}" class="link-dark link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover">${raceWinner[index]}</a></td>
			      <td id="winConstructor">${winConstructor[index]}</td>
			      <td id="detail"><a href="/event/${value.season}_${value.round}" class="btn btn-primary">レース詳細</a></td>
		    	</tr>
			`
			$('#raceDataTbody').append(raceDataHtml)
		})
		
		//検索ボタンを押したときの処理
		$('#raceBtnSearch').click(function(event){
			//セレクトボックスのvalueを受け取ってその期間のyearを繰り返しで検索実施
			let formData = $('#searchRaceForm').serializeArray();
			
			$.ajax({
				type: 'GET',
				url: '/event/api/search',
				data: formData,
				dataType: 'json',
			}).done(function(data){
				renderSearchResults(data)
			}).fail(function(jqXHR, textStatus, errorThrown){
				alert("レース情報の取得に失敗しました")
			})
		})
		
		//検索の非同期処理に成功後のデータを表示させる関数
	 	function renderSearchResults(searchData){
			let raceWinData = searchData.winnerObjectNode.raceWinData
			let raceSearch = searchData.objectNode
			//優勝者とコンストラクタの名前を入れておく変数
			let raceWinner = []
			let winConstructor = []
			let driverId = []
			
			//前回の値を削除する処理
			$('#raceDataTbody').empty()
			
			$.each(raceWinData, function(index, value){
				//シーズンの優勝者の名前を繰り返し処理で取得
				$.each(value.Results, function(index, value){
					raceWinner.push(value.Driver.givenName + ' ' + value.Driver.familyName)
					winConstructor.push(value.Constructor.name)
					driverId.push(value.Driver.driverId)
				})
			})
			
			$.each(raceSearch.raceSearch, function(index, value){
				//選択されたシーズンのレースを表示
				var raceDataHtml = `
					<tr>
				      <th scope="row" id="round">${value.round}</th>
				      <td id="raceName">${value.raceName}</td>
				      <td id="date">${value.date.replace(/-/g, "/")}</td>
				      <td id="circuitName">${value.Circuit.circuitName}</td>
				      <td id="wins" style="background-color: #e6b422"><a href="/driver/${driverId[index]}" class="link-dark link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover">${raceWinner[index]}</a></td>
				      <td id="winConstructor">${winConstructor[index]}</td>
				      <td id="detail"><a href="/event/${value.season}_${value.round}" class="btn btn-primary">レース詳細</a></td>
			    	</tr>
				`
				$('#raceDataTbody').append(raceDataHtml)
			})
		}
	}
		
	$(document).on('mouseover', '.wikiLinkBtn', function(){
	    $(this).css({
	        color: "#E00400"
	  	 })
	})
	
	$(document).on('mouseout', '.wikiLinkBtn', function(){
		$(this).css({
			color: "#000000"
		})
	})
	
})