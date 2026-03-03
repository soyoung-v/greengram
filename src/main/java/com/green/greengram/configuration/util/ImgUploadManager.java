package com.green.greengram.configuration.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImgUploadManager {
    private final MyFileUtil myFileUtil;

    public List<String> saveFeedPics(long feedId, List<MultipartFile>pics){
        String feedPath = String.format("feed/%d", feedId);
        myFileUtil.makeFolders(feedPath);

        List<String> picFileNames = new ArrayList(pics.size());
        for(MultipartFile mf : pics){
            String randomFileName = myFileUtil.makeRandomFileName(mf);
            picFileNames.add(randomFileName);

            try {
                myFileUtil.transferTo(mf, feedPath + "/" + randomFileName);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "E01 - 서버에서 에러가 발생하였습니다.");
            }
        }

        return picFileNames;
    }
}
