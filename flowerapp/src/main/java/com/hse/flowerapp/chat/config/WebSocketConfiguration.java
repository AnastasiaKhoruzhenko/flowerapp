package com.hse.flowerapp.chat.config;

import com.hse.flowerapp.chat.handler.ChatWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    private static final String CHAT_ENDPOINT = "/chat";

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(getChatWebSocketHandler(), CHAT_ENDPOINT)
        .setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler getChatWebSocketHandler() {
        return new ChatWebSocketHandler();
    }
}