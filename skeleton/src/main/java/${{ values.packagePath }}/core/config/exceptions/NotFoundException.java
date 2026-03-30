package {{ values.groupId }}.{{ values.packageName }}.core.config.exceptions;

public class NotFoundException extends RuntimeException {

  public NotFoundException(String message) {
    super(message);
  }

}
