package cs3500.pa02.FileReader;

import cs3500.pa02.MdFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The MdFileReader class reads and processes Markdown files to create instances of MDFile.
 * It extends FilesReader.
 */
public class MdFileReader extends FilesReader {

  /**
   * Constructs a FilesReader object.
   */
  public MdFileReader() {
  }

  private Map<String, ArrayList<String>> getImportantText(Path mdPath) {
    ArrayList<String> fileContent = readFileContent(mdPath);
    List<List<String>> splitArrayList = splitFileContent(fileContent);
    return processSplitArrayList(splitArrayList);
  }

  private ArrayList<String> getQuestions(Path mdPath) {
    ArrayList<String> fileContent = readFileContent(mdPath);
    List<List<String>> splitArrayList = splitFileContent(fileContent);
    ArrayList<String> fileQuestions = new ArrayList<>();
    for (List<String> list : splitArrayList) {
      ArrayList<String> temp = extractQuestions(list);
      fileQuestions.addAll(temp);
    }
    return fileQuestions;
  }

  private Map<String, ArrayList<String>> processSplitArrayList(List<List<String>> splitArrayList) {
    Map<String, ArrayList<String>> importantInfo = new LinkedHashMap<>();
    for (List<String> list : splitArrayList) {
      String key = list.get(0);
      list.remove(0);
      StringBuilder sb = new StringBuilder();
      for (String item : list) {
        sb.append(item);
      }
      importantInfo.put(key, extractStrings(sb.toString()));
    }
    return importantInfo;
  }

  private static ArrayList<String> extractStrings(String input) {
    ArrayList<String> extractedStrings = new ArrayList<>();

    // Define the pattern to match the desired content within double square brackets
    Pattern pattern = Pattern.compile("\\[\\[(.*?)]]");
    Matcher matcher = pattern.matcher(input);

    // Find all matches and extract the captured groups
    while (matcher.find()) {
      String extractedString = matcher.group(1);
      extractedStrings.add(extractedString);
    }
    extractedStrings.removeIf(item -> item.contains(":::"));
    return extractedStrings;
  }

  private static ArrayList<String> extractQuestions(List<String> input) {
    ArrayList<String> extractedQuestions = new ArrayList<>();
    ArrayList<String> temp = new ArrayList<>();
    // Define the pattern to match the desired content within double square brackets
    Pattern pattern = Pattern.compile("\\[\\[(.*?)]]");
    Matcher matcher;

    for (String line : input) {
      matcher = pattern.matcher(line);
      // Find all matches and extract the captured groups
      while (matcher.find()) {
        String extractedString = matcher.group(1);
        temp.add(extractedString);
      }
    }

    for (String item : temp) {
      if (item.contains(":::")) {
        String trimmedString = item.replaceAll("\\s*:::\\s*", ":::");
        extractedQuestions.add(trimmedString);
      }
    }
    return extractedQuestions;
  }


  private FileTime getFileAttribute(Path mdPath, String attribute) throws IOException {
    BasicFileAttributes attributes = Files.readAttributes(mdPath, BasicFileAttributes.class);
    return switch (attribute) {
      case "creationTime" -> attributes.creationTime();
      case "modificationTime" -> attributes.lastModifiedTime();
      default -> throw new IllegalArgumentException("Invalid attribute: " + attribute);
    };
  }

  private List<List<String>> splitFileContent(ArrayList<String> fileContent) {
    List<List<String>> splitArrayList = new ArrayList<>();
    List<String> currentSublist = new ArrayList<>();

    for (String item : fileContent) {
      if (item.contains("#")) {
        if (!currentSublist.isEmpty()) {
          splitArrayList.add(currentSublist);
          currentSublist = new ArrayList<>();
        }
      }
      currentSublist.add(item);
    }

    if (!currentSublist.isEmpty()) {
      splitArrayList.add(currentSublist);
    }

    return splitArrayList;
  }

  /**
   * Creates and returns an ArrayList of MDFile objects corresponding to the Markdown files
   * specified in the constructor.
   *
   * @return MDFile ArrayList objects corresponding to the Markdown files specified in constructor.
   */
  public ArrayList<MdFile> createMdFiles(ArrayList<Path> mdFilePaths) {
    ArrayList<MdFile> mdFiles = new ArrayList<>();
    try {
      for (Path mdPath : mdFilePaths) {
        String fileName = mdPath.getFileName().toString();
        Map<String, ArrayList<String>> importantInfo = getImportantText(mdPath);
        FileTime creationTime = getFileAttribute(mdPath, "creationTime");
        FileTime modificationTime = getFileAttribute(mdPath, "modificationTime");
        ArrayList<String> fileQuestions = getQuestions(mdPath);
        MdFile mdFile =
            new MdFile(mdPath, fileName, importantInfo, creationTime, modificationTime,
                fileQuestions);
        mdFiles.add(mdFile);
      }
    } catch (IOException e) {
      System.err.println("Problem with loading file.");
      e.printStackTrace();
      System.exit(1);
    }
    return mdFiles;
  }
}
