package ua.lann.protankiserver.orm.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ua.lann.protankiserver.enums.ChatModeratorLevel;
import ua.lann.protankiserver.enums.Rank;
import ua.lann.protankiserver.security.BCryptHasher;

@Entity
public class Player {
    @Getter
    @Id private String nickname;

    @Getter
    @Column private String password;

    @Getter @Setter
    @Column private int crystals = 0;

    @Getter @Setter
    @Column private int experience = 0;

    @Getter @Setter @Enumerated(EnumType.STRING)
    @Column private ChatModeratorLevel level = ChatModeratorLevel.None;

    public Player() {}
    public Player(String nickname) {
        this.nickname = nickname;
    }

    public void setPassword(String password) { this.password = BCryptHasher.hash(password); }

    public boolean canSpectate() { return level == ChatModeratorLevel.Moderator; }
    public Rank getRank() {
        for(Rank rank : Rank.values()) {
            if(experience >= rank.minExperience && experience < rank.maxExperience) return rank;
        }

        return Rank.Legend;
    }
}
