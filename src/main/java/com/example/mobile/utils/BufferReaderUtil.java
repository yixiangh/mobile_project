package com.example.mobile.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 使用缓冲流读取输入流
 */
public class BufferReaderUtil {

    /**
     * 将传入的数据输入流转为字符流读出
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static String BufferReader(InputStream inputStream) throws IOException {
        InputStreamReader isr = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(isr);
        String line = null;
        StringBuffer stringBuffer = new StringBuffer();
        while ((line = br.readLine()) != null)
        {
            stringBuffer.append(line);
        }
        isr.close();
        br.close();
        return stringBuffer.toString();
    }
}
