package ua.lann.protankiserver.orm.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ua.lann.protankiserver.enums.ChatModeratorLevel;
import ua.lann.protankiserver.enums.Rank;

@Entity
public class Player {
    @Id
    private String nickname;

    @Getter @Setter
    @Column private int crystals;

    @Getter @Setter
    @Column private long experience;

    @Getter @Setter @Enumerated(EnumType.STRING)
    @Column private ChatModeratorLevel level = ChatModeratorLevel.None;

    public boolean canSpectate() { return level == ChatModeratorLevel.Moderator; }
    public Rank getRank() {
        for(Rank rank : Rank.values()) {
            if(experience >= rank.minExperience && experience < rank.maxExperience) return rank;
        }

        return Rank.Legend;
    }
}
