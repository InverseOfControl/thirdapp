package com.zendaimoney.thirdpp.channel.util.unspay;

import java.io.IOException;

import org.apache.commons.codec.binary.Base64;

public class Base64Util {
    

    /**
     * BASE64解码
     * @param input
     *        待解码数据
     * @return 解码后的数据
     * @throws IOException
     */
    public static byte[] base64Decode(String input) throws IOException {
        return Base64.decodeBase64(input);
    }

    /**
     * BASE64编码
     * @param inputByte
     *        待编码数据
     * @return 解码后的数据
     * @throws IOException
     */
    public static byte[] base64Encode(byte[] inputByte) throws IOException {
        return Base64.encodeBase64(inputByte);
    }
}
