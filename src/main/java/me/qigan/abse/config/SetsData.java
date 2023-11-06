package me.qigan.abse.config;

public class SetsData<T> {
    public final String setId;
    public final String guiName;
    public final ValType dataType;
    public final T defVal;


    public SetsData(String setId, String guiName, ValType dataType, T defVal) {
        this.setId = setId;
        this.guiName = guiName;
        this.dataType = dataType;
        this.defVal = defVal;
    }
}
