/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inputStream;

import java.io.*;

/**
 *
 * @author MuFin
 */
public class fileInputStreamDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Syntax - FileInputStreamb file");
            return;
        }
        try {
            // Membuat input stream yang membaca dr file
            InputStream fileInput = new FileInputStream (args[0]);
            int data = fileInput.read(); // Baca byte ke 1
        
            while (data != -1) // ulangi : hingga end of file (EOF) dicapai
            {
                System.out.write ( data ); // menampilkan byte data ke console
                data = fileInput.read(); // baca byte berikutnya
            }
            fileInput.close(); // Close the file
        }
        catch (IOException ioe) {
            System.err.println ("I/O error - " + ioe);
        }
    }
}
