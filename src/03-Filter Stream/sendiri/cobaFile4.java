/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sendiri;

import java.io.*;


/**
 *
 * @author MuFin
 */
public class cobaFile4 {
    public static void main(String[] args){
        if(args.length != 1){
            System.out.println("Usage : java CobaFile4 <filetoread>");
            System.exit(1);
        }
        try {
            FileReader f = new FileReader(args[0]);
            BufferedReader r = new BufferedReader(f);
            String s = null;
            while((s=r.readLine())!=null){
                System.out.println(s);
            }
            r.close();
            f.close();
        } catch(FileNotFoundException e){
            System.out.println("File not found!");
            System.exit(1);
        } catch(IOException e){
            System.out.println("IO Error!");
            System.exit(1);
        }
    }
}
