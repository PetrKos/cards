//package cards.logic.data;
//
//import cards.logic.model.Card;
//import cards.logic.model.enums.Role;
//import cards.logic.model.User;
//import cards.logic.model.enums.Status;
//import cards.logic.repository.CardRepository;
//import cards.logic.repository.UserRepository;
//import com.github.javafaker.Faker;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.time.ZoneId;
//import java.util.List;
//import java.util.Random;
//import java.util.concurrent.TimeUnit;
//import java.util.stream.IntStream;
//
//@Component
//public class DataLoader implements CommandLineRunner {
//
//  private final UserRepository userRepository;
//  private final CardRepository cardRepository;
//
//  private final PasswordEncoder passwordEncoder;
//  private final Faker faker = new Faker();
//
////  BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//  private static final Random random = new Random();
//
//  public DataLoader(UserRepository userRepository, CardRepository cardRepository, PasswordEncoder passwordEncoder) {
//    this.userRepository = userRepository;
//    this.cardRepository = cardRepository;
//    this.passwordEncoder = passwordEncoder;
//  }
//
//  @Override
//  public void run(String... args) {
//    if (userRepository.count() == 0) {
//      List<User> randomUsers = createRandomUsers();
//      userRepository.saveAll(randomUsers);
//    }
//
//    if (cardRepository.count() == 0) {
//      List<Card> randomCards = createRandomCards();
//      cardRepository.saveAll(randomCards);
//    }
//  }
//
//  private List<User> createRandomUsers() {
//    return IntStream.rangeClosed(1, 50)
//            .mapToObj(i -> createRandomUser())
//            .toList();
//  }
//
//  private List<Card> createRandomCards() {
//    return IntStream.rangeClosed(1, 50)
//            .mapToObj(i -> createRandomCard())
//            .toList();
//  }
//
//  private User createRandomUser() {
//    return new User(
//            faker.bothify("????##@gmail.com"),
//            passwordEncoder.encode("12345"),
//            Role.values()[random.nextInt(Role.values().length)]);
//  }
//
//  //will help with the slicing and pagination
//  private Card createRandomCard() {
//    return new Card(generateRandomUniqueName(), "some description", null, null, Status.TO_DO, getSomeLocalDate());
//  }
//
//  private LocalDate getSomeLocalDate() {
//    return faker.date().past(1, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//  }
//
//
//  public String generateRandomUniqueName() {
//    String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
//    int length = 8;
//
//    StringBuilder name = new StringBuilder(length);
//    for (int i = 0; i < length; i++) {
//      int index = random.nextInt(CHARACTERS.length());
//      name.append(CHARACTERS.charAt(index));
//    }
//    return name.toString();
//  }
//
////  public void run(String... args) {
////
////    List<User> randomUsers = IntStream.rangeClosed(1, 50)
////            .mapToObj(i -> {
////              String rawPassword = faker.internet().password(8, 10);
////              String encodedPassword = passwordEncoder.encode(rawPassword);
////      return new User(faker.bothify("????##@gmail.com"), faker.internet().password(8, 10), Role.values()[random.nextInt(Role.values().length)]);
////    }).toList();
////
////    userRepository.saveAll(randomUsers);
////  }
//
//
//
//}
