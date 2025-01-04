package com.mw.tool;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

public class BeanPropertyRowMapperWithPrefix<T> extends BeanPropertyRowMapper<T> {
    private String columnPrefix;

    /* 限定使用该构造方法，可以设置列名的前缀 */
    public BeanPropertyRowMapperWithPrefix(String columnPrefix) {
        super();
        this.columnPrefix = columnPrefix;
    }

    public String getColumnPrefix() {
        return columnPrefix;
    }

    /*
     * 覆盖掉父类的查找ResultSet中列名的方法
     * 如果有前缀，那么列名应添加前缀
     */
    @Override
    protected Set<String> mappedNames(PropertyDescriptor pd) {
        Set<String> mappedNames = new HashSet<>(4);

        if(columnPrefix != null) {
            mappedNames.add(columnPrefix + lowerCaseName(pd.getName()));
            mappedNames.add(columnPrefix + underscoreName(pd.getName()));
        }
        else {
            mappedNames.add(lowerCaseName(pd.getName()));
            mappedNames.add(underscoreName(pd.getName()));
        }

        return mappedNames;
    }
}
