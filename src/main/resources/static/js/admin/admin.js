'use strict'
jQuery(function($){
	
	let contentType = ''
	let selectedYear = ''
	let aftCommentData = []
	let filteredCommentData = []
	let thisYear
	
	//画面がロードされた時にまずはユーザー情報を表示させる
	userAjax()
	
	$('#usersBtn').click(function(){
		$('#commentParent').empty()
		userAjax()
	})
	
	function userAjax(){
		$.ajax({
			type: "GET",
			dataType: "json",
			url: "/admin/api/users",
		}).done(function(data){
			insertUsers(data)
		}).fail(function(jqXHR, textStatus, errorThrown){
			alert("ユーザーの取得に失敗しました")
		})
	}
	
	function insertUsers(data){
		//すでに表示している要素が合ったら空にして、新規のユーザー情報を表示する
		if($('#adminWrapper')){
			$('#adminWrapper').empty()
		}
		
		contentType = 'users'
		headerChange(contentType)
		
		$.each(data, function(index, value){
			let userHtml = `
	            <tr>
			    	<td class="name">${value.name}</th>
			     	<td class="email">${value.email}</td>
			      	<td class="role">${value.role == 'ADMIN' ? "管理者" : "一般"}</td>
			     	<td class="createdAt">${value.createdAt.year}/${value.createdAt.monthValue}/${value.createdAt.dayOfMonth}</td>
			      	<td class="userDetail"><p><a href="/user/detail" class="link-warning link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover">編集</a></p></td>
		    	</tr>
			`
			$('#adminWrapper').append(userHtml)
		})
	}
	
	//53行目より下はコメント関連の関数群
	//サイドバーのボタンをクリックしたら実行される関数を実装
	$('#commentsBtn').click(function(){
		createCommentElement()
		createSelectBox()
		commentAjax()
	})
	
	$(document).on('change', '#yearSelectBox', function () {
		filteredCommentData = []
		selectedYear = $(this).val()
		
		$.each(aftCommentData, function(index, value){
			let sortYear = String(value.eventId)
			sortYear = sortYear.slice(0, 4)
			if(sortYear == selectedYear){
				filteredCommentData.push(value)
			}
		})
		insertComments(filteredCommentData)
		createRoundSelectbox(selectedYear)
	})
	
	$(document).on('change', '#roundSelectBox', function(){
		let aftSelectedRound = []
		let selectedRound = $(this).val()
		
		$.each(filteredCommentData, function(index, value){
			let sortRound = String(value.eventId)
			sortRound = sortRound.slice(4)
			if(sortRound == selectedRound){
				aftSelectedRound.push(value)
			}
		})
		insertComments(aftSelectedRound)
	})
	
	function commentAjax(){
		$.ajax({
			type: "GET",
			dataType: "json",
			url: "/admin/api/comments",
		}).done(function(data){
			aftCommentData = data
			insertComments(data)
		}).fail(function(jqXHR, textStatus, errorThrown){
			alert("ユーザーの取得に失敗しました")
		})
	}
	
	function insertComments(data){
		//すでに表示している要素が合ったら空にして、新規でコメントを表示する
		if($('#adminWrapper')){
			$('#adminWrapper').empty()
		}
		
		contentType = 'comments'
		headerChange(contentType)
		
		$.each(data, function(index, value){
			let eventId = changeEventId(value.eventId)
			let commentHtml = `
				<tr>
			    	<td class="name">${value.user.name}</th>
			     	<td class="content">${value.content}</td>
			     	<td class="createdAt">${value.createdAt.year}/${value.createdAt.monthValue}/${value.createdAt.dayOfMonth} ${value.createdAt.hour}:${value.createdAt.minute}</td>
			      	<td class="userDetail"><p><a href="/event/${eventId}" class="link-warning link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover">コメントのレース</a></p></td>
		    	</tr>
			`
			$('#adminWrapper').append(commentHtml)
		})
		
	}
	
	//コメント、ユーザーによってヘッダー表示を変化させる関数
	function headerChange(contentType){
		$('#headWrapper').empty()
		let replaceHtml = ``
		if(contentType == 'comments'){
			replaceHtml = `
				<tr>
		      		<th scope="col">ユーザー名</th>
					<th scope="col">コメント本文</th>
		      		<th scope="col">投稿日時</th>
					<th scope="col"></th>
		    	</tr>
			`
			$('#headWrapper').append(replaceHtml)
		}else if(contentType == 'users'){
			replaceHtml = `
				<tr>
		      		<th scope="col">名前</th>
					<th scope="col">メールアドレス</th>
		      		<th scope="col">権限</th>
			  		<th scope="col">アカウント作成日</th>
					<th scope="col"></th>
		    	</tr>
			`
			$('#headWrapper').append(replaceHtml)
		}
	}
	
	//eventIdの数字の間に'_'を入れる関数
	function changeEventId(eventId){
		let eventIdAry = []
		if(eventId.length == 6){
			eventId.split('').forEach((char, index) => {
				if(index == 4){
					eventIdAry.push('_')
				}
				eventIdAry.push(char)
			})
		}else{
			eventId.split('').forEach((char, index) => {
				if(index == 4){
					eventIdAry.push('_')
				}
				eventIdAry.push(char)
			})
		}
		eventId = eventIdAry.join('')
		return eventId
	}
	
	//コメントを選択された場合のHTMLを生成
	function createCommentElement(){
		let commentElement = '\
				<div class="row align-items-start">\
					<p class="text-primary">コメントがついたレースの年度とラウンドを選んで並べ替えできます</p>\
				    <div class="col">\
						<select class="form-select mb-3" aria-label="Default select example" id="yearSelectBox">\
							<!-- admin.jsでセレクトボックス作成 -->\
						</select>\
				    </div>\
				    <div class="col">\
						<select class="form-select mb-3" aria-label="Default select example" id="roundSelectBox">\
						  	<!-- admin.jsでセレクトボックス作成 -->\
						</select>\
				    </div>\
				</div>\
		    '
		    $('#commentParent').html(commentElement)
	}
	
	//開催年度のセレクトボックスを作成
	function createSelectBox(){
		thisYear = new Date().getFullYear()
		let past = thisYear - 1949
		let yearHtml = ''
		
		for(let i = 0;i < past;i++){
			yearHtml += '<option value="' + (thisYear - i) + '">' + (thisYear - i) + '</option>'
		}
		
		$('#yearSelectBox').html(yearHtml)
		createRoundSelectbox(thisYear)
	}
	
	//選択された年度の開催数を取得してセレクトボックス作成の関数
	function createRoundSelectbox(year){
		const url = `https://ergast.com/api/f1/${year}.json`
		let roundHtml = ''
		
		$.ajax({
			type: "GET",
			dataType: "json",
			url: url,
		}).done(function (data){
			const totalRounds = data.MRData.total;
			for(let i = 1; i <= totalRounds; i++){
				roundHtml += '<option value="' + i + '">' + i + '</option>'
			}
			$('#roundSelectBox').html(roundHtml)
		}).fail(function(jqXHR, textStatus, errorThrown){
			alert(`${selectedYear}年の開催数の取得に失敗しました`)
		})
	}
})