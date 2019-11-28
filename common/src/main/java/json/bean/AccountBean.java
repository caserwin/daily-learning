package json.bean;

/**
 * @author yidxue
 */
public class AccountBean {
    private String accountId;
    private int type;
    private DataBean data;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return this.accountId + "\t" + this.type;
    }
}
