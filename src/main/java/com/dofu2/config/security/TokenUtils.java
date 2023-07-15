package com.dofu2.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TokenUtils {
    private static String CLIENT_ID = "315687330122-oql11qeaddlv99jbt6rs9rl61331t7mo.apps.googleusercontent.com";
    static GsonFactory jsonFactory = new GsonFactory();
    static NetHttpTransport transport = new NetHttpTransport();
    static GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
            .setAudience(Collections.singletonList(CLIENT_ID))
            .build();
    // (Receive idTokenString by HTTPS POST)
    public static Boolean validateToken(String token) throws GeneralSecurityException, IOException {
        GoogleIdToken idToken = verifier.verify(token);
        if (idToken != null) {
            return true;
        } else {
            return false;
        }
    }

    public static BigInteger getUserIdFromToken(String token) throws GeneralSecurityException, IOException {
        GoogleIdToken idToken = verifier.verify(token);
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();
            // Get profile information from payload
            BigInteger id = new BigInteger(((String) payload.get("sub")));


            return id;
        } else {
            return null;
        }
    }

    public static String getUserEmailFromToken(String token) throws GeneralSecurityException, IOException {
        GoogleIdToken idToken = verifier.verify(token);
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = (String) payload.get("email");
            return email;
        } else {
            return null;
        }
    }

    public static String getClaimFromToken(String token) throws GeneralSecurityException, IOException {
        GoogleIdToken idToken = verifier.verify(token);
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();
            // Print user identifier
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId);
            // Get profile information from payload
            String email = payload.getEmail();
            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");
            BigInteger id = new BigInteger(((String) payload.get("sub")));

            Map<String, Object> object = new HashMap<>();
            object.put("email", email);
            object.put("emailVerified", emailVerified);
            object.put("name", name);
            object.put("pictureUrl", pictureUrl);
            object.put("locale", locale);
            object.put("familyName", familyName);
            object.put("givenName", givenName);
            object.put("id_token", token);
            object.put("id", id);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(object);
        } else {
            return "";
        }
    }



}
