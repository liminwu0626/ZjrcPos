package com.whty.zjrcpos.entity;

import com.google.gson.annotations.SerializedName;

/**
* @function PersonalInfoInputEntity 个人信息录入响应实体类
* @author wulimin
* @time 2017/3/14 下午4:41
*/


public class PersonalInfoInputEntity extends BaseEntity{

    @SerializedName("KEHKHH")
    private String KEHKHH;
    @SerializedName("KEHUXM")
    private String KEHUXM;
    @SerializedName("KHZHLX")
    private String KHZHLX;
    @SerializedName("KHZJHM")
    private String KHZJHM;
    @SerializedName("JYJIEG")
    private String JYJIEG;
    @SerializedName("TxnStatus")
    private String TxnStatus;
    @SerializedName("TxnSeqId")
    private String TxnSeqId;

    public String getKEHKHH() {
        return KEHKHH;
    }

    public void setKEHKHH(String KEHKHH) {
        this.KEHKHH = KEHKHH;
    }

    public String getKEHUXM() {
        return KEHUXM;
    }

    public void setKEHUXM(String KEHUXM) {
        this.KEHUXM = KEHUXM;
    }

    public String getKHZHLX() {
        return KHZHLX;
    }

    public void setKHZHLX(String KHZHLX) {
        this.KHZHLX = KHZHLX;
    }

    public String getKHZJHM() {
        return KHZJHM;
    }

    public void setKHZJHM(String KHZJHM) {
        this.KHZJHM = KHZJHM;
    }

    public String getJYJIEG() {
        return JYJIEG;
    }

    public void setJYJIEG(String JYJIEG) {
        this.JYJIEG = JYJIEG;
    }

    public String getTxnStatus() {
        return TxnStatus;
    }

    public void setTxnStatus(String txnStatus) {
        TxnStatus = txnStatus;
    }

    public String getTxnSeqId() {
        return TxnSeqId;
    }

    public void setTxnSeqId(String txnSeqId) {
        TxnSeqId = txnSeqId;
    }

    @Override
    public String toString() {
        return "PersonalInfoInputEntity{" +
                "KEHKHH='" + KEHKHH + '\'' +
                ", KEHUXM='" + KEHUXM + '\'' +
                ", KHZHLX='" + KHZHLX + '\'' +
                ", KHZJHM='" + KHZJHM + '\'' +
                ", JYJIEG='" + JYJIEG + '\'' +
                ", TxnStatus='" + TxnStatus + '\'' +
                ", TxnSeqId='" + TxnSeqId + '\'' +
                '}';
    }
}
