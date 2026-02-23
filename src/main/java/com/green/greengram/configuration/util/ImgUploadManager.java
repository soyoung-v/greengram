package com.green.greengram.configuration.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

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
                e.printStackTrace();
            }
        }

        return picFileNames;
    }
}
