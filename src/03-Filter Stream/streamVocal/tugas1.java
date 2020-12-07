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
public class tugas1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String Filenya = "src/BASDAT NYIng.txt";
        String YangDiganti = "Belajar membaca dan menulis file di Java!";
        
        try {
            FileWriter fileWriter = new FileWriter(Filenya);
            fileWriter.write(YangDiganti);
            fileWriter.close();
            
            System.out.println("File sudah selesai ditulis ulang!");
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan karena: " + e.getMessage());
        }
    }
    
}
