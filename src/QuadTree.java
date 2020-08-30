/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import java.awt.List;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

/**
 *
 * @author k-sam
 */
public class QuadTree {

    int[][][] pixel3DArray;
    QNode root;
    int deepLevel = 8;
    double treshholdMeanOfColorError = 5;
    int deepwidth;
    int deepheight;
    int imgWidth = 0;
    int imgHeight = 0;
    int colorSize = 255;
    int[][][] writePixel3DArray;
    String outName;
    public QuadTree(String filePath,String outname) throws IOException {
        readPPM(filePath);
        outName=outname;
        //readOther(filePath);
        root = new QNode(0, 0, imgWidth, imgHeight);
        root.initilizeNode(pixel3DArray, root.meanOfColors);
        root.isRoot = true;
        generateQuadTree(root);
        generate8Image(8);
        writePixel3DArray = pixel3DArray;
        write(13);

    }
    int xx;
    int yy;
    int ii;
    int len;

    public void readPPM(String filePath) {
        try {
            File f = new File(filePath);
            String content = new String(Files.readAllBytes(Paths.get(filePath)));

            String words[] = content.replace("\n", " ").split(" ");
            if (words[0].compareTo("P3") != 0) {
                System.out.println("wrong format");
                return;
            }
            imgWidth = Integer.parseInt(words[1]);
            imgHeight = Integer.parseInt(words[2]);
            colorSize = Integer.parseInt(words[3]);
            pixel3DArray = new int[imgWidth][imgHeight][5];
            ArrayList colors = new ArrayList();
            for (int i = 4; i < words.length; i++) {
                try {
                    int num = Integer.parseInt(words[i]);
                    colors.add(num);
                    if(num>255)
                    {
                        int j=0;
                    }
                    // is an integer!
                } catch (NumberFormatException e) {
                    // not an integer!
                }
            }
            int i = 0;
            int y = 0;
            while (y < imgHeight) {
                for (int x = 0; x < imgWidth; x++) {
                    xx = x;
                    yy = y;
                    ii = i;
                    //System.out.println("x=" + x + "\n");
                    //System.out.println("y=" + y + "\n");
                   

                    //get red
                    pixel3DArray[x][y][0] = (int)colors.get(i);

                    //get green
                    pixel3DArray[x][y][1] = (int)colors.get(i+1);

                    //get blue
                    pixel3DArray[x][y][2] = (int)colors.get(i+2);
                    
                     //get alpha;
                    pixel3DArray[x][y][3] = (int)colors.get(i);
                    //rgb mean
                    pixel3DArray[x][y][4] = (pixel3DArray[x][y][0] + pixel3DArray[x][y][1] + pixel3DArray[x][y][2]) / 3;
                    i += 3;

                }

                y++;

            }

//            BufferedReader b;
//
//            b = new BufferedReader(new FileReader(f));
//
//            String readLine = "";
//            readLine = b.readLine();
//            if (readLine == null && readLine != "P3") {
//                System.out.println("wrong format");
//                return;
//            }
//            readLine = b.readLine();
//            String size[] = readLine.split(" ");
//            if (readLine == null || size.length < 2) {
//                System.out.println("wrong format");
//                return;
//            }
//            imgWidth = Integer.parseInt(size[0]) ;
//            imgHeight = Integer.parseInt(size[1]) ;
//
//            readLine = b.readLine();
//            colorSize = Integer.parseInt(readLine);
//            pixel3DArray = new int[imgWidth][imgHeight][5];
//            int y = 0;
//            
//            while (y < imgHeight) {
//                String row = "";
//                for (int i = 0; i < 3; i++) {
//                    do {
//                        readLine = b.readLine();
//                    } while (readLine.length() < imgWidth*3);
//                    row = row + readLine;
//                }
//                String rgbcolors[] = row.split(" ");
//                len=rgbcolors.length;
//                int i=0;
//                for (int x = 0; x < imgWidth; x ++) {
//                    xx=x;
//                    yy=y;
//                    ii=i;
//                    //System.out.println("x=" + x + "\n");
//                    //System.out.println("y=" + y + "\n");
//                    //get alpha;
//                    pixel3DArray[x][y][0] = Integer.parseInt(rgbcolors[i]);
//
//                    //get red
//                    pixel3DArray[x][y][1] = Integer.parseInt(rgbcolors[i]);
//
//                    //get green
//                    pixel3DArray[x][y][2] = Integer.parseInt(rgbcolors[i + 1]);
//
//                    //get blue
//                    pixel3DArray[x][y][3] = Integer.parseInt(rgbcolors[i + 2]);
//                    //rgb mean
//                    pixel3DArray[x][y][4] = (pixel3DArray[x][y][1] + pixel3DArray[x][y][2] + pixel3DArray[x][y][3]) / 3;
//                    i+=3;
//
//                }
//
//                y++;
//
//            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(QuadTree.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(QuadTree.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("x= " + xx);
            System.out.println("y= " + yy);
            System.out.println("i= " + ii);
            System.out.println("len= " + len);
        }

    }

