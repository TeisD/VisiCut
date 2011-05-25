/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t_oster.liblasercut;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.LinkedList;

/**
 *
 * @author thommy
 */
public class RasterPart {
    
    private EngravingProperty curProp;
    
    private List<BufferedImage> images = new LinkedList<BufferedImage>();
    private List<EngravingProperty> properties = new LinkedList<EngravingProperty>();
    private List<Point> starts = new LinkedList<Point>();
    
    public RasterPart(EngravingProperty initialEngravingProperty){
        this.curProp = initialEngravingProperty;
    }
    
    public void setCurrentEngravingProperty(EngravingProperty eng){
        this.curProp = eng;
    }
    
    public EngravingProperty getCurrentEngravingProperty(){
        return this.curProp;
    }
    
    public void setPower(int power){
        this.curProp.setPower(power);
    }
    
    public void setSpeed(int speed){
        this.curProp.setSpeed(speed);
    }
    
    /**
     * Adds the given Image to this RasterPart
     * The Image must be in sRGB Format. The grey value of every pixel
     * is mapped to the LaserPower (scaled to the LaserPower in the
     * current engraving property)
     * @param img 
     */
    public void addImage(BufferedImage img, Point start){
        this.addImage(img, curProp, start);
    }
    
    public void addImage(BufferedImage img, EngravingProperty prop, Point start){
        this.images.add(img);
        this.properties.add(prop);
        this.starts.add(start);
    }
    
    /**
     * Returns the number of Rasters, this rasterpart contains
     * @return 
     */
    public int getRasterCount(){
        return this.images.size();
    }
    
    /**
     * Returns the full width of the complete raster Part
     * @return
     */
    public int getWidth(){
        int minx = 0;
        int maxx = 0;
        for (int i=0;i<this.getRasterCount();i++){
            Point start = this.getRasterStart(i);
            minx = Math.min(minx, start.x);
            maxx = Math.max(maxx, start.x+this.getRasterWidth(i));
        }
        return maxx-minx;
    }

    /**
     * Returns the full height of the complete raster Part
     * @return
     */
    public int getHeight(){
        int miny = 0;
        int maxy = 0;
        for (int i=0;i<this.getRasterCount();i++){
            Point start = this.getRasterStart(i);
            miny = Math.min(miny, start.y);
            maxy = Math.max(maxy, start.y+this.getRasterHeight(i));
        }
        return maxy-miny;
    }

    /**
     * Returns the upper left point of the given raster
     * @param raster the raster which upper left corner is to determine
     * @return 
     */
    public Point getRasterStart(int raster){
        //TODO
        return new Point(0,0);
    }
    
    /**
     * Returns one line of the given rasterpart
     * every byte represents one pixel and the value corresponds to
     * the raster power
     * @param raster
     * @param line
     * @return 
     */
    public List<Byte> getRasterLine(int raster, int line){
        BufferedImage img = this.images.get(raster);
        List<Byte> result = new LinkedList<Byte>();
        for (int x=0;x<img.getWidth();x++){
            Color c = new Color(img.getRGB(x, line));
            result.add((byte) ((c.getRed()+c.getGreen()+c.getBlue())/3));
        }
        return result;
    }

    public int getRasterWidth(int raster){
        return this.images.get(raster).getWidth();
    }

    public int getRasterHeight(int raster){
        return this.images.get(raster).getHeight();
    }

    public BufferedImage[] getImages(){
        return this.images.toArray(new BufferedImage[0]);
    }
    
    public EngravingProperty[] getPropertys(){
        return this.properties.toArray(new EngravingProperty[0]);
    }
}
