package fr.uge.jee.ugeoverflow.user;

import fr.uge.jee.ugeoverflow.note.Note;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Users")
public class User {
    private static final String USERNAME_REGEX = "(^[A-Za-z][\\w]{4,29}$)|(admin)";
    private static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";

    @NotBlank(message = "Username is mandatory")
    @Pattern(regexp = USERNAME_REGEX, message = "The username cannot contain special characters apart from \"_\" and must start with a letter.")
    @Id
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp = PASSWORD_REGEX, message = "The password must contain at least eight characters, such that at least : one uppercase, one lowercase, one special character, one digit.")
    private String password;

    @NotBlank(message = "Email is mandatory")
    @Email
    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinTable(
            name = "Followed_Users",
            joinColumns = @JoinColumn(name = "User_Username"),
            inverseJoinColumns = @JoinColumn(name = "Followed_User_Username")
    )
    private Set<User> followedUsers;

    @OneToMany(orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Note> confidenceScore;

    public User(String username, String password, String email, Role role, Set<User> followedUsers) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.followedUsers = followedUsers;
    }
}
