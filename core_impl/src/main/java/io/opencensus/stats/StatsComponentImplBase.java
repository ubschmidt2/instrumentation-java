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

import io.opencensus.common.Clock;
import io.opencensus.internal.EventQueue;

/** Base implementation of {@link StatsComponent}. */
class StatsComponentImplBase extends StatsComponent {

  private final ViewManagerImpl viewManager;
  private final StatsRecorderImpl statsRecorder;
  private final StatsContextFactoryImpl statsContextFactory;

  StatsComponentImplBase(EventQueue queue, Clock clock) {
    StatsManager statsManager = new StatsManager(queue, clock);
    this.viewManager = new ViewManagerImpl(statsManager);
    this.statsRecorder = new StatsRecorderImpl(statsManager);
    this.statsContextFactory = new StatsContextFactoryImpl(statsRecorder);
  }

  @Override
  public ViewManagerImpl getViewManager() {
    return viewManager;
  }

  @Override
  public StatsRecorderImpl getStatsRecorder() {
    return statsRecorder;
  }

  @Override
  StatsContextFactoryImpl getStatsContextFactory() {
    return statsContextFactory;
  }
}
