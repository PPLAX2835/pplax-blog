package xyz.pplax.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import xyz.pplax.auth.constant.OauthClientConfigConstant;
import xyz.pplax.auth.po.OauthClientDetails;
import xyz.pplax.auth.pojo.OauthClientDetailsPojo;
import xyz.pplax.auth.vo.OauthClientDetailsVO;
import xyz.pplax.core.exception.oauth.OauthException;
import xyz.pplax.core.util.BeanUtils;
import xyz.pplax.core.util.lambda.AssertUtils;
import xyz.pplax.data.entity.Condition;
import xyz.pplax.data.entity.PageData;
import xyz.pplax.data.util.PageUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Oauth服务层
 */
@Service
public class OauthClientDetailsService {

    @Autowired
    private PPLAXOauthClientDetailsService pplaxOauthClientDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;



    /**
     * 新增验证客户端信息
     * @param oauthClientDetailsPojo
     */
    public void insertOauthClient(OauthClientDetailsPojo oauthClientDetailsPojo) {
        // 检查数据是否合法
        AssertUtils.ifNullThrow(oauthClientDetailsPojo, () -> new OauthException("必须存在客户端信息"));
        AssertUtils.ifNullThrow(oauthClientDetailsPojo.getClientId(), () -> new OauthException("必须存在clientId"));
        AssertUtils.ifNullThrow(oauthClientDetailsPojo.getAuthorizedGrantTypes(), () -> new OauthException("必须存在授权类型"));
        AssertUtils.ifNullThrow(oauthClientDetailsPojo.getClientSecret(), () -> new OauthException("必须要有密码"));

        // 判断此clientId是否存在
        OauthClientDetails oauthClientDetails = pplaxOauthClientDetailsService.queryOne(new OauthClientDetails() {{
            setClientId(oauthClientDetailsPojo.getClientId());
        }});
        AssertUtils.stateThrow(oauthClientDetails == null, () -> new OauthException("此clientId已经存在"));

        // 判断授权类型
        String authorizedGrantTypes = assertAuthorizedGrantTypes(oauthClientDetailsPojo);
        AssertUtils.stateThrow(StringUtils.hasLength(authorizedGrantTypes), () -> new OauthException("没有授权类型"));

        // 编码密码
        oauthClientDetailsPojo.setClientSecret(passwordEncoder.encode(oauthClientDetailsPojo.getClientSecret()));

        // 添加到数据库
        pplaxOauthClientDetailsService.insert(BeanUtils.copyProperties(oauthClientDetailsPojo, OauthClientDetails.class));
    }


    /**
     * 物理删除
     * @param oauthClientDetailsPojo
     * @return
     */
    public int physicalDeleteOauthClient(OauthClientDetailsPojo oauthClientDetailsPojo) {
        String clientId = oauthClientDetailsPojo.getClientId();

        // clientId为空就抛异常
        AssertUtils.ifNoLengthThrow(clientId, () -> new OauthException("clientId不能为null"));

        // 封装clientId到OauthClientDetails
        OauthClientDetails oauthClientDetails = new OauthClientDetails();
        oauthClientDetails.setClientId(clientId);

        // 执行物理删除
        return pplaxOauthClientDetailsService.deleteByWhere(oauthClientDetails);
    }


    /**
     * 更新验证客户端信息
     * @param oauthClientDetailsPojo
     * @return
     */
    public int updateOauthClient(OauthClientDetailsPojo oauthClientDetailsPojo) {
        // 检查数据完整
        AssertUtils.ifNullThrow(oauthClientDetailsPojo, () -> new OauthException("必须存在客户端信息"));
        AssertUtils.ifNullThrow(oauthClientDetailsPojo.getClientId(), () -> new OauthException("必须存在clientId"));

        // 判断授权类型是存在
        if (StringUtils.hasLength(oauthClientDetailsPojo.getAuthorizedGrantTypes())) {
            String authorizedGrantTypes = assertAuthorizedGrantTypes(oauthClientDetailsPojo);
            // 如果存在授权类型，封装，否则置空
            if (StringUtils.hasLength(authorizedGrantTypes)) {
                oauthClientDetailsPojo.setAuthorizedGrantTypes(authorizedGrantTypes);
            } else {
                oauthClientDetailsPojo.setAuthorizedGrantTypes(null);
            }
        }

        // 判断是否存在密钥
        if (StringUtils.hasLength(oauthClientDetailsPojo.getClientSecret())) {
            // 编码密码
            oauthClientDetailsPojo.setClientSecret(passwordEncoder.encode(oauthClientDetailsPojo.getClientSecret()));
        }

        // 执行更新操作
        return pplaxOauthClientDetailsService.updateById(BeanUtils.copyProperties(oauthClientDetailsPojo, OauthClientDetails.class));
    }


    /**
     * 查询列表，条件查询
     * @param condition
     * @return
     */
    public PageData<OauthClientDetailsVO> queryListOauthClientByCondition(Condition<Object> condition) {
        return PageUtils.copyPageDataResult(pplaxOauthClientDetailsService.queryListByCondition(condition), OauthClientDetailsVO.class);
    }

    /**
     * 通过clientId查询
     * @param pojo
     * @return
     */
    public OauthClientDetailsVO queryOauthClientByClientId(OauthClientDetailsPojo pojo) {
        // 检查必须参数
        AssertUtils.ifNullThrow(pojo, () -> new OauthException("必须存在客户端信息"));
        AssertUtils.ifNullThrow(pojo.getClientId(), () -> new OauthException("必须存在clientId"));

        // 封装
        OauthClientDetails oauthClientDetails = new OauthClientDetails();
        oauthClientDetails.setClientId(pojo.getClientId());

        // 查询
        return BeanUtils.copyProperties(pplaxOauthClientDetailsService.queryOne(oauthClientDetails), OauthClientDetailsVO.class);
    }


    private String assertAuthorizedGrantTypes(OauthClientDetailsPojo oauthClientDetailsPojo) {
        return Arrays.stream(oauthClientDetailsPojo.getAuthorizedGrantTypes().split(","))
                .filter(OauthClientConfigConstant.AUTHORIZED_GRANT_TYPES::contains)
                .collect(Collectors.joining(","));
    }
}
