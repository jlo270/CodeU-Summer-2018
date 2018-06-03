package codeu.model.store.basic;

import codeu.model.data.Activity;
import codeu.model.store.persistence.PersistentStorageAgent;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ActivityStoreTest {

    private ActivityStore activityStore;
    private PersistentStorageAgent mockPersistentStorageAgent;

    private final Activity ACTIVITY_ONE =
            new Activity(
                    UUID.randomUUID(), Activity.Type.UserJoined, Instant.ofEpochMilli(1000));

    @Before
    public void setup() {
        mockPersistentStorageAgent = Mockito.mock(PersistentStorageAgent.class);
        activityStore = ActivityStore.getTestInstance(mockPersistentStorageAgent);

        final List<Activity> activityList = new ArrayList<>();
        activityList.add(ACTIVITY_ONE);
        activityStore.setActivities(activityList);
    }

    @Test
    public void testGetActivityWithId_found() {
        Activity resultActivity =
                activityStore.getActivityWithId(ACTIVITY_ONE.getObjectId());

        assertEquals(ACTIVITY_ONE, resultActivity);
    }

    @Test
    public void testGetActivityWithid_notFound() {
        UUID testId = new UUID(0,0);
        Activity resultActivity = activityStore.getActivityWithId(testId);

        Assert.assertNull(resultActivity);
    }

    private void assertEquals(Activity expectedActivity, Activity actualActivity) {
        Assert.assertEquals(expectedActivity.getObjectId(), actualActivity.getObjectId());
        Assert.assertEquals(expectedActivity.getType(), actualActivity.getType());
        Assert.assertEquals(
                expectedActivity.getCreationTime(), actualActivity.getCreationTime());
    }
}
