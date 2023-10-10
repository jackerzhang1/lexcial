/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

/**
 *
 * @author heten
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Tokenizer {
    private static List<String> reservedWords;
    private static List<String> operator;
      private static Set<String> punctuators ;
    private static Set<Character> variable;
  

    static {
        reservedWords = Arrays.asList("while", "boolean", "if", "else", "for", "int", "float", "char", "return", "void","double","dbl");
        operator= Arrays.asList("=", "= =", "<", ">", "<=", ">=", "+", "-", "*", "/");
        variable= new HashSet<>();
        punctuators= new HashSet<>(Arrays.asList(";", ",", "{", "}", "(", ")",".",">","<"));
        
        for (char alphabet = 'a'; alphabet <= 'z'; alphabet++) {
            variable.add(alphabet);
        }
        for (char alphabet = 'A';alphabet <= 'Z'; alphabet++) {
            variable.add(alphabet);
        }
        for (char number = '0'; number <= '9'; number++) {
            variable.add(number);
        }
    }

    public static void main(String[] args) throws IOException {
        File inputFile = new File("input.txt");
        String statement;
        try {
            BufferedReader input = new BufferedReader(new FileReader(inputFile));
            

            while ((statement = input.readLine()) != null) {
                if (statement.trim().startsWith("/***") || statement.trim().startsWith("/*") ||statement.trim().startsWith(" ")
                        || statement.trim().endsWith("*/")) {
                    continue;
                }
                System.out.println("\n Statement: " + statement);
                Recursive(statement);
            }

            input.close();
       
        } catch (FileNotFoundException e) {
             System.out.println("professor, you may use wrong input file: " + inputFile.getName());
        }
    }

    private static void Recursive(String line) {
       
        String[] tokens = line.split("(?<=\\W)|(?=\\W)");

        for (String token : tokens) {
            token = token.trim();
            if (token.isEmpty()) {
                continue;
            }

            if (ReservedWord(token)) {
                System.out.println("Next Token is: reserved words, Next Lexeme is: " + token);
            } 
            else if (Operator(token)) {
                System.out.println("Next Token is: operators ASSIGN_OP, Next Lexeme is: " + token);
            } 
            else if (PUNCTUATOR(token)) {
                System.out.println("Next Token is punctuator, Next Lexeme is: " + token);
            } else if (word(token)) {
                System.out.println("Next Token is: variable, Next Lexeme is: " + token);
            } else if (constant(token)) {
                System.out.println("Token: constants number, Lexeme: " + token);
            } else {
                System.out.println("Next Token is:unknown, Next Lexeme is: " + token);
            }
        }
    }



    private static boolean word(String token) {
        if (token.isEmpty()) {
            return false;
        }

        char alphabet = token.charAt(0);
        if (!Character.isLetter(alphabet) && alphabet != '_') {
            return false;
        }

        for (int i = 1; i < token.length(); i++) {
            char alphabets = token.charAt(i);
            if (!variable.contains(alphabets)) {
                return false;
            }
        }

        return true;
    }

    private static boolean constant(String token) {
        try {
            Integer.parseInt(token);
            return true;
        } catch (java.lang.IllegalArgumentException e) {
            return false;
        }
    }

 private static boolean ReservedWord(String token) {
        return reservedWords.contains(token);
    }

    private static boolean Operator(String token) {
        return operator.contains(token);
    }

    private static boolean PUNCTUATOR(String token) {
        return punctuators.contains(token);
    }
}