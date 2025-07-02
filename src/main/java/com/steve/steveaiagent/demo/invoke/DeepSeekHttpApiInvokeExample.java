package com.steve.steveaiagent.demo.invoke;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

public class DeepSeekHttpApiInvokeExample {
    public static void main(String[] args) {
        String apiUrl = "https://api.deepseek.com/chat/completions";
        String apiKey = "xxxxx"; // 替换为你的实际API Key

        // 构建请求体
        JSONObject message1 = new JSONObject();
        message1.set("role", "system");
        message1.set("content", "我是一个正在学习AI编程的程序员，我需要你的帮助");

        JSONObject message2 = new JSONObject();
        message2.set("role", "user");
        message2.set("content", "Hello!");

        JSONArray messages = new JSONArray();
        messages.add(message1);
        messages.add(message2);

        JSONObject requestBody = new JSONObject();
        requestBody.set("model", "deepseek-chat");
        requestBody.set("messages", messages);
        requestBody.set("stream", false);

        // 发送POST请求
        String response = HttpRequest.post(apiUrl)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .body(requestBody.toString())
                .execute()
                .body();

        System.out.println(response);
    }
}
