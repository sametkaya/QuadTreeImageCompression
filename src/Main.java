/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author k-sam
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        //java Main -ctext -i test.ppm -o out
        if (args[0].compareTo("-c") == 0) {
            if (args[1].compareTo("-i") == 0) {
                String imagename = args[2];
                if (args[3].compareTo("-o") == 0) {
                    String imageoutname = args[4];
                    QuadTree mt=new QuadTree(imagename,imageoutname);
                } else {
                    System.out.println("Wrong Parameters");
                    System.out.println("java Main -c -i test.ppm -o out");
                }

            } else {
                System.out.println("Wrong Parameters");
                System.out.println("java Main -c -i test.ppm -o out");
            }

        } else if (args[0].compareTo("-c") == 0) {

        } else {
            System.out.println("Wrong Parameters");
            System.out.println("java Main -c -i test.ppm -o out");
        }
        System.out.println(args[0]);
        System.out.println(args[1]);
        System.out.println(args[2]);
        // TODO code application logic here
        // QuadTree mt=new QuadTree("C:\\Users\\k-sam\\OneDrive\\Belgeler\\NetBeansProjects\\Quadtree\\src\\quadtree\\kira.ppm");
//            QuadTree mt=new QuadTree("C:\\Users\\k-sam\\OneDrive\\Belgeler\\NetBeansProjects\\Quadtree\\src\\quadtree\\lena.png");
    }

}
