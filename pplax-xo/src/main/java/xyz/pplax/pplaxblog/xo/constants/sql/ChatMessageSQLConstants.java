package xyz.pplax.pplaxblog.xo.constants.sql;

import xyz.pplax.pplaxblog.commons.constants.BaseSQLConstants;

/**
 * chat_message表的sql字段常量
 */
public class ChatMessageSQLConstants extends BaseSQLConstants {

    public final static String C_UID = "t_chat_message.uid";
    public final static String C_CREATE_TIME = "t_chat_message.create_time";
    public final static String C_UPDATE_TIME = "t_chat_message.update_time";
    public final static String C_STATUS = "t_chat_message.status";

    public final static String USER_UID = "t_chat_message.user_uid";
    public final static String CHAT_ROOM_UID = "t_chat_message.chat_room_uid";
    public final static String CONTENT = "t_chat_message.content";
}
