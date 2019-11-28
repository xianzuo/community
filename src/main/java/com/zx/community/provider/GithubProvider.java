package com.zx.community.provider;

import com.alibaba.fastjson.JSON;
import com.zx.community.dto.AccesstokenDTO;
import com.zx.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToken(AccesstokenDTO accesstokenDTO) {
        MediaType json = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();


        RequestBody body = RequestBody.create(json, JSON.toJSONString(accesstokenDTO));
        Request request = new Request.Builder().url("https://github.com/login/oauth/access_token").post(body).build();
        try (Response response = client.newCall(request).execute()) {
            String str=response.body().string();
            //System.out.println(str);
            return str.split("&")[0].split("=")[1];
        } catch (IOException e) {

        }
        return null;

    }

    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://api.github.com/user?access_token=" + accessToken).build();

        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
           // System.out.println(str);
            return JSON.parseObject(str, GithubUser.class);
        } catch (IOException e) {

        }
        return null;
    }
}
