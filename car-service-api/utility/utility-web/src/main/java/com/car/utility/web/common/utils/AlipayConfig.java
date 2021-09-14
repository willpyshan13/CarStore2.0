package com.car.utility.web.common.utils;

/**
 * @author linjiang.xie
 * @date 2020/2/9 14:36
 */
public class AlipayConfig {
    /**
     * 请求网关地址
     */
    public static String URL = "https://openapi.alipay.com/gateway.do";
    /**
     * 请求网关地址
     */
    public static String TEST_URL = "https://openapi.alipaydev.com/gateway.do";
    /**
     * 编码
     */
    public static String CHARSET = "UTF-8";
    /**
     * 返回格式
     */
    public static String FORMAT = "json";
    /**
     * 支付宝公钥
     */
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsXWKwUPEnl2TqWL7IJ5niUNhHPb83ujJH7yPhGP7v8JtoANDFn1uIILzQ6U3TdOkStsrXluvGcbQ82UqO7laPwVPQA0tyY5hy5/gDX3Uln2fYqoc2gSaVN9zy1AqsYYzYSMtXvmDPzL+hqCvJPIwJERIu5LsLV5sbwl3wwZ6Nf6fW5yJgP5NwFUO6KX6796Gny+2OO9bELp48B5Qqw1YCAQMUm9XYJljIEWfGQ04IND6GeOwlbh9vyQT52+WG+qztQjRgtNcL/4eWp7KKjIEkC9W4HBVSX5aXTwtOd3tjMjle4LTo9/yx2Pu/4XJQmQcVg3aj6DEgn6EHx0Gjd7i/QIDAQAB";

    /**
     * 支付宝公钥沙箱
     */
    public static String TEST_ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiAVjp/SjICSXqGfrDZBGyweF0h64ENaZ/V2lu505+bysgqsVjt1H3LG5HQEZ8pVb92k7Jw8VIJRrVGaWe3uDOfFhHMAwa3dB81qfm9QHss/wsOBP6Y3sCsWKz6w/lYV67HEqgLk6r+qPeQDZIAotHxrW4yMWIUnOOobM6Dpyal8IMuXmEuRpNO44lygo2C4psHsnCfJrAo/0JwB6q1chmOi64mPEhgHxv8Zym6/YSMDfjHhAeHamvAV+8OMqzJ7k1y9HLorIjdvvVlhlq2i5rCvyOeK7eMoXVVRU/b8SlUwKpsUBCypTemEuPqr95nJA1wIu9KKY+SVXDAdNMROGcwIDAQAB";
    /**
     * RSA2
     */
    public static String SIGNTYPE = "RSA2";
    /**
     * 支付宝应用私钥匙
     */
    public static String PKCS8 = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCZ8Ewd/1++dWouW7gHKr5hHKuh/nW6mdhmz21k1sBGV8vvZKVApQfKqKhh3lzNAVfexU0D/ixvdxQvV5pWTuAC5UM148fzj0zpkoAuJu2W9TRSgPVY32uKVyBU0PnNfQD+LjIW0F9PNhd1P+wDC3fbVWHHJJBOA4iLBjWwKneHS0O3qbaK7zs2LJzB0mhl9+QTwppknjdXwVS5wRI1/JszEf+HxnFiYj4mWikOoNZ6S2RdZFZQFjHtl/g3RiGVYrtsXpNuH44+imGJ3ybYQ2xk3IXEQbBH7udDqkohvrx2e/eugXvE+iXUWyVkFgsePomE7nennDpudkAlHDJZRizXAgMBAAECggEAZmZFi/LTcrNSNZJHgWT6jUjwFutYzw8Ft6Ylw91iu6gtfYp6rtzYqQO2NpwfKTPKiuK+D3jlgCQ7Jh7lCKKhOuvc4J0r5iWd6QXOBGVayH9GFde43SyJsdWxKRp2AS41GeAK68gijmOSCSnDSmc3jIxoZXs6Dtsk2i4ASraFGlZ5ytuztrdo1Vuqjnk3CLPXSAyWx8JySLdaVl9h44l2f+vKm4FkqlHdH5mA9isGEcly6JdPo2jpxLm9/tVub3sOSR8ERdKHVMJAZBmJ7j31q8LYt1qjYIyDhdho8dGB8sJHXyPK7AF+HM35sXccwUdxvHZkSVZLMEvkBEF8XUSecQKBgQDe8rBJ1RpG1lX3OlNsUFz+hRAY4Nrwqjssk88cIgTXY0lazIF3JIzUdHl6NaktwVeRiyYyMuw44a9Dli23S3b1pU8wLKUS+oTljfJdhg38LhxYw5wUhuHTYN50SRXeKE3uAr5dj77braVfh/HZ1rZJGLwtHJNokTB8e56OecHUmQKBgQCwwpHVyRqSkt1//7+8Hl9/yVCO3bldDlL2XvRWhKXmXhP4KZdRI/mf1Abia14+3ubRfO9+LxIu8am6PuE4ch0QwlNHuMkoIv4YnjLajTIrAuDSlinjTmD2TKxI+dpL3LEU7nJASSLTQp7JOl5NhcbX/Etfeq1Hw3mvS9rmOBOC7wKBgQDBHfcV/UGFmqNcQ2l/qRzkWt5Wrm73iH05X89GzzDiYV5pCDwct8xFO579/P1OZOs1ZxLu6K01mEf4g12WKAgjP84YLJPQnLABJ7OIz1XN2yMywD2yiiOStZX7pFMM3FykjjvXT2xDXWaiGV+lEhdTudlGOjp1mU4mhe4R++WqAQKBgBMptOfJeqEDXRIgZuf9Hvtaluo2LX9iKypclv6+OMIl1uJjl+0LCmgCc5CedUQ5pCp38frrHXyLZIw1fvNAo9gKST8QUHaSfJusovTAs/ynvjn2X6ot+CMbot3ygHETBvuu8iH4C9d9dHt6YXjkFO+bgYxjX1/VWF7hyWSGFc9vAoGBAMSNMs1b97EH9oGsI7zh5Z7rSt5NR2Lxze6LnJcVzaEuq5Vsjos0XMkkSMwlt4/4EC6wy3WcRkxy6/+X1Q9NkRw8OAdRyRHdJmts77rvxkT5aTZAuKBGIRrA73tNlpb8ogjXnlmmYzPPQ3xbzb+PAAX4iu6++0ulsLBTFvf/e82b";

