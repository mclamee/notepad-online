package com.wicky;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class IOUtil {
	
    public static String readFileNio(File file) throws Exception{
        String encoding = System.getProperty("file.encoding");  
        FileInputStream fileInputStream = new FileInputStream(file);  
        FileChannel channel = fileInputStream.getChannel();  
        System.out.println(channel.size());  

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024 * 1024);  
        StringBuilder sb = new StringBuilder();
        while (channel.read(byteBuffer) > 0) {  
            byteBuffer.rewind();
            CharBuffer charBuffer = Charset.forName(encoding).decode(byteBuffer);  
            sb.append(charBuffer.array());
            byteBuffer.flip();  
        }
        fileInputStream.close();
        return sb.toString();
    }  
}
