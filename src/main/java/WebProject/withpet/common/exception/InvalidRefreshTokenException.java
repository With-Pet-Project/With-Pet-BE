package WebProject.withpet.common.exception;

import WebProject.withpet.common.constants.ErrorCode;

public class InvalidRefreshTokenException extends CustomException {
    public InvalidRefreshTokenException() {
        super(ErrorCode.EXPIRED_REFRESH_TOKEN);
    }

    @Override
    public ErrorCode getErrorCode() {
        return super.getErrorCode();
    }
}
