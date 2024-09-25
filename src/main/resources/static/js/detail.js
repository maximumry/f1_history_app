const driverUrl = `http://ergast.com/api/f1/drivers/${driverId}.json`;
const teamUrl = "http://ergast.com/api/f1/2024/constructors.json";
let colDriver = document.getElementById("colDriver");
fetch(driverUrl)
	.then((response) => {
		return response.json();
	})
	.then((data) => {
		insertInfoDriver(data.MRData.DriverTable.Drivers);
	})
	.catch((error) => {
		alert("読み込みエラーが発生しました");
	})
/**async function allFetch(){
	try{
		const response1 = await fetch(driverUrl);
		const data1 = await response1.json();
		console.log(data1)
		insertInfoDriver(data1.MRData.DriverTable.Drivers)
		
		
		const response2 = await fetch(teamUrl);
		const data2 = await response2.json();
		insertInfoDriver(data2.MRData.ConstructorTable.Constructors);
	}catch(error){
		alert("読み込みエラーが発生しました");
	}
}**/
function insertInfoDriver(data){
	let driverHtml = "";
	
	data.forEach((driver) => {
		//ドライバー画像の変数
		let driverImg = "/img/" + driver.dateOfBirth + driver.driverId + driver.permanentNumber + ".jpg";
		driverHtml += `
			<div class="col-6">
				<img  src="${driverImg}" alt="画像表示エラー" style="max-width: 100%; height: auto;">
		    </div>
		    <div class="col-5">
				<ul class="list-group list-group-flush">
					<li class="list-group-item">名前 : ${driver.givenName + " " + driver.familyName}</li>
				  	<li class="list-group-item">誕生日 : ${driver.dateOfBirth.replace(/-/g, "/")}</li>
				  	<li class="list-group-item">出身国 : ${driver.nationality}</li>
				  	<li class="list-group-item">カーナンバー : ${driver.permanentNumber}</li>
				  	<a id="driverLink" href="${driver.url}" class="list-group-item">Wikipedia</a>
				</ul>
		    </div>
			`;
		console.log(driver.dateOfBirth);
		
	});
	colDriver.insertAdjacentHTML("afterbegin", driverHtml)
}

$(function() {
	$()
})