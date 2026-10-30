package com.idbi.ppf;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PpfAuthFlowTest {
    @Autowired
    private MockMvc mockMvc;

    @Value("${gbm.jwt.secret}")
    private String jwtSecret;

    @Test
    void ppfRejectsAnonymousAndAcceptsGbmJwt() throws Exception {
        mockMvc.perform(get("/api/ppf/home")).andExpect(status().isUnauthorized());
        mockMvc.perform(get("/api/ppf/accounts")).andExpect(status().isUnauthorized());

        String token = gbmToken("admin.ppf", "ADMIN");

        mockMvc.perform(get("/api/ppf/home").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.module").value("PPF"))
                .andExpect(jsonPath("$.username").value("admin.ppf"));

        mockMvc.perform(get("/api/ppf/accounts").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/ppf/accounts")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"accountNumber":"PPF24001852","customerName":"Ananya Rao","customerId":"CUST1001","openingBalance":5000.00,"openedOn":"2026-10-30"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountNumber").value("PPF24001852"));
    }

    private String gbmToken(String username, String role) {
        Instant now = Instant.now();
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .subject(username)
                .claim("uid", 1L)
                .claim("role", role)
                .claim("displayName", "GBM Admin")
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(1800)))
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }
}
