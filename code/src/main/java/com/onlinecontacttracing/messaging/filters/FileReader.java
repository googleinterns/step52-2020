package com.onlinecontacttracing.messaging.filters;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner;
import java.util.ArrayList;

public class FileReader {
  public static ArrayList<String> getListFromFile(String fileName) {
    ArrayList<String> listOfIndicators = new ArrayList<String> ();
    try {
      File file = new File(fileName);
      Scanner scanner = new Scanner(file);
      while (scanner.hasNextLine()) {
        listOfIndicators.add(scanner.nextLine());
      }
      scanner.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return listOfIndicators;
  }
}