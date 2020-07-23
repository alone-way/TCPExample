package com.cmd;


import com.model.Result;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

/**
 * @author IceCube
 * @date 2020/7/22 17:55
 */
public class RuntimeUtil {

    public RuntimeUtil() {
    }

    public Result exec(String command) {
        Result result = new Result();

        try {
            Process p = Runtime.getRuntime().exec(command);

            StringBuilder message = new StringBuilder();
            BufferedReader mgReader = new BufferedReader(new InputStreamReader(p.getInputStream(), "GBK"));
            String line;
            while ((line = mgReader.readLine()) != null) {
                message.append(line);
            }
            result.setMessage(message.toString());

            StringBuilder error = new StringBuilder();
            BufferedReader erReader = new BufferedReader(new InputStreamReader(p.getErrorStream(), "GBK"));
            while ((line = erReader.readLine()) != null) {
                error.append(line);
            }
            result.setError(error.toString());

            p.waitFor(5, TimeUnit.SECONDS);
            result.setExitValue(p.exitValue());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }
}
