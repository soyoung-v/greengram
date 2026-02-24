package com.green.greengram.application.feedlike;

import com.green.greengram.application.feedlike.model.FeedLikeReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeedLikeService {
    private final FeedLikeMapper feedLikeMapper;

    public boolean toggleFeedLike(FeedLikeReq req){
        int result = feedLikeMapper.delLike(req);
        boolean toggle = result > 0 ? false : true;
        if(toggle){
            result = feedLikeMapper.insLike(req);
        }
        return toggle;


//        if(feedLikeMapper.delLike(req) > 0){
//            return false; // 좋아요 취소
//        }
//
//        feedLikeMapper.insLike(req);
//        return true; // 좋아요 생성

    }
}
