package com.example.practicalfive;

public class TextProcessor {

    private static final String[] NAMES = {"Beijing", "Shanghai", "Chongqing", "Tianjin"};

    // 验证并返回首字母大写的正确城市名，无效返回 null
    public String processMunicipality(String municipality) {
        if (municipality == null) return null;
        String trimmed = municipality.trim();
        if (trimmed.isEmpty()) return null;
        for (String name : NAMES) {
            if (name.equalsIgnoreCase(trimmed)) {
                return name;
            }
        }
        return null;
    }

    // 获取城市在数组中的索引 (注意数组顺序和表格顺序不一样，用这个方法映射最安全)
    public int indexOf(String municipality) {
        String normalized = processMunicipality(municipality);
        if (normalized == null) return -1;
        for (int i = 0; i < NAMES.length; i++) {
            if (NAMES[i].equals(normalized)) return i;
        }
        return -1;
    }
}