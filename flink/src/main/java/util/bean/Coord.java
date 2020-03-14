package util.bean;

import lombok.Data;

/**
 * Created by yidxue on 2019/3/16
 */
@Data
public class Coord {
    private int id;
    private int x;
    private int y;

    public Coord(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }
}