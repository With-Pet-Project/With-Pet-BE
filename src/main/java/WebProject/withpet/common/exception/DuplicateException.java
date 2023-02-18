package WebProject.withpet.common.exception;

import WebProject.withpet.common.constants.ErrorCode;

public class DuplicateException extends CustomException {

    public DuplicateException(ErrorCode errorCode) {
        super(errorCode);
    }
}
