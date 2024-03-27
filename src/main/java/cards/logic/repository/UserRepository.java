package cards.logic.repository;


import cards.logic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for {@link User} entities.
 * Extends {@link JpaRepository} to provide standard CRUD operations on User entities.
 *
 * This repository interface is used for accessing and performing operations on the user data
 * stored in the database. It includes custom operations specific to {@link User} entities,
 * such as finding a user by email.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  /**
   * Finds a user by their email address.
   *
   * @param email The email address to search for.
   * @return An {@link Optional} of {@link User} if a user with the given email address exists,
   *         otherwise, an empty {@link Optional}.
   */
  Optional<User> findByEmail(String email);
}
