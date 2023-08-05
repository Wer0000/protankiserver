package ua.lann.protankiserver.enums;

public enum Rank {
    Recruit(0, 100),
    Private(100, 500),
    Gefreiter(500, 1500),
    Corporal(1500, 3700),
    MasterCorporal(3700, 7100),
    Sergeant(7100, 12300),
    StaffSergeant(12300, 20000),
    MasterSergeant(20000, 29000),
    FirstSergeant(29000, 41000),
    SergeantMajor(41000, 57000),
    WarrantOfficer1(57000, 76000),
    WarrantOfficer2(76000, 98000),
    WarrantOfficer3(98000, 125000),
    WarrantOfficer4(125000, 156000),
    WarrantOfficer5(156000, 192000),
    ThirdLieutenant(192000, 233000),
    SecondLieutenant(233000, 280000),
    FirstLieutenant(280000, 332000),
    Captain(332000, 390000),
    Major(390000, 455000),
    LieutenantColonel(455000, 527000),
    Colonel(527000, 606000),
    Brigadier(606000, 692000),
    MajorGeneral(692000, 787000),
    LieutenantGeneral(787000, 889000),
    General(889000, 1000000),
    Marshal(1000000, 1122000),
    Fieldmarshal(1122000, 1255000),
    Commander(1255000, 1400000),
    Generalissimo(1400000, 0);

    public final int minExperience;
    public final int maxExperience;

    Rank(int a, int b) {
        this.minExperience = a;
        this.maxExperience = b;
    }

    public int getNumber() {
        return ordinal() + 1;
    }

    public Rank nextRank() {
        int ordinal = getNumber() + 1;
        return ordinal > 31 ? Rank.Generalissimo : Rank.values()[ordinal];
    }
}
