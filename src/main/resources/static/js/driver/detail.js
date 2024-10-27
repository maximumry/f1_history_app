const driverUrl = `http://ergast.com/api/f1/drivers/${driverId}.json`;
const otherUrl = `http://localhost:8080/driver/api/${driverId}`;
let colDriver = document.getElementById("colDriver");
	window.addEventListener('DOMContentLoaded', function(){
		allFetch();
	});
	
	async function allFetch(){
		const urls = [driverUrl, otherUrl];
		
		let response = await Promise.all(urls.map(url => fetch(url)));
		let data = await Promise.all(response.map(res => res.json()));
		insertInfoDriver(data)
	}
		
//外部APIからの値をドライバー詳細画面に表示
function insertInfoDriver(drivers){
	
	let driverData = drivers[0].MRData.DriverTable.Drivers[0]
	
	const driverImgPath = "/img/driver/" + driverData.dateOfBirth + driverData.driverId + driverData.permanentNumber + ".jpg";
	const driverImg = document.getElementById("driverImg");
	const driverName = document.getElementById("driverName");
	const driverBirth = document.getElementById("driverBirth");
	const driverNationality = document.getElementById("driverNationality");
	const driverNumber = document.getElementById("driverNumber");
	const abbr = document.getElementById("abbr");
	
	driverImg.setAttribute("src", driverImgPath);
	driverName.textContent = `${driverData.givenName} ${driverData.familyName}`;
	driverBirth.textContent = driverData.dateOfBirth.replace(/-/g, "/");
	driverNationality.textContent = driverData.nationality;
	driverNumber.textContent = driverData.permanentNumber;
	abbr.textContent = driverData.code;
	
	drivers[1].forEach((driver) => {
		//ドライバー詳細画面 右側のDOMを取得
		const worldChampionsNum = document.getElementById("worldChampionsNum");
		const winsNum = document.getElementById("winsNum");
		const teamName = document.getElementById("teamName");
		const driverLink = document.getElementById("driverLink");
		const recRelaDriver = document.getElementById("recRelaDriver");
			
		//一覧画面で選択されたドライバーの詳細を表示するための条件分岐(レコメンドも含めた値が取得されるための条件分岐)
		if(driver.driverId === driverId){
			
			worldChampionsNum.textContent = `${driver.worldChampionships}回`;
			winsNum.textContent = `${driver.wins}勝`;
			teamName.textContent = driver.team;
			driverLink.setAttribute("href", driver.wikiUrl)
			
			//詳細ページのドライバーイベントを表示するための処理
			const colDriverEvent = document.getElementById("colDriverEvent");
			let driverEventHtml = "";
			if (driver.eventList.length == 0){
				//もしイベントが0だった時の処理を書く
				console.log("イベント0")
				driverEventHtml = `<p>ドライバーイベントがありませんでした。<a href="/inquiry/form">こちら</a>からあなたの好きなドライバーの名シーンを追加しませんか？</p>`;
				colDriverEvent.insertAdjacentHTML("afterbegin", driverEventHtml);
			}else{
				driver.eventList.forEach((event) => {
					let eventImgPath = ``;
					let eventTitle = event.title;
					let eventDate = event.date.year + "/" + event.date.dayOfMonth + "/" + event.date.monthValue;
					
					let eventText = event.description;
					let eventCondition = event.weatherCondition;
					let youtubeUrl = event.youtubeUrl;
					
					driverEventHtml += `
						<div class="col">
							<div class="card mt-3 w-100 h-100" style="width: 20rem;">
							  <img src="${eventImgPath}" class="card-img-top" alt="..." id="eventImg">
							  <div class="card-body">
							    <h5 class="card-title" id="eventTitle">${eventTitle}</h5>
								<p class="card-text" id="eventDate">${eventDate}</p>
								<p class="card-text" id="eventText">${eventText}</p>
								<p class="card-text" id="eventCondition">${eventCondition}</p>
							    <a href="${youtubeUrl}" class="card-link" id="youtubeUrl">YouTube</a>
							    <a href="/event/${event.eventId}" class="card-link">イベント編集</a>
							  </div>
							</div>
						</div>
						`
					});
					colDriverEvent.insertAdjacentHTML("afterbegin", driverEventHtml);
			}
		}else{
			
			//レコメンドドライバー表示のためのDOM取得
			const colRecDriver = document.getElementById("colRecDriver");
			
			let recDriverHtml = "";
			recDriverHtml += `
				<div class="col" id="colDriver">
					<div class="card mt-3" style="width: 20rem;" id="driverCard">
					  <div class="card-body">
					    <h5 class="card-title" id="driverName">${driver.driverId}</h5>
						<a href="http://localhost:8080/driver/${driver.driverId}" class="btn btn-info">ドライバー詳細</a>
					  </div>
					</div>
				</div>
			`;
			colRecDriver.insertAdjacentHTML("afterbegin", recDriverHtml);
		}
		recRelaDriver.textContent = driverId + "に関連したドライバー"
	})
	
	driverLink.addEventListener("mouseover", function(event){
		setTimeout(() => {
			event.target.style.color = "red";
		}, 1);
	}, false);
		
	driverLink.addEventListener("mouseout", function(event){
		setTimeout(() => {
			event.target.style.color = "";
		}, 1);
	}, false);
	
	//ドライバー編集ボタンが押された時の処理
	const editDriverBtn = document.getElementById('edit-driver-btn');
	editDriverBtn.addEventListener('click', function(){
		window.location.href = `/driver/${driverData.driverId}/edit`;
	})

	//ドライバー削除ボタンが押された時の処理
	const deleteDriverBtn = document.getElementById("delete-driver-btn");
	deleteDriverBtn.addEventListener('click', async () => {
		
		const deleteConfirm = confirm("ドライバーを削除しても大丈夫ですか？");
		
		if(deleteConfirm){
			try{
					const response = await fetch(
					`http://localhost:8080/driver/api/${driverId}`,
					{
						method: 'DELETE'
					})
			}catch(error){
				alert("削除に失敗しました");
				window.location.href = "/driver";
			}
		}else{
			console.log("delete cancel")
		}
	})
	
};

//データベースから取得した値を詳細画面に表示
function insertInfoDriverOther(recDriver){
	
	
	
	
}