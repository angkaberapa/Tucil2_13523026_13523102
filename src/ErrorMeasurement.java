public class ErrorMeasurement {
    public static double calculateError(int[][][] block, int x, int y, int width, int height, int method, double[] mean) {
        return switch (method) {
            case 0 -> variance(block, x, y, width, height, mean);
            case 1 -> mad(block, x, y, width, height, mean);
            case 2 -> maxDiff(block, x, y, width, height);
            case 3 -> entropy(block, x, y, width, height);
            // case 4 -> // ssim(block, x, y, width, height);
            default -> 0;
        };
    }

    private static double variance(int[][][] block, int x, int y, int width, int height, double[] mean) {
        int size = width * height;

        double var_rgb = 0;
        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                for (int k = 0; k < 3; k++) {
                    var_rgb += Math.pow(block[i][j][k] - mean[k], 2);
                }
            }
        }

        var_rgb /= (size * 3);
        return var_rgb;
    }

    private static double mad(int[][][] block, int x, int y, int width, int height, double[] mean) {
        int size = width * height;

        double mad_rgb = 0;
        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                for (int k = 0; k < 3; k++) {
                    mad_rgb += Math.abs(block[i][j][k] - mean[k]);
                }
            }
        }

        mad_rgb /= (size * 3);
        return mad_rgb;
    }

    private static double maxDiff(int[][][] block, int x, int y, int width, int height) {
        double[] max = new double[3];
        double[] min = new double[3];
        for (int k = 0; k < 3; k++) {
            max[k] = Double.MIN_VALUE;
            min[k] = Double.MAX_VALUE;
        }

        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                for (int k = 0; k < 3; k++) {
                    if (block[i][j][k] > max[k]) {
                        max[k] = block[i][j][k];
                    }
                    if (block[i][j][k] < min[k]) {
                        min[k] = block[i][j][k];
                    }
                }
            }
        }

        double max_diff_rgb = 0;
        for (int k = 0; k < 3; k++) {
            max_diff_rgb += (max[k] - min[k]);
        }

        max_diff_rgb /= 3;
        return max_diff_rgb;
    }

    private static double entropy(int[][][] block, int x, int y, int width, int height) {
        double[][] histogram = new double[256][3];
        int size = width * height;

        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                for (int k = 0; k < 3; k++) {
                    histogram[block[i][j][k]][k]++;
                }
            }
        }

        for (int i = 0; i < 256; i++) {
            for (int k = 0; k < 3; k++) {
                histogram[i][k] /= size;
            }
        }

        double entropy_rgb = 0;
        for (int i = 0; i < 256; i++) {
            for (int k = 0; k < 3; k++) {
                if (histogram[i][k] > 0) {
                    entropy_rgb -= histogram[i][k] * Math.log(histogram[i][k]);
                }
            }
        }

        entropy_rgb /= 3;
        return entropy_rgb;
    }

    // TO DO
    // private static double ssim(int[][][] block, int[][][] reconstructedBlock) {

    // }
}