package com.green.greengram.application.feedcomment;

import com.green.greengram.application.feedcomment.model.FeedCommentPostReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeedCommentService {
    private final FeedCommentMapper feedCommentMapper;

    public long postFeedComment(FeedCommentPostReq req){
        return feedCommentMapper.save(req);
    }
}
