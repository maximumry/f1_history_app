'use strict';
jQuery(function($){
	const url = "http://ergast.com/api/f1/2024/drivers.json"
	const sortDriver = document.getElementById("sortDriver");
	const rowDriver = document.getElementById("rowDriver");
	
	console.log("通ったよ");
	fetch(url)
		.then(response => {
			return response.json();
		})
		.then((data) => {
			console.log("通った2")
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
		
		//ユーザーが(/driver) エンドポイントにアクセスした時、sortされた後に値を表示させる関数
		function aftSelectDriver(data){
			
			//すでに表示させているdriverがいたら空にするにして、sort後や検索後のdriverのみを表示させる
			if(rowDriver){
				rowDriver.innerHTML = '';
			}
			let driverHtml = "";
			data.forEach((drivers) => {
				let driverName = drivers.givenName + " " + drivers.familyName;
				let image = "/img/driver/" + drivers.dateOfBirth + drivers.driverId + drivers.permanentNumber + ".jpg";
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
		
		/** 検索ボタンを押したときの処理 */
		$('#btn-search').click(function(event){
			//検索後の値を挿入するための配列を下記で用意
			let newData = [];
			let formData = $('#driver-search-form').serialize();
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
});