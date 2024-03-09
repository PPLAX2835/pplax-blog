package xyz.pplax.pplaxblog.xo.dto.list;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.pplax.pplaxblog.commons.validator.Messages;
import xyz.pplax.pplaxblog.commons.validator.annotion.LongNotNull;
import xyz.pplax.pplaxblog.commons.validator.group.GetList;
import xyz.pplax.pplaxblog.xo.base.dto.PageDto;

/**
 * 获得用户列表用到的dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserGetListDto extends PageDto {

    private String username;

    private String nickname;

}
