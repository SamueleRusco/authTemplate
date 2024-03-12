package com.spring.authentication.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo-controller")
class DemoController {

    @GetMapping
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("hello from secured endpoint");
    }

    @GetMapping
    @PreAuthorize("ADMIN")
    @RequestMapping("/helloadmin")
    public ResponseEntity<String> helloAdmin() {
        return ResponseEntity.ok("hello admin");
    }

    @GetMapping
    @Secured({"USER"})
    @RequestMapping("/hellouser")
    public ResponseEntity<String>  helloUser() {
        return ResponseEntity.ok("hello user");
    }
}


