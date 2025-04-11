public class TargetCompression {
    private static double left = 0;
    private static double right = 48768.75; // max error
    private static int[][][] closestImageRGB;
    private static int[][][] tempImageRGB;
    private static double closestCompressionRate = 100;
    private static double tempCompressionRate;
    private static double mostOptimalThreshold;
    private static int tempTotalNodes;
    private static int tempMaxDepth;
    private static int iteration = 0;

    public static void compressToTargetPercentage(){
        // target compression adalah ImageData.targetCompressionRate
        left = 0;
        right = 48768.75; 
        closestImageRGB = new int[ImageData.imageHeight][ImageData.imageWidth][3];
        tempImageRGB = new int[ImageData.imageHeight][ImageData.imageWidth][3];
        Utils.copyImage(ImageData.imageRGB, tempImageRGB);
        ImageData.totalNodes = 0;
        ImageData.maxDepth = 0;

        // Binary search 
        while (left <= right) {
            iteration++;
            // reset ImageData.imageRGB
            Utils.copyImage(tempImageRGB, ImageData.imageRGB);
            double mid = (left + right) / 2;
            ImageData.threshold = mid;

            // Kompresi gambar sesuai threshold
            QuadTreeNode root = new QuadTreeNode(0, 0, ImageData.imageWidth, ImageData.imageHeight);
            root.divide(0);
            ImageData.saveImage("temporary.jpg");

            // Hasil kompresi
            tempCompressionRate = 100.0 * (1 - ((double) ImageData.compressedSize / ImageData.originalSize));
            System.out.println("Iterasi " + iteration + ":");
            System.out.println("Compression Rate: " + tempCompressionRate + "%");
            System.out.println("Threshold: " + ImageData.threshold);

            // Mencari hasil dengan target kompresi terdekat
            if (Math.abs(tempCompressionRate - ImageData.targetCompressionRate) < Math.abs(closestCompressionRate - ImageData.targetCompressionRate)) {
                closestCompressionRate = tempCompressionRate;
                mostOptimalThreshold = ImageData.threshold;
                tempTotalNodes = ImageData.totalNodes;
                tempMaxDepth = ImageData.maxDepth;
                Utils.copyImage(ImageData.imageRGB, closestImageRGB);
            }

            if (tempCompressionRate < ImageData.targetCompressionRate) {
                // treshold >> 
                left = mid + 1;
            } else { // treshold <<
                right = mid - 1;
            }
        }
        // simpan closest to ImageData.imageRGB
        Utils.copyImage(closestImageRGB, ImageData.imageRGB);
        ImageData.threshold = mostOptimalThreshold;
        ImageData.totalNodes = tempTotalNodes;
        ImageData.maxDepth = tempMaxDepth;
    }
}
