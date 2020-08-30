package myenum;


import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * User: caserwin
 * Date: 2020-06-14 17:11
 * Description:
 */

@Data
public class EnumDemo {
    private Long itemId;
    private Double score;
    private Type ype;

    public enum Type {

        /**
         *
         */
        TYPE_1(1, "type_1"),
        /**
         *
         */
        TYPE_2(2, "type_2"),
        /**
         *
         */
        TYPE_3(3, "type_3"),
        /**
         *
         */
        TYPE_4(4, "type_4");

        private Integer id;
        private String name;

        Type(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public static boolean contains(Type testRecaller) {
            for (Type recaller : Type.values()) {
                if (recaller.name.equals(testRecaller.name)) {
                    return true;
                }
            }
            return false;
        }

        public static Map<Integer, Type> vMap = new HashMap<Integer, Type>() {{
            for (Type matchType : Type.values()) {
                put(matchType.getId(), matchType);
            }
        }};
    }

    public static void main(String[] args) {
        System.out.println(Type.TYPE_1.getId() + " -> " + Type.TYPE_1.getName());
        System.out.println(Type.vMap);
    }
}


