import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import javax.imageio.ImageIO;

public class MapAnalyzer {
	
	final int RAINSHADOW_INC = 60;
	final int WATER_RAINSHADOW_DEC = 10;
	final int LAND_RAINSHADOW_DEC = 1;
	
	final int CELL_SCALE_FACTOR = 48;
	final int MAX_CELLS_MERGE = CELL_SCALE_FACTOR / 5;
	final int MAX_SEARCH_SPACE = CELL_SCALE_FACTOR / 12;
	
	final int MAX_COAST_FILL = CELL_SCALE_FACTOR / 12;
	final int MAX_TRANSITION_FILL = CELL_SCALE_FACTOR / 12;
	
	final int MAX_EXP_SEARCH_SPACE = MAX_SEARCH_SPACE;
	
	public Climates[][] generateClimates(BufferedImage map, boolean[][] rainshadows, int[][] regions) {
		
		Climates[][] climates = new Climates[map.getHeight()][map.getWidth()];
		for(int i = 0; i < map.getHeight(); i++) {
			for(int j = 0; j < map.getWidth(); j++) {
				int elevation = new Color(map.getRGB(j, i)).getRed();
				int latitude = Math.abs((int)(((double)map.getHeight()/2 - i)/(map.getHeight()/2)*90));
				
				climates[i][j] = getPixelClimate(latitude, elevation, rainshadows[i][j], regions[i][j]);
			}
		}
		
		return climates;
	}
	
	private Climates getPixelClimate(int latitude, int elevation, boolean rainshadow, int region) {
		if(elevation < 90) return Climates.OCEAN;
		
		if(region == 4 || region == 5) rainshadow = false;
		
		int elev_diff = elevation - 90;
		latitude += elev_diff/2;
		
		latitude = Math.min(90, latitude);
		latitude = latitude - (latitude % 5); //floor to nearest multiple of 5;
		
		switch(latitude) {
		case 0:
			switch(region) {
			case 1: return Climates.TROPICALWETFOREST;
			case 2: return Climates.TROPICALWETFOREST;
			case 3: return Climates.TROPICALDRYFOREST;
			case 4: return Climates.TROPICALWETFOREST;
			case 5: return Climates.TROPICALWETFOREST;
			}
		case 5:
			switch(region) {
			case 1: return Climates.TROPICALWETFOREST;
			case 2: return Climates.TROPICALDRYFOREST;
			case 3: return Climates.TROPICALWOODLANDS;
			case 4: return Climates.TROPICALDRYFOREST;
			case 5: return Climates.TROPICALWETFOREST;
			}
		case 10:
			switch(region) {
			case 1: return Climates.TROPICALDRYFOREST;
			case 2: return Climates.TROPICALWOODLANDS;
			case 3: return Climates.TROPICALWOODLANDS;
			case 4: return Climates.TROPICALDRYFOREST;
			case 5: return Climates.TROPICALWETFOREST;
			}
		case 15:
			switch(region) {
			case 1: return Climates.SUBTROPICALWOODLANDS;
			case 2: return Climates.SUBTROPICALWOODLANDS;
			case 3: return Climates.SUBTROPICALWOODLANDS;
			case 4: return Climates.SUBTROPICALDRYFOREST;
			case 5: return Climates.SUBTROPICALWETFOREST;
			}
		case 20:
			switch(region) {
			case 1: return Climates.SUBTROPICALSCRUB;
			case 2: return Climates.SUBTROPICALSCRUB;
			case 3: return Climates.SUBTROPICALWOODLANDS;
			case 4: return Climates.SUBTROPICALDRYFOREST;
			case 5: return Climates.SUBTROPICALWETFOREST;
			}
		case 25:
			switch(region) {
			case 1: return Climates.EXTREMEDESERT;
			case 2: return Climates.DESERT;
			case 3: return Climates.SUBTROPICALSCRUB;
			case 4: return Climates.SUBTROPICALWOODLANDS;
			case 5: return Climates.SUBTROPICALFOREST;
			}
		case 30:
			switch(region) {
			case 1: return Climates.EXTREMEDESERT;
			case 2: return Climates.DESERT;
			case 3: return Climates.SUBTROPICALSCRUB;
			case 4: return Climates.SUBTROPICALWOODLANDS;
			case 5: return Climates.SUBTROPICALFOREST;
			}
		case 35:
			switch(region) {
			case 1: return Climates.MEDITERRANEAN;
			case 2: return Climates.STEPPE;
			case 3: return Climates.STEPPE;
			case 4: return Climates.TEMPERATEWOODLANDS;
			case 5: return Climates.TEMPERATEFOREST;
			}
		case 40:
			switch(region) {
			case 1: return Climates.MEDITERRANEAN;
			case 2: return Climates.STEPPE;
			case 3: return Climates.STEPPE;
			case 4: return Climates.TEMPERATEWOODLANDS;
			case 5: return Climates.TEMPERATEFOREST;
			}
		case 45:
			switch(region) {
			case 1: return Climates.TEMPERATEWETFOREST;
			case 2: return Climates.TEMPERATEWOODLANDS;
			case 3: return Climates.STEPPE;
			case 4: return Climates.TEMPERATEWOODLANDS;
			case 5: return Climates.TEMPERATEFOREST;
			}
		case 50:
			switch(region) {
			case 1: return Climates.TEMPERATEWETFOREST;
			case 2: return Climates.TEMPERATEWOODLANDS;
			case 3: return Climates.STEPPE;
			case 4: return Climates.TEMPERATEWOODLANDS;
			case 5: return Climates.TEMPERATEFOREST;
			}
		case 55:
			switch(region) {
			case 1: return Climates.TEMPERATEWETFOREST;
			case 2: return Climates.TEMPERATEWOODLANDS;
			case 3: return Climates.TEMPERATEWOODLANDS;
			case 4: return Climates.BOREALFOREST;
			case 5: return Climates.BOREALFOREST;
			}
		case 60:
			switch(region) {
			case 1: return Climates.TEMPERATEWETFOREST;
			case 2: return Climates.BOREALFOREST;
			case 3: return Climates.BOREALFOREST;
			case 4: return Climates.BOREALFOREST;
			case 5: return Climates.BOREALFOREST;
			}
		case 65:
			switch(region) {
			case 1: return Climates.BOREALFOREST;
			case 2: return Climates.BOREALFOREST;
			case 3: return Climates.BOREALFOREST;
			case 4: return Climates.BOREALFOREST;
			case 5: return Climates.TUNDRA;
			}
		case 70:
			switch(region) {
			case 1: return Climates.BOREALFOREST;
			case 2: return Climates.BOREALFOREST;
			case 3: return Climates.TUNDRA;
			case 4: return Climates.TUNDRA;
			case 5: return Climates.TUNDRA;
			}
		case 75:
			switch(region) {
			case 1: return Climates.WETTUNDRA;
			case 2: return Climates.TUNDRA;
			case 3: return Climates.TUNDRA;
			case 4: return Climates.TUNDRA;
			case 5: return Climates.TUNDRA;
			}
		case 80: case 85: case 90:
			switch(region) {
			case 1: return Climates.ICECAP;
			case 2: return Climates.ICECAP;
			case 3: return Climates.ICECAP;
			case 4: return Climates.ICECAP;
			case 5: return Climates.ICECAP;
			}
		}
		
		return null;
	}
	
