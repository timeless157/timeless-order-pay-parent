package com.timeless.constants;

public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(20000, "操作成功"),
    SYSTEM_ERROR(20001, "服务器内部错误"),
    STOCK_NOT_ENOUGH(20002, "库存不足"),
    SECKILL_PRODUCT_NOT_EXIST(20003, "秒杀商品不存在"),
    CONTINUE_PAY(20004, "待付款"),
    DONE_PAY(20005, "已付款"),
    RSACHECK_FAIL(20006, "验证签名失败!"),
    REFUND_FAIL(20007, "退款失败!"),
    DONE_REFUND(20008, "已退款"),
    ORDER_CANCEL(20009, "已取消"),
    PAY_FAIL(20010, "付款失败!");


    public static final Long USERID = 1000L;
    int code;
    String msg;

    AppHttpCodeEnum(int code, String Message) {
        this.code = code;
        this.msg = Message;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
