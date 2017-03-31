package com.whty.zjrcpos.entity;

import com.google.gson.annotations.SerializedName;

/**
* @function CardOpenEntity
* @author wulimin
* @time 2017/3/14 下午4:13
*/


public class CardOpenEntity extends BaseEntity{
//03-11 18:24:42.084 10152-10552/com.whty.zjrcpos D/OkHttp:
// {"KPNUID":"4F0FBED0","HXYWLX":"004","JIO1DM":"AM01","TxnStlmDate":"20160903",
// "TxnDate":"20170311","TxnCode":"AM01","KHZHLX":"01","KHZJHM":"342425199311040211",
// "KPYXQI":"20991231","TxnTime":"182441","ZHUKKH":"1008000051","JYMSHU":"开立账户失败",
// "KEHKHH":"SC000000000000000002","HXJ1LX":"000001","TxnSeqId":"000000000095",
// "KEHUXM":"海宁","J1JGDM":"000000","KPCPDM":"1000000001","JIO1QD":"2809",
// "JIO1GY":"admin","HXJ1QD":"01","TxnStatus":"E","KPANLX":"2000","JYJIEG":"SC78105"}

    @SerializedName("KPNUID")
    private String KPNUID;//客户账户号
    @SerializedName("HXYWLX")
    private String HXYWLX;//主卡号卡号
    @SerializedName("JIO1DM")
    private String JIO1DM;//卡产品代码
    @SerializedName("TxnStlmDate")
    private String TxnStlmDate;//交易结果
    @SerializedName("TxnDate")
    private String TxnDate;//状态
    @SerializedName("TxnCode")
    private String TxnCode;//系统流水号
    @SerializedName("KHZHLX")
    private String KHZHLX;//系统流水号
    @SerializedName("KHZJHM")
    private String KHZJHM;//系统流水号
    @SerializedName("KPYXQI")
    private String KPYXQI;//系统流水号
    @SerializedName("TxnTime")
    private String TxnTime;//系统流水号
    @SerializedName("ZHUKKH")
    private String ZHUKKH;//系统流水号
    @SerializedName("JYMSHU")
    private String JYMSHU;//系统流水号
    @SerializedName("KEHKHH")
    private String KEHKHH;//系统流水号
    @SerializedName("HXJ1LX")
    private String HXJ1LX;//系统流水号
     @SerializedName("TxnSeqId")
    private String TxnSeqId;//系统流水号
    @SerializedName("KEHUXM")
    private String KEHUXM;//系统流水号
    @SerializedName("J1JGDM")
    private String J1JGDM;//系统流水号
    @SerializedName("KPCPDM")
    private String KPCPDM;//系统流水号
    @SerializedName("JIO1QD")
    private String JIO1QD;//系统流水号
    @SerializedName("JIO1GY")
    private String JIO1GY;//系统流水号
    @SerializedName("HXJ1QD")
    private String HXJ1QD;//系统流水号
    @SerializedName("TxnStatus")
    private String TxnStatus;//系统流水号
    @SerializedName("KPANLX")
    private String KPANLX;//系统流水号
    @SerializedName("JYJIEG")
    private String JYJIEG;//系统流水号

    public String getKPNUID() {
        return KPNUID;
    }

    public void setKPNUID(String KPNUID) {
        this.KPNUID = KPNUID;
    }

    public String getHXYWLX() {
        return HXYWLX;
    }

    public void setHXYWLX(String HXYWLX) {
        this.HXYWLX = HXYWLX;
    }

    public String getJIO1DM() {
        return JIO1DM;
    }

    public void setJIO1DM(String JIO1DM) {
        this.JIO1DM = JIO1DM;
    }

    public String getTxnStlmDate() {
        return TxnStlmDate;
    }

    public void setTxnStlmDate(String txnStlmDate) {
        TxnStlmDate = txnStlmDate;
    }

    public String getTxnDate() {
        return TxnDate;
    }

    public void setTxnDate(String txnDate) {
        TxnDate = txnDate;
    }

    public String getTxnCode() {
        return TxnCode;
    }

