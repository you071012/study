package com.ukar.study.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Random;

/**
 * @author jia.you
 * @date 2020/06/04
 */
public class Md5Util {
    public static String getMD5Str(String str, String charsetName) {
        byte[] digest = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            digest  = md5.digest(str.getBytes(charsetName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //16??????16???
        String md5Str = new BigInteger(1, digest).toString(16);
        return md5Str;
    }

    public static void main(String[] args) {
        System.out.println(getMD5Str("aa", "utf-8"));
        String[] arr1 = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f","g",
                "h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H",
                "I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        String[] arr2 = {"~","!","@","#","$","%","^","&","*","(",")","_","+",
                "{","}","|",";","'",",",".","/","?","<",">","?","?","?","?","-","?","?","[","]","`"};
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        for(int i = 0 ; i < 128; i++){
            if(i > 0 && (i % 10 == 0 || i % 15 == 0)){
                int next = random.nextInt(arr2.length);
                buffer.append(arr2[next]);
            }else{
                int next = random.nextInt(arr1.length);
                buffer.append(arr1[next]);
            }
        }
        System.out.println(buffer.toString());
        //i4UgWMb6Zj#EXk9-zDyH'2tVMPMAPg^ThDZSIYEs-u4F6?oDGJ-Mv62uQ7cl&Qcx9LBAzv?sLeu}keAf;xY5ON5ThO}agVaNcqZA?LbpZ.tkSg<HsFj3imK5%apJwgwq
        //XAxMnPL2Cr(z5tl%iUDI,125iYhsEj?0EGd9uhjC$QRRO>qzGR?MjdpeqfSg[dvHY0ofvZ*JlhZ`qJAu'W2T2XBvkn-S7BcvySKb_bk7Y,DZXa?T62e2GJWN.MVxkQDN
    }
}
