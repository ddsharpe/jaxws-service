package ddsharpe.samples.jaxws.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class UserCredential {
  private static HashMap<String, UserCredential> users = new HashMap<>();

  static {
    users.put("scott", new UserCredential("scott", "abc123"));
    users.put("tiger", new UserCredential("tiger", "xyz123"));
  }

  private String id;
  private String password;
  private List<String> roles = new ArrayList<>();

  public UserCredential(String id, String password, String... roles) {
    this.id = id;
    this.password = password;
    if (roles != null && roles.length > 0) {
      this.roles.addAll(Arrays.asList(roles));
    }
  }

  public static UserCredential getUserCredential(String id) {
    UserCredential result = users.get(id);
    return result == null ? new UserCredential(id, "ERROR - ID NOT FOUND") : result;
  }

  @Override
  public String toString() {
    return id + ":" + password;
  }
}
