package ua.lann.protankiserver.orm.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;
import ua.lann.protankiserver.enums.ChatModeratorLevel;
import ua.lann.protankiserver.enums.Rank;
import ua.lann.protankiserver.models.PlayerPermissionsBitfield;
import ua.lann.protankiserver.orm.HibernateUtils;
import ua.lann.protankiserver.orm.converters.PlayerPermissionsBitfieldConverter;
import ua.lann.protankiserver.orm.entities.garage.EquippedTankData;
import ua.lann.protankiserver.orm.entities.garage.OwnedGarageItem;
import ua.lann.protankiserver.security.BCryptHasher;

import java.util.*;

@Entity
@Getter @Setter
public class Player {
    @Getter @Id
    private String nickname;

    @Getter @Column
    private String password;

    @Getter @Setter
    @Column private int crystals = 0;

    @Getter @Setter
    @Column private int experience = 0;

    @Getter @Setter
    @Convert(converter = PlayerPermissionsBitfieldConverter.class)
    @Column private PlayerPermissionsBitfield permissions = new PlayerPermissionsBitfield(0);

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column private ChatModeratorLevel chatModeratorLevel = ChatModeratorLevel.None;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "equipment_id")
    private EquippedTankData equipment = new EquippedTankData("smoky", "hunter", "green");

    @Getter @Setter @Column private int healthCount;
    @Getter @Setter @Column private int armorCount;
    @Getter @Setter @Column private int doubleDamageCount;
    @Getter @Setter @Column private int nitroCount;
    @Getter @Setter @Column private int mineCount;

    @Getter @Setter
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "owned_items")
    private List<OwnedGarageItem> ownedItems = List.of(
        new OwnedGarageItem("smoky", 0),
        new OwnedGarageItem("hunter", 0),
        new OwnedGarageItem("green", 0),
        new OwnedGarageItem("holiday", 0),

        new OwnedGarageItem("health", 0),
        new OwnedGarageItem("armor", 0),
        new OwnedGarageItem("double_damage", 0),
        new OwnedGarageItem("n2o", 0),
        new OwnedGarageItem("mine", 0)
    );

    @Getter
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "playerFriends",
            joinColumns = @JoinColumn(name = "playerId"),
            inverseJoinColumns = @JoinColumn(name = "friendId")
    )
    private final Set<Player> friends = new HashSet<>();

    @Getter
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private final Set<FriendRequest> outgoingFriendRequests = new HashSet<>();

    @Getter
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private final Set<FriendRequest> incomingFriendRequests = new HashSet<>();

    public Player() {}
    public Player(String nickname) {
        this.nickname = nickname;
    }

    public void setPassword(String password) {
        this.password = BCryptHasher.hash(password);
    }

    public boolean canSpectate() {
        return chatModeratorLevel == ChatModeratorLevel.Moderator;
    }

    public Rank getRank() {
        for (Rank rank : Rank.values()) {
            if (experience >= rank.minExperience && experience < rank.maxExperience) {
                return rank;
            }
        }

        return Rank.Generalissimo;
    }

    public static boolean exists(String nickname) {
        try(Session session = HibernateUtils.session()) {
            String hql = "SELECT COUNT(b) FROM Player b WHERE b.nickname = :nickname";
            Query query = session.createQuery(hql, Long.class);
            query.setParameter("nickname", nickname);
            return (Long) query.getSingleResult() != 0;
        }
    }
}

