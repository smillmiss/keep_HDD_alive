package com.schedule.keep_alive.listener;

import com.alibaba.fastjson.JSONObject;
import com.schedule.keep_alive.schedule.KeepHDDAlive;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

@Component
public class InitListener implements ApplicationRunner, ApplicationListener<ContextClosedEvent> {

    @Override
    public void run(ApplicationArguments args) {
        System.out.println("system start");
        File file = new File("");
        try {
            String filePath = file.getCanonicalPath() + "/keepAliveConfig.json";
            System.out.println("reading config file:" + filePath);
            File configFile = new File(filePath);
            if (!configFile.exists()) {
                configFile.createNewFile();
                FileUtils.writeStringToFile(configFile, "{\n" +
                        "    \"loopMinute\": 5,\n" +
                        "    \"loopDir\": [\n" +
                        "        \"D:\\\\\"\n" +
                        "    ]\n" +
                        "}", Charset.forName("utf-8"));
                System.out.println("creating file " + filePath + " please config the file and restart this program !");
                KeepHDDAlive.configJson = null;
                return;
            }
            KeepHDDAlive.configJson = JSONObject.parseObject(FileUtils.readFileToString(configFile, Charset.forName("utf-8")));
            KeepHDDAlive.loopIndex = KeepHDDAlive.configJson.getInteger("loopMinute");
            FileUtils.readFileToString(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {

        System.out.println("system stop");
    }

}
