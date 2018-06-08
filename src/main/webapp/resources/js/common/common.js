/**
 * 验证码操作
 */
function changeVerifyCode(img) {
    img.src = "../Kaptcha?" + Math.floor(Math.random() * 100);
}
/**
 * 匹配传递过来的参数
 */
function getQueryString(name) {
    // 正则表达式
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    // 匹配url
    var r = window.location.search.substr(1).match(reg);
    // 符合参数名，则取值
    if (r != null) {
        return decodeURIComponent(r[2]);
    }
    return '';
}