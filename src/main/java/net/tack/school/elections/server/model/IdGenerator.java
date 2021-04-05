package net.thumbtack.school.elections.server.model;

public class IdGenerator {
    private static int nextId = 0;

    public static int getNextId() {
        return ++nextId;
    }
}
