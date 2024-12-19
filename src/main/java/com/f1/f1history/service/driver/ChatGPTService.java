package com.f1.f1history.service.driver;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.f1.f1history.entity.ChatGPTRequest;
import com.f1.f1history.entity.ChatGPTResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatGPTService {

	@Value("${chatgpt.api.url}")
	private String chatGPTApiUrl;

	@Value("${chatgpt.api.key}")
	private String chatGPTApiKey;

	private final RestTemplate restTemplate;

	private static final String SYSTEM_MESSAGE = "日本語で回答してください。";

	//APIにリクエストを送信して、レスポンスからメッセージを取得する
	public String generateAPIMessage(String userMessage) {
		RequestEntity<ChatGPTRequest> request = createRequestEntity(userMessage);

		try {
			//API接続
			ResponseEntity<ChatGPTResponse> response = restTemplate.exchange(request, ChatGPTResponse.class);
			//レスポンスからメッセージを抽出して返却
			return extractAPIMessage(response);
		} catch (RestClientException e) {
			e.printStackTrace();
			return "Error connecting to the API";
		}
	}

	//リクエストエンティティを生成する
	private RequestEntity<ChatGPTRequest> createRequestEntity(String userMessage) {
		//リクエストヘッダ生成
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(chatGPTApiKey);

		//リクエストボディ生成
		ChatGPTRequest body = new ChatGPTRequest();
		body.setModel("gpt-4o-mini");
		body.setMessages(List.of(
				body.new Message("system", SYSTEM_MESSAGE),
				body.new Message("user", userMessage)));

		//リクエストエンティティ生成と返却
		return RequestEntity.post(chatGPTApiUrl).headers(headers).body(body);
	}

	//ChatGPTからのレスポンスを処理し、メッセージを抽出する
	private String extractAPIMessage(ResponseEntity<ChatGPTResponse> response) {
		//レスポンスが空の場合
		if (response == null) {
			throw new RuntimeException("Error processing the request");
		}

		//レスポンスボディまたはレスポンスボディのメッセージフィールドが空の場合
		ChatGPTResponse responseBody = response.getBody();
		if (responseBody == null || responseBody.getChoices().length == 0) {
			throw new RuntimeException("Error processing the request");
		}

		//1つ目の回答からメッセージを抽出して返却
		// *ChatGPTは複数回答をレスポンスするが、今回は一つ目の回答のみを扱う
		return responseBody.getChoices()[0].getMessage().getContent();
	}
}
