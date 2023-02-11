package WebProject.withpet.common.exception;

import WebProject.withpet.common.constants.ErrorCode;

public class UnauthorizedException extends CustomException {
    public UnauthorizedException() {
        super(ErrorCode.UNAUTHORIZED);
    }

    @Override
    public ErrorCode getErrorCode() {
        return super.getErrorCode();
    }
}
