const url = "http://ergast.com/api/f1/2024/drivers.json"
let colDriver = document.getElementById("colDriver");
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
function insertDriverInfo(data){
	let driverHtml = "";
	data.forEach((drivers) => {
		let driverName = drivers.givenName + " " + drivers.familyName;
		let image = "/img/" + drivers.dateOfBirth + drivers.driverId + drivers.permanentNumber + ".jpg";
        var driverBirthDay = drivers.dateOfBirth.replace(/-/g, '/');
				  
		driverHtml += `
			<div class="col">
				<div class="card mt-3" style="width: 20rem;" id="driverCard">
				  <div class="card-body">
				    <img src="${image}" class="card-img-top" id="driverImage">
				    <h5 class="card-title" id="driverName">${driverName}</h5>
				    <p class="card-text mb-2">BirthDay：${driverBirthDay}</p>
				    <p class="card-text">Nationality：${drivers.nationality}</p>
					<a href="${drivers.url}" class="btn btn-light">Wikipedia</a>
					<a href="http://localhost:8080/driver/${drivers.driverId}" class="btn btn-info">ドライバー詳細</a>
				  </div>
				</div>
			</div>
		`;
	});
	colDriver.insertAdjacentHTML("afterbegin", driverHtml);
}