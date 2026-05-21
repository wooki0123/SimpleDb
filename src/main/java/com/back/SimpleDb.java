package com.back;

public class SimpleDb {
    public void setDevMode(boolean b) {
    }

    public SimpleDb(String localhost, String root, String password, String log) {
    }

    public void run(String sql) {
    }

    // 개별 인자로 전달하기 위한 오버로딩된 run 메서드
    public void run(String sql, Object... params) {
    }

    public Sql genSql() {
        return new Sql(this);
    }

    public void close() {
    }

    public void startTransaction() {
    }

    public void commit() {
    }

    public void rollback() {
    }
}
