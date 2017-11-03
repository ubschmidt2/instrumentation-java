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

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

/** A JUnit4 {@link Runner} which cancels all further tests on the first failure. */
public class FailFastRunner extends BlockJUnit4ClassRunner {

  public FailFastRunner(Class<?> klass) throws InitializationError {
    super(checkNotNull(klass));
  }

  @Override
  public void run(RunNotifier notifier) {
    checkNotNull(notifier);

    notifier.addListener(new FailFastListener(notifier));
    super.run(notifier);
  }
}
