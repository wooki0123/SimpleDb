package com.back.simpleDb;

import lombok.SneakyThrows;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class Sql {
    private final SimpleDb simpleDb;

    // sql 쿼리를 담는 StringBuilder와 쿼리의 파라미터를 담는 List
    private final StringBuilder stringBuilder = new StringBuilder();
    private final List<Object> param = new ArrayList<>();

    public Sql(SimpleDb simpleDb) {
        this.simpleDb = simpleDb;
    }

    // 개별 인자로 전달하기 위한 오버로딩된 append 메서드
    public Sql append(String sql, Object... params) {
        stringBuilder.append(sql).append(" ");
        this.param.addAll(Arrays.asList(params));

        return this;
    }

    // 개별 인자로 전달하기 위한 오버로딩된 appendIn 메서드
    public Sql appendIn(String sql, Object... params) {
        return this;
    }

    // 개별 인자로 전달받은 sql과 params를 사용하여 PreparedStatement를 생성하는 메서드
    @SneakyThrows
    private PreparedStatement buildStatement(boolean returnKeys) {
        Connection connection = simpleDb.getConnection();

        PreparedStatement ps = connection.prepareStatement(
                stringBuilder.toString(),
                Statement.RETURN_GENERATED_KEYS
        );

        for (int i=0; i<param.size(); i++) {
            ps.setObject(i + 1, param.get(i));
        }

        return ps;
    }

    @SneakyThrows
    public long insert() {
        // 생성한 id가 필요하므로 true 입력
        PreparedStatement ps = buildStatement(true);
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        return rs.getLong(1);
    }

    @SneakyThrows
    public int update() {
        return buildStatement(false).executeUpdate();
    }

    @SneakyThrows
    public int delete() {
        return buildStatement(false).executeUpdate();
    }

    @SneakyThrows
    public List<Map<String, Object>> selectRows() {
        List<Map<String, Object>> result = new ArrayList<>();

        PreparedStatement ps = buildStatement(false);
        ResultSet rs = ps.executeQuery();
        ResultSetMetaData metaData = rs.getMetaData();

        // 쿼리 끝까지 반복
        while (rs.next()) {
            Map<String, Object> row = new HashMap<>();
            // 리스트에 각 컬럼값 입력
            for (int i=1; i<=metaData.getColumnCount(); i++) {
                String columnName = metaData.getColumnName(i);
                Object value = rs.getObject(i);
                row.put(columnName, value);
            }
            result.add(row);
        }
        return result;
    }

    public <T> List<T> selectRows(Class<T> cls) {
        return null;
    }

    public Map<String, Object> selectRow() {
        return null;
    }

    public <T> T selectRow(Class<T> cls) {
        return null;
    }

    public LocalDateTime selectDatetime() {
        return null;
    }

    public Long selectLong() {
        return null;
    }

    public List<Long> selectLongs() {
        return null;
    }

    public String selectString() {
        return null;
    }

    public Boolean selectBoolean() {
        return null;
    }
}
