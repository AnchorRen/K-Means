import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

/**
 * A Modification of the Picture Class from CS10 by Scot Drysdale
 * 
 * Diksha Gautham - Lab 1 
 * 
 * Copyright Georgia Institute of Technology 2004-2008
 * @author Barbara Ericson ericson@cc.gatech.edu
 * Modified by Scot Drysdale to eliminate some warnings.
 * 
 * Modified by Diksha Gautham for Lab 1 to add picture mapping methods (all methods after "convolve method")  
 */


public class Picture extends SimplePicture { 

  ///////////////////// constructors //////////////////////////////////
  
  /**
   * Constructor that takes no arguments 
   */
  public Picture () {	
    
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
  }
  
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName) {
    // let the parent class handle this fileName
    super(fileName);
  }
  
  /**
   * Constructor that takes the width and height
   * @param width the width of the desired picture
   * @param height the height of the desired picture
   */
  public Picture(int width, int height) {
    // let the parent class handle this width and height
    super(width,height);
  }
  
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   */
  public Picture(Picture copyPicture) {
    // let the parent class do the copy
    super(copyPicture);
  }
  
  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image) {
    super(image);
  }
  
  ////////////////////// methods ///////////////////////////////////////
  
  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString() {
    String output = "Picture, filename " + getFileName() + 
      " height " + getHeight() 
      + " width " + getWidth();
    return output;
    
  }
  
   /**
   * Class method to let the user pick a file name and then create the picture 
   * and show it
   * @return the picture object
   */
  public static Picture pickAndShow() {
    String fileName = FileChooser.pickAFile();
    Picture picture = new Picture(fileName);
    picture.show();
    return picture;
  }
  
  /**
   * Class method to create a picture object from the passed file name and 
   * then show it
   * @param fileName the name of the file that has a picture in it
   * @return the picture object
   */
  public static Picture showNamed(String fileName) {
    Picture picture = new Picture(fileName);
    picture.show();
    return picture;
  }
  
  /**
   * A method create a copy of the current picture and return it
   * @return the copied picture
   */
  public Picture copy()
  {
    return new Picture(this);
  }
  
  /**
   * Method to increase the red in a picture.
   */
  public void increaseRed() {
    Pixel [] pixelArray = this.getPixels();
    for (Pixel pixelObj : pixelArray) {
      pixelObj.setRed(pixelObj.getRed()*2);
    }
  }
  
  /**
   * Method to negate a picture
   */
  public void negate() {
    Pixel [] pixelArray = this.getPixels();
    int red,green,blue;
    
    for (Pixel pixelObj : pixelArray) {
      red = pixelObj.getRed();
      green = pixelObj.getGreen();
      blue = pixelObj.getBlue();
      pixelObj.setColor(new Color(255-red, 255-green, 255-blue));
    }
  }
  
  /**
   * Method to flip a picture 
   */
  public Picture flip() {
    Pixel currPixel = null;
    Pixel targetPixel = null;
    Picture target = 
      new Picture(this.getWidth(),this.getHeight());
    
    for (int srcX = 0, trgX = getWidth()-1; 
         srcX < getWidth();
         srcX++, trgX--) {
      for (int srcY = 0, trgY = 0; 
           srcY < getHeight();
           srcY++, trgY++) {
        
        // get the current pixel
        currPixel = this.getPixel(srcX,srcY);
        targetPixel = target.getPixel(trgX,trgY);
        
        // copy the color of currPixel into target
        targetPixel.setColor(currPixel.getColor());
      }
    }
    return target;
  }
  
  /**
   * Method to decrease the red by half in the current picture
   */
  public void decreaseRed() {
  
    Pixel pixel = null; // the current pixel
    int redValue = 0;       // the amount of red

    // get the array of pixels for this picture object
    Pixel[] pixels = this.getPixels();

    // start the index at 0
    int index = 0;

    // loop while the index is less than the length of the pixels array
    while (index < pixels.length) {

      // get the current pixel at this index
      pixel = pixels[index];
      // get the red value at the pixel
      redValue = pixel.getRed();
      // set the red value to half what it was
      redValue = (int) (redValue * 0.5);
      // set the red for this pixel to the new value
      pixel.setRed(redValue);
      // increment the index
      index++;
    }
  }
  
  /**
   * Method to decrease the red by an amount
   * @param amount the amount to change the red by
   */
  public void decreaseRed(double amount) {
 
    Pixel[] pixels = this.getPixels();
    Pixel p = null;
    int value = 0;

    // loop through all the pixels
    for (int i = 0; i < pixels.length; i++) {
 
      // get the current pixel
      p = pixels[i];
      // get the value
      value = p.getRed();
      // set the red value the passed amount time what it was
      p.setRed((int) (value * amount));
    }
  }
  
  /**
   * Method to compose (copy) this picture onto a target picture
   * at a given point.
   * @param target the picture onto which we copy this picture
   * @param targetX target X position to start at
   * @param targetY target Y position to start at
   */
  public void compose(Picture target, int targetX, int targetY) {
 
    Pixel currPixel = null;
    Pixel newPixel = null;

    // loop through the columns
    for (int srcX=0, trgX = targetX; srcX < this.getWidth();
         srcX++, trgX++) {
  
      // loop through the rows
      for (int srcY=0, trgY=targetY; srcY < this.getHeight();
           srcY++, trgY++) {

        // get the current pixel
        currPixel = this.getPixel(srcX,srcY);

        /* copy the color of currPixel into target,
         * but only if it'll fit.
         */
        if (trgX < target.getWidth() && trgY < target.getHeight()) {
          newPixel = target.getPixel(trgX,trgY);
          newPixel.setColor(currPixel.getColor());
        }
      }
    }
  }
  
  /**
   * Method to scale the picture by a factor, and return the result
   * @param factor the factor to scale by (1.0 stays the same,
   *    0.5 decreases each side by 0.5, 2.0 doubles each side)
   * @return the scaled picture
   */
  public Picture scale(double factor) {
    
    Pixel sourcePixel, targetPixel;
    Picture canvas = new Picture(
                                 (int) (factor*this.getWidth())+1,
                                 (int) (factor*this.getHeight())+1);
    // loop through the columns
    for (double sourceX = 0, targetX=0;
         sourceX < this.getWidth();
         sourceX+=(1/factor), targetX++) {
      
      // loop through the rows
      for (double sourceY=0, targetY=0;
           sourceY < this.getHeight();
           sourceY+=(1/factor), targetY++) {
        
        sourcePixel = this.getPixel((int) sourceX,(int) sourceY);
        targetPixel = canvas.getPixel((int) targetX, (int) targetY);
        targetPixel.setColor(sourcePixel.getColor());
      }
    }
    return canvas;
  }
  
  /**
   * Method to do chromakey using an input color for the background
   * and a point for the upper left corner of where to copy
   * @param target the picture onto which we chromakey this picture
   * @param bgColor the color to make transparent
   * @param threshold within this distance from bgColor, make transparent
   * @param targetX target X position to start at
   * @param targetY target Y position to start at
   */
  public void chromakey(Picture target, Color bgColor, int threshold,
                        int targetX, int targetY) {
 
    Pixel currPixel = null;
    // loop through the columns
    for (int srcX=0, trgX=targetX;
        srcX<getWidth() && trgX<target.getWidth();
        srcX++, trgX++) {

      // loop through the rows
      for (int srcY=0, trgY=targetY;
        srcY<getHeight() && trgY<target.getHeight();
        srcY++, trgY++) {

        // get the current pixel
        currPixel = this.getPixel(srcX,srcY);

        /* if the color at the current pixel is within threshold of
         * the input color, then don't copy the pixel
         */
        if (currPixel.colorDistance(bgColor)>threshold) {
          target.getPixel(trgX,trgY).setColor(currPixel.getColor());
        }
      }
    }
  }
  
    /**
   * Method to do chromakey assuming a blue background 
   * @param target the picture onto which we chromakey this picture
   * @param targetX target X position to start at
   * @param targetY target Y position to start at
   */
  public void blueScreen(Picture target,
                        int targetX, int targetY) {

    Pixel currPixel = null;
    // loop through the columns
    for (int srcX=0, trgX=targetX;
         srcX<getWidth() && trgX<target.getWidth();
         srcX++, trgX++) {

      // loop through the rows
      for (int srcY=0, trgY=targetY;
           srcY<getHeight() && trgY<target.getHeight();
           srcY++, trgY++) {

        // get the current pixel
        currPixel = this.getPixel(srcX,srcY);

        /* if the color at the current pixel mostly blue (blue value is
         * greater than red and green combined), then don't copy pixel
         */
        if (currPixel.getRed() + currPixel.getGreen() > currPixel.getBlue()) {
          target.getPixel(trgX,trgY).setColor(currPixel.getColor());
        }
      }
    }
  }
  
  /**
   * Method to change the picture to gray scale with luminance
   */
  public void grayscaleWithLuminance()
  {
    Pixel[] pixelArray = this.getPixels();
    Pixel pixel = null;
    int luminance = 0;
    double redValue = 0;
    double greenValue = 0;
    double blueValue = 0;

    // loop through all the pixels
    for (int i = 0; i < pixelArray.length; i++)
    {
      // get the current pixel
      pixel = pixelArray[i];

      // get the corrected red, green, and blue values
      redValue = pixel.getRed() * 0.299;
      greenValue = pixel.getGreen() * 0.587;
      blueValue = pixel.getBlue() * 0.114;

      // compute the intensity of the pixel (average value)
      luminance = (int) (redValue + greenValue + blueValue);

      // set the pixel color to the new color
      pixel.setColor(new Color(luminance,luminance,luminance));

    }
  }
  
  /** 
   * Method to do an oil paint effect on a picture
   * @param dist the distance from the current pixel 
   * to use in the range
   * @return the new picture
   */
  public Picture oilPaint(int dist) {
    
    // create the picture to return
    Picture retPict = new Picture(this.getWidth(),this.getHeight());
    
    // declare pixels
    Pixel currPixel = null;
    Pixel retPixel = null;
    
    // loop through the pixels
    for (int x = 0; x < this.getWidth(); x++) {
      for (int y = 0; y < this.getHeight(); y++) {
        currPixel = this.getPixel(x,y);
        retPixel = retPict.getPixel(x,y);
        retPixel.setColor(currPixel.getMostCommonColorInRange(dist));
      }
    }
    return retPict;
  }
  
  /***********
   * Methods added by Scot Drysdale to demonstrate ArrayLists
   **********/
 
 /**
  * Reduces the number of colors to 8 by picking two values for red,
  * two for green, and two for blue.  The two red values chosen are the
  * average of the pixel red value that are greater than a threshold
  * and the average of the pixel red values less than or equal to the threshold.
  * The same is done for green and blue
  */
 public void reduceTo8() {
   Pixel [] pixelArray = this.getPixels();  // Array of all pixels in the image
   final int THRESHOLD = 126;     // Dividing line between low and high color values
   
   for(int colorNum = 1; colorNum <= 3; colorNum++) {
     ArrayList<Pixel> lowValues = new ArrayList<Pixel>();
     ArrayList<Pixel> highValues = new ArrayList<Pixel>();
     
     for(Pixel pixel : pixelArray) {
       // Split the pixels into low and high color values for color colorNum
       if(getColor(pixel, colorNum) <= THRESHOLD)
         lowValues.add(pixel);
       else
         highValues.add(pixel);
     } 
       
     int lowAve = Math.round(averageColors(lowValues, colorNum));
     int highAve = Math.round(averageColors(highValues, colorNum));
       
     // Reset the color values to the average values
     for (Pixel lowPix : lowValues)
       setColor(lowPix, lowAve, colorNum);
     for (Pixel highPix : highValues)
       setColor(highPix, highAve, colorNum);                                 
   }
 }
 
 /**
  * Gets the value of the color corresponding to colorNum.
  * In an ideal world this would be added to the Pixel class
  * Precondition - colorNum is 1, 2, or 3.  (We will learn to throw exceptions later.)
  * 
  * @param pixel the pixel whose color is returned
  * @param colorNum the color to choose: 1 = red, 2 = green, 3 = blue
  */
 public static int getColor(Pixel pixel, int colorNum) {
   if(colorNum == 1)
     return pixel.getRed();
   else if (colorNum == 2)
     return pixel.getGreen();
   else
     return pixel.getBlue();
 }
 
   /**
  * Sets the value of the color corresponding to colorNum to newValue.
  * In an ideal world this would be added to the Pixel class
  * Precondition - colorNum is 1, 2, or 3.  (We will learn to throw exceptions later.)
  * 
  * @param pixel the pixel whose color is set
  * @param newValue the new value for the color
  * @param colorNum the color to choose: 1 = red, 2 = green, 3 = blue
  */
 public static void setColor(Pixel pixel, int newValue, int colorNum) {
   if(colorNum == 1)
     pixel.setRed(newValue);
   else if (colorNum == 2)
     pixel.setGreen(newValue);
   else
     pixel.setBlue(newValue);
 }
 
 /**
  * Averages the chosen color for all the pixels in an ArrayList.
  * Returns 0 if ArrayList is empty.
  * Precondition - colorNum is 1, 2, or 3.  (We will learn to throw exceptions later.)
  * 
  * @param pixels the list of pixels to be averaged
  * @param colorNum the color to average: 1 = red, 2 = green, 3 = blue
  * @return the average of the chosen color value
  */
 public static float averageColors(ArrayList<Pixel> pixels, int colorNum) {
   float sum = 0.0f;
   
   for(int i = 0; i < pixels.size(); i++)
     sum += getColor(pixels.get(i), colorNum);
   
   if(pixels.size() > 0)
     return sum/pixels.size();
   else
     return 0.0f;
 }
  
 /* 
  * End of additional methods added by Scot Drysdale.
  */
 
 /**
  * DIKSHA GAUTHAM EDITS: 
  * Produces a pixel whose color is the weighted sum
  * of its color and the colors of the surrounding pixels
  * 
  */
 public void convolveOnePixel(Picture pic, double [][] matrix, Pixel center, int x, int y)  {

	// Initialize the sums of the weights
	int sum_red = 0;
	int sum_green = 0;
	int sum_blue = 0;
		
	// Get all the pixels around the center pixel
	for (int i=(x-1); i <= x+1; i++ ) {
		for (int j=(y-1); j <= y+1; j++ ) {
			Pixel neighbor = pic.getPixel(i, j);

			int neighbor_red = neighbor.getRed();
			int neighbor_green = neighbor.getGreen();
			int neighbor_blue = neighbor.getBlue();
			
			double coefficient = matrix[i+1-x][j+1-y];
			sum_red += neighbor_red * coefficient; 
			sum_green += neighbor_green * coefficient; 
			sum_blue += neighbor_blue * coefficient; 		

		}
	}
	center.setRed(sum_red);
	center.setGreen(sum_green);
	center.setBlue(sum_blue);

 }
 
 
 /**
 * Convolves all the pictures in the image except the ones on the edge
 */

 public Picture convolve(double [][] matrix, String title) {	 
	 // Make a new picture, which is a copy of the picture passed in 
	 Picture target = new Picture(this);
	 	 
	 // Loop through all the pixel locations in the image that are NOT on the edge 
	 for (int x = 1; x < target.getWidth()-1; x++) {
		 for (int y = 1; y < target.getHeight()-1; y++) {
			 
			 // Get the pixel at that location
			 Pixel pixelToConvolute = target.getPixel(x,y);
			 
			 // Convolve each pixel
			 this.convolveOnePixel(this, matrix, pixelToConvolute, x, y);	 
		 }
	}
   target.setTitle(title); 
   return target;
        
 }
 
 
 /**
 * HELPER METHOD to mapToColorList
 * Returns the INDEX of the color in colorList that is closest to the pixel color 
 * 
 */

 	public int getClosestColor(ArrayList<Color> colorList, Color colorToCompare) {
 		
 		// initialize the closest color and distance 
 		int closestColorIndex = 0; 
 		double closestDistance = 1000000; //initialize distance at something big or infinity
 		
 		// loop through all the elements in "colors"
    for (Color x : colorList) {
    // compute the distances
   	// if the distance is greater than closestDistance, replace closestColorIndex with the index of the color 
    	double distance = Pixel.colorDistance(x, colorToCompare);
    	if (distance < closestDistance) {
    		closestDistance = distance; 
    		closestColorIndex = colorList.indexOf(x);
    	}
    }
 				
 		// return the index of the closest color
 		return closestColorIndex;
 	}
 	
  /**
 	 * HELPER METHOD to computeColors to find the "centroid" colors 
 	 * Returns an average color in a an arrayList of colors
 	 */
 	
 	public Color getAverage(ArrayList<Color> arrayList) {
    int n = arrayList.size();
    int sum_red =0;
    int sum_green=0;
    int sum_blue=0;
    int average_red;
    int average_green;
    int average_blue;
    
    if (arrayList == null || arrayList.isEmpty()) {
    	average_red = 0;
    	average_green = 0;
    	average_blue = 0;
    }
    
    else {
      // for every color in the array
      for (int i = 0; i < n; i++) {
        sum_red += arrayList.get(i).getRed();
        sum_green += arrayList.get(i).getGreen();
        sum_blue += arrayList.get(i).getBlue();
      }
      average_red = sum_red/n; 
      average_green = sum_green/n;       		
      average_blue = sum_blue/n;     	
    }
 		return new Color(average_red, average_green, average_blue);
 	}
 	
  /**
 	* HELPER METHOD to generate a random color list of specified length
 	* @param length is the list length
 	*/
 	
 	public ArrayList<Color> generateRandomColorList(int length) {
		
 		Random rand = new Random(); 
 		
 		ArrayList<Color> random_colors = new ArrayList<Color>();
 		
 		for(int i = 0; i < length; i++) {
 			int random_red = rand.nextInt(256);
 			int random_green = rand.nextInt(256);
 			int random_blue = rand.nextInt(256);
 	 		random_colors.add(new Color(random_red, random_green, random_blue));
 		}
 		return random_colors; 		
}
 	
  /**
 	* Returns the first k unique colors in an image
 	*/
 	public ArrayList<Color> generateUniqueColors(int k) {
 		
 		ArrayList<Color> kUniqueColors = new ArrayList<Color>();
 		
 		// go through each pixel
 		 for (int x = 0; x < this.getWidth(); x++) {
			 for (int y = 0; y < this.getHeight(); y++) {
				 
				 // get the pixel color 
				 Color pixelColor = this.getPixel(x,y).getColor();
		 	
				 // if it's unique, add it to the kUniqueColors array
				 // when the array is of size k, return the complete array
				 if (isUnique(kUniqueColors, pixelColor)) {
					 kUniqueColors.add(pixelColor);
					 if (kUniqueColors.size() == k) {
					 		return kUniqueColors;						 
				 }
			 }
 		 }
 		} 
 		 // if no unique colors, return however many colors we find 
 		 return kUniqueColors;
 	}
	
  /**
	 * HELPER METHOD 
	 * Returns "true" if a color is unique when compared to a list of colors
	 * @param colorList and pixelColor to compare against
	 */
 	
 	public boolean isUnique(ArrayList<Color> colorList, Color pixelColor) {
 		for(Color color: colorList) {
 			if (pixelColor.equals(color)) {
 				return false; 
 			}
 		}
 		return true; 
 	}

 	
  /**
	 	 * Returns an arrayList of the best colors to use, calculated with k-means
	 	 * @param number is the number of colors in the picture 
	 	 */  
		 
	 	public ArrayList<Color> computeColors(int number) {
	 	ArrayList<Color> newColors = generateRandomColorList(number); // use if using a random list of colors 
//	 		ArrayList<Color> newColors = generateUniqueColors(number); // use if using k unique colors 
	
	 		ArrayList<Color> currentColors;
	 		
			// initialize clusters, and add the correct number of empty clusters to the clusters ArrayList
	 		ArrayList<ArrayList<Color>> clusters = new ArrayList<ArrayList<Color>>();
			for (int i=0; i < number; i++) {
				clusters.add(new ArrayList<Color>());
			}
				
	 		do { 	
	 			// clear and reset the current and new clusters
	 			currentColors = newColors;	
	 			newColors = new ArrayList<Color>(number);
	
	 			// for each pixel in the picture 
				for (int x = 0; x < this.getWidth(); x++) {
				for (int y = 0; y < this.getHeight(); y++) {
					
					// get its color
					Color pixelColor = this.getPixel(x, y).getColor();
					// get the index of the closest color in the current colorlist
					int colorIndex = getClosestColor(currentColors, pixelColor);
					// add the pixel color to the cluster with that index 
					clusters.get(colorIndex).add(pixelColor);
			 		}  			
				}
				// Now, go through every cluster					
					for(int i=0; i < clusters.size(); i++) {
						
					// if the cluster is empty, drop it 
					if (clusters.get(i).isEmpty()) {
						clusters.remove(i);
					}
					// otherwise, get its average (the "centroid" color)
					else {
					Color centroidColor = getAverage(clusters.get(i));
					// add it to the newColors
					newColors.add(centroidColor);
					}
				}
				
	 		} while (!(currentColors.equals(newColors)));
	 		return newColors;
	 	}

	/**
	 * Returns a new picture where each pixel in the original picture 
	 * is replaced by the color in @param colors that's nearest to it
	 * @param title is the title of the image 
	 */
	 
	 public Picture mapToColorList(ArrayList<Color> colors, String title) {
		 Picture target = new Picture(this);
		 
	 	// loop through all the pixel locations
		 for (int x = 0; x < target.getWidth(); x++) {
			 for (int y = 0; y < target.getHeight(); y++) {
				 
				 // get the pixel we are changing 
				 Pixel pixelToChange = target.getPixel(x, y);
	
				 // get its color
				 Color pixelToChangeColor =  pixelToChange.getColor();
						 
				 // get the index of the closest color in the list
				 int closestColorIndex = getClosestColor(colors, pixelToChangeColor);
				 Color closestColor = colors.get(closestColorIndex);
				 
				 // replace the pixel color with that color 
				 pixelToChange.setColor(closestColor);
	
			 	}
			 }
	   target.setTitle(title); 
	   return target;
	
	 }

	/**
 	* Method which calls computeColors and passes the result to mapToColorList
 	*/
 	public Picture reduceColors(int number, String title) {
 		return mapToColorList(computeColors(number), title);
 	}
 	
 	
  public static void main(String[] args) {
  	
// 		RANDOM COLOR LIST: 
//  	ArrayList<Color> random_colors = generateRandomColorList(256);
 
//  	MANUALLY SELECTED COLOR LIST: 	
//		ArrayList<Color> colors = new ArrayList<Color>;
//  	colors.add(new Color(168,206,229)); // light blue 
//  	colors.add(new Color(0,0,0)); // black
//  	colors.add(new Color(156, 158, 95)); //dark green
//  	colors.add(new Color(235, 212, 168)); // sand color 
//  	colors.add(new Color(99, 114, 147)); //water color
//  	colors.add(new Color(118, 137, 151)); //mountain
//  	colors.add(new Color(67, 44, 26)); //darker sand color 
//  	colors.add(new Color(235, 235, 235 )); // whiteish
  	
  	Picture p = new Picture(FileChooser.pickAFile());
  	p.explore();
  	
  	Picture q = p.reduceColors(256, "k-means,  256 unique colors");
  	q.explore();
  }
  
  
} // this } is the end of class Picture, put all new methods before this
 
