package entities;

import javax.persistence.*;

@Entity
@Table(name = "book")
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false, unique = true)
  private String name;

  @ManyToOne
  @JoinColumn(name = "author")
  private Author author;

  protected Book() {
    // for JPA
  }

  public Book( String name, Author author) {
    this.name = name;
    this.author = author;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Author getAuthor() {
    return author;
  }

  @Override
  public String toString() {
    return "Book{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", author=" + author +
        '}';
  }
}
