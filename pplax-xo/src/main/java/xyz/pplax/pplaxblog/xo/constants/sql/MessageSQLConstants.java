package xyz.pplax.pplaxblog.xo.constants.sql;

import xyz.pplax.pplaxblog.commons.constants.BaseSQLConstants;

/**
 * message表的sql字段常量
 */
public class MessageSQLConstants extends BaseSQLConstants {

    public final static String C_UID = "t_message.uid";
    public final static String C_CREATE_TIME = "t_message.create_time";
    public final static String C_UPDATE_TIME = "t_message.update_time";
    public final static String C_STATUS = "t_message.status";

    public final static String USER_UID = "t_message.user_uid";
    public final static String READ_USER_UIDS = "t_message.read_user_uids";
    public final static String IP = "t_message.ip";
    public final static String ADDRESS = "t_message.address";
    public final static String STATUS = "t_message.status";
    public final static String CHAT_ROOM_UID = "t_message.chat_room_uid";
    public final static String CONTENT = "t_message.content";
    public final static String TYPE = "t_message.type";
}
