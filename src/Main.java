import java.awt.image.BufferedImage;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 1. INPUT: path gambar
        System.out.print("Masukkan nama gambar input yang ada di folder test (misal: input.png): ");
        String inputPath = sc.nextLine();

        // 2. Load gambar
        ImageData.loadImage(inputPath);

        if (ImageData.imageRGB == null) {
            System.out.println("Gambar tidak ditemukan pada folder test\nKeluar dari program...");
            System.exit(1);
        }

        // 3. INPUT: metode error
        System.out.println("1. Variance");
        System.out.println("2. MAD");
        System.out.println("3. Max Pixel Diff");
        System.out.println("4. Entropy");
        System.out.println("5. SSIM (bonus)");
        System.out.print("Pilih metode error: ");
        ImageData.methodErrorCalculation = sc.nextInt();

        // 4. INPUT: threshold
        System.out.print("Masukkan nilai threshold: ");
        ImageData.threshold = sc.nextDouble();

        // 5. INPUT: minimum block size
        System.out.print("Masukkan ukuran blok minimum: ");
        ImageData.minBlockSize = sc.nextInt();

        // 6. INPUT: target kompresi (bonus, bisa 0)
        System.out.print("Masukkan target persentase kompresi dalam persen (1-100) (0 jika tidak digunakan): ");
        ImageData.targetCompressionRate = sc.nextDouble();

        // 7. INPUT: output path
        sc.nextLine(); // buang newline
        System.out.print("Masukkan nama file gambar hasil (misal: output.png): ");
        String outputPath = sc.nextLine();

        sc.close();

        System.out.println("\nKompresi gambar...");
        long start = System.currentTimeMillis();
        if(ImageData.targetCompressionRate == 0){
            QuadTreeNode root = new QuadTreeNode(0, 0, ImageData.imageWidth, ImageData.imageHeight);
            
            ImageData.nodePrediction = Utils.totalNodePrediction(0.5, ImageData.minBlockSize, ImageData.threshold);
            ImageData.base = Utils.basePrediction(ImageData.nodePrediction, 1);

            // System.out.println("Perkiraan jumlah simpul: " + ImageData.nodePrediction); // Debugging
            // System.out.println("Perkiraan yg dikali 4: " + ImageData.base); // Debugging

            root.divide(0); // 8. Kompresi gambar
        }
        else{
            ImageData.isTarget = true;
            QuadTreeNode oriRoot = new QuadTreeNode(0, 0, ImageData.imageWidth, ImageData.imageHeight);

            TargetCompression.compressToTargetPercentage();

            ImageData.isTarget = false;
            ImageData.base = Utils.basePrediction(ImageData.totalNodes, 1);
            oriRoot.divide(0);
        }

        long end = System.currentTimeMillis();

        // 9. Simpan hasil
        ImageData.saveImage(outputPath);

        // Bonus: Simpan GIF
        System.out.println("\nMenyimpan GIF...");
        String gifPath = outputPath.substring(0, outputPath.lastIndexOf('.')) + ".gif";

        BufferedImage[] bufferedFrames = GIF.snapshotFrames.toArray(new BufferedImage[0]);
        
        try {
            System.out.println("Jumlah frame pada GIF: " + GIF.snapshotFrames.size()); // Debugging
            GIF.saveGIF(bufferedFrames, gifPath, 100); // atur speed GIF yang diinginkan (ms per frame)
        } catch (Exception e) {
            System.out.println("Error saat menyimpan GIF: " + e.getMessage());
        }

        // 10. Output statistik
        System.out.println("\n--- HASIL ---");
        System.out.println("Waktu eksekusi: " + (end - start) + " ms");
        System.out.println("Ukuran gambar sebelum: " + ImageData.originalSize + " kB");
        System.out.println("Ukuran gambar setelah: " + ImageData.compressedSize + " kB");
        System.out.println("Persentase kompresi: " +
                (100.0 * (1 - ((double) ImageData.compressedSize / ImageData.originalSize))) + " %");
        System.out.println("Jumlah simpul: " + ImageData.totalNodes);
        System.out.println("Kedalaman pohon: " + ImageData.maxDepth);
        if(ImageData.targetCompressionRate != 0){
            System.out.println("Nilai threshold yang digunakan: " + ImageData.threshold);
        }
    }
}
