package codeu.model.store.basic;

import codeu.model.data.Activity;
import codeu.model.store.persistence.PersistentDataStoreException;
import codeu.model.store.persistence.PersistentStorageAgent;
import codeu.model.data.User;
import codeu.model.data.Message;
import codeu.model.data.Conversation;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.time.Instant;

/**
 * Store class that uses in-memory data structures to hold values and automatically loads from and
 * saves to PersistentStorageAgent. It's a singleton so all servlet classes can access the same
 * instance.
 */
public class ActivityStore {

    /** Singleton instance of ConversationStore. */
    private static ActivityStore instance;

    /**
     * Returns the singleton instance of ActivityStore that should be shared between all servlet
     * classes. Do not call this function from a test; use getTestInstance() instead.
     */
    public static ActivityStore getInstance() {
        if (instance == null) {
            instance = new ActivityStore(PersistentStorageAgent.getInstance());
        }
        return instance;
    }

    /**
     * The PersistentStorageAgent responsible for loading Activities from and saving Activities
     * to Datastore.
     */
    private PersistentStorageAgent persistentStorageAgent;

    /** The in-memory list of Activities. */
    private List<Activity> activities;

    /** This class is a singleton, so its constructor is private. Call getInstance() instead. */
    private ActivityStore(PersistentStorageAgent persistentStorageAgent) {
        this.persistentStorageAgent = persistentStorageAgent;
        activities = new ArrayList<>();
    }

    /** Access the current set of activities known to the application. */
    public List<Activity> getActivities(int indexFrom, int maxElements)
            throws PersistentDataStoreException {

        List<Conversation> allConversations = persistentStorageAgent.loadConversations();
        List<User> allUsers = persistentStorageAgent.loadUsers();
        List<Message> allMessages = persistentStorageAgent.loadMessages();

        // Adds all users to activities list
        for (int u = 0; u < allUsers.size(); ++u) {
            User tempUser = allUsers.get(u);
            Activity newUser = new Activity(tempUser.getId(),
                    Activity.Type.UserJoined,
                    tempUser.getCreationTime());
            activities.add(newUser);
        }

        // Adds all conversations to activities list
        for (int c = 0; c < allConversations.size(); ++c) {
            Conversation tempConversation = allConversations.get(c);
            Activity newConversation = new Activity(tempConversation.getId(),
                    Activity.Type.ConversationCreated,
                    tempConversation.getCreationTime());
            activities.add(newConversation);
        }

        // Adds all messages to activities list
        for (int m = 0; m < allMessages.size(); ++m) {
            Message tempMessage = allMessages.get(m);
            Activity newMessage = new Activity(tempMessage.getId(),
                    Activity.Type.MessageSent,
                    tempMessage.getCreationTime());
            activities.add(newMessage);
        }

        return activities;
    }

    static int activityComparator(Activity a, Activity b) {
        int comp = a.creationTime.compareTo(b.creationTime);
        if (comp > 0) return 1; // positive if greater/later
        else if (comp < 0) return -1;
        else return 0;
    }

    public List<Activity> sortActivities(int maxElements) {
        // Sort all activities by creation time
        activities.sort(new Comparator<Activity>() {
            @Override
            public int compare(Activity o1, Activity o2) {
                int comp = o1.creationTime.compareTo(o2.creationTime);
                if (comp > 0) return 1; // positive if greater/later
                else if (comp < 0) return -1;
                else return 0;
            }
        });

        List<Activity> sortedActivity = new List<Activity>;
        for (int i = 0; i < maxElements; ++i) {
            sortedActivity.add(i,activities.get(i));
        }
        return sortedActivity;
    }
}
