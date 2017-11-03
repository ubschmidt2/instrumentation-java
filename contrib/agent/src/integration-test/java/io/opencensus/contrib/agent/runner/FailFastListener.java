/*
 * Copyright 2017, OpenCensus Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.opencensus.contrib.agent.runner;

import static com.google.common.base.Preconditions.checkNotNull;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;

/** A JUnit4 {@link RunListener} which cancels all further tests on the first failure. */
final class FailFastListener extends RunListener {

  private final RunNotifier notifier;

  FailFastListener(RunNotifier notifier) {
    this.notifier = checkNotNull(notifier);
  }

  @Override
  public void testRunStarted(Description description) {}

  @Override
  public void testRunFinished(Result result) {}

  @Override
  public void testStarted(Description description) {}

  @Override
  public void testFinished(Description description) {}

  @Override
  public void testFailure(Failure failure) {
    notifier.pleaseStop();
  }

  @Override
  public void testAssumptionFailure(Failure failure) {}

  @Override
  public void testIgnored(Description description) {}
}
