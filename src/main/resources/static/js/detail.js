const driverUrl = `http://ergast.com/api/f1/drivers/${driverId}.json`;
const otherUrl = `http://localhost:8080/driver/api/${driverId}`;
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
				console.log(data2)
				insertInfoDriverOther(data2);
				
			}catch(error){
				alert("読み込みエラーが発生しました");
			}
		}
function insertInfoDriver(data){
	console.log(data.dateOfBirth)
	data.forEach((driver) => {
		const driverImgPath = "/img/" + driver.dateOfBirth + driver.driverId + driver.permanentNumber + ".jpg";
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
	const worldChampionsNum = document.getElementById("worldChampionsNum");
	const winsNum = document.getElementById("winsNum");
	const teamName = document.getElementById("teamName");
	const driverLink = document.getElementById("driverLink");
	
	worldChampionsNum.textContent = `${data.worldChampionships}回`;
	winsNum.textContent = `${data.wins}勝`;
	teamName.textContent = data.team;
	driverLink.setAttribute("href", data.wikiUrl)
	
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