package cards.logic.model.enums;

public enum Role {
  MEMBER("ROLE_MEMBER"),
  ADMIN("ROLE_ADMIN");

  private final String role;

  Role(String role) {
    this.role = role;
  }

  @Override
  public String toString() {
    return role;
  }

}


