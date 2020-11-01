package cn.itlym.platform.uaa.core.model.converter;

import cn.itlym.platform.uaa.core.model.*;
import cn.itlym.platform.uaa.storage.po.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 模型转换
 *
 * @author lym
 */
@Mapper(componentModel = "spring") // 置为 spring 则默认注入 bean
public interface ModelConverter {

    ModelConverter CONVERTER = Mappers.getMapper(ModelConverter.class);

    UserGroup toModel(UserGroupPO po);

    UserGroupPO toPo(UserGroup model);

    // ----

    UserInfo toModel(UserInfoPO po);

    UserInfoPO toPo(UserInfo model);

    // ----

    UserLoginInfo toModel(UserLoginInfoPO po);

    UserLoginInfoPO toPo(UserLoginInfo model);

    // ----

    UserLoginRecordFail toModel(UserLoginRecordFailPO po);

    UserLoginRecordFailPO toPo(UserLoginRecordFail model);

    // ----

    UserLoginRecordSuccess toModel(UserLoginRecordSuccessPO po);

    UserLoginRecordSuccessPO toPo(UserLoginRecordSuccess model);

    // ----

}