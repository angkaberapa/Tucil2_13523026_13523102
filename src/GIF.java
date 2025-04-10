import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.image.BufferedImage;
import com.icafe4j.image.gif.GIFTweaker;

public class GIF {
    public static List<BufferedImage> snapshotFrames = new ArrayList<>();

    public static void saveGIF(BufferedImage[] frames, String outputPath, int delayMs) throws IOException {
        int[] delays = new int[frames.length];
        Arrays.fill(delays, delayMs);

        try (FileOutputStream out = new FileOutputStream(outputPath)) {
            GIFTweaker.writeAnimatedGIF(frames, delays, out);
            System.out.println("GIF berhasil disimpan: " + outputPath);
        } catch (Exception e) {
            System.err.println("Gagal menyimpan GIF: " + e.getMessage());
        }
    }
}