	public int[][] findContinents(BufferedImage map) {
		int height = map.getHeight();
		int width = map.getWidth();
		
		/*
		 * Divide map into X px square cells (using CELL_SCALE_FACTOR)
		 */
		int cellX_height = height/CELL_SCALE_FACTOR + 1;
		int cellX_width = width/CELL_SCALE_FACTOR + 1;
		
		int[][] cellX = new int[cellX_height][cellX_width];
		for(int i = 0; i < cellX_height; i++) {
			for(int j = 0; j < cellX_width; j++) {
				
				/*
				 * Determine if each X px by X px grid cell is land or water
				 */
				
				int numLand = 0;
				int numWater = 0;
				
				for(int row = 0; row < CELL_SCALE_FACTOR; row++) {
					
					int map_Y = CELL_SCALE_FACTOR*i + row;
					if(map_Y >= height) break; //Make sure we don't go out of bounds
					
					for(int col = 0; col < CELL_SCALE_FACTOR; col++) {
						
						int map_X = CELL_SCALE_FACTOR*j + col;
						if(map_X >= width) break;
						
						
						int elevation = new Color(map.getRGB(map_X, map_Y)).getRed();
						if(elevation >= 90) {
							numLand++;
						} else {
							numWater++;
						}
						
					}
				}
				
				if(numWater >= numLand) {
					cellX[i][j] = -1;
				}
			}
		}
		
		/*
		 * Write cellX to cellX_1
		 */
		BufferedImage export = new BufferedImage(cellX_width, cellX_height, BufferedImage.TYPE_4BYTE_ABGR);
		for(int i = 0; i < cellX.length; i++) {
			for(int j = 0; j < cellX[0].length; j++) {
				if(cellX[i][j] == 0) {
					export.setRGB(j, i, new Color(255, 0, 0).getRGB());
				}
			}
		}
		
		try {
			ImageIO.write(export, "png", new File("cellX_1.png"));
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		/*
		 * Basic flood fill algorithm
		 */
		int numRegions = 0;
		for(int i = 0; i < cellX_height; i++) {
			for(int j = 0; j < cellX_width; j++) {
				if(cellX[i][j] == 0) {
					numRegions++;
					cellX = floodFill(cellX, i, j, numRegions);
				}
			}
		}
		
		/*
		 * Find sizes of each of the different 'regions' and then try and merge the smaller regions into
		 * nearby larger regions
		 */
		int[] sizes = findSizes(cellX, numRegions);
		
		cellX = tryAndMerge(cellX, sizes);
		
		/*
		 * Write flood fill algorithm to map
		 */
		
		int[] colorsR = new int[sizes.length];
		int[] colorsG = new int[sizes.length];
		int[] colorsB = new int[sizes.length];

		for(int i = 0; i < sizes.length; i++) {
			colorsR[i] = (int)(Math.random()*256);
			colorsG[i] = (int)(Math.random()*256);
			colorsB[i] = (int)(Math.random()*256);
		}
		
		BufferedImage img_cellX_2 = new BufferedImage(cellX_width, cellX_height, BufferedImage.TYPE_4BYTE_ABGR);
		
		for(int i = 0; i < cellX.length; i++) {
			for(int j = 0; j < cellX[0].length; j++) {
				if(cellX[i][j] == -1) {
					img_cellX_2.setRGB(j, i, new Color(182, 220, 244).getRGB());
				} else {
					img_cellX_2.setRGB(j, i, new Color(colorsR[cellX[i][j]], colorsG[cellX[i][j]], colorsB[cellX[i][j]]).getRGB());
				}
			}
		}
		
		try {
			ImageIO.write(img_cellX_2, "png", new File("cellX_2.png"));
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		/*
		 * Divide continents into {WEST COAST, WEST TRANSITION, INLAND, EAST TRANSITION, EAST COAST}
		 * with ids {1, 2, 3, 4, 5};
		 */
		int[][] cellX_transitions = new int[cellX.length][cellX[0].length];
		
		for(int id = 1; id < sizes.length; id++) {
			
			int depthFilled = 0;
			boolean keepTransitionLoop = true;
			while(keepTransitionLoop) {
				//Alternate from filling west and east coasts while haven't filled X depth
				
				if(depthFilled < MAX_COAST_FILL) {
					
					//Fill west coast
					for(int i = 0; i < cellX.length; i++) {
						for(int j = 0; j < cellX[0].length; j++) {
							if(cellX[i][j] == id && cellX_transitions[i][j] == 0) {
								cellX_transitions[i][j] = 1;
								break;
							}
						}
					}
					
					//Fill east coast
					for(int i = 0; i < cellX.length; i++) {
						for(int j = cellX[0].length - 1; j >= 0; j--) {
							if(cellX[i][j] == id && cellX_transitions[i][j] == 0) {
								cellX_transitions[i][j] = 5;
								break;
							}
						}
					}
					
					depthFilled++;
					
				} else if(depthFilled < MAX_COAST_FILL + MAX_TRANSITION_FILL) {
					
					//Fill west transition zone
					for(int i = 0; i < cellX.length; i++) {
						for(int j = 0; j < cellX[0].length; j++) {
							if(cellX[i][j] == id && cellX_transitions[i][j] == 0) {
								cellX_transitions[i][j] = 2;
								break;
							}
						}
					}
					
					//Fill east transition zone
					for(int i = 0; i < cellX.length; i++) {
						for(int j = cellX[0].length - 1; j >= 0; j--) {
							if(cellX[i][j] == id && cellX_transitions[i][j] == 0) {
								cellX_transitions[i][j] = 4;
								break;
							}
						}
					}
					
					depthFilled++;
					
				} else {
					
					//Fill inland
					int numFilled = 0;
					for(int i = 0; i < cellX.length; i++) {
						for(int j = 0; j < cellX[0].length; j++) {
							if(cellX[i][j] == id && cellX_transitions[i][j] == 0) {
								cellX_transitions[i][j] = 3;
								numFilled++;
							}
						}
					}
					
					if(numFilled == 0) {
						keepTransitionLoop = false;
					}
				}
			}
		}
		
		/*
		 * Write coast algorithm to map
		 */
		
		BufferedImage img_cellX_3 = new BufferedImage(cellX_width, cellX_height, BufferedImage.TYPE_4BYTE_ABGR);
		
		for(int i = 0; i < cellX.length; i++) {
			for(int j = 0; j < cellX[0].length; j++) {
				if(cellX[i][j] == -1) {
					img_cellX_3.setRGB(j, i, new Color(182, 220, 244).getRGB());
				} else {
					
					int R = colorsR[cellX[i][j]];
					int G = colorsG[cellX[i][j]];
					int B = colorsB[cellX[i][j]];
					int C = cellX_transitions[i][j];
					
					img_cellX_3.setRGB(j, i, new Color(R - C*R/6, G - C*G/6, B - C*B/6).getRGB());
				}
			}
		}
		
		try {
			ImageIO.write(img_cellX_3, "png", new File("cellX_3.png"));
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		/*
		 * Expand the cellX_transitions out by X to compensate for later upward scaling. find the
		 * closest type of transition that exists.
		 */
		
		int[][] areasToAdd = new int[cellX_transitions.length][cellX_transitions[0].length];
		int[][] areasToAddCont = new int[cellX_transitions.length][cellX_transitions[0].length];
		
		for(int row = 0; row < areasToAdd.length; row++) {
			for(int col = 0; col < areasToAdd[0].length; col++) {
				
				if(cellX[row][col] != -1) continue;
								
				HashMap<Integer, Integer> distance = new HashMap<>();
				HashMap<Integer, Integer> distanceContinent = new HashMap<>();
				
				for(int r2 = -MAX_EXP_SEARCH_SPACE; r2 <= MAX_EXP_SEARCH_SPACE; r2++) {
					for(int c2 = -MAX_EXP_SEARCH_SPACE; c2 <= MAX_EXP_SEARCH_SPACE; c2++) {
						
						if(row + r2 < 0 || row + r2 >= cellX.length) break;
						if(col + c2 < 0 || col + c2 >= cellX[0].length) continue;
						
						int type = cellX_transitions[row + r2][col + c2];
						
						if(type != 0 && (!distance.containsKey(type) || Math.abs(r2) + Math.abs(c2) < distance.get(type))) {
							distance.put(type, Math.abs(r2) + Math.abs(c2));
							distanceContinent.put(type, cellX[row + r2][col + c2]);
						}
					}
				}
				
				int minDistance = Integer.MAX_VALUE;
				int minDistanceType = 0;
				
				ArrayList<Integer> keys = new ArrayList<Integer>(distance.keySet());
				
				System.out.print(keys + " -> ");
				
				for(int i = 0; i < keys.size(); i++) {
					if(distance.get(keys.get(i)) < minDistance) {
						minDistance = distance.get(keys.get(i));
						minDistanceType = keys.get(i);
					}
				}
				
				System.out.println(minDistanceType);
				
				if(minDistanceType != 0) {
					areasToAdd[row][col] = minDistanceType;
					System.out.println("The closest type was " + minDistanceType + " belonging to continent #" + distanceContinent.get(minDistanceType));
					areasToAddCont[row][col] = distanceContinent.get(minDistanceType);
				}
			}
		}
		
		for(int i = 0; i < areasToAdd.length; i++) {
			for(int j = 0; j < areasToAdd[0].length; j++) {
				if(areasToAdd[i][j] != 0) {
					System.out.println("Adding " + i + " " + j);
					cellX_transitions[i][j] = areasToAdd[i][j];
					cellX[i][j] = areasToAddCont[i][j];
				}
			}
		}
		
		/*
		 * Write expanded cells to map
		 */
		
		BufferedImage img_cellX_3b = new BufferedImage(cellX_width, cellX_height, BufferedImage.TYPE_4BYTE_ABGR);
		
		for(int i = 0; i < cellX.length; i++) {
			for(int j = 0; j < cellX[0].length; j++) {
				if(cellX[i][j] == -1) {
					img_cellX_3b.setRGB(j, i, new Color(182, 220, 244).getRGB());
				} else {
					
					int R = colorsR[cellX[i][j]];
					int G = colorsG[cellX[i][j]];
					int B = colorsB[cellX[i][j]];
					int C = cellX_transitions[i][j];
					
					img_cellX_3b.setRGB(j, i, new Color(R - C*R/6, G - C*G/6, B - C*B/6).getRGB());
				}
			}
		}
		
		try {
			ImageIO.write(img_cellX_3b, "png", new File("cellX_3b.png"));
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		/*
		 * Write continents to base-map scale
		 */
		int[][] large_cellX = new int[map.getHeight()][map.getWidth()];
		int[][] large_transitions = new int[map.getHeight()][map.getWidth()];
		for(int i = 0; i < cellX_transitions.length; i++) {
			for(int j = 0; j < cellX_transitions[0].length; j++) {
				for(int row = CELL_SCALE_FACTOR*i; row < CELL_SCALE_FACTOR*(i + 1) && row < map.getHeight(); row++) {
					for(int col = CELL_SCALE_FACTOR*j; col < CELL_SCALE_FACTOR*(j + 1) && col < map.getWidth(); col++) {
						int elevation = new Color(map.getRGB(col, row)).getRed();
						
						if(elevation >= 90) {
							if(cellX[i][j] != -1) {
								large_transitions[row][col] = cellX_transitions[i][j];
								large_cellX[row][col] = cellX[i][j];
							} else {
								if(cellX_transitions[i][j] != 0) {
									large_transitions[row][col] = cellX_transitions[i][j];
								} else {
									large_transitions[row][col] = 1;
								}
								large_cellX[row][col] = 1;
							}
						}
					}
				}
			}
		}
		
		/*
		 * Write continents to map
		 */
		
		BufferedImage img_cellX_4 = new BufferedImage(map.getWidth(), map.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		
		for(int i = 0; i < map.getHeight(); i++) {
			for(int j = 0; j < map.getWidth(); j++) {
				if(large_cellX[i][j] == -1) {
					img_cellX_4.setRGB(j, i, new Color(182, 220, 244).getRGB());
				} else if(large_cellX[i][j] == -2) {
					img_cellX_4.setRGB(j, i, new Color(0, 0, 0).getRGB());
				} else {
					
					int R = colorsR[large_cellX[i][j]];
					int G = colorsG[large_cellX[i][j]];
					int B = colorsB[large_cellX[i][j]];
					int C = large_transitions[i][j];
					
					img_cellX_4.setRGB(j, i, new Color(R - C*R/6, G - C*G/6, B - C*B/6).getRGB());
				}
			}
		}
		try {
			ImageIO.write(img_cellX_4, "png", new File("cellX_4.png"));
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return large_transitions;
	}
	
	private int[][] floodFill(int[][] cellX, int i, int j, int numRegions) {
		int cellX_height = cellX.length;
		int cellX_width = cellX[0].length;
		
		Queue<Integer> qX = new LinkedList<>();
		Queue<Integer> qY = new LinkedList<>();
				
		qX.add(j);
		qY.add(i);
		while(!qX.isEmpty()) {
			int X = qX.remove();
			int Y = qY.remove();
			
			if(X < 0 || X >= cellX_width) continue;
			if(Y < 0 || Y >= cellX_height) continue;
						
			if(cellX[Y][X] == 0) {
				cellX[Y][X] = numRegions;
			} else {
				continue;
			}
			
			qX.add(X + 1);
			qY.add(Y);
			
			qX.add(X);
			qY.add(Y - 1);
			
			qX.add(X);
			qY.add(Y + 1);
			
			qX.add(X - 1);
			qY.add(Y);
		}
		
		return cellX;
	}
	
	private int[] findSizes(int[][] cellX, int numRegions) {
		int[] sizes = new int[numRegions + 1];
		
		for(int i = 0; i < cellX.length; i++) {
			for(int j = 0; j < cellX[0].length; j++) {
				if(cellX[i][j] != -1) {
					sizes[cellX[i][j]]++;
				}
			}
		}
		
		return sizes;
	}
	
	private int[][] tryAndMerge(int[][] cellX, int[] sizes) {
		for(int i = 0; i < sizes.length; i++) {
			if(sizes[i] > MAX_CELLS_MERGE) continue; //Don't merge regions if they're larger than X cells
			
			for(int row = 0; row < cellX.length; row++) {
				for(int col = 0; col < cellX[0].length; col++) {
					if(cellX[row][col] != i) continue;
					
					
					for(int r2 = -MAX_SEARCH_SPACE; r2 <= MAX_SEARCH_SPACE; r2++) {
						for(int c2 = -MAX_SEARCH_SPACE; c2 <= MAX_SEARCH_SPACE; c2++) {
							
							if(row + r2 < 0 || row + r2 >= cellX.length) break;
							if(col + c2 < 0 || col + c2 >= cellX[0].length) continue;
							
							int id = cellX[row + r2][col + c2];
							
							if(id != -1 && sizes[id] > sizes[i]) {
								cellX = merge(cellX, id, i);
							}
						}
					}
				}
			}
		}
		
		return cellX;
	}
	
	private int[][] merge(int[][] cellX, int id, int id2) {
		for(int i = 0; i < cellX.length; i++) {
			for(int j = 0; j < cellX[0].length; j++) {
				if(cellX[i][j] == id2) {
					cellX[i][j] = id;
				}
			}
		}
		
		return cellX;
	}
	
	public boolean[][] findRainShadows(BufferedImage map) {
		int height = map.getHeight();
		int width = map.getWidth();
		
		if(height*2 != width) {
			//Should throw exception because map is not equirectangular
			return null;
		}
		
		boolean[][] rainshadow = new boolean[height][width];
		
		rainshadow = findRainShadowsInBand(map, rainshadow, 0, height*1/6, false);
		rainshadow = findRainShadowsInBand(map, rainshadow, height*1/6, height*2/6, true);
		rainshadow = findRainShadowsInBand(map, rainshadow, height*2/6, height*3/6, false);
		rainshadow = findRainShadowsInBand(map, rainshadow, height*3/6, height*4/6, false);
		rainshadow = findRainShadowsInBand(map, rainshadow, height*4/6, height*5/6, true);
		rainshadow = findRainShadowsInBand(map, rainshadow, height*5/6, height, false);
		
		//rainshadow = softenRainshadow(rainshadow);
		
		return rainshadow;
	}
	
	private boolean[][] findRainShadowsInBand(BufferedImage map, boolean[][] rainshadow, int top, int bottom, boolean eastwards) {
		int height = map.getHeight();
		int width = map.getWidth();
		
		int inc = eastwards ? 1 : -1;
		int start = eastwards ? 0 : width - 1;
		
		for(int r = top; r < bottom; r++) {
			
			int c = start;
			
			int currElev = 0;
			int maxElev = 0;
			int rainshadowCounter = 0;
			
			for(int i = 0; i < width; i++) {
				
				int elevation = new Color(map.getRGB(c, r)).getRed();
				
				//If water
				if(elevation < 90) {
					rainshadowCounter = Math.max(0, rainshadowCounter - WATER_RAINSHADOW_DEC);
					maxElev = elevation;
				} else {
					rainshadowCounter = Math.max(0, rainshadowCounter - LAND_RAINSHADOW_DEC);
					
					maxElev = Math.max(maxElev, elevation);
					
					if(elevation < maxElev - 30 && elevation < currElev && currElev >= 110) {
						rainshadowCounter = RAINSHADOW_INC;
					}
				}
				
				if(rainshadowCounter > 0) {
					rainshadow[r][c] = true;
				}
				
				currElev = elevation;
				c += inc;
			}
		}
		
		return rainshadow;
	}
	
	/*
	private boolean[][] softenRainshadow(boolean[][] rainshadow) {
		
	}*/
}
