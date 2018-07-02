package jdbc.bean;

/**
 * Created by yidxue on 2018/6/30
 */
public class PersonRecord extends BaseRecord {

    public PersonRecord(String cls) {
        super(cls);
    }

    @Override
    public PersonRecord buildFields(String... args) {
        super.setColAndValue("id", args[0]);
        super.setColAndValue("name", args[1]);
        super.setColAndValue("age", args[2]);
        super.setColAndValue("gender", args[3]);
        return this;
    }

    public static void main(String[] args) {
        PersonRecord record1 = new PersonRecord("person").buildFields("1", "erwin1", "19", "male");
        System.out.println(record1.toString());
    }
}