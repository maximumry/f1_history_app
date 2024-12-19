package com.f1.f1history.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ChatGPTResponse {

	private String id;
	private String object;
	private Long created;
	private String model;
	private String systemFingerprint;
	private Choice[] choices;
	private Usage usage;

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Choice {
		private int index;
		private Message message;
		private String logprobs;
		private String finish_reason;
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Usage {
		private int prompt_tokens;
		private int completion_tokens;
		private int total_tokens;
		private CompletionTokensDetail completionTokensDetails;
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Message {
		private String role;
		private String content;
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class CompletionTokensDetail {
		private int reasoningTokens;
		private int acceptedPredictionTokens;
		private int rejectedPredictionTokens;
	}
}
