package net.thumbtack.school.elections.server;

public class ServerException extends Exception{
    ServerExceptionErrorCode errorCode;

    public ServerException(ServerExceptionErrorCode errorCode) {
        super(errorCode.getErrorCode());
        this.errorCode = errorCode;
    }

    public ServerException(ServerExceptionErrorCode errorCode, Throwable cause) {
        super(errorCode.getErrorCode(), cause);
        this.errorCode = errorCode;
    }

    public ServerExceptionErrorCode getServerExceptionErrorCode() {
        return errorCode;
    }
}
