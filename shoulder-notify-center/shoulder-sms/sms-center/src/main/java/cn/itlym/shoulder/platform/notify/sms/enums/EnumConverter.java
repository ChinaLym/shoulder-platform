package cn.itlym.shoulder.platform.notify.sms.enums;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

public class EnumConverter implements Converter<String, Enum<?>> {

    @SuppressWarnings("unchecked")
    private Class<? extends Enum> enumType;

    public EnumConverter(Class<? extends Enum> enumType) {
        this.enumType = enumType;
    }

    @Override
    public Enum convert(@NonNull String source) {
        if (source.isBlank()) {
            return null;
        }
        // 尝试用名称匹配。忽略大小写
        Enum[] enums = enumType.getEnumConstants();
        for (Enum e : enums) {
            if (e.name().equalsIgnoreCase(source)) {
                return e;
            }
        }
        return null;

        // 尝试使用 public static T of(String source) 方法

        // 尝试使用标识字段匹配

        // 尝试使用标识方法匹配
    }


}