package src.main.java.ru.mai.lessons;

public class FilenameShouldNotBeEmptyException extends Exception {

  public FilenameShouldNotBeEmptyException(String message) {
    super(message);
  }
}
