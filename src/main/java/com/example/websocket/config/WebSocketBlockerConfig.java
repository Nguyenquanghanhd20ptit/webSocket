package com.example.websocket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketBlockerConfig  implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private HttpHandshakeInterceptor handshakeInterceptor;
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //phân phối tin nhắn tới các kết nối WebSocket khac co tien to la /topic va /user
        registry.enableSimpleBroker("/topic","/user");
        // tien to voi cac dich den la tren server
        registry.setApplicationDestinationPrefixes("/app");
    }

    // để đăng ký các endpoint Stomp (Giao thức luồng văn bản theo hướng tin nhắn)
    @Override
    public void  registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*")
                .addInterceptors(handshakeInterceptor)
                .withSockJS()
                .setStreamBytesLimit(512 * 1024)
                .setHttpMessageCacheSize(1000)
                .setDisconnectDelay(30 * 1000);
    }
}
