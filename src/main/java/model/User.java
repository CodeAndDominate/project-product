package model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User {
    private int id;
    private String name;
    private String password;
}
