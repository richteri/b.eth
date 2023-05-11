package com.beth.rest;

import com.beth.domain.ChangeNamePayload;
import com.beth.domain.User;
import com.beth.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.val;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    List<User> findTop100() {
        return service.findTop100();
    }

    @GetMapping("{address}")
    User findByAddress(@PathVariable("address") String address) {
        return service.findByAddress(address);
    }

    @GetMapping("count")
    Map<String, Long> count() {
        return Collections.singletonMap("count", service.count());
    }

    @PatchMapping("{address}")
    void changeNameByAddress(
            @PathVariable("address") String address, @RequestBody ChangeNamePayload payload) {
        val user = service.findByAddress(address);
        user.setName(payload.getName());

        service.update(user);
    }
}
