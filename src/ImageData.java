import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageData {
    // Image data
    public static int[][][] imageRGB;
    public static int imageWidth;
    public static int imageHeight;

    // Parameter input
    public static double threshold;
    public static int minBlockSize;
    public static int methodErrorCalculation; // 1: variance, 2: MAD, 3: max diff, 4: entropy, 5: SSIM
    public static double targetCompressionRate; // 0 = no target, 1.0 = 100% (no compression), 0.5 = 50% (half size), etc.

    // Statistik hasil kompresi
    public static int totalNodes = 0;
    public static int maxDepth = 0;
    public static double originalSize;
    public static double compressedSize;

    public static int[][][]imageGIF;
    public static int nodePrediction;
    public static int base;

    public static void loadImage(String path) {
        try {
            path = "test/" + path;
            File file = new File(path);
            originalSize = file.length() / 1024; // in kB
            BufferedImage image = ImageIO.read(file);
            imageWidth = image.getWidth();
            imageHeight = image.getHeight();
            imageRGB = new int[imageHeight][imageWidth][3];
            imageGIF = new int[imageHeight][imageWidth][3];

            for (int y = 0; y < imageHeight; y++) {
                for (int x = 0; x < imageWidth; x++) {
                    int pixel = image.getRGB(x, y);
                    imageRGB[y][x][0] = (pixel >> 16) & 0xFF; // R
                    imageRGB[y][x][1] = (pixel >> 8) & 0xFF;  // G
                    imageRGB[y][x][2] = (pixel) & 0xFF;       // B
                }
            }

        } catch (IOException e) {
            System.out.println("Gagal membaca file gambar: " + e.getMessage());
        }
    }
    public static void saveImage(String path) {
        BufferedImage output = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                int r = imageRGB[y][x][0];
                int g = imageRGB[y][x][1];
                int b = imageRGB[y][x][2];
                int rgb = (r << 16) | (g << 8) | b;
                output.setRGB(x, y, rgb);
            }
        }

        try {
            File outputFile = new File(path);
            ImageIO.write(output, "jpg", outputFile);
            System.out.println("Gambar berhasil disimpan ke: " + path);
            compressedSize = outputFile.length() / 1024; // in kB
        } catch (IOException e) {
            System.out.println("Gagal menyimpan gambar: " + e.getMessage());
        }
    }
  
    public static BufferedImage convertImage(int[][][] image){
        BufferedImage output = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                int r = image[y][x][0];
                int g = image[y][x][1];
                int b = image[y][x][2];
                int rgb = (r << 16) | (g << 8) | b;
                output.setRGB(x, y, rgb);
            }
        }
        return output;
    }
}