    /**
     * 支付宝应用私钥匙
     */
    public static String TEST_PKCS8 = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCIBWOn9KMgJJeoZ+sNkEbLB4XSHrgQ1pn9XaW7nTn5vKyCqxWO3UfcsbkdARnylVv3aTsnDxUglGtUZpZ7e4M58WEcwDBrd0HzWp+b1Aeyz/Cw4E/pjewKxYrPrD+VhXrscSqAuTqv6o95ANkgCi0fGtbjIxYhSc46hszoOnJqXwgy5eYS5Gk07jiXKCjYLimweycJ8msCj/QnAHqrVyGY6LriY8SGAfG/xnKbr9hIwN+MeEB4dqa8BX7w4yrMnuTXL0cuisiN2+9WWGWraLmsK/I54rt4yhdVVFT9vxKVTAqmxQELKlN6YS4+qv3mckDXAi70opj5JVcMB00xE4ZzAgMBAAECggEAfw+pvCdGiqwkD2ijphP0TYxFcbbIctJFHuzv+IYythuNerKefMowavSJ25I/zt++/SsL2ry4G6sJZ9o9EDScrO/Sq6zMjlblAJLTLhmu51ZfnJqti5rLuHQ36y8YZnA+F8jOz1g4fkQ6qeok6U0YQUotGhiYf5gWUY3Cs9yFLN8kg3hTqc6CUca2tX9C7M4xdZsVJrMhmvh1IZo4VbX8t12GTgazakLuU11pAOb5Z9rmYdHiGsF/CmK2+ypKDqhTx5PkU/Jh7YGwoJ3bFXiuUl1ky+7AUIX3cRF/MolQN3RMuqvtbUf8MJA+HiW4eUHyZJ0QE9zOJFRAvCkfdS8vAQKBgQC7L9zCRMgVbIF0P+OKmiDwmd4163ePJspzUJE89hl7YpzPksW79iqdPaMhazX9C/I7ynAwMgZNsTrQJV1qRZ/tkHvYL8g1ZSA8D59K5npDLkTBeoWroKF0gT3hdBtmMJ8EiuC1szDf4b9fNUp1jlpIo5juhGkcJ3uKNs87wboz4QKBgQC6Bk+DGqi2d+pl6yfycZCVfJe2LxaU8iBT/DBocdK0ysCL55LrxX14+riIgtq6G2txG13wJAiz2eSFBVuLu/4RuKTDyGMs6P1o95zfEzIwh1JCuRfL0gtDrNOYGg3otL5m6OxW4UKXNGvjSFHegcQe6X+t3BLf0Zpj+Owl2TNE0wKBgQCx2ZvqiOeCU4H8LefH/QFnGQrkOEF0peqc/ePBBCLZ9KwBHPb6y71ZgQLJ8c9uCjexT3qgkCclEktLEPVcz6JQWYMRuPSEpvyllEJJLArmYcO6AOuYUhqVU2a0G1kdEv1Dl6BBg2vgi+EEWOHSJ9roQWhA11X3Bhx/CeyEXmDIgQKBgBImL3fBEBjNVkcfTERxJAQRpdOWgIODo/fNsQNfp4kQBv0fWMw79mPpQju/WIuzxSXB38ZgHaE+bvczcJ0PeFhORXT8hgCJsr+c+OcivlDRwwrTeCjgk0zE/dfnIthDWhg8PWK0k4ShDq2cDrDAxFunuFdEE2tWzdNXI+MFb+UVAoGAes+rcbB9r20IcFnP4Et3QFOtuv2bzF2g237F9t1P4696QuLBrR2pLWqFZO+u2Cu1LrN6SbUZvjXSzYUeTL/FTdaizrOV31RhSXSH+7qxZel7WNgoPmmpmi9Tv1q+MLB0kWjjFu2RVH/zo5ufhfNnPgueWKZe1wWf2UP1fgem/Gk=";
    /**
     * 支付宝应用ID
     */
    public static String APP_ID = "2021002127675655";

    /**
     * 支付宝应用ID
     */
    public static String TEST_APP_ID = "2021000117695818";

}
