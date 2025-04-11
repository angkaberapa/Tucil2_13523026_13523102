public class QuadTreeNode {
    private int x, y, width, height;
    private double[] avgColorRGB = new double[3];
    private boolean isLeaf;

    private QuadTreeNode topLeft;
    private QuadTreeNode topRight;
    private QuadTreeNode bottomLeft;
    private QuadTreeNode bottomRight;

    public QuadTreeNode(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isLeaf = false;
    }

    public void divide(int depth) {
        // Increment total nodes & update maximum depth
        ImageData.totalNodes++;
        ImageData.maxDepth = Math.max(ImageData.maxDepth, depth);

        // Calculate error for the current block
        double error = ErrorMeasurement.calculateError(ImageData.imageRGB, x, y, width, height, ImageData.methodErrorCalculation, avgColorRGB);

        // Check split condition
        if (error <= ImageData.threshold || width*height <= ImageData.minBlockSize) {
            // This block is uniform enough or small enough → make it a leaf
            isLeaf = true;
            avgColorRGB = Utils.averageRGB(ImageData.imageRGB, x, y, width, height);
            Utils.fillBlock(ImageData.imageRGB, x, y, width, height, avgColorRGB);

            // kondisi size untuk menghindari memory out of bound
            if (GIF.snapshotFrames.size() <= 300 && ImageData.totalNodes % 100 == 0) { // pada kelipatan berapa frame akan disimpan
                GIF.snapshotFrames.add(ImageData.convertImage(ImageData.imageRGB)); // Save kondisi image saat ini menjadi frame GIF
            }
            return;
        }

        // Split the block into 4 parts
        int newWidth = width / 2;
        int newHeight = height / 2;

        topLeft = new QuadTreeNode(x, y, newWidth, newHeight);
        topRight = new QuadTreeNode(x + newWidth, y, width - newWidth , newHeight);
        bottomLeft = new QuadTreeNode(x, y + newHeight, newWidth, height - newHeight);
        bottomRight = new QuadTreeNode(x + newWidth, y + newHeight, width - newWidth , height - newHeight);

        topLeft.divide(depth + 1);
        topRight.divide(depth + 1);
        bottomLeft.divide(depth + 1);
        bottomRight.divide(depth + 1);
    }
}