package ua.lann.protankiserver.orm.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class FriendRequest {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter @ManyToOne
    @JoinColumn(name = "senderId")
    private Player sender;

    @Getter @Setter @ManyToOne
    @JoinColumn(name = "receiverId")
    private Player receiver;
}
