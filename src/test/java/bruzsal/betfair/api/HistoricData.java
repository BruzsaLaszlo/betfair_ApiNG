package bruzsal.betfair.api;

import bruzsal.betfair.navigation.NavigationData;
import jdk.jfr.Description;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;

class HistoricData {

    String fp = "c:\\temp\\BASIC";
    File dir = new File(fp);

    private final StringBuilder sb = new StringBuilder(25_000_000);

    private int counter = 0;


    @Description("egy root mappától kezdve kicsomagolja a bz2 fájlokat, ugyan abba a mappába")
    @Test
    void uncompress() throws IOException {

        listFiles(dir, 0);

        Files.writeString(Path.of(fp + "\\FilesUncompressed.txt"), sb.toString());

    }

    private void decompressBz2(File inputFile, String outputFile) throws IOException {
        var input = new BZip2CompressorInputStream(new BufferedInputStream(new FileInputStream(inputFile)));
        var output = new FileOutputStream(outputFile);
        try (input; output) {
            IOUtils.copy(input, output);
        }
    }


    @Description("az adott mappába lévö öszzes filet kiírja az all.txt fájlba")
    @Test
    void writeAllFileNameToFile() throws IOException {

        bejaras(dir, 0);

        Path path = Path.of(fp).resolve("all.txt");
        Files.writeString(path, sb.toString());

    }

    private void bejaras(File root, int depth) {

        for (File f : root.listFiles()) {
            sb.append(NavigationData.SPACES[depth]).append(f).append('\n');

            if (f.isDirectory())
                bejaras(f, depth + 1);
        }

    }


    public void listFiles(File file, int depth) throws IOException {

        for (File f : file.listFiles()) {

            sb.append(NavigationData.SPACES[depth]).append(f).append('\n');

            if (f.isDirectory()) {
                listFiles(f, depth + 1);
            } else {
                String outputFile = f.toString().substring(0, f.toString().length() - 3) + "json";
                decompressBz2(f, outputFile);
            }

            if (++counter % 1000 == 0)
                System.out.println(counter);

        }
    }

    @Disabled
    @Test
    void deleteAllBz2Files() {

        deleteFiles(dir, "bz2");

        System.out.println("Files deleted: " + counter);

    }

    @Execution(CONCURRENT)
    void deleteFiles(File targetDir, String extension) {

        for (File f : targetDir.listFiles()) {
            if (f.isDirectory()) {
                deleteFiles(f, extension);
            } else {
                if (f.getName().endsWith(extension)) {
                    f.delete();
                    if (++counter % 1000 == 0) {
                        System.out.println(counter + " deleted");
                    }
                }
            }
        }

    }


}
