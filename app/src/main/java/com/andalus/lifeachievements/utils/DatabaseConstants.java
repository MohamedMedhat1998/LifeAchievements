package com.andalus.lifeachievements.utils;

public class DatabaseConstants {

    public static final String DATABASE_NAME = "app-database";

    public static class PostTable {
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_OWNER_ID = "ownerId";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_ACHIEVEMENT_ID = "achievementId";
        public static final String COLUMN_LIKES = "likes";
        public static final String COLUMN_COMMENTS = "comments";
        public static final String COLUMN_SHARES = "shares";
    }

    public static class UserTable {
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_FIRST_NAME = "firstName";
        public static final String COLUMN_LAST_NAME = "lastName";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_GENDER = "gender";
        public static final String COLUMN_PICTURE = "picture";
        public static final String COLUMN_COUNTRY = "country";
        public static final String COLUMN_PASSWORD = "password";
    }

    public static class AchievementTable {
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_DAYS = "days";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_PUBLISHED = "published";

    }
}
