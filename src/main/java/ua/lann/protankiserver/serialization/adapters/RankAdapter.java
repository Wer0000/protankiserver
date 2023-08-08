package ua.lann.protankiserver.serialization.adapters;

import com.squareup.moshi.ToJson;
import ua.lann.protankiserver.enums.Rank;

public class RankAdapter {
    @ToJson public int toJson(Rank rank) {
        return rank.getNumber();
    }
}
