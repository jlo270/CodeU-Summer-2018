package codeu.model.data;

import org.junit.Assert;
import org.junit.Test;

public class ActivityTest {

    @Test
    public void testCreate() {
        String creationTime = "test time";
        String output = "test output";

        Activity activity = new Activity();
        activity.output = output;
        activity.creationTime = creationTime;

        Assert.assertEquals(output, activity.getOutput());
        Assert.assertEquals(creationTime, activity.getCreationTime());
    }
}