/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnq.utils;

import java.io.Serializable;
import java.security.SecureRandom;

/**
 *
 * @author quocl
 */
public class RandomID implements Serializable {
    private final static String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static SecureRandom random = new SecureRandom();
    
    public static String randomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));       
        }
        return sb.toString();
    }
}
