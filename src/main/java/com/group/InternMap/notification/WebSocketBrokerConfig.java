package com.group.InternMap.Notification;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketBrokerConfig implements WebSocketMessageBrokerConfigurer {

    //this is an internal broker that holds the messages or notifications and send it to users
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //we can then add any topic we want to notify the users with - /topic/notificationExample
        config.enableSimpleBroker("/topic", "/queue");
        // the "/app" prefix is for any message or notification sent, it will go to the / app controller
        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket/ws").setAllowedOriginPatterns("*");

        registry.addEndpoint("/websocket/ws").withSockJS();
    }
}
