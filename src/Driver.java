import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Driver {
	
	public static void main(String[] args) {
		
		BufferedImage map = new MapLoader(new File("map.png")).load();
		boolean[][] rainShadow = new MapAnalyzer().findRainShadows(map);
		
		BufferedImage export = new BufferedImage(map.getWidth(), map.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		for(int i = 0; i < rainShadow.length; i++) {
			for(int j = 0; j < rainShadow[0].length; j++) {
				if(rainShadow[i][j]) {
					export.setRGB(j, i, new Color(255, 0, 0).getRGB());
				}
			}
		}
		
		try {
			ImageIO.write(export, "png", new File("export.png"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}