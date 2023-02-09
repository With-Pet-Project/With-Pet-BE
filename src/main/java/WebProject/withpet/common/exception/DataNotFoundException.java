package WebProject.withpet.common.exception;

import WebProject.withpet.common.constants.ErrorCode;

public class DataNotFoundException extends CustomException {
    public DataNotFoundException() {
        super(ErrorCode.DATA_NOT_FOUND);
    }

    @Override
    public ErrorCode getErrorCode() {
        return super.getErrorCode();
    }
}
