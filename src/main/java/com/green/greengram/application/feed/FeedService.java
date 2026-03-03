package com.green.greengram.application.feed;

import com.green.greengram.application.feed.model.*;
import com.green.greengram.application.feedcomment.FeedCommentService;
import com.green.greengram.application.feedcomment.model.FeedCommentGetReq;
import com.green.greengram.application.feedcomment.model.FeedCommentGetRes;
import com.green.greengram.configuration.util.ImgUploadManager;
import com.green.greengram.configuration.util.MyFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeedService {
    private final FeedMapper feedMapper;
    private final ImgUploadManager imgUploadManager;
    private final MyFileUtil myFileUtil;

    @Transactional
    public FeedPostRes postFeed(FeedPostReq req, List<MultipartFile> pics){
        int saveAffectedRows = feedMapper.save(req);

        long feedId = req.getFeedId();
        log.info("feedId: {}", feedId);

        List<String> picSavedNames = imgUploadManager.saveFeedPics(feedId, pics);
        try {
            feedMapper.savePics(feedId, picSavedNames);
        } catch (Exception e) {
            //사진을 지운다.
            String directoryPath = String.format("%s/feed/%d", myFileUtil.fileUploadPath, feedId);
            log.info("directoryPath: {}", directoryPath);
            myFileUtil.deleteDirectory(directoryPath);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "SQL Syntax Error 발생");
        }

        return new FeedPostRes(feedId,picSavedNames);
    }

    public List<FeedGetRes> getFeedList(FeedGetReq req){
        List<FeedGetRes> list = feedMapper.findAll(req);
        //ㅍㅣ드 당 사진 정보를 가져오는 작업을 해야함
        for(FeedGetRes res : list){
            //사진 가져오는 select
            List<String> pics = feedMapper.findPicsById(res.getId());
            res.setPics(pics);
        }
        return list;
    }

    @Transactional
    public int deleteFeed(FeedDeleteReq req) {

        //feed_pic, feed_like, feed_comment에 feedId가 사용된 모든 row를 삭제
        feedMapper.deleteRef(req);

        //feed 테이블의 row는 가장 마지막에 삭제처리
        feedMapper.delete(req);

        // 폴더 째 삭제
        String delDirectoryPath = String.format("%s/feed/%d", myFileUtil.fileUploadPath, req.getFeedId());
        myFileUtil.deleteDirectory(delDirectoryPath);
        return 1;
    }
}
