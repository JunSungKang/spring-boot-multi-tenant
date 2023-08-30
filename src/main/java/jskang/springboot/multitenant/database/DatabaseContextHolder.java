package jskang.springboot.multitenant.database;

public class DatabaseContextHolder {
    private static ThreadLocal<DatabaseEnum> threadLocal = new ThreadLocal<>();

    public static void resetCustomerContext() {
        threadLocal.remove();
    }

    public static void setCustomerContext(DatabaseEnum databaseEnum) {
        threadLocal.set(databaseEnum);
    }

    public static DatabaseEnum getCustomerContext(){
        return threadLocal.get();
    }

    public static void clearCustomerContext(){
        threadLocal.remove();
    }
}
