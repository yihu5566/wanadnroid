package test.juyoufuli.com.myapplication.app.exception;

/**
 * 类名：OnHttpExceptionCall
 *
 * @author wuxin<br/>
 *         实现的主要功能:自定义错误信息，统一处理返回处理
 *         创建日期：16/7/25
 *         修改者，修改日期16/7/25，修改内容。
 */
public class OnHttpServiceException extends RuntimeException {


    public OnHttpServiceException (int resultCode) {
        this(getApiExceptionMessage(resultCode));
    }

    public OnHttpServiceException (String detailMessage) {
        super(detailMessage);
    }

    /**
     * 转换Code
     * @param code
     *
     * @return
     */
    private static String getApiExceptionMessage (int code) {
        //TODO 根据服务器的状态回显友好的用户提示
        String message = "";
        return message;
    }
}

