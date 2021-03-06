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

package io.opencensus.stats;

/**
 * Class that holds the implementations for {@link ViewManager}, {@link StatsRecorder}, and {@link
 * StatsContextFactory}.
 *
 * <p>All objects returned by methods on {@code StatsComponent} are cacheable.
 */
// TODO(sebright): Add a no-op StatsComponent.
public abstract class StatsComponent {
  /** Returns the default {@link ViewManager}. */
  public abstract ViewManager getViewManager();

  /** Returns the default {@link StatsRecorder}. */
  public abstract StatsRecorder getStatsRecorder();

  /** Returns the default {@link StatsContextFactory}. */
  // TODO(sebright): Remove this method once StatsContext is replaced by TagContext.
  abstract StatsContextFactory getStatsContextFactory();
}
