package library.model;

import javax.persistence.*;

@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "firstname", nullable = false)
    private String firstName;

    @Column(name = "secondname")
    private String secondName;

    @Column(name = "lastname", nullable = false)
    private String lastName;

    @Column
    private int century;

    public Author(int id, String firstName, String secondName, String lastName, int century) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.century = century;
    }

    public Author(String lastName) {
        this.lastName = lastName;
    }

    public Author() {
    }

    public Author(String firstName, String secondName, String lastName) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCentury(int century) {
        this.century = century;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getCentury() {
        return century;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        return id == author.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        StringBuilder name = new StringBuilder("");
        if (firstName != null && !firstName.equals("")) name.append(firstName.charAt(0)).append(". ");
        if (secondName != null && !secondName.equals("")) name.append(secondName.charAt(0)).append(". ");
        name.append(lastName);
        return name.toString();
    }
}
