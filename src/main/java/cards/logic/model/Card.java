package cards.logic.model;


import cards.logic.model.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "cards")
public class Card {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String description;

  private String color;

  @Enumerated(EnumType.STRING)
  private Status status = Status.TO_DO; // Default status

  private LocalDate creationDate = LocalDate.now();

  @Column(name = "user_id", nullable = false)
  private Long userId;



  public Card(Long id, String name, String description, String color, Long userId, Status status, LocalDate creationDate) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.color = color;
    this.userId = userId;
    this.status = status;
    this.creationDate = creationDate;
  }

  public Card(String name, String description, String color, Long userId, Status status, LocalDate creationDate) {
    this.name = name;
    this.description = description;
    this.color = color;
    this.userId = userId;
    this.status = status;
    this.creationDate = creationDate;
  }

  public Card() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public LocalDate getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDate creationDate) {
    this.creationDate = creationDate;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Card card)) return false;
    return Objects.equals(getId(), card.getId()) && Objects.equals(getName(), card.getName()) && Objects.equals(getDescription(), card.getDescription()) && Objects.equals(getColor(), card.getColor()) && getStatus() == card.getStatus() && Objects.equals(getCreationDate(), card.getCreationDate()) && Objects.equals(getUserId(), card.getUserId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getName(), getDescription(), getColor(), getStatus(), getCreationDate(), getUserId());
  }
}

