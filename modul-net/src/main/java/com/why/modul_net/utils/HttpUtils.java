package com.why.modul_net.utils;

import com.alibaba.fastjson.JSONObject;
import com.why.modul_net.BuildConfig;

import java.net.URLEncoder;

public class HttpUtils {
    /**
     * Form表单转成JSON字符串
     *
     * @param body
     * @return
     */
    public static String formToJson(String body) {
        if (body != null) {
            String[] split = body.split("&");
            if (split.length > 0) {
                JSONObject jsonObj = new JSONObject();
                boolean foundAppID = false;
                boolean foundAppVer = false;
                for (String keyValue : split) {
                    String[] kv = keyValue.split("=");
                    if (kv.length == 2) {
                        if (kv[0].equals("appid")) {
                            foundAppID = true;
                            kv[1] = BuildConfig.APPLICATION_ID;
                        } else if (kv[0].equals("app_version")) {
                            foundAppVer = true;
                            kv[1] = BuildConfig.VERSION_NAME;
                        }
                    } else if (kv.length == 1) {
                        jsonObj.put(kv[0], "");
                    }
                }

                if (!foundAppID) {
                    jsonObj.put("appid",BuildConfig.APPLICATION_ID);
                }

                if (!foundAppVer) {
                    jsonObj.put("app_version", BuildConfig.VERSION_NAME);
                }

                return jsonObj.toJSONString();
            }
        }
        return null;
    }


    public static String toURLEncoded(String paramString) {
        if (paramString == null || paramString.equals("")) {
            return "";
        }
        try
        {
            String str = new String(paramString.getBytes(), "UTF-8");
            str = URLEncoder.encode(str, "UTF-8");
            return str;
        }
        catch (Exception localException)
        {
        }

        return "";
    }

}
