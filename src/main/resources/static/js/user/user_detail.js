'use strict'
jQuery(function($){
	
	$('#updateBtn').click(function(){
		if (confirm("アカウント情報を更新しますか？")) {
		  	$('#userDetailForm').submit()
		}else{
			event.preventDefault()
			alert("アカウント更新をキャンセルしました")
		}
	})
	
	$('#deleteBtn').click(function(event){
		let result = confirm("アカウントを削除してもよろしいですか？\nアカウントは元に戻せませんのでご注意下さい")
		if(result){
			alert("アカウントを削除しました")
			window.location.href = "/user/login"
		}else{
			event.preventDefault()
			alert("アカウント削除をキャンセルしました")
		}
	})
})