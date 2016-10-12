package com.rayuniverse.framework.comm;

import java.io.UnsupportedEncodingException; 
  
/** 
 * MD5的算法在RFC1321 中定�?在RFC 1321中，给出了Test suite用来�?��你的实现是否正确�?MD5 ("") = 
 * d41d8cd98f00b204e9800998ecf8427e MD5 ("a") = 0cc175b9c0f1b6a831c399e269772661 
 * MD5 ("abc") = 900150983cd24fb0d6963f7d28e17f72 MD5 ("message digest") = 
 * f96b697d7cb7938d525a2f31aaf161d0 MD5 ("abcdefghijklmnopqrstuvwxyz") = 
 * c3fcd3d76192e4007dfb496cca67e13b 
 *  
 * @author haogj 
 *  
 *         传入参数：一个字节数�?传出参数：字节数组的 MD5 结果字符�?
 */
public class MD5 { 
    public static String getMD5(byte[] source) { 
        String s = null; 
        char hexDigits[] = { // 用来将字节转换成 16 进制表示的字�?
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 
                'e', 'f' }; 
        try { 
            java.security.MessageDigest md = java.security.MessageDigest 
                    .getInstance("MD5"); 
            md.update(source); 
            byte tmp[] = md.digest(); // MD5 的计算结果是�?�� 128 位的长整数， 
            // 用字节表示就�?16 个字�?
            char str[] = new char[16 * 2]; // 每个字节�?16 进制表示的话，使用两个字符， 
            // �?��表示�?16 进制�?�� 32 个字�?
            int k = 0; // 表示转换结果中对应的字符位置 
            for (int i = 0; i < 16; i++) { // 从第�?��字节�?��，对 MD5 的每�?��字节 
                // 转换�?16 进制字符的转�?
                byte byte0 = tmp[i]; // 取第 i 个字�?
                str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中�?4 位的数字转换, 
                // >>> 为�?辑右移，将符号位�?��右移 
                str[k++] = hexDigits[byte0 & 0xf]; // 取字节中�?4 位的数字转换 
            } 
            s = new String(str); // 换后的结果转换为字符�?
  
        } catch (Throwable e) { 
             throw new RuntimeException(e);
        } 
        return s; 
    } 
  
    public static void main(String[] argv) {  
        // 计算 "a" �?MD5 
        // 代码，应该为�?cc175b9c0f1b6a831c399e269772661 
        try { 
            System.out.println(MD5.getMD5("a".getBytes("GBK"))); 
        String xml="12345xml";//12345为机构验证码，xml为要发�?的报�?
            System.out.println(MD5.getMD5(xml.getBytes("GBK"))); 
        } catch (UnsupportedEncodingException e) { 
            // TODO Auto-generated catch block 
            e.printStackTrace(); 
        } 
  
  
    } 
}