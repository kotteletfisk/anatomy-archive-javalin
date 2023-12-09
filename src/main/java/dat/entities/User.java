package dat.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mindrot.jbcrypt.BCrypt;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class User
{
    @Id
    @Column(name = "user_name", nullable = false, unique = true)
    private String username;
    @Column(name = "user_password", nullable = false)
    private String password;
    @Enumerated
    @Column(name = "role", nullable = false)
    private Role role;

    public User(String username, String password)
    {
        this.username = username;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean verifyPassword(String pw)
    {
        return BCrypt.checkpw(pw, password);
    }
}
