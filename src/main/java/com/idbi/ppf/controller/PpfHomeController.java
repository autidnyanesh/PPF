package com.idbi.ppf.controller;

import java.util.List;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ppf")
public class PpfHomeController {
    @GetMapping("/home")
    public Map<String, Object> home(Authentication authentication) {
        return Map.of(
                "module", "PPF",
                "title", "Public Provident Fund",
                "username", authentication.getName(),
                "operations", List.of("accounts", "contributions", "withdrawals"));
    }
}
