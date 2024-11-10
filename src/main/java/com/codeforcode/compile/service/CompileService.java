package com.codeforcode.compile.service;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class CompileService {

    private final Gson gson = new Gson();

    public String sendCodeToCompileServer(String language, String code, List<Object> inputParams, String expectedOutput) throws IOException, InterruptedException {
        String response = sendHttpRequestToCompileServer(language, code, inputParams);

        if (response.trim().equals(expectedOutput.trim())) {
            log.info("정답 결과: {}", response);
            return "정답입니다.";
        } else {
            log.warn("출력 예제: {}, 결과: {}", expectedOutput, response);
            return "오답입니다. 출력예제: " + expectedOutput + ", 결과: " + response;
        }
    }

    private String sendHttpRequestToCompileServer(String language, String code, List<Object> inputParams) throws IOException {
        String COMPILE_SERVER_URL = "http://localhost:8081/compile";
        URL url = new URL(COMPILE_SERVER_URL);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);

        connection.setConnectTimeout(5000);
        connection.setReadTimeout(10000);

        String jsonInputString = createJsonPayload(language, code, inputParams);

        log.debug("JSON 페이로드 생성 완료 - payload 길이: {}", jsonInputString.length());

        try (var os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
            log.debug("컴파일 서버로 데이터 전송 완료");
        }

        try (var br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;

            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            return response.toString();
        }
    }

    private String createJsonPayload(String language, String code, List<Object> inputParams) {
        var payloadMap = new HashMap<>();

        payloadMap.put("language", language);
        payloadMap.put("code", code);
        payloadMap.put("inputParams", inputParams.stream().map(Object::toString).toArray(String[]::new));

        return gson.toJson(payloadMap);
    }
}