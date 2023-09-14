package xyz.pplax.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import xyz.pplax.admin.vo.NavigationVO;

import java.util.List;




@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NavigationDTO {

    @Schema(title = "用户uid")
    private Long userUid;

    @Schema(title = "父导航数量")
    private Integer ParentNavigationLength;

    @Schema(title = "该用户的所有导航节点信息")
    private List<ParentNavigation> navigationList;

    @EqualsAndHashCode(callSuper = true)
    @Data
    static class ParentNavigation extends NavigationVO {
        /**
         * 该父导航行的子导航集合
         */
        private List<NavigationVO> sonNavigationList;
    }
}

