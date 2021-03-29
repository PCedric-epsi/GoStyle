package com.epsi.gostyle.rest;

import com.epsi.gostyle.bean.CodeBean;
import com.google.gson.Gson;

import java.util.List;

public class CodeUtils {

    //private static String BASE_URL = "http://10.0.2.2:8887/api/codes"; //For emulator
    private static String BASE_URL = "http://localhost:8000/api/codes";
    public static List<CodeBean> getCodes() throws Exception {

        String response = OkhttpUtils.findCodes(BASE_URL);
        return new Gson().fromJson(response, List.class);
    }

    public static CodeBean getCode(String code) throws Exception {

        String response = OkhttpUtils.findCodeByName(BASE_URL + "/", code);
        return new Gson().fromJson(response, CodeBean.class);
    }
}
