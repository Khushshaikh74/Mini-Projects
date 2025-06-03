package Lesson_3_PasswordGeneratorApp;

//backend

import java.util.Random;

public class PasswordGenerator {

    private final String uppercase = "QWERTYUIOPASDFGHJKLZXCVBNM";
    private final String lowercase = "qwertyuiopasdfghjklzxcvbnm";
    private final String numbers = "0123456789";
    private final String symbols = "!@#$%&*";

    private final Random random;

    public PasswordGenerator(){
        random = new Random();
    }

    public String generatePassword(int length, boolean upper, boolean lower, boolean num, boolean sym){

        StringBuilder passwordBuild = new StringBuilder();
        String validChar = "";

        if(upper) validChar += uppercase;
        if(lower) validChar += lowercase;
        if(num) validChar += numbers;
        if(sym) validChar += symbols;

        for (int i = 0; i < length; i++) {
            int validCharIdx = random.nextInt(validChar.length());
            char charAtIdx = validChar.charAt(validCharIdx);
            passwordBuild.append(charAtIdx);
        }

        return passwordBuild.toString();
    }

}
