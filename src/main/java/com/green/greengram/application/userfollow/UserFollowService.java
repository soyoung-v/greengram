package com.green.greengram.application.userfollow;

import com.green.greengram.application.userfollow.model.UserFollowReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserFollowService {
    private final UserFollowMapper userFollowMapper;

    public int postUserFollow(UserFollowReq req){
    return userFollowMapper.saveFollow(req);
    }

    public void deleteFollow(long fromUserId, long toUserId){
        userFollowMapper.delFollow(fromUserId,toUserId);
    }
}
