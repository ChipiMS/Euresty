/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package euresty;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author jonathanconejovaladez
 */
public class FillTool {
  private BufferedImage image;
  private int oldColor,newColor;
  
    
    public FillTool(int x, int y, int width, int height,Color color, BufferedImage image)
    {
        oldColor=image.getRGB(x, y);
        newColor=color.getRGB();
        this.image=image;
        
    }
    
    public void flood(int x, int y)
    {
      
        if(x<0)return;
        if(y<0)return;
        if(x>=image.getWidth()) return;
        if(y>=image.getHeight()) return;
        
        if(image.getRGB(x, y)!=oldColor)return;
        if(image.getRGB(x, y)==newColor) return;
        
        image.setRGB(x, y, newColor);
        
        flood(x-1, y);
        flood(x+1, y);
        flood(x, y-1);
        flood(x, y+1);
        
   
        
        
    }
    
    public BufferedImage getImgFilled()
    {
        return this.image;
    }
    
}