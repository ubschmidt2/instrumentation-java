/*
 * Copyright 2017, Google Inc.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.opencensus.trace.export;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.auto.value.AutoValue;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

/**
 * This class allows users to access in-process information about all running spans.
 *
 * <p>The running spans tracking is available for all the spans with the option {@link
 * io.opencensus.trace.Span.Options#RECORD_EVENTS}. This functionality allows users to debug stuck
 * operations or long living operations.
 */
@ThreadSafe
public abstract class RunningSpanStore {

  protected RunningSpanStore() {}

  /**
   * Returns the summary of all available data such, as number of running spans.
   *
   * @return the summary of all available data.
   */
  public abstract Summary getSummary();

  /**
   * Returns a list of running spans that match the {@code Filter}.
   *
   * @param filter used to filter the returned spans.
   * @return a list of running spans that match the {@code Filter}.
   */
  public abstract Collection<SpanData> getRunningSpans(Filter filter);

  /** The summary of all available data. */
  @AutoValue
  @Immutable
  public abstract static class Summary {

    Summary() {}

    /**
     * Returns a new instance of {@code Summary}.
     *
     * @param perSpanNameSummary a map with summary for each span name.
     * @return a new instance of {@code Summary}.
     * @throws NullPointerException if {@code perSpanNameSummary} is {@code null}.
     */
    public static Summary create(Map<String, PerSpanNameSummary> perSpanNameSummary) {
      return new AutoValue_RunningSpanStore_Summary(
          Collections.unmodifiableMap(
              new HashMap<String, PerSpanNameSummary>(
                  checkNotNull(perSpanNameSummary, "perSpanNameSummary"))));
    }

    /**
     * Returns a map with summary of available data for each span name.
     *
     * @return a map with all the span names and the summary.
     */
    public abstract Map<String, PerSpanNameSummary> getPerSpanNameSummary();
  }

  /** Summary of all available data for a span name. */
  @AutoValue
  @Immutable
  public abstract static class PerSpanNameSummary {

    PerSpanNameSummary() {}

    /**
     * Returns a new instance of {@code PerSpanNameSummary}.
     *
     * @param numRunningSpans the number of running spans.
     * @return a new instance of {@code PerSpanNameSummary}.
     * @throws IllegalArgumentException if {@code numRunningSpans} is negative.
     */
    public static PerSpanNameSummary create(int numRunningSpans) {
      checkArgument(numRunningSpans >= 0, "Negative numRunningSpans.");
      return new AutoValue_RunningSpanStore_PerSpanNameSummary(numRunningSpans);
    }

    /**
     * Returns the number of running spans.
     *
     * @return the number of running spans.
     */
    public abstract int getNumRunningSpans();
  }

  /**
   * Filter for running spans. Used to filter results returned by the {@link
   * #getRunningSpans(Filter)} request.
   */
  @AutoValue
  @Immutable
  public abstract static class Filter {

    Filter() {}

    /**
     * Returns a new instance of {@code Filter}.
     *
     * <p>Filters all the spans based on {@code spanName} and returns a maximum of {@code
     * maxSpansToReturn}.
     *
     * @param spanName the name of the span.
     * @param maxSpansToReturn the maximum number of results to be returned. {@code 0} means all.
     * @return a new instance of {@code Filter}.
     * @throws NullPointerException if {@code spanName} is {@code null}.
     * @throws IllegalArgumentException if {@code maxSpansToReturn} is negative.
     */
    public static Filter create(String spanName, int maxSpansToReturn) {
      checkArgument(maxSpansToReturn >= 0, "Negative maxSpansToReturn.");
      return new AutoValue_RunningSpanStore_Filter(spanName, maxSpansToReturn);
    }

    /**
     * Returns the span name.
     *
     * @return the span name.
     */
    public abstract String getSpanName();

    /**
     * Returns the maximum number of spans to be returned. {@code 0} means all.
     *
     * @return the maximum number of spans to be returned.
     */
    public abstract int getMaxSpansToReturn();
  }
}
