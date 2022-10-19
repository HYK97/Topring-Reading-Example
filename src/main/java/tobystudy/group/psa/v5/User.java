package tobystudy.group.psa.v5;


import lombok.*;

@Builder
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@Setter
@NoArgsConstructor
@ToString
public class User {
    private String id;
    private String name;
    private String password;
    private Level level;
    private int login;
    private int recommend;

    public void upgradeLevel() {
        Level nextLevel = this.level.nextLevel();
        if (nextLevel != null) {
            this.level = nextLevel;
        } else {
            throw new IllegalArgumentException("업그레이드 불가" + this.level);
        }
    }
}
