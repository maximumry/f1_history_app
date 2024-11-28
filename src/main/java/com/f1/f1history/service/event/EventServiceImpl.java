package com.f1.f1history.service.event;

import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.f1.f1history.dao.EventInfoMapper;
import com.f1.f1history.entity.Event;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

	public static final String RACEURL = "https://ergast.com/api/f1/year.json";
	public static final String WINNERURL = "https://ergast.com/api/f1/${year}/results/1.json";

	private final EventInfoMapper eventInfoMapper;
	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;

	@Override
	public List<Event> getEventAll() {
		return eventInfoMapper.getEventAll();
	}

	@Override
	public void insertEvent(Event event) {
		eventInfoMapper.insertEvent(event);
	}

	@Override
	public void updateEvent(Event event) {
		eventInfoMapper.updateEvent(event);
	}

	@Override
	public Optional<Event> getEvent(String eventId) {
		return eventInfoMapper.getEvent(eventId);
	}

	@Override
	public void deleteEvent(Event event) {
		eventInfoMapper.deleteEvent(event);
	}

	@Override
	public Map<String, ObjectNode> getSearchRace(String raceSearch) {
		Map<String, ObjectNode> raceList = filterRace(raceSearch);
		return raceList;
	}

	@Override
	public Map<String, ObjectNode> filterRace(String raceSearch) {

		//JavaでJSONデータを扱うためにArrayNodeのオブジェクトを作成
		ArrayNode arrayNode = objectMapper.createArrayNode();
		ArrayNode raceWinnerArray = objectMapper.createArrayNode();
		//最後はObjectNodeのインスタンスのsetメソッドを使うことでkeyとvalueの形になる
		ObjectNode objectNode = objectMapper.createObjectNode();
		ObjectNode winnerObjectNode = objectMapper.createObjectNode();

		try {
			if (raceSearch.getClass().getSimpleName().equals("String")) {
				//クライアントサイドの文字列を小文字に変換することで一致させやすくした
				raceSearch = raceSearch.toLowerCase();
				int year = Year.now().getValue();
				int past = year - 1949;

				for (int i = 0; i < past; i++) {
					String url = "https://ergast.com/api/f1/" + year + ".json";
					String raceUrl = restTemplate.getForObject(url, String.class);
					JsonNode jsonNode = objectMapper.readTree(raceUrl);
					JsonNode raceData = jsonNode.get("MRData")
							.get("RaceTable")
							.get("Races");

					Map<Integer, JsonNode> roundMap = new HashMap<Integer, JsonNode>();

					//複数のレース情報からパラメーターに部分一致したレース情報を配列に追加
					for (JsonNode race : raceData) {
						String raceName = race.get("raceName").asText().toLowerCase();
						raceName = raceName.replaceAll("\\s", "");
						if (raceSearch.contains(raceName) || raceName.contains(raceSearch)) {
							arrayNode.add(race);
							int raceRound = race.get("round").asInt();
							roundMap.put(raceRound, race);
						}
					}
					objectNode.set("raceSearch", arrayNode);

					String winnerUrl = "https://ergast.com/api/f1/" + year + "/results/1.json";
					String getRest = restTemplate.getForObject(winnerUrl, String.class);
					JsonNode winNode = objectMapper.readTree(getRest);
					JsonNode winnerData = winNode.get("MRData")
							.get("RaceTable")
							.get("Races");

					for (JsonNode winner : winnerData) {
						int winnerRound = winner.get("round").asInt();
						if (roundMap.containsKey(winnerRound)) {
							raceWinnerArray.add(winner);
						}
					}
					winnerObjectNode.set("raceWinData", raceWinnerArray);
					// 現在の年から1950年まで遡って処理するために年を1年ずつ減少させる
					year--;
					System.out.println(year);
					if (i == 10) {
						System.out.println("処理終了");
						break;
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e + "エラーが発生しました");
		}
		Map<String, ObjectNode> resultRace = new HashMap<String, ObjectNode>();
		resultRace.put("objectNode", objectNode);
		resultRace.put("winnerObjectNode", winnerObjectNode);
		return resultRace;
	}

}
