package aping.api;

import aping.historic.Root;
import aping.historic.RootRepository;
import aping.navigation.Node;
import aping.util.JsonMapper;
import jdk.jfr.Description;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;

@Disabled
@SpringBootTest()
class HistoricDataIT {

    @Autowired
    RootRepository repository;

    @Autowired
    JsonMapper jsonmapper;


    //    static final String fp = "c:\\temp\\31372403";
    static final String fp = "c:\\temp\\BASIC";
    static final File dir = new File(fp);

    final StringBuilder sb = new StringBuilder(25_000_000);
    int counter = 0;

    final List<Root> roots = new ArrayList<>(1000);


    @Description("egy root mappától kezdve kicsomagolja a bz2 fájlokat, json-ból java entitást csinál" +
                 " és menti az adatbázisba")
    @Test
    void uncompress() throws IOException {
        listFiles(dir, 0);
        repository.saveAll(roots); // a maradék mentése

        Files.writeString(Path.of("c:\\temp\\FilesToDatabase.txt"), sb.toString());

        assertThat(repository.findById(1L)).isNotNull();
        assertThat(repository.count()).isEqualTo(165_761);
    }

    public void listFiles(File file, int depth) throws IOException {

        for (File f : Objects.requireNonNull(file.listFiles())) {

            sb.append(Node.SPACES[depth]).append(f).append('\n');

            if (f.isDirectory()) {
                listFiles(f, depth + 1);
            } else {
                var input = new BZip2CompressorInputStream(new BufferedInputStream(new FileInputStream(f)));
                roots.add(jsonmapper.readValue(input.readAllBytes(), Root.class));

                if (++counter % 1000 == 0) {
                    repository.saveAll(roots);
                    roots.clear();
                    System.out.println(counter);
                }
            }
        }
    }

    private void decompressBz2ToFile(File inputFile, String outputFile) throws IOException {
        var input = new BZip2CompressorInputStream(new BufferedInputStream(new FileInputStream(inputFile)));
        var output = new FileOutputStream(outputFile);

        try (input; output) {
            IOUtils.copy(input, output);
        }
    }


    @Description("az adott mappába lévö öszzes filet kiírja az all.txt fájlba")
    @Test
    @Disabled
    void writeAllFileNameToFile() throws IOException {
        bejaras(dir, 0);

        Path path = Path.of(fp).resolve("all.txt");
        Files.writeString(path, sb.toString());

    }

    private void bejaras(File root, int depth) {

        for (File f : root.listFiles()) {
            sb.append(Node.SPACES[depth]).append(f).append('\n');

            if (f.isDirectory())
                bejaras(f, depth + 1);
        }

    }


    @Disabled
    @Test
    void deleteAllBz2Files() {

        deleteFiles(dir, "bz2");

        System.out.println("Files deleted: " + counter);

    }

    @Execution(CONCURRENT)
    @Disabled
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
