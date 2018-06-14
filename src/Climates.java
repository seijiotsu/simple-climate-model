import java.awt.Color;

public enum Climates {
		
		
	    TROPICALWETFOREST(117, 146, 60),
	    TROPICALDRYFOREST(204, 255, 51),
	    SUBTROPICALWOODLANDS(252, 213, 180),
	    TROPICALWOODLANDS(255, 251, 193),
	    SUBTROPICALSCRUB(204, 204, 0),
	    EXTREMEDESERT(255, 0, 0),
	    DESERT(155, 153, 0),
	    SUBTROPICALDRYFOREST(215, 228, 188),
	    SUBTROPICALWETFOREST(0, 176, 80),
	    SUBTROPICALFOREST(102, 255, 102),
	    MEDITERRANEAN(217, 151, 149),
	    STEPPE(148, 139, 84),
	    TEMPERATEWOODLANDS(242, 221, 220),
	    TEMPERATEFOREST(219, 238, 243),
	    TEMPERATEWETFOREST(147, 205, 221),
	    BOREALFOREST(157, 177, 149),
	    TUNDRA(191, 191, 191),
	    WETTUNDRA(204, 192, 218),
	    ICECAP(255, 255, 255),
	    TROPICALSCRUB(255, 255, 0),
	    COOLDESERT(149, 55, 53),
	    POLARDESERT(90, 90, 90),
		OCEAN(182, 220, 244);
		
		/*
		 * RGB values of the climates
		 */
		private final int RGB; 
		Climates(int r, int g, int b) {
			this.RGB = new Color(r, g, b).getRGB();
		}
		
		public int val() {
			return RGB;
		}
	}