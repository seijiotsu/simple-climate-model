import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Driver {
	
	public static void main(String[] args) {
		
		BufferedImage map = new MapLoader(new File("map2.png")).load();
		boolean[][] rainshadow = new MapAnalyzer().findRainShadows(map);
		
		/*
		 * Export the rainshadow map
		 */
		BufferedImage export = new BufferedImage(map.getWidth(), map.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		for(int i = 0; i < rainshadow.length; i++) {
			for(int j = 0; j < rainshadow[0].length; j++) {
				if(rainshadow[i][j]) {
					export.setRGB(j, i, new Color(255, 0, 0).getRGB());
				}
			}
		}
		
		try {
			ImageIO.write(export, "png", new File("rainshadow.png"));
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		/*
		 * Find the regions
		 */
		int[][] regions = new MapAnalyzer().findContinents(map);
		
		/*
		 * Make the climates
		 */
		
		Climates[][] climates = new MapAnalyzer().generateClimates(map, rainshadow, regions);
		
		/*
		 * Output climate map
		 */
		
		BufferedImage climate_map = new BufferedImage(map.getWidth(), map.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		for(int i = 0; i < climates.length; i++) {
			for(int j = 0; j < climates[0].length; j++) {
				climate_map.setRGB(j, i, climates[i][j].val());
			}
		}
		
		try {
			ImageIO.write(climate_map, "png", new File("climates.png"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}