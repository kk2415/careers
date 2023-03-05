package com.levelup.notification.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Value("${project.properties.firebase-create-scoped}")
    String fireBaseCreateScoped;

    @Bean
    public FirebaseMessaging firebaseMessaging() throws IOException {
        InputStream serviceAccount = new ClassPathResource("firebase/level-up-516d8-firebase-adminsdk-2ffux-dc6b1f5357.json").getInputStream();

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(
                        GoogleCredentials
                                .fromStream(serviceAccount)
                                .createScoped(fireBaseCreateScoped)
                )
                .setDatabaseUrl("https://fcm.googleapis.com/v1/projects/level-up-516d8/message:send")
                .setProjectId("level-up-516d8")
                .build();

        FirebaseApp app = FirebaseApp.initializeApp(options);
        return FirebaseMessaging.getInstance(app);
    }
}
