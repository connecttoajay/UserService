package com.codebuffer.userservice.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.codebuffer.userservice.Entity.UserData;
import com.codebuffer.userservice.VO.Department;
import com.codebuffer.userservice.VO.ResponseEntityVO;
import com.codebuffer.userservice.service.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    public static final String CB_1 = "CB_1";

    @PostMapping("/")
    public UserData saveUser(@RequestBody UserData user) {
        log.info("Invoked from saveuser method from UserController");
        return userService.save(user);
    }

    int count = 0;

    @GetMapping("/{id}")
    // @CircuitBreaker(name = CB_1, fallbackMethod = "fallBackMethodDeptService")
    // @Retry(name = CB_1, fallbackMethod = "fallBackMethodDeptService")
    @RateLimiter(name = CB_1)
    public ResponseEntityVO getUsersWithDept(@PathVariable("id") Long id) {
        ResponseEntityVO vo = new ResponseEntityVO();

        System.out.println("Method retry:" + count++ + " Time: " + new Date());
        UserData userData = userService.findByUserId(id);

        vo.setUserData(userData);
        Department dep = restTemplate.getForObject(
                "http://DEPARTMENT-SERVICE/departments/" + userData.getDepartmentId(),
                Department.class);

        vo.setDepartment(dep);
        return vo;
    }

    public ResponseEntityVO fallBackMethodDeptService(Exception excp) {
        return new ResponseEntityVO();
    }

}
