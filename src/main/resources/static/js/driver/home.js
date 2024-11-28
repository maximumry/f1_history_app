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
			let formData = $('#searchRaceForm').serialize();
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
					console.log(value.Driver + "ドライバーウィン")
					winConstructor.push(value.Constructor.name)
					driverId.push(value.Driver.driverId)
				})
			})
			
			$.each(raceSearch.raceSearch, function(index, value){
				console.log(value + "ドライバー除法")
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

//前レースを取得する
/**jQuery(function($){
	
	//ErgastAPIから全てのレースを取得 + Driver情報も全て取得して
	const url = "http://ergast.com/api/f1/2024/drivers.json"
	const sortDriver = document.getElementById("sortDriver");
	const rowDriver = document.getElementById("rowDriver");
	
	fetch(url)
		.then(response => {
			return response.json();
		})
		.then((data) => {
			insertDriverInfo(data.MRData.DriverTable.Drivers);
		})
		.catch((error) => {
			alert("読み込みエラーが発生しました");
		});
		
	//非同期通信成功時の関数
	function insertDriverInfo(data){
		//ユーザーが (/driver) エンドポイントにアクセスした際に、ページロード時に最初に実行される関数
		aftSelectDriver(data);
		
		//home.htmlのセレクトボックスに変化があった場合に実行される関数
		sortDriver.addEventListener("change", (e) => {
			let descendSort = "";
			const selectVal = e.target.value;
			if(selectVal == "familyName"){
				//sortの引数にはdataの値を二つ比べる為にある
				descendSort = data.sort((driver1, driver2) => {
					return (driver1.familyName < driver2.familyName) ? -1 : 1;
				});
			}else if(selectVal == "carNum"){
				descendSort = data.sort((a, b) => {
					return (a.permanentNumber < b.permanentNumber) ? -1 : 1;
				})
			}else if(selectVal == "age"){
				descendSort = data.sort((a, b) => {
					return (a.dateOfBirth < b.dateOfBirth) ? -1 : 1;
				})
			}
			//sortされた値をhome.htmlに表示させる
			aftSelectDriver(descendSort);
		})
		
		//ユーザーが(/driver) エンドポイントにアクセスした時とsortされた後に値を表示させる関数
		function aftSelectDriver(data){
			
			//すでに表示させているdriverがいたら空にするにして、sort後や検索後のdriverのみを表示させる
			if(rowDriver){
				rowDriver.innerHTML = '';
			}
			let driverHtml = "";
			data.forEach((drivers) => {
				let driverName = drivers.givenName + " " + drivers.familyName;
				let image = "/img/driver/" + drivers.driverId + ".jpg";
		        var driverBirthDay = drivers.dateOfBirth.replace(/-/g, '/');
						  
				driverHtml += `
					<div class="col" id="colDriver">
						<div class="card mt-3" style="width: 20rem;" id="driverCard">
						  <div class="card-body">
						    <img src="${image}" class="card-img-top" id="driverImage">
						    <h5 class="card-title" id="driverName">${driverName}</h5>
						    <p class="card-text mb-2">BirthDay：${driverBirthDay}</p>
						    <p class="card-text">Nationality：${drivers.nationality}</p>
							<a href="http://localhost:8080/driver/${drivers.driverId}" class="btn btn-info">ドライバー詳細</a>
						  </div>
						</div>
					</div>
				`;
			});
			rowDriver.insertAdjacentHTML("afterbegin", driverHtml);
		}
		
		//検索ボタンを押したときの処理 
		$('#raceBtnSearch').click(function(event){
			let formData = $('#searchRaceForm').serialize();
			formData = formData.toLowerCase();
			formData = formData.split("=")[1];
			data.forEach((driver) => {
				let fullName = driver.givenName + " " + driver.familyName;
				fullName = fullName.toLowerCase();
				if(fullName.indexOf(formData) != -1){
					newData.push(driver);
				}
			})
			aftSelectDriver(newData)
		});
		
	}
});*/