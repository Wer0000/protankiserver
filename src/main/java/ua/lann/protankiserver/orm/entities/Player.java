package ua.lann.protankiserver.orm.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ua.lann.protankiserver.enums.ChatModeratorLevel;
import ua.lann.protankiserver.enums.Rank;
import ua.lann.protankiserver.security.BCryptHasher;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Getter
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "playerFriends",
            joinColumns = @JoinColumn(name = "playerId"),
            inverseJoinColumns = @JoinColumn(name = "friendId")
    ) private final Set<Player> friends = new HashSet<>();

    @Getter
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private final Set<FriendRequest> outgoingFriendRequests = new HashSet<>();

    @Getter
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private final Set<FriendRequest> incomingFriendRequests = new HashSet<>();

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

        return Rank.Generalissimo;
    }
}
