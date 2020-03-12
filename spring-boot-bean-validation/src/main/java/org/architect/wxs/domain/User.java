package org.architect.wxs.domain;
import org.architect.wxs.validation.constraints.ValidCardNum;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * {@link}
 *
 * @author wangxiaoshuai on 2020/2/23
 * @javadoc ：
 */
public class User {
    @Max(value = 10000)
    private long id;
    @NotNull
    private String name;
    //GUPAO-123456789
    @NotNull
    @ValidCardNum(message = "卡号校验失败")
    private String cardNum;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }
}
