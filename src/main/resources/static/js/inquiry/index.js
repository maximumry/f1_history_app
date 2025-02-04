'use strict'

//画面ロード時の処理
jQuery(function($){
	//問い合わせ状況のスイッチが変化した時の処理
	let isSwitch = false
	$('#flexSwitchCheckDefault').click(function(event){
		switchDone(isSwitch);
		isSwitch = !isSwitch
	});
});

//問い合わせ状況(完了/未完了)変化の処理
function switchDone(isSwitch){
	$('#switchInquiryDone').empty()
	if(isSwitch){
		changeTables(inquiryList)
	}
	let count = 0
	let switchInquiryList = []
	while(inquiryList.length > count){
		if(inquiryList[count].deletedAt == null){
			switchInquiryList[count] = inquiryList[count]
		}
		count++
	}
	changeTables(switchInquiryList)
}

function changeTables(switchInquiryList){
	$.each(switchInquiryList, function(index, value){
		//問い合わせの種類を分別
		let inquirySelect = ""
		if(value.inquirySelect == "problem"){
			inquirySelect = "トラブル・技術的な問題に関する問い合わせ"
		}else if(value.inquirySelect == "account"){
			inquirySelect = "アカウントに関する問い合わせ"
		}else{
			inquirySelect = "そのほかのお問い合わせ"
		}
		//問い合わせの完了/未完を分別
		let unfinished = ""
		if(value.deletedAt == null){
			unfinished = "未完"
		}else{
			unfinished = "完了"
		}
		//問い合わせ投稿日時の成型
		let dataTime = value.createdAt.replace(/-/g, '/')
		dataTime = dataTime.replace(/T/, ' ')
		
		let newTableInquiry = `
			<tr>
		      <td>${value.user.name}</th>
		      <td>${value.user.email}</td>
		      <td>${dataTime}</td>
		      <td>${inquirySelect}</td>
		      <td>${unfinished}</td>
		      <td><a href="/inquiry/${value.inquiryId}">
		      	<button type="button" class="btn btn-primary">詳細</button>
		      </a></td>
		    </tr>
		` 
		$('#switchInquiryDone').append(newTableInquiry)
	})
}