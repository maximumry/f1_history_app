const driverUrl = `http://ergast.com/api/f1/drivers/${driverId}.json`;
const otherUrl = `http://localhost:8080/driver/api/${driverId}`;
const eventUrl = `http://localhost:8080/event/api/${driverId}`;
let colDriver = document.getElementById("colDriver");
	window.addEventListener('DOMContentLoaded', function(){
		allFetch();
	});
	async function allFetch(){
			try{
				const response1 = await fetch(driverUrl);
				if(!response1.ok) throw new Error('エルガストAPIからの読み取りに失敗しました');
				const data1 = await response1.json();
				insertInfoDriver(data1.MRData.DriverTable.Drivers)
				
				const response2 = await fetch(otherUrl);
				if(!response2.ok) throw new Error('その他の読み取りに失敗しました');
				const data2 = await response2.json();
				insertInfoDriverOther(data2);
				
			}catch(error){
				alert("読み込みエラーが発生しました");
			}
		}
function insertInfoDriver(data){
	data.forEach((driver) => {
		const driverImgPath = "/img/driver/" + driver.dateOfBirth + driver.driverId + driver.permanentNumber + ".jpg";
		const driverImg = document.getElementById("driverImg");
		const driverName = document.getElementById("driverName");
		const driverBirth = document.getElementById("driverBirth");
		const driverNationality = document.getElementById("driverNationality");
		const driverNumber = document.getElementById("driverNumber");
		const abbr = document.getElementById("abbr");
		
		driverImg.setAttribute("src", driverImgPath);
		driverName.textContent = `${driver.givenName} ${driver.familyName}`;
		driverBirth.textContent = driver.dateOfBirth.replace(/-/g, "/");
		driverNationality.textContent = driver.nationality;
		driverNumber.textContent = driver.permanentNumber;
		abbr.textContent = driver.code;
	});
};

function insertInfoDriverOther(data){
	//ドライバープロフィールのDOM取得
	const worldChampionsNum = document.getElementById("worldChampionsNum");
	const winsNum = document.getElementById("winsNum");
	const teamName = document.getElementById("teamName");
	const driverLink = document.getElementById("driverLink");
	
	worldChampionsNum.textContent = `${data.worldChampionships}回`;
	winsNum.textContent = `${data.wins}勝`;
	teamName.textContent = data.team;
	driverLink.setAttribute("href", data.wikiUrl)
	
	//詳細ページのドライバーイベントを表示するための処理
	const colDriverEvent = document.getElementById("colDriverEvent");
	let driverEventHtml = "";
	if(data.eventList.length == 0){
		//もしイベントが0だった時の処理を書く
		driverEventHtml = `<p>ドライバーイベントがありませんでした。<a href="/inquiry/form">こちら</a>からあなたの好きなドライバーの名シーンを追加しませんか？</p>`;
		colDriverEvent.insertAdjacentHTML("afterbegin", driverEventHtml);
	}else{
		data.eventList.forEach((event, i) => {
		let eventImgPath = ``;
		let eventTitle = event.title;
		let eventDate = event.date.replace(/-/g, '/');
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
				  </div>
				</div>
			</div>
			`
		});
		colDriverEvent.insertAdjacentHTML("afterbegin", driverEventHtml);
	}
		
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
}