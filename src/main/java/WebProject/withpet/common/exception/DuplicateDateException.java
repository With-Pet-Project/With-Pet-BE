package WebProject.withpet.common.exception;

import WebProject.withpet.common.constants.ErrorCode;

public class DuplicateDateException extends CustomException {
    public DuplicateDateException(ErrorCode errorCode) {
        super(errorCode);
    }

    @Override
    public ErrorCode getErrorCode() {
        return super.getErrorCode();
    }
}
