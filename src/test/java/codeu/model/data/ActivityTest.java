package codeu.model.data;

import java.time.Instant;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;

public class ActivityTest {

    @Test
    public void testCreate() {
        UUID id = UUID.randomUUID();
        Activity.Type type = Activity.Type.UserJoined; // for test, use UserJoined
        Instant creation = Instant.now();

        Activity activity = new Activity(id, type, creation);

        Assert.assertEquals(id, activity.getObjectId());
        Assert.assertEquals(type, activity.getType());
        Assert.assertEquals(creation, activity.getCreationTime());
    }
}