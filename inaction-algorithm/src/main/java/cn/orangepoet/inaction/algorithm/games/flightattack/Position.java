package cn.orangepoet.inaction.algorithm.games.flightattack;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chengzhi
 * @date 2019/10/24
 */
@Getter
@EqualsAndHashCode
public class Position {
    private final Integer x;
    private final Integer y;
    private final static Map<String, Position> POSITION_MAP = new HashMap<>();

    private Position(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }

    public static Position get(int x, int y) {
        String key = String.format("%s-%s", x, y);
        Position position = POSITION_MAP.get(key);
        if (position == null) {
            position = new Position(x, y);
            POSITION_MAP.put(key, position);
        }
        return position;
    }
}
