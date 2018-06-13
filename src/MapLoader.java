import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MapLoader {
	private File in;
	
	public MapLoader(File in) {
		this.in = in;
	}
	
	public BufferedImage load() {
		try {
			BufferedImage map = ImageIO.read(in);
			return map;
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
