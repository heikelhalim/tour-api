package com.insider.travel.japan.wabisabi.vacaite.console.account.utils;

import com.insider.travel.japan.wabisabi.common.utils.StringEscapeUtil;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * 
 */
public class HttpRequestUtil {
    
    private static final Logger LOG = LoggerFactory.getLogger(HttpRequestUtil.class);

    /** 
     * API呼び出し制限オーバー時のHTTPステータスコード 
     * EE7のjavax.ws.rs.core.Response.Statusに定数定義されていないので別途用意した
     */
    public static final int TOO_MANY_REQUESTS = 429;

    private static final int IP_ADDRESS_LENGTH = 39;
    private static final String IP_ADDRESS_PATTERN = "[0-9A-Za-z:.]*";

    /**
     * HTTPヘッダX-Forwarded-Forの値を返します
     * @param httpRequest
     * @return 
     */
    public static String getXForwardedFor(HttpServletRequest httpRequest) {
        String value = httpRequest.getHeader("X-Forwarded-For");
        if (value == null) {
            return null;
        }
        // リバースプロクシを経由するとカンマ区切りで値が入っている。先頭のものがユーザのIPアドレス
        // https://tools.ietf.org/html/draft-petersson-forwarded-for-02#section-5.2
        int comma = value.indexOf(",");
        if (comma == -1) {
            return value;
        }
        return value.substring(0, comma);
    }

    /**
     * IPアドレスの妥当性を検証します 
     * @param httpRequest
     * @return 
     */
    public static boolean isValid(String ipAddress) {
        if (ipAddress == null) {
            return true;
        }
        if (ipAddress.length() > IP_ADDRESS_LENGTH) {
            LOG.warn(SiteLogMessage.WARN_IPADDRESS_INCORRECT_LENGTH.getMessage());
            return false;
        }
        if (! ipAddress.matches(IP_ADDRESS_PATTERN)) {
            LOG.warn(StringEscapeUtil.escapeForLog(SiteLogMessage.WARN_IPADDRESS_INCORRECT.getMessage(ipAddress)));
            return false;
        }
        return true;
    }
    
}
