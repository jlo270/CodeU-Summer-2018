package codeu.model.data;

import java.time.Instant;
import java.util.UUID;

/**
 * Class representing an activity, which can be thought of as any action
 * within the chat room. Activities are triggered by a User joining a
 * conversation, starting a conversation or replying to a message.
 */
public class Activity {
    public final UUID objectId;
    public final Type type;
    public final Instant creationTime;

    public enum Type {
        UserJoined,
        ConversationCreated,
        MessageSent
    }

    /**
     * Constructs a new Conversation.
     *
     * @param objectId the ID of this Activity
     * @param type the type of this Activity this is
     * @param creationTime the creation time of this Activity
     */
    public Activity(UUID objectId, Type type, Instant creationTime) {
        this.objectId = objectId;
        this.type = type;
        this.creationTime = creationTime;
    }

    /** Returns the ID of this Activity. */
    public UUID getObjectId() {
        return objectId;
    }

    /** Returns the type of this Activity. */
    public Type getType() {
        return type;
    }

    /** Returns the creation time of this Activity. */
    public Instant getCreationTime() {
        return creationTime;
    }

}
