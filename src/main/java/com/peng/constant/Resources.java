package com.peng.constant;

/**
 * Created by Rukiy on 2017-12-04
 */
public class Resources {

    public static final String RESOURCES_PATH = "/,*.html,*.woff2,*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid,/druid/*,/swagger-resources,/swagger-resources/*,/v2/api-docs*,/swagger*";

    public static String[] RESOURCES_PATH_ARRAY = RESOURCES_PATH.split("\\s*,\\s*");
    
    public static String[] token = new String[]{"/manage","/manage/*","/druid","/druid/*","/swagger-resources","/swagger-resources/*","/swagger*"};
}
