package com.banka.util;

import java.util.Random;

public class PasswordGenerator {
	public static String getRandomPassword(int length)
    {
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();
        char[] chars = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'r', 's', 't', 'u', 'v', 'y', 'z', 'x', 'q',
                                    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                                    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'R', 'S', 'T', 'U', 'V', 'Y', 'Z', 'X', 'Q'};
        
        for ( int i = 0; i < length; i++ ) {
            buffer.append(chars[random.nextInt(chars.length)]);
        }
        return buffer.toString();
    }
}
