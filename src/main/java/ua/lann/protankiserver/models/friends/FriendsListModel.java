package ua.lann.protankiserver.models.friends;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class FriendsListModel {
    private List<String> friendsAccepted = new ArrayList<>();
    private List<String> friendsAcceptedNew = new ArrayList<>();
    private List<String> friendsIncoming = new ArrayList<>();
    private List<String> friendsIncomingNew = new ArrayList<>();
    private List<String> friendsOutgoing = new ArrayList<>();
}