    public void readOther(String filePath) //read jpg,png images
    {
        try {
            File f = new File(filePath);

            BufferedImage img = ImageIO.read(f);

            if (img == null) //eğer resim yoksa dön
            {
                return;
            }
            imgWidth = img.getWidth();
            imgHeight = img.getHeight();
            //3d pixel array [x][y][alfa,red,green,blue,(r+g+b)/3 ]
            pixel3DArray = new int[img.getWidth()][img.getHeight()][5];
            for (int x = 0; x < img.getWidth(); x++) {
                for (int y = 0; y < img.getHeight(); y++) {

                    int px = img.getRGB(x, y);

//                int a =(px >> 24)& 0xff;
//                int r =(px >> 16)& 0xff;
//                int g =(px >> 8)& 0xff;
//                int b =(px >> 0)& 0xff;


                    //get red
                    pixel3DArray[x][y][0] = (px >> 16) & 0xff;

                    //get green
                    pixel3DArray[x][y][1] = (px >> 8) & 0xff;

                    //get blue
                    pixel3DArray[x][y][2] = (px >> 0) & 0xff;
                    
                    //get alpha;
                    pixel3DArray[x][y][3] = (px >> 24) & 0xff;
                    //rgb mean
                    pixel3DArray[x][y][4] = (pixel3DArray[x][y][0] + pixel3DArray[x][y][1] + pixel3DArray[x][y][2]) / 3;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(QuadTree.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generate8Image(int level) {

        for (int i = 0; i < level; i++) {

            calculateDeepLevel(deepLevel);
            QuadTreeToPixel3DArray(root);
            write(i);
            deepLevel--;
        }
    }

    private void calculateDeepLevel(int level) {
        double dwidth = root.width;
        double dheight = root.height;
        for (int i = 0; i < level; i++) {
            dwidth = dwidth / 2;
            dheight = dheight / 2;
        }
        deepheight = (int) dheight;
        deepwidth = (int) dwidth;
    }

    public void generateQuadTree(QNode pnode) {

        int w = pnode.width / 2;
        int h = pnode.height / 2;

        if (w <= 1 || h <= 1) {
            return;
        }
        //create NW Node
        QNode nwNode = new QNode(pnode.x + 0, pnode.y + 0, w, h);
        nwNode.initilizeNode(pixel3DArray, pnode.meanOfColors);
        if (treshholdMeanOfColorError < nwNode.meanOfColorsError) {
            appendQNode(nwNode, pnode);
            generateQuadTree(nwNode);
        }

        //create NE Node
        QNode neNode = new QNode(pnode.x + w, pnode.y + 0, w, h);
        neNode.initilizeNode(pixel3DArray, pnode.meanOfColors);
        if (treshholdMeanOfColorError < neNode.meanOfColorsError) {
            appendQNode(neNode, pnode);
            generateQuadTree(neNode);
        }

        //create SW Node
        QNode swNode = new QNode(pnode.x + 0, pnode.y + h, w, h);
        swNode.initilizeNode(pixel3DArray, pnode.meanOfColors);
        if (treshholdMeanOfColorError < swNode.meanOfColorsError) {
            appendQNode(swNode, pnode);
            generateQuadTree(swNode);
        }

        //create SE Node
        QNode seNode = new QNode(pnode.x + w, pnode.y + h, w, h);
        seNode.initilizeNode(pixel3DArray, pnode.meanOfColors);

        if (treshholdMeanOfColorError < seNode.meanOfColorsError) {
            appendQNode(seNode, pnode);
            generateQuadTree(seNode);
        }

//        generateQuadTree(nwNode,level++);
//        generateQuadTree(neNode,level++);
//        generateQuadTree(swNode,level++);
//        generateQuadTree(seNode,level++);
    }

    public void appendQNode(QNode newnode, QNode pnode) {
        int w = pnode.width / 2;
        int h = pnode.height / 2;

        if (newnode.x < pnode.x + w && newnode.y < pnode.y + h)//add NW child
        {
            if (pnode.NW == null) {
                pnode.NW = newnode;
                return;
            } else {
                appendQNode(newnode, pnode.NW);
            }
        } else if (newnode.x >= pnode.x + w && newnode.y < pnode.y + h)//add NE child
        {
            if (pnode.NE == null) {
                pnode.NE = newnode;

            } else {
                appendQNode(newnode, pnode.NE);
            }
        } else if (newnode.x < pnode.x + w && newnode.y >= pnode.y + h)//add SW child
        {
            if (pnode.SW == null) {
                pnode.SW = newnode;
                return;
            } else {
                appendQNode(newnode, pnode.SW);
            }
        } else if (newnode.x >= pnode.x + w && newnode.y >= pnode.y + h)//add SE child
        {
            if (pnode.SE == null) {
                pnode.SE = newnode;
                return;
            } else {
                appendQNode(newnode, pnode.SE);
            }
        }

    }
    // String header = "P3\n" + root.width + " " + root.height + "\n255\n";

    public void QuadTreeToPixel3DArray(QNode pnode) {
        if (pnode.isRoot == true) {
            writePixel3DArray = new int[pnode.width][pnode.height][3];
        }
        int w = pnode.width / 2;
        int h = pnode.height / 2;

        if (w < deepwidth || h < deepheight) {

            for (int x = pnode.x; x < pnode.x + pnode.width; x++) {
                for (int y = pnode.y; y < pnode.y + pnode.height; y++) {
                    writePixel3DArray[x][y][0] = (int) pnode.meanR;
                    writePixel3DArray[x][y][1] = (int) pnode.meanG;
                    writePixel3DArray[x][y][2] = (int) pnode.meanB;
                }
            }

            return;
        }

        if (pnode.NW != null) {

            QuadTreeToPixel3DArray(pnode.NW);
        } else {
            for (int x = pnode.x; x < pnode.x + w; x++) {
                for (int y = pnode.y; y < pnode.y + h; y++) {
                    writePixel3DArray[x][y][0] = (int) pnode.meanR;
                    writePixel3DArray[x][y][1] = (int) pnode.meanG;
                    writePixel3DArray[x][y][2] = (int) pnode.meanB;
                }
            }
        }

        if (pnode.NE != null) {

            QuadTreeToPixel3DArray(pnode.NE);
        } else {
            for (int x = pnode.x + w; x < pnode.x + pnode.width; x++) {
                for (int y = pnode.y; y < pnode.y + h; y++) {
                    writePixel3DArray[x][y][0] = (int) pnode.meanR;
                    writePixel3DArray[x][y][1] = (int) pnode.meanG;
                    writePixel3DArray[x][y][2] = (int) pnode.meanB;
                }
            }
        }

        if (pnode.SW != null) {
            QuadTreeToPixel3DArray(pnode.SW);
        } else {
            for (int x = pnode.x; x < pnode.x + w; x++) {
                for (int y = pnode.y + h; y < pnode.y + pnode.height; y++) {
                    writePixel3DArray[x][y][0] = (int) pnode.meanR;
                    writePixel3DArray[x][y][1] = (int) pnode.meanG;
                    writePixel3DArray[x][y][2] = (int) pnode.meanB;
                }
            }
        }

        if (pnode.SE != null) {
            QuadTreeToPixel3DArray(pnode.SE);
        } else {
            for (int x = pnode.x + w; x < pnode.x + pnode.width; x++) {
                for (int y = pnode.y + h; y < pnode.y + pnode.height; y++) {
                    writePixel3DArray[x][y][0] = (int) pnode.meanR;
                    writePixel3DArray[x][y][1] = (int) pnode.meanG;
                    writePixel3DArray[x][y][2] = (int) pnode.meanB;
                }
            }
        }

    }

    public void write(int level) {
        try {
            BufferedWriter outppm = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outName + level + ".ppm")));

            outppm.write("P3\n" + root.width + " " + root.height + "\n255\n");

            BufferedImage image = new BufferedImage(root.width, root.height, BufferedImage.TYPE_INT_RGB);

            for (int y = 0; y < root.height; y++) {
                for (int x = 0; x < root.width; x++) {
                    Color cl = new Color(writePixel3DArray[x][y][0], writePixel3DArray[x][y][1], writePixel3DArray[x][y][2]);

                    image.setRGB(x, y, cl.getRGB());
                    outppm.write(writePixel3DArray[x][y][0] + " " + writePixel3DArray[x][y][1] + " " + writePixel3DArray[x][y][2] + " ");

                }
                outppm.write("\n");
            }
            File outpng = new File(outName + level + ".png");

            ImageIO.write(image, "png", outpng);
            outppm.close();
        } catch (IOException ex) {
            Logger.getLogger(QuadTree.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
