package cn.itlym.shoulder.platform.notify.sms.enums;

public enum MyEnum {

    /**
     *
     */
    V1,
    V2,

    ;

    public static MyEnum from(String source) {
        switch (source) {
            case "2":
                return V2;
            default:
                return V1;
        }
    }

}
