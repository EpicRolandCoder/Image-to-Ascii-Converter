import java.awt.image.BufferedImage;
//Buffered Image Class from the .awt.image library
import java.io.File;
//File Class from the IO library
import java.io.FileNotFoundException;
import java.io.IOException;
//exceptions for error handling
import javax.imageio.ImageIO;
//library to convert File object into Buffered Image object
public class ImageToAscii {
    public static void main(String[] args){
        try{
            File imagePath = new File(args[0]);
            //turn the file path into a file object
            BufferedImage imageBuffered = ImageIO.read(imagePath);
            //read the file object and create a buffered image object base on it
            int width = imageBuffered.getWidth();
            int height = imageBuffered.getHeight();
            //width and height of bufferedImage allows for iteration over each pixel
            String AsciiCharacters = "";
            //AsciiCharacters variable is initialised
            if (args[1].equals("light")){
                AsciiCharacters = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"^`'";
                //ascii characters arranged from most character space to least
            }
            else if (args[1].equals("dark")){
                AsciiCharacters = "'`^\",:;Il!i><~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$";
                //ascii characters arranged from least character space to most
            }
            else {
                throw new IndexOutOfBoundsException();
                //if the user has not specified a background color, throw this exception
            }

            for (int h = 0; h < height; h++){
                for (int w = 0; w< width; w++) {
                    int pixelColour = imageBuffered.getRGB(w, h);
                    //the rgb value of a pixel at a specified coordinates are set to an integer value
                    int greyScaleValue = (int) (((pixelColour >> 16)& 0xFF) * 0.299 + ((pixelColour >> 8)& 0xFF) * 0.587 + (pixelColour & 0xFF) * 0.114);
                    // a pixel's greyscale value can be represented as an integer
                    //the >> bitwise shifts the RGB integer value the specified number of positions to the right.
                    //the & 0xFF operation extracts the lowest 8 bits of the integer value
                    //Extracting the lowest 8 bits after: shifting 16 times provides the red value, shifting 8 times provides the green value, shifting no times provides the red value.
                    //multiplying each individual R G B value by a weighted sum formula will provide the integer value of a pixel's greyscale


                    int mappingIndex = (greyScaleValue* (AsciiCharacters.length()-1)) / 255;
                    //since the grayscale value is between 1 and 255, multiplying it by the character list will roughly rank it on the scale of greyscale to other pixels
                    char character = (char) AsciiCharacters.charAt(mappingIndex);
                    //appropriate character index selected to print out

                    //since characters are roughly 3 times as long as wide, print out 3 times.
                    System.out.print(character);
                    System.out.print(character);
                    System.out.print(character);
                }
                System.out.println();
                //new line for the new width of pixels
            }

            //exception catching
        } catch (IndexOutOfBoundsException e){
            e.printStackTrace();
            System.out.println("Enter a file as an argument THEN Specify the background colour as 'light' or 'dark' as another argument");
        } catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println("File not found");
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("File is not an image (png for best results)");
        }
    }
}