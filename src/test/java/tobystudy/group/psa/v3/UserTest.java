package tobystudy.group.psa.v3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


class UserTest {

    User user;

    @BeforeEach
    public void setUp() {
        user = new User();
    }


    @Test
    public void upgradeLevel() {
        Level[] levels = Level.values();
        for (Level level : levels) {
            if (level.nextLevel() == null) {
                continue;
            }
            user.setLevel(level);
            user.upgradeLevel();
            assertThat(user.getLevel()).isEqualTo(level.nextLevel());
        }
    }

    @Test
    public void cannotUpgradeLevel() throws Exception {
        Level[] levels = Level.values();
        for (Level level : levels) {
            if (level.nextLevel() != null) {
                continue;
            }
            assertThatThrownBy(() -> {
                user.setLevel(level);
                user.upgradeLevel();
            }).isInstanceOf(IllegalArgumentException.class);
        }

        user.setLevel(Level.GOLD);


    }
}