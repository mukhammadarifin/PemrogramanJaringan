/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package streamVocal;

import java.io.*;

/**
 *
 * @author MuFin
 */
public class bacaKarakter {
    public static void main(String[] args){
        char c;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Masukkan Karakter (akhiri dengan \"q\") : ");
            do {
                c = (char) br.read();
                System.out.println("Karakter terbaca : " + c);
            } while (c!='q');
        } 
        catch(IOException e){
            System.out.println("Ada error IO");
            System.exit(0);
        }  
    }
}
