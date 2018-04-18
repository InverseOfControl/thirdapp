package com.zendaimoney.thirdpp.channel.util.unspay;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class DeflaterUtil {
    /**
     * 压缩
     * @param input
     *        待压缩数据
     * @return 压缩结果
     * @throws IOException
     */
    public static byte[] compress(final byte[] input) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(input.length);
        Deflater compressor = new Deflater(1);
        try {
            compressor.setInput(input);
            compressor.finish();
            byte[] buf = new byte[1024];
            while (!compressor.finished()) {
                int count = compressor.deflate(buf);
                bos.write(buf, 0, count);
            }
        } finally {
            bos.close();
            compressor.end();
        }
        return bos.toByteArray();
    }

    /**
     * 解压
     * @param input
     *        待解压数据
     * @return 解压结果
     * @throws IOException
     */
    public static byte[] uncompress(final byte[] input) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(input.length);
        Inflater decompressor = new Inflater();
        try {
            decompressor.setInput(input);
            final byte[] buf = new byte[1024];
            while (!decompressor.finished()) {
                int count = decompressor.inflate(buf);
                if (count == 0) {
                    break;
                }
                bos.write(buf, 0, count);
            }
        } catch (DataFormatException ex) {
            System.out.println("解压数据出错--数据格式转换异常");
            ex.printStackTrace();
        } finally {
            bos.close();
            decompressor.end();
        }
        return bos.toByteArray();
    }
}
