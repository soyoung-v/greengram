package com.green.greengram.application.userfollow;

import com.green.greengram.application.userfollow.model.UserFollowReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserFollowMapper {
    int saveFollow(UserFollowReq req);
    int delFollow(long fromUserId, long toUserId);
}
