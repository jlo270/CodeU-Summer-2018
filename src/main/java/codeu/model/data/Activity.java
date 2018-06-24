package codeu.model.data;

/**
 * Class representing an activity, which can be thought of as any action
 * within the chat room. Activities are triggered by a User joining a
 * conversation, starting a conversation or replying to a message.
 */
public class Activity {
  private final String creationTime;
  private final String output;

  /**
   * Constructs a new Activity.
   *
   * @param creationTime the creation time of this Activity as a String
   * @param output the formatted description of the Activity as a String
   */
  public Activity(String creationTime, String output) {
    this.creationTime = creationTime;
    this.output = output;
  }

  /** Returns the creation time of this Activity. */
  public String getCreationTime() { return creationTime; }

  /** Returns the title of this Conversation. */
  public String getOutput() {
    return output;
  }

  public String toString() {
    return creationTime + ": " + output;
  }

  @Override
  public boolean equals(Object object) {
    Activity activity = (Activity)object;
    if (!this.creationTime.equals(activity.creationTime)) return false;
    if (!this.output.equals(activity.output)) return false;
    return true;
  }
}
