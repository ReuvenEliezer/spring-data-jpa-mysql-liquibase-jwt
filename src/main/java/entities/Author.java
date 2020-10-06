package entities;

import javax.persistence.*;

@Entity
@Table(name = "author")
public class Author {

  /**
   * Explaining strategies: https://thoughts-on-java.org/jpa-generate-primary-keys/
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false, unique = true)
  private String name;

  protected Author() {
    // for JPA
  }

  public Author(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

  public Author( String name) {
    this.name = name;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "Author{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
