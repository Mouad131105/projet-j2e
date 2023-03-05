package fr.uge.jee.ugeoverflow.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

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
    private static final String USERNAME_REGEX = "^[A-Za-z][\\w]{4,29}$";
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

    //@Version
    private long confidenceScore;
}
