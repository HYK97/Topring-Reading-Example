package tobystudy.group.psa.v1;


import lombok.*;

@Builder
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@Setter
public class User {
    private String id;
    private String name;
    private String password;
    private Level level;
    private int login;
    private int recommend;

}