    public void setTxnCode(String txnCode) {
        TxnCode = txnCode;
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

    public String getKPYXQI() {
        return KPYXQI;
    }

    public void setKPYXQI(String KPYXQI) {
        this.KPYXQI = KPYXQI;
    }

    public String getTxnTime() {
        return TxnTime;
    }

    public void setTxnTime(String txnTime) {
        TxnTime = txnTime;
    }

    public String getZHUKKH() {
        return ZHUKKH;
    }

    public void setZHUKKH(String ZHUKKH) {
        this.ZHUKKH = ZHUKKH;
    }

    public String getJYMSHU() {
        return JYMSHU;
    }

    public void setJYMSHU(String JYMSHU) {
        this.JYMSHU = JYMSHU;
    }

    public String getKEHKHH() {
        return KEHKHH;
    }

    public void setKEHKHH(String KEHKHH) {
        this.KEHKHH = KEHKHH;
    }

    public String getHXJ1LX() {
        return HXJ1LX;
    }

    public void setHXJ1LX(String HXJ1LX) {
        this.HXJ1LX = HXJ1LX;
    }

    public String getTxnSeqId() {
        return TxnSeqId;
    }

    public void setTxnSeqId(String txnSeqId) {
        TxnSeqId = txnSeqId;
    }

    public String getKEHUXM() {
        return KEHUXM;
    }

    public void setKEHUXM(String KEHUXM) {
        this.KEHUXM = KEHUXM;
    }

    public String getJ1JGDM() {
        return J1JGDM;
    }

    public void setJ1JGDM(String j1JGDM) {
        J1JGDM = j1JGDM;
    }

    public String getKPCPDM() {
        return KPCPDM;
    }

    public void setKPCPDM(String KPCPDM) {
        this.KPCPDM = KPCPDM;
    }

    public String getJIO1QD() {
        return JIO1QD;
    }

    public void setJIO1QD(String JIO1QD) {
        this.JIO1QD = JIO1QD;
    }

    public String getJIO1GY() {
        return JIO1GY;
    }

    public void setJIO1GY(String JIO1GY) {
        this.JIO1GY = JIO1GY;
    }

    public String getHXJ1QD() {
        return HXJ1QD;
    }

    public void setHXJ1QD(String HXJ1QD) {
        this.HXJ1QD = HXJ1QD;
    }

    public String getTxnStatus() {
        return TxnStatus;
    }

    public void setTxnStatus(String txnStatus) {
        TxnStatus = txnStatus;
    }

    public String getKPANLX() {
        return KPANLX;
    }

    public void setKPANLX(String KPANLX) {
        this.KPANLX = KPANLX;
    }

    public String getJYJIEG() {
        return JYJIEG;
    }

    public void setJYJIEG(String JYJIEG) {
        this.JYJIEG = JYJIEG;
    }

    @Override
    public String toString() {
        return "CardOpenEntity{" +
                "KPNUID='" + KPNUID + '\'' +
                ", HXYWLX='" + HXYWLX + '\'' +
                ", JIO1DM='" + JIO1DM + '\'' +
                ", TxnStlmDate='" + TxnStlmDate + '\'' +
                ", TxnDate='" + TxnDate + '\'' +
                ", TxnCode='" + TxnCode + '\'' +
                ", KHZHLX='" + KHZHLX + '\'' +
                ", KHZJHM='" + KHZJHM + '\'' +
                ", KPYXQI='" + KPYXQI + '\'' +
                ", TxnTime='" + TxnTime + '\'' +
                ", ZHUKKH='" + ZHUKKH + '\'' +
                ", JYMSHU='" + JYMSHU + '\'' +
                ", KEHKHH='" + KEHKHH + '\'' +
                ", HXJ1LX='" + HXJ1LX + '\'' +
                ", TxnSeqId='" + TxnSeqId + '\'' +
                ", KEHUXM='" + KEHUXM + '\'' +
                ", J1JGDM='" + J1JGDM + '\'' +
                ", KPCPDM='" + KPCPDM + '\'' +
                ", JIO1QD='" + JIO1QD + '\'' +
                ", JIO1GY='" + JIO1GY + '\'' +
                ", HXJ1QD='" + HXJ1QD + '\'' +
                ", TxnStatus='" + TxnStatus + '\'' +
                ", KPANLX='" + KPANLX + '\'' +
                ", JYJIEG='" + JYJIEG + '\'' +
                '}';
    }
}
