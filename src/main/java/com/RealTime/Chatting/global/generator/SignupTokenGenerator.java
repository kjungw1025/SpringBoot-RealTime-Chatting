package com.RealTime.Chatting.global.generator;


import java.util.UUID;

public class SignupTokenGenerator {

    public static String generateUUIDCode() {
        return UUID.randomUUID().toString();
    }
}
