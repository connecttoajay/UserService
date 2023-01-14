package com.codebuffer.userservice.VO;

import com.codebuffer.userservice.Entity.UserData;

import lombok.Data;

@Data
public class ResponseEntityVO {
    private Department department;
    private UserData userData;
}
