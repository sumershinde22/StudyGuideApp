package cs3500.pa02.FileWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa02.FileReader.FilesReader;
import cs3500.pa02.MdFile;
import cs3500.pa02.MdFileCollection;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The SrFileWriterTest class contains unit tests for the SrFileWriter class.
 */
class SrFileWriterTest {
  SrFileWriter srFileWriter;
  File file = new File("outputStudyGuides/studyGuideFilename.sr");

  @BeforeEach
  public void setup() {
    Map<String, ArrayList<String>> importantInfo1 = new LinkedHashMap<>();
    ArrayList<String> alist1 = new ArrayList<>();
    alist1.add("ArrayList is a Java class implemented using the List interface");
    importantInfo1.put("# ArrayList in Java", alist1);
    ArrayList<String> alist2 = new ArrayList<>();
    alist2.add("AbstractList Class and implement only the get() and the size() methods");
    importantInfo1.put("## AbstractList", alist2);
    ArrayList<String> alist3 = new ArrayList<>();
    alist3.add("making a fresh copy of the list");
    importantInfo1.put("## CopyOnWriteArrayList", alist3);
    ArrayList<MdFile> mdFiles = new ArrayList<>();
    ArrayList<String> fileQuestions = new ArrayList<>();
    fileQuestions.add(
        "What is the purpose of the CopyOnWriteArrayList class in Java?:::The CopyOnWriteArrayList "
            + "class in Java is an enhanced version of ArrayList.");
    mdFiles.add(new MdFile(Path.of("testDirectory/ArrayList/ArrayList.md"),
        "ArrayList.md",
        importantInfo1,
        FileTime.from(Instant.parse("2023-05-13T20:08:12Z")),
        FileTime.from(Instant.parse("2023-05-13T20:16:10.966753036Z")), fileQuestions));
    MdFileCollection mdFileCollection = new MdFileCollection(mdFiles);
    srFileWriter = new SrFileWriter(mdFileCollection);
  }

  /**
   * Tests the write method of the SrFileWriter class.
   */
  @Test
  public void testWrite() {
    srFileWriter.write(file);
    String file1Content = String.valueOf(FilesReader.readFileContent(file.toPath()));
    String file2Content = String.valueOf(FilesReader.readFileContent(
        Path.of("src/test/resources/srFileWriterTest.sr")));
    assertEquals(file1Content, file2Content);
  }
}