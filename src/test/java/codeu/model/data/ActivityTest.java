// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.model.data;

import org.junit.Assert;
import org.junit.Test;

public class ActivityTest {

  private String creationTime = "1969-12-31 18:00:02";
  private String output = "test_username joined Charmer Chat.";

  @Test
  public void testCreate() {

    Activity activity = new Activity(creationTime,output);

    Assert.assertEquals(output, activity.getOutput());
    Assert.assertEquals(creationTime, activity.getCreationTime());
  }

  @Test
  public void testEquals() {
    Activity activity = new Activity(creationTime,output);
    Activity otherActivity = new Activity("1969-12-31 18:01:00","other_username joined Charmer Chat.");

    Assert.assertEquals(activity, new Activity("1969-12-31 18:00:02","test_username joined Charmer Chat."));
    Assert.assertNotEquals(otherActivity,activity.toString());
  }

}
