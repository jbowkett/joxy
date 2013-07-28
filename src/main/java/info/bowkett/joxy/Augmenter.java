package info.bowkett.joxy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jbowkett
 * Date: Jul 28, 2013
 * Time: 10:52:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class Augmenter {
  private Filter[] filters;

  public Augmenter(Filter[] filters) {
    this.filters = filters;
  }

  public byte[] augment(Request request, byte[] response) {
    final List<Filter> withFilters = requiredFiltersFor(request);
    return augment(request, response, withFilters);
  }

  /**
   * Augments the response from request using filters.  If no filters
   * express interest, response is returned unchanged.
   *
   * @param request
   * @param response
   * @param filters
   * @return the response augmented by all filters.
   */
  private byte[] augment(Request request, byte[] response, List<Filter> filters) {
    byte[] currentResponse = response;
    for (Filter filter : filters) {
      currentResponse = filter.augment(request, currentResponse);
    }
    return currentResponse;
  }

  private List<Filter> requiredFiltersFor(Request request) {
    final List<Filter> required = new ArrayList<Filter>();
    for (Filter filter : filters) {
      if (filter.matches(request)) required.add(filter);
    }
    return required;
  }
}
