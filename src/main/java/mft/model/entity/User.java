package mft.model.entity;

import com.google.gson.Gson;
import lombok.*;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder(toBuilder = true)
public class User {
    private int id;
    private String username;
    private String password;
    private boolean enabled;


    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
