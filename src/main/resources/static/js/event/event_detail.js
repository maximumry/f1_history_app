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
	
	//非同期処理で得たデータをHTMlの入った変数に定義。
	function detailRaceInsert(raceDetail){
		$.each(raceDetail.Races[0].Results, function(index, value){
			var raceDetailHtml = `
				<tr>
			      <td class="position">${value.position}</th>
			      <td class="carNum">${value.number}</td>
			      <td class="driverName">${value.Driver.givenName} ${value.Driver.familyName}</td>
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
			var commentHtml = `
				<div class="card mb-2">
					<div class="card-body">
				    	<h5 class="card-title">${value.user.name}</h5>
				    	<p class="card-text">${value.content}</p>
				    	<div class="card-footer text-body-secondary">
							${value.createdAt.year}/${value.createdAt.monthValue}/${value.createdAt.dayOfMonth} ${value.createdAt.hour}:${value.createdAt.minute}
						</div>
						<div class="text-end">
							<button type="button" class="commentDeleteBtn" data-id="${value.commentId}">
								<img src="/img/event/trash.png" class="card-img-top" alt="画像が読み込まれませんでした" style="width: 30px; height: 30px;">
							</button>
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
				const newComment = `
					<div class="card mb-2">
						<div class="card-body">
							<input type="hidden" 
					    	<h5 class="card-title">${comment.user.name}</h5>
					    	<p class="card-text">${comment.content}</p>
					    	<div class="card-footer text-body-secondary">
					    		${comment.createdAt.year}/${comment.createdAt.monthValue}/${comment.createdAt.dayOfMonth} ${comment.createdAt.hour}:${comment.createdAt.minute}
							</div>
							<div class="text-end">
								<button type="button" class="commentDeleteBtn" data-id="${comment.commentid}">
									<img src="/img/event/trash.png" class="card-img-top" alt="画像が読み込まれませんでした" style="width: 30px; height: 30px;">
								</button>
							</div>
						</div>
					</div>
				`
				$('#showComment').prepend(newComment)
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
		console.log("通った")
		if(confirm("削除してもよろしいですか？")){
			deleteComment(documentComment)
		}
	})
	
	function deleteComment(comment){
		//同じバリデーションが重なって表示されないようにバリデーションを削除
		console.log(comment)
		
		console.log("あああ")
		
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