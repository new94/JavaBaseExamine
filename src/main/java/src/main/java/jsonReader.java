package src.main.java;

import src.main.java.ru.mai.lessons.FilenameShouldNotBeEmptyException;

import java.io.BufferedReader;
import java.io.IOException;

public class jsonReader {

  public static String loadConfig(String configPath) throws FilenameShouldNotBeEmptyException {
    if (configPath == null || configPath.isEmpty()) {
      throw new FilenameShouldNotBeEmptyException("The config path is missing");
    }

    StringBuilder config = new StringBuilder();

    try (BufferedReader file_reader = new BufferedReader(new java.io.FileReader(configPath))) {
      while (file_reader.ready()) {
        config.append(file_reader.readLine()).append(System.lineSeparator());
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return config.toString();
  }
}
