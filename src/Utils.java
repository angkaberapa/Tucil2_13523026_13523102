public class Utils {
    public static double[] averageRGB(int[][][] block, int x, int y, int width, int height) {
        double[] mean = new double[3];
        int size = width * height;

        for (int i = y; i < y + height; i++) {
            for (int j = x; j < x + width; j++) {
                for (int k = 0; k < 3; k++) {
                    mean[k] += block[i][j][k];
                }
            }
        }

        for (int k = 0; k < 3; k++) {
            mean[k] /= size;
        }

        return mean;
    }

    public static void fillBlock(int[][][] block, int x, int y, int width, int height, double[] color, int[][][] gif) {
        int[] colorInt = new int[3];
        for (int k = 0; k < 3; k++) {
            colorInt[k] = (int) Math.round(color[k]);
        }
        for (int i = y; i < y + height; i++) {
            for (int j = x; j < x + width; j++) {
                for (int k = 0; k < 3; k++) {
                    block[i][j][k] = colorInt[k];
                    if (gif != null) {
                        gif[i][j][k] = colorInt[k];
                    }
                }
            }
        }
    }

    // Helper: copy array 3D
    public static void copyImage(int[][][] from, int[][][] to) {
        for (int i = 0; i < from.length; i++) {
            for (int j = 0; j < from[0].length; j++) {
                for (int k = 0; k < 3; k++) {
                    to[i][j][k] = from[i][j][k];
                }
            }
        }
    }

    public static int totalNodePrediction(double complexity, int minBlockSize, double threshold){
        double temp = 1;
        double min = minBlockSize;
        temp = complexity*(ImageData.originalSize/min)*(1/threshold+1e5);
        int total = (int) Math.round(temp);
        return total;
    }

    public static int basePrediction(int totalNodes){
        if (totalNodes <= 300){
            return 1;
        } else {
            int temp = Math.round(totalNodes/300);
            if (temp*4 <=300){
                return temp;
            }
            return temp/4;
        }
    }
}
