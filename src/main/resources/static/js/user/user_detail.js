'use strict'
jQuery(function($){
	
	$('#updateBtn').click(function(event){
		if (confirm("アカウント情報を更新しますか？")) {
		  	$('#userDetailForm').submit()
		}else{
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
	
	$('.commentDeleteBtn').click(function(){
		let commentId = $(this).data('id')
		let result = confirm('アカウントを削除してもよろしいですか？\nアカウントは元に戻せませんのでご注意下さい')
		if(result){
			deleteMyComment(commentId)
		}
		console.log(commentId)
	})
	
	function deleteMyComment(commentId){
		$.ajax({
			type: 'DELETE',
			url: `/comment/${commentId}`,
			dataType: 'json',
		}).done(function(data){
			if(data === true){
				$(`#cardContainer${commentId}`).remove()
			}else if(data === false){
				alert("削除に失敗しました")
			}
		}).fail(function(jqXHR, textStatus, errorThrown){
			alert("削除に失敗しました")
		})
	}
})