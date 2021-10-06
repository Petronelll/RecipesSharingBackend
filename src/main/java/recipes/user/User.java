package recipes.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Getter @Setter
public class User {

    @Id
    @JsonIgnoreProperties
    @GeneratedValue
    private int user_id;

    @Email(message = "Invalid email", regexp = "^(.+)@(.+)(\\.+)(.+)$")
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;
}
