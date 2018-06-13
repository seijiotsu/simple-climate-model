import java.awt.Color;
import java.awt.image.BufferedImage;

public class MapAnalyzer {
	
	enum Direction {
		SOUTHEAST,
		SOUTHWEST,
		NORTHEAST,
		NORTHWEST;
	}
	
	public boolean[][] findRainShadows(BufferedImage map) {
		int height = map.getHeight();
		int width = map.getWidth();
		
		if(height*2 != width) {
			//Should throw exception because map is not equirectangular
			return null;
		}
		
		boolean[][] rainshadow = new boolean[height][width];
		
		rainshadow = findRainShadowsInBand(rainshadow, 0, height/6, map, Direction.SOUTHWEST);
		rainshadow = findRainShadowsInBand(rainshadow, height/6, height*1/3, map, Direction.NORTHEAST);
		rainshadow = findRainShadowsInBand(rainshadow, height*1/3, height/2, map, Direction.SOUTHWEST);
		rainshadow = findRainShadowsInBand(rainshadow, height/2, height*2/3, map, Direction.NORTHWEST);
		rainshadow = findRainShadowsInBand(rainshadow, height*2/3, height*5/6, map, Direction.SOUTHEAST);
		rainshadow = findRainShadowsInBand(rainshadow, height*5/6, height, map, Direction.NORTHWEST);
		
		return rainshadow;
	}
	
	private boolean[][] findRainShadowsInBand(boolean[][] rainshadow, int topBound, int bottomBound, BufferedImage map, Direction direction) {
		
		final int height = map.getHeight();
		final int width = map.getWidth();
		
		
		if(direction == Direction.SOUTHEAST) {
			for(int c = -width/2; c < width; c++) {
				
				int currCol = c;
				
				int currElev = 0;
				int maxElev = 0;
				boolean isRainshadow = true;
				
				for(int r = topBound; r < bottomBound; r++) {
					
					if(currCol < 0) {
						currCol++;
						continue;
					}
					
					if(currCol >= width) {
						break;
					}
					
					int rgb = map.getRGB(currCol, r);
					int elevation = new Color(rgb).getRed();
					
					if(elevation < 90) {
						maxElev = 0;
						isRainshadow = false;
					} else {
						if(elevation < maxElev && maxElev > 110) {
							isRainshadow = true;
						}
						currElev = elevation;
						maxElev = Math.max(currElev, maxElev);
					}
					
					if(isRainshadow) {
						rainshadow[r][currCol] = true;
					}
					
					currCol++;
				}
			}
		}
		
		if(direction == Direction.SOUTHWEST) {
			for(int c = width*3/2; c > 0; c--) {
				
				int currCol = c;
				
				int currElev = 0;
				int maxElev = 0;
				boolean isRainshadow = true;
				
				for(int r = topBound; r < bottomBound; r++) {
					
					if(currCol < 0) {
						break;
					}
					
					if(currCol >= width) {
						currCol--;
						continue;
					}
					
					int rgb = map.getRGB(currCol, r);
					int elevation = new Color(rgb).getRed();
					
					if(elevation < 90) {
						maxElev = 0;
						isRainshadow = false;
					} else {
						if(elevation < maxElev && maxElev > 110) {
							isRainshadow = true;
						}
						currElev = elevation;
						maxElev = Math.max(currElev, maxElev);
					}
					
					if(isRainshadow) {
						rainshadow[r][currCol] = true;
					}
					
					currCol--;
				}
			}
		}
		
		if(direction == Direction.NORTHEAST) {
			for(int c = -width/2; c < width; c++) {
				
				int currCol = c;
				
				int currElev = 0;
				int maxElev = 0;
				boolean isRainshadow = true;
				
				for(int r = bottomBound - 1; r >= topBound; r--) {
					
					if(currCol < 0) {
						currCol++;
						continue;
					}
					
					if(currCol >= width) {
						break;
					}
					
					int rgb = map.getRGB(currCol, r);
					int elevation = new Color(rgb).getRed();
					
					if(elevation < 90) {
						maxElev = 0;
						isRainshadow = false;
					} else {
						if(elevation < maxElev && maxElev > 110) {
							isRainshadow = true;
						}
						currElev = elevation;
						maxElev = Math.max(currElev, maxElev);
					}
					
					if(isRainshadow) {
						rainshadow[r][currCol] = true;
					}
					
					currCol++;
				}
			}
		}
		
		if(direction == Direction.NORTHWEST) {
			for(int c = width*3/2; c > 0; c--) {
				
				int currCol = c;
				
				int currElev = 0;
				int maxElev = 0;
				boolean isRainshadow = true;
				
				for(int r = bottomBound - 1; r >= topBound; r--) {
					
					if(currCol < 0) {
						break;
					}
					
					if(currCol >= width) {
						currCol--;
						continue;
					}
					
					int rgb = map.getRGB(currCol, r);
					int elevation = new Color(rgb).getRed();
					
					if(elevation < 90) {
						maxElev = 0;
						isRainshadow = false;
					} else {
						if(elevation < maxElev && maxElev > 110) {
							isRainshadow = true;
						}
						currElev = elevation;
						maxElev = Math.max(currElev, maxElev);
					}
					
					if(isRainshadow) {
						rainshadow[r][currCol] = true;
					}
					
					currCol--;
				}
			}
		}
		
		return rainshadow;
	}
}
