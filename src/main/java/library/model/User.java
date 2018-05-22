package library.model;

import javax.persistence.*;

@Entity
@Table(name = "libuser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String login;

    @Column(name = "firstname", nullable = false)
    private String firstName;

    @Column(name = "lastname", nullable = false)
    private String lastName;

    @Column(name = "passwordhash", nullable = false)
    private String passwordHash;

    @Column(name = "urole")
    private String role;

    public User(int id, String login, String firstName, String lastName, String passwordHash, String role) {
        this.id = id;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public User(User user) {
        this.id = user.id;
        this.login = user.login;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.passwordHash = user.passwordHash;
        this.role = user.role;
    }

    public User(String login, String firstName, String lastName, String passwordHash, String role) {
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return new StringBuilder(login).append(": ").append(firstName)
                .append(" ").append(lastName).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id == user.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
