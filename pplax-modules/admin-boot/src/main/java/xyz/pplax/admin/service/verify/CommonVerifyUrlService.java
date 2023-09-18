package xyz.pplax.admin.service.verify;

/**
 * 通用的验证账户信息的service
 */
public interface CommonVerifyUrlService {

    boolean bindEmail(String incomingSecretKey);

    boolean removeAccountDisable(String incomingSecretKey);
}
