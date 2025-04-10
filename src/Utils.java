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

    public static void fillBlock(int[][][] block, int x, int y, int width, int height, double[] color) {
        int[] colorInt = new int[3];
        for (int k = 0; k < 3; k++) {
            colorInt[k] = (int) Math.round(color[k]);
        }
        for (int i = y; i < y + height; i++) {
            for (int j = x; j < x + width; j++) {
                for (int k = 0; k < 3; k++) {
                    block[i][j][k] = colorInt[k];
                }
            }
        }
    }
}
