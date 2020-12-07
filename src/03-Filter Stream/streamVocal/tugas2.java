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
public class tugas2 {
        
    public static void main(String[] args) throws IOException {
        File FileYangDibaca = new File("src/BASDAT NYIng.txt"); 
        String[] words = null; 
        FileReader Baca = new FileReader(FileYangDibaca); 
        BufferedReader DoBaca = new BufferedReader(Baca); 
        String s;
        int urutVokal[] = { 0, 0, 0, 0, 0 };
        while ((s = DoBaca.readLine()) != null) {
            words = s.split(" "); 
            for (int i = 0; i < words.length; i++) {
                for (int j = 0; j < words[i].length(); j++) {
                    char karakter = words[i].charAt(j);
                    if (karakter == 'a')
                        urutVokal[0]++;
                    if (karakter == 'i')
                        urutVokal[1]++;
                    if (karakter == 'u')
                        urutVokal[2]++;
                    if (karakter == 'e')
                        urutVokal[3]++;
                    if (karakter == 'o')
                        urutVokal[4]++;
                }
            }
        }
        DoBaca.close();
 
        String vokal[] = { "a", "i", "u", "e", "o" };
        for (int i = 0; i < vokal.length; i++) {
            System.out.println("Huruf" + vokal[i] + ", berjumlah = " + urutVokal[i]);
        }
    }
}
