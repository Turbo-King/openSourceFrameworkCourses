package com.xzit.domain;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * \* Created with IntelliJ IDEA.
 * \* Author: wzh
 * \* Date: 2023/10/8
 * \* TODO
 * \* Description:
 * \
 */
@Data
public class CollectionPropertyDI {
    private String[] strs;
    private List<Department> departmentList;
    private Set<String> stringSet;
    private Map<String, String> map;
    private Properties p;
}
