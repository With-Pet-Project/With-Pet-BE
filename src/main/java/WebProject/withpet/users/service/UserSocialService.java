package WebProject.withpet.users.service;

import WebProject.withpet.users.dto.SocialUserInfoDto;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class UserSocialService {

    // TODO: 예외처리 하기

    public String getAccessToken(String code) throws JSONException {

        HttpHeaders httpHeaders = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();

        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");



        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "b52140c9061cc85f0ff31b4d548e998d");
        body.add("redirect_uri", "http://localhost:3000/login/oauth/callback");
        body.add("code", code);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(
            "https://kauth.kakao.com/oauth/token",
            HttpMethod.POST,
            entity,
            String.class
        );

        JSONObject jsonBody = new JSONObject(response.getBody());
        String access_token = jsonBody.getString("access_token");

        return access_token;
    }

    public SocialUserInfoDto getUserInfoByToken(String token) throws JSONException {

        HttpHeaders httpHeaders = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();

        httpHeaders.add("Authorization", "Bearer " + token);
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(
            "https://kapi.kakao.com/v2/user/me",
            HttpMethod.POST,
            request,
            String.class
        );

        JSONObject body = new JSONObject(response.getBody());

        return SocialUserInfoDto.builder()
            .email(body.getJSONObject("kakao_account").getString("email"))
            .nickname(body.getJSONObject("properties").getString("nickname"))
            .build();

    }

}
