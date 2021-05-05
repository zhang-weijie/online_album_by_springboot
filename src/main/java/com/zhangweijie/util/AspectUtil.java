package com.zhangweijie.util;

/**
 * @author zhangweijie, E-mail:zhangweijiepku@gmail.com
 * @create 2021-01-17 23:10
 */
public class AspectUtil {
    public static final String FOR_OPTION_INITIAL =
            "execution(* com.zhangweijie.controller.OptionController.loadOption(..)) ";

    @Deprecated
    public static final String FOR_OPTION_UPDATE_DEPRECATED =
            "execution(* com.zhangweijie.controller.OptionController.add(..)) ||" +
                    "execution(* com.zhangweijie.controller.OptionController.update(..)) ||" +
                    "execution(* com.zhangweijie.controller.OptionController.delete(..))";

    public static final String FOR_OPTION_UPDATE =
            "execution(* com.zhangweijie.controller.OptionController.addOption(..)) ||" +
                    "execution(* com.zhangweijie.controller.OptionController.updateOption(..)) ||" +
                    "execution(* com.zhangweijie.controller.OptionController.deleteOption(..))";

    public static final String FOR_PHOTO_VIEW =
            "execution(* com.zhangweijie.controller.PhotoController.view(..))";
}
