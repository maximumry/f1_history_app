'use strict'
jQuery(function($){
	const raceUrl = `http://ergast.com/api/f1/${eventId[0]}/${eventId[1]}/results.json`
	const commentUrl = `http://localhost:8080/comment/${eventId[0]}${eventId[1]}`
	
	async function allAjax(){
		const urls =[raceUrl, commentUrl]
		
		let response = await Promise.all(urls.map(url => fetch(url)))
		let data = await Promise.all(response.map(res => res.json()))
		const raceDetail = data[0].MRData.RaceTable
		const commentData = data[1]
		detailRaceInsert(raceDetail)
		showComments(commentData)
	}
	
	//非同期通信の処理を開始
	allAjax()

	//非同期処理で得たデータを変数に定義してDOMに挿入
	function detailRaceInsert(raceDetail){
		$.each(raceDetail.Races[0].Results, function(index, value){
			let driverId = value.Driver.driverId
			var raceDetailHtml = `
				<tr>
			      <td class="position">${value.position}</th>
			      <td class="carNum">${value.number}</td>
			      <td class="driverName"><a href="/driver/${driverId}" class="link-dark link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover">${value.Driver.givenName} ${value.Driver.familyName}</a></td>
			      <td class="constructor">${value.Constructor.name}</td>
			      <td class="startGrid">${value.grid}</td>
			      <td class="status">${value.status}</td>
			      <td class="point">${value.points}</td>
		    	</tr>
			`
			$('#raceDetialTbody').append(raceDetailHtml)
			if(value.position == 1){
				$("tr:eq(1) td").css("background-color", "#FFF9E6")
			}else if(value.position == 2){
				$("tr:eq(2) td").css("background-color", "#c0c0c0")
			}else if(value.position == 3){
				$("tr:eq(3) td").css("background-color", "#b87333")
			}
		})
	}
	
	function showComments(commentData){
		$.each(commentData, function(index, value){
			//ログイン中のユーザーがコメントを投稿した場合は削除ボタンを表示する処理
			if(userId == value.user.userId){
				var deleteBtnHtml = `
					<div class="text-end mt-2">
						<button type="button" class="commentDeleteBtn" data-id="${value.commentId}">
							<img src="/img/event/trash.png" class="card-img-top" alt="画像が読み込まれませんでした" style="width: 30px; height: 35px;">
						</button>
					</div>
				`
			}else{
				var deleteBtnHtml = ``
			}
			
			var commentHtml = `
				<div class="card mb-2" id="cardContainer${value.commentId}">
					<div class="card-body">
				    	<h5 class="card-title">${value.user.name}</h5>
				    	<p class="card-text">${value.content}</p>
				    	<div class="d-flex justify-content-between align-items-center">
				    		<div class="card-footer text-body-secondary w-100">
								${value.createdAt.year}/${value.createdAt.monthValue}/${value.createdAt.dayOfMonth} ${value.createdAt.hour}:${value.createdAt.minute}
							</div>
							${deleteBtnHtml}
				    	</div>
					</div>
				</div>
			`
			$('#showComment').append(commentHtml)
		})
	}
	
	// コメント投稿ボタンが押された時の処理を設定
	$('#commentInsertBtn').click(function(){
		$('#hiddenEventId').val(`${eventId[0]}${eventId[1]}`)
		postComment()
	})
	
	//コメント入力フォームを空にする関数
	function resetForm(){
		$('textarea').val('')
	}
	
	// コメントをPOSTリクエストで送信する処理
	function postComment(){
		//同じバリデーションが重なって表示されないようにバリデーションを削除
		removeValidResult()
		
		const commentData = $('#commentInsertForm').serializeArray()
		
		$.ajax({
			type: "POST",
			dataType: "json",
			url: "/comment",
			data: commentData,
		}).done(function(data){
			//ajaxに成功した時の処理の関数
			if(data.result === 0){
				const comment = data.comment
				console.log(data)
				const newComment = `
					<div class="card mb-2" id="cardContainer${comment.commentId}">
						<div class="card-body">
					    	<h5 class="card-title">${comment.user.name}</h5>
					    	<p class="card-text">${comment.content}</p>
					    	<div class="d-flex justify-content-between align-items-center">
						    	<div class="card-footer text-body-secondary w-100">
						    		${comment.createdAt.year}/${comment.createdAt.monthValue}/${comment.createdAt.dayOfMonth} ${comment.createdAt.hour}:${comment.createdAt.minute}
								</div>
								<div class="text-end mt-2">
									<button type="button" class="commentDeleteBtn" data-id="${comment.commentId}">
										<img src="/img/event/trash.png" class="card-img-top" alt="画像が読み込まれませんでした" style="width: 30px; height: 35px;">
									</button>
								</div>
							</div>
						</div>
					</div>
				`
				//コメント欄の先頭へDOMを挿入
				$('#showComment').prepend(newComment)
				
				//入力フォームをクリアにする
				resetForm()
			}else if(data.result === 90){
				$.each(data.errors, function(key, value){
					reflectValidResult(key, value)
				})
			}
		}).fail(function(jqXHR, textStatus, errorThrown){
			alert("コメントのポストに失敗しました")
		})
	}
	
	// コメント削除ボタンが押された時の処理を設定
	$(document).on('click', '.commentDeleteBtn', function(event){
		var documentComment = $(this).data('id')
		if(confirm("削除してもよろしいですか？")){
			deleteComment(documentComment)
		}
	})
	
	function deleteComment(commentId){
		//同じバリデーションが重なって表示されないようにバリデーションを削除
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
	
	// 入力フィールドにバリデーションエラーを表示するための関数
	function reflectValidResult(key, value){
		console.log(key)
	    $('textarea[name=' + key + ']').addClass('is-invalid')
	        .after('<div class="invalid-feedback">' + value + '</div>')
	}
	
	// バリデーションエラーメッセージを削除するための関数
	function removeValidResult(){
		$('.is-invalid').removeClass('is-invalid')
		$('.invalid-feedback').remove()
	}
})