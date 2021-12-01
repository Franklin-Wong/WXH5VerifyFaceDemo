package com.example.network.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证规则
 * Created by Wongerfeng on 2020/8/6.
 */
public class MatchUtils {

    // 企业名正则[\u4E00-\u9FA5A-Za-z.&-]+$

    /**
     * 手机号的正则
     * "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
     */
    private static final String MOBILE = "[1][34578]\\d{9}";
    /**
     * 是否为4位数字的验证码
     */
    public static final String MATCH_VERIFYCODE_4 = "[0-9]{4}";


    public static final String MATCH_VERIFYCODE_6 = "\\d{6}";
    /**
     * 是否为8位数字的邮箱验证码
     */
    public static final String MATCH_MAIL_VERIFYCODE = "[0-9]{8}";

    /**
     * 验证手机号是否符合规则
     *
     * @param mobile
     * @return
     */
    public static boolean isMobileRight(String mobile) {
        String reg = MOBILE;//
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    /**
     * 判断密码是否为6~15位数字加字母
     *
     * @param pwd
     * @return
     */
    public static boolean isPwdRight(String pwd) {
        String reg1 = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,15}$";//6至12位密码
//        String reg2 = "[a-zA-Z0-9]{6,12}";//6至12位密码
        // String reg3 = "[a-zA-Z]{8,32}";
        // String reg4 = "[0-9]{8,32}";//8至32位密码

        boolean is6To12NumOrWord = Pattern.compile(reg1).matcher(pwd).matches();
        // boolean is6To20Num = Pattern.compile(reg2).matcher(pwd).matches();
        // boolean is6To20Word = Pattern.compile(reg3).matcher(pwd).matches();

        return is6To12NumOrWord /* && (!is6To20Num) && (!is6To20Word) */;
    }

    /**
     * 判断密码是否为6~15位数字加字母
     *
     * @param pwd
     * @return
     */
    public static boolean isPassWordRight(String pwd) {
        String reg1 = "/^[a-zA-Z0-9]{6,15}$/";
        return Pattern.compile(reg1).matcher(pwd).matches();
    }

    /**
     * 验证码是否正确
     *
     * @param verifycationCode
     * @return
     */
    public static boolean isVerifycationCodeRight(String verifycationCode) {
        return Pattern.compile(MATCH_VERIFYCODE_6).matcher(verifycationCode).matches();
    }
    /**
     * 邮箱验证码是否正确
     *
     * @param verifycationCode
     * @return
     */
    public static boolean isMailVerifyCodeRight(String verifycationCode) {
        return Pattern.compile(MATCH_MAIL_VERIFYCODE).matcher(verifycationCode).matches();
    }

    /**
     * 邮箱格式是否正确
     *
     * @param email
     * @return
     */
    public static boolean isEmialRight(String email) {
        // 说明：
        // ①/内容/i 构成一个不区分大小写的正则表达式；^ 匹配开始；$ 匹配结束。
        // ②[a-z] E-Mail前缀必需是一个英文字母开头
        // ③([a-z0-9]*[-_]?[a-z0-9]+)*
        // 和_a_2、aaa11、_1_a_2匹配，和a1_、aaff_33a_、a__aa不匹配，如果是空字符，也是匹配的，*表示0个或者多个。
        // ④*表示0个或多个前面的字符.
        // ⑤[a-z0-9]* 匹配0个或多个英文字母或者数字；[-_]? 匹配0个或1“-”，因为“-”不能连续出现。
        // ⑥[a-z0-9]+ 匹配1个或多个英文字母或者数字，因为“-”不能做为结尾
        // ⑦@ 必需有个有@
        // ⑧([a-z0-9]*[-_]?[a-z0-9]+)+
        // 见上面([a-z0-9]*[-_]?[a-z0-9]+)*解释，但是不能为空，+表示一个或者为多个。
        // ⑨[\.] 将特殊字符(.)当成普通字符；[a-z]{2,3} 匹配2个至3个英文字母，一般为com或者net等。
        // ⑩([\.][a-z]{2})? 匹配0个或者1个[\.][a-z]{2}(比如.cn等)
        // 我不知道一般.com.cn最后部份是不是都是两位的,如果不是请修改{2}为{起始字数,结束字数}
        String reg = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
        // String reg =
        // "/^[a-z]([a-z0-9]*[-_]?[a-z0-9]+)*@([a-z0-9]*[-_]?[a-z0-9]+)+[\\.][a-z]{2,3}([\\.][a-z]{2})?$/i";
        return Pattern.compile(reg).matcher(email).matches();
    }

    /**
     * 判断是否为网址
     * @param content
     * @return
     */
    public static boolean isHtml(String content){
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern wikiWordMatcher = Pattern.compile(str);
        boolean isOk = wikiWordMatcher.matcher(content).matches();
        return isOk;
    }

    /**
     * 名字是否符合规则(4-30个汉字)
     *
     * @param name
     * @return
     */
    public static boolean isNameRight(String name) {
        return Pattern.compile("^.{4,30}$").matcher(name).matches();//4-30个所有字符
//        return Pattern.compile("[\u4E00-\u9FA5]{4,30}").matcher(name).matches();
    }

    public static boolean isCertifyIDNumber(String certifyId){
        String reg = "^(\\d{6})(19|20)(\\d{2})(1[0-2]|0[1-9])(0[1-9]|[1-2][0-9]|3[0-1])(\\d{3})(\\d|X|x)$";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(certifyId);
        return matcher.matches();
    }
}
