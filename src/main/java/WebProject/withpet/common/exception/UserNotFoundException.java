package WebProject.withpet.common.exception;

import WebProject.withpet.common.constants.ErrorCode;

public class UserNotFoundException extends CustomException {

    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }

    @Override
    public ErrorCode getErrorCode() {
        return super.getErrorCode();
    }
}
