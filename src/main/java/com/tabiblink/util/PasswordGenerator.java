package com.tabiblink.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String motDePasseEncode = encoder.encode("admin123");
        System.out.println(motDePasseEncode);
    }
}