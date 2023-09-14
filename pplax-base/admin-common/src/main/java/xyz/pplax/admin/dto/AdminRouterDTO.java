package xyz.pplax.admin.dto;

import lombok.Data;
import xyz.pplax.admin.vo.AdminRouterVO;

import java.util.List;



@Data
public class AdminRouterDTO extends AdminRouterVO {

    private List<AdminRouterDTO> children;
}
