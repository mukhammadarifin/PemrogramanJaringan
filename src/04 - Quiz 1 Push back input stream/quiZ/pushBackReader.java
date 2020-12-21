/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quiZ;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.io.PushbackReader;
import java.io.Reader;
import java.io.StringReader;

/**
 *
 * @author MuFin
 */
public class pushBackReader {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        Reader reader = new FileReader("C:\\Users\\MuFin\\Documents\\NetBeansProjects\\pemProm\\src\\quiZ\\test.txt");

        try(PushbackReader pushbackReader = new PushbackReader(reader)){
            byte byteData;
            int CountKata = 0;
            int CountBaris = 1;
            int jumlahKarakter = pushbackReader.read();
            
            StringBuilder sb = new StringBuilder();
            while ((byteData = (byte) pushbackReader.read()) != -1) {
            if ((char) byteData == ' ' || (char) byteData == '\n') {
            CountKata++;
            }
            if ((char) byteData == '\n') {
            CountBaris++;
            }
            sb.append((char)byteData);
            }
            pushbackReader.close();
            System.out.println("Hasil baca file:");
            System.out.println(sb.toString());
            System.out.println("Jumlah kata: " + (CountKata+1));
        System.out.println("Jumlah karakter: " + (jumlahKarakter));
        System.out.println("Jumlah baris: " + (CountBaris));
        } catch (Exception e2) {
        e2.printStackTrace();
        }
    }
}
