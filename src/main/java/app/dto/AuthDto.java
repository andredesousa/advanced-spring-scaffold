package app.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class AuthDto {

    @NotNull
    public String username;

    @NotNull
    public String password;
}
