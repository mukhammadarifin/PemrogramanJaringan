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
public class bacaString {
    public static void main(String[] args){
        String str;
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Masukkan string (akhiri dengan \"end\") : ");
            do {
                str = br.readLine();
                System.out.println("Kata terbaca : " + str);
            } while (str.equalsIgnoreCase("end")==false);
        }
        catch(IOException e){
            System.out.println("Ada error IO");
            System.exit(0);
        }
    }
}
