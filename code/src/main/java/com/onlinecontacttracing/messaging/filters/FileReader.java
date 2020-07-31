package com.onlinecontacttracing.messaging.filters;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

public class FileReader {
  public static String[] getListFromFile(String fileName) {
    try{
      InputStream in = (new FileReader()).getClass().getClassLoader().getResourceAsStream(fileName);
      ByteArrayOutputStream result = new ByteArrayOutputStream();
      byte[] buffer = new byte[1024];
      int length;
      while ((length = in.read(buffer)) != -1) {
          result.write(buffer, 0, length);
      }

      return result.toString(StandardCharsets.UTF_8.name()).split("\n");
    } catch (Exception e) {
      e.printStackTrace();
      return new String[0];
    } 
  }
}