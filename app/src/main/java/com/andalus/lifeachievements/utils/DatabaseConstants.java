package com.andalus.lifeachievements.utils;

public class DatabaseConstants {

    public static final String DATABASE_NAME = "app-database";

    public static class PostTable {
        public static final String COLUMN_OWNER_ID = "ownerId";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_ACHIEVEMENT_ID = "achievementId";
        public static final String COLUMN_LIKES = "likes";
        public static final String COLUMN_COMMENTS = "comments";
        public static final String COLUMN_SHARES = "shares";
    }

    public static class UserTable {
        public static final String ID = "id";
        public static final String FIRST_NAME = "firstName";
        public static final String LAST_NAME = "lastName";
        public static final String EMAIL = "email";
        public static final String PHONE = "phone";
        public static final String USERNAME = "username";
        public static final String GENDER = "gender";
        public static final String PICTURE = "picture";
        public static final String COUNTRY = "country";
        public static final String PASSWORD = "password";
    }

    public static class AchievementTable {
        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String DAYS = "days";
        public static final String TYPE = "type";
        public static final String PUBLISHED = "published";

    }
}
