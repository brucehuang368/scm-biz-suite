package com.doublechaintech.retailscm.search;

public enum QueryOperator {
    /**
     * 等于,如fieldName = 5
     */
    EQUAL(" = ", "", ""),
    /**
     * 不等于,如fieldName <> 5
     */
    NOT_EQUAL(" <> ", "", ""),
    /**
     * 大于,如fieldName > 5
     */
    GREATER_THAN(" > ", "", ""),
    /**
     * 大于等于,如fieldName >= 5
     */
    GREATER_THAN_OR_EQUAL(" >= ", "", ""),
    /**
     * 小于,如fieldName < 5
     */
    LESS_THAN(" <  ", "", ""),
    /**
     * 小于等于,如fieldName <= 5
     */
    LESS_THAN_OR_EQUAL(" <= ", "", ""),
    /**
     * 字符串的结束于,指以该字符串结尾,封装sql时,值加单引号,如fieldName like '%value'
     */
    END_WITH(" like ", "%", ""),
    /**
     * 字符串的不结束于,指不以该字符串结尾,封装sql时,值加单引号,如fieldName not like '%value'
     */
    NOT_END_WITH(" not like ", "%", ""),
    /**
     * 字符串的开始于,指以该字符串开头,封装sql时,值加单引号,如fieldName like 'value%'
     */
    BEGIN_WITH(" like ", "", "%"),
    /**
     * 字符串的不开始于,指不以该字符串开头,封装sql时,值加单引号,如fieldName not like 'value%'
     */
    NOT_BEGIN_WITH(" not like ", "", "%"),
    /**
     * 字符串的包含,指包含该字符串,封装sql时,值加单引号,如fieldName like '%value%'
     */
    CONTAIN(" like ", "%", "%"),
    /**
     * 字符串的不包含,指不包含该字符串,封装sql时,值加单引号,如fieldName not like '%value%'
     */
    NOT_CONTAIN(" not like ", "%", "%"),
    /**
     * 查询不为null的字段,如fieldName is not null
     */
    IS_NOT_NULL(" is not null ", "", ""),
    /**
     * 查询为null的字段,如fieldName is null
     */
    IS_NULL(" is null ", "", ""),
    /**
     * in 条件查询,如fieldName in ('1','2','3','4')
     */
    IN(" in ", "(", ")"),
    /**
     * not in 条件查询,如fieldName not in ('1','2','3','4')
     */
    NOT_IN(" not in ", "(", ")");



    private String operator; // 操作符,如 =,>=,in
    private String prefix; // 查询的值的前缀,如%,(
    private String suffix; // 查询的值的后缀,如%,)

    private QueryOperator(String operator, String prefix, String suffix) {
        this.setOperator(operator);
        this.setPrefix(prefix);
        this.setSuffix(suffix);
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public boolean hasParameter(){
        return this != IS_NULL && this != IS_NOT_NULL;
    }

    public boolean containsOperator() {
        return this == CONTAIN || this == BEGIN_WITH || this == END_WITH || this == NOT_CONTAIN
            || this == NOT_BEGIN_WITH || this == NOT_END_WITH;
    }

    public boolean hasArrayParameter(){
        return this == IN || this == NOT_IN;
    }
}


