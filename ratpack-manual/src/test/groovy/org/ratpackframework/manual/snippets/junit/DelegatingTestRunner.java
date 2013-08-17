/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ratpackframework.manual.snippets.junit;

import org.junit.runner.Runner;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;

import java.util.List;

public class DelegatingTestRunner extends Suite {

  public DelegatingTestRunner(Class<?> clazz) throws InitializationError {
    super(clazz, extractRunners(clazz));
  }

  @SuppressWarnings("unchecked")
  private static List<Runner> extractRunners(Class<?> clazz) throws InitializationError {
    if (!RunnerProvider.class.isAssignableFrom(clazz)) {
      throw new InitializationError(clazz.getName() + " does not implement " + RunnerProvider.class.getName());
    }

    Class<RunnerProvider> asType = (Class<RunnerProvider>) clazz;
    RunnerProvider instance;
    try {
      instance = asType.newInstance();
    } catch (InstantiationException e) {
      throw new InitializationError(e);
    } catch (IllegalAccessException e) {
      throw new InitializationError(e);
    }

    return instance.getRunners();
  }
}
