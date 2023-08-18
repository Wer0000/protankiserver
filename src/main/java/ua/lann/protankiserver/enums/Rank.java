package ua.lann.protankiserver.enums;

public enum Rank {
    Recruit(0, 100, 0),
    Private(100, 500, 10),
    Gefreiter(500, 1500, 40),
    Corporal(1500, 3700, 120),
    MasterCorporal(3700, 7100, 230),
    Sergeant(7100, 12300, 420),
    StaffSergeant(12300, 20000, 740),
    MasterSergeant(20000, 29000, 950),
    FirstSergeant(29000, 41000, 1400),
    SergeantMajor(41000, 57000, 2000),
    WarrantOfficer1(57000, 76000, 2500),
    WarrantOfficer2(76000, 98000, 3100),
    WarrantOfficer3(98000, 125000, 3900),
    WarrantOfficer4(125000, 156000, 4600),
    WarrantOfficer5(156000, 192000, 5600),
    ThirdLieutenant(192000, 233000, 6600),
    SecondLieutenant(233000, 280000, 7900),
    FirstLieutenant(280000, 332000, 8900),
    Captain(332000, 390000, 10000),
    Major(390000, 455000, 12000),
    LieutenantColonel(455000, 527000, 14000),
    Colonel(527000, 606000, 16000),
    Brigadier(606000, 692000, 17000),
    MajorGeneral(692000, 787000, 20000),
    LieutenantGeneral(787000, 889000, 22000),
    General(889000, 1000000, 24000),
    Marshal(1000000, 1122000, 28000),
    Fieldmarshal(1122000, 1255000, 31000),
    Commander(1255000, 1400000, 34000),
    Generalissimo(1400000, 0, 37000);

    public final int minExperience;
    public final int maxExperience;
    public final int crystalBonus;

    Rank(int a, int b, int c) {
        this.minExperience = a;
        this.maxExperience = b;
        this.crystalBonus = c;
    }

    public int getNumber() {
        return ordinal() + 1;
    }

    public static Rank getRankByNumber(int number) {
        for (Rank rank : Rank.values()) {
            if(rank.getNumber() == number) return rank;
        }

        return Rank.Generalissimo;
    }

    public static Rank getRankByExp(int experience) {
        for (Rank rank : Rank.values()) {
            if (experience >= rank.minExperience && experience < rank.maxExperience) {
                return rank;
            }
        }

        return Rank.Generalissimo;
    }

    public Rank nextRank() {
        int ordinal = getNumber() + 1;
        return ordinal > 31 ? Rank.Generalissimo : Rank.values()[ordinal];
    }
}
