/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quiZ;

import java.io.*;  


/**
 *
 * @author MuFin
 */
public class pushBackInputStream {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        String srcFile = "C:\\Users\\MuFin\\Documents\\NetBeansProjects\\pemProm\\src\\quiZ\\test.txt";
        
        try ( PushbackInputStream pis = new PushbackInputStream(new FileInputStream(srcFile))) {
        byte byteData;
        int CountKata = 0;
        int CountBaris = 1;
        int jumlahKarakter = pis.available();
        
        StringBuilder sb = new StringBuilder();
        while ((byteData = (byte) pis.read()) != -1) {
        if ((char) byteData == ' ' || (char) byteData == '\n') {
        CountKata++;
        }
        if ((char) byteData == '\n') {
        CountBaris++;
        }
        sb.append((char)byteData);
        }
        pis.close();
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
