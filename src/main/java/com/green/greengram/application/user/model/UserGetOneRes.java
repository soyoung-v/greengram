package com.green.greengram.application.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserGetOneRes {
    private long id;
    private String uid;
    private String upw;
    private String nm;
    private String pic;
    private String createdAt;
}
