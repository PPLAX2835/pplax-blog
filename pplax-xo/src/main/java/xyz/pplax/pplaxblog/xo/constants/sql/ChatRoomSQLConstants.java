package xyz.pplax.pplaxblog.xo.constants.sql;

import xyz.pplax.pplaxblog.commons.constants.BaseSQLConstants;

/**
 * comment表的sql字段常量
 */
public class ChatRoomSQLConstants extends BaseSQLConstants {

    public final static String C_UID = "t_chat_room.uid";
    public final static String C_CREATE_TIME = "t_chat_room.create_time";
    public final static String C_UPDATE_TIME = "t_chat_room.update_time";
    public final static String C_STATUS = "t_chat_room.status";

    public final static String NAME = "t_chat_room.name";
    public final static String MEMBER_UIDS = "t_chat_room.member_uids";
    public final static String OWNER_UID = "t_chat_room.owner_uid";
    public final static String AVATAR_UID = "t_chat_room.avatar_uid";
    public final static String TYPE = "t_chat_room.type";
}
