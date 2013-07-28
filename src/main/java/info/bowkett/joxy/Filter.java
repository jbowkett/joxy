package info.bowkett.joxy;

/**
 * Created by IntelliJ IDEA.
 * User: jbowkett
 * Date: Jul 25, 2013
 * Time: 9:40:44 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Filter {
  public boolean matches(Request request);

  byte[] augment(Request request, byte[] response);
}
