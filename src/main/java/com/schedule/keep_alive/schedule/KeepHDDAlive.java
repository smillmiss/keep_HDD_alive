package com.schedule.keep_alive.schedule;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Component
public class KeepHDDAlive {
    public static JSONObject configJson = null;
    public static int loopIndex = 0;

    @Scheduled(initialDelay = 5000, fixedDelay = 60000)
    public void UpdateICNumber() throws IOException {
        if (configJson != null) {
            int loopMinute = configJson.getInteger("loopMinute");
            if (loopMinute <= loopIndex) {
                loopIndex = 0;
                System.out.println(new Date()+"   start scan dir");
                JSONArray jsonArray = configJson.getJSONArray("loopDir");
                for (int i = 0; i < jsonArray.size(); i++) {
                    String targetDir = jsonArray.getString(i)+"keepAlive.txt";
                    File targetFile = new File(targetDir);
                    if (targetFile.exists()){
                        targetFile.delete();
                    }
                    targetFile.createNewFile();
                    FileUtils.writeStringToFile(targetFile,"keepAlive","utf-8");
                }
                System.out.println(new Date()+"   stop scan dir");
            }
            loopIndex++;
        }
    }
}
