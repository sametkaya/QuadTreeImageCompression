/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author k-sam
 */
public class QNode {
    // for root node
    boolean isRoot;
    //gray scale color
    double meanOfColors;
    //red
    double meanR;
    //green
    double meanG;
    //blue
    double meanB;
    //transparency
    double meanA;
    //gray scale color error
    double meanOfColorsError;
    //range width
    int width;
    //range height
    int height;
    //range location
    int x;
    int y;
    //child nodes
    QNode NW;
    QNode NE;
    QNode SW;
    QNode SE;
    public QNode(int lx, int ly, int w, int h)
    {
        isRoot=false;
        width=w;
        height=h;
        meanOfColors=1;
        meanOfColorsError=0;
        x=lx;
        y=ly;
    }
    // calculate node pixels and mean error according to parent node
    public void initilizeNode(int [][][] pixel3DArray, double pmean)
    {
        double mean=0;
        double meanR=0;
        double meanG=0;
        double meanB=0;
        double meanA=0;
        double meanError=0;
        for (int x = this.x; x < this.x+this.width; x++) {
             for (int y = this.y; y < this.y+this.height; y++) {
                 mean+=pixel3DArray[x][y][4];
                 
                 meanR+=pixel3DArray[x][y][0];
                 meanG+=pixel3DArray[x][y][1];
                 meanB+=pixel3DArray[x][y][2];
                 meanA+=pixel3DArray[x][y][3];
                 //calculete meen error of colors according to parrent
                 meanError+=(Math.pow(pixel3DArray[x][y][0]-pmean,2)
                         +(Math.pow(pixel3DArray[x][y][1]-pmean,2))
                         +(Math.pow(pixel3DArray[x][y][2]-pmean,2)));
             }
        }
        int pixelcount=(this.width*this.height);
        this.meanOfColors=mean/(this.width*this.height);
        this.meanOfColorsError=meanError/pixelcount/1000; // divide by 1000 because result of (meanError/pixelcount) is too big 
        //avarage of color
        this.meanA=meanA/pixelcount;
        this.meanR=meanR/pixelcount;
        this.meanG=meanG/pixelcount;
        this.meanB=meanB/pixelcount;
    }
            
}
