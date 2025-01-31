/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.druid.tests.query;

import com.google.inject.Inject;
import org.apache.druid.testing.clients.CoordinatorResourceTestClient;
import org.apache.druid.testing.guice.DruidTestModuleFactory;
import org.apache.druid.testing.utils.ITRetryUtil;
import org.apache.druid.testing.utils.TestQueryHelper;
import org.apache.druid.tests.TestNGGroup;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

@Test(groups = {TestNGGroup.QUERY, TestNGGroup.CENTRALIZED_TABLE_SCHEMA})
@Guice(moduleFactory = DruidTestModuleFactory.class)
public class ITTwitterQueryTest
{
  private static final String TWITTER_DATA_SOURCE = "twitterstream";
  private static final String TWITTER_QUERIES_RESOURCE = "/queries/twitterstream_queries.json";
  @Inject
  CoordinatorResourceTestClient coordinatorClient;
  @Inject
  private TestQueryHelper queryHelper;

  @BeforeMethod
  public void before()
  {
    // ensure that the twitter segments are loaded completely
    ITRetryUtil.retryUntilTrue(
        () -> coordinatorClient.areSegmentsLoaded(TWITTER_DATA_SOURCE), "twitter segment load"
    );
  }

  @Test
  public void testTwitterQueriesFromFile() throws Exception
  {
    queryHelper.testQueriesFromFile(TWITTER_QUERIES_RESOURCE);
  }

}
