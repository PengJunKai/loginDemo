package com.peng.constant;

/**
 * Created by PengJK on 2018/3/26.
 */
public enum ModuleType {

    Movie("Movie","影"),

    Teleplay("Teleplay","剧"),

    Book("Book","书"),

    Pet("Pet","宠");

    private String module;
    private String moduleName;

    ModuleType(String module, String moduleName) {
        this.module = module;
        this.moduleName = moduleName;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
