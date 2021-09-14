package com.car.utility.web.common.utils;

import com.car.utility.web.common.utils.weixin.WXPayUtil;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Map;

/**
 * @author linjiang.xie
 * @date 2020/4/11 14:22
 */
public class DecodeUtil {
    private static final String ALGORITHM = "AES";

    private static final String ALGORITHM_MODE_PADDING = "AES/ECB/PKCS5Padding";

    private static final String key = "0368d6515c9f43909c3c8b1cc1d63e93";

    private static SecretKeySpec secretKey = new SecretKeySpec(Md5Util.MD5Encode(key, "UTF-8").toLowerCase().getBytes(), ALGORITHM);

    public static String decryptData(String base64Data) {
        try{
            Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64Utils.decode(base64Data)));
        }catch (Exception ex){
            ex.printStackTrace();;
            return null;
        }

    }

    public static void main(String[] args) throws Exception {
        String key ="wolQkXQyk3kWqgqulgOQo96tFHcN7g7Ifbagqt0SbHC37b+81wG4+FER2MBEe5r+";
        String s = AESUtils.aesDecrypt(key,"61QEg436u1qjigJF");
        System.out.println(s);
        String A = DecodeUtil.decryptData("Pcb6CLkalfQG+58gCeM+Cgf2EMdEyJT1HkgiGa3AAFsEO5YW0i1tVe6dR2e2duzANMvxpNDSMBbwm5cgr3SRMBhAhcmds2o5u45/b0C2zbskbFH7C+dG4YQmTUHyTNWNGdphJtecsfgihqZXU2Ybr1QSxTy+563RwnSbb6ywXtZChPwxd+66uhyfnSl576+nACinKL7rSWj0txEulh2sVEVOLtP7+PDNEr8FBlUrKkT19FsI/6Y9Bv7JWjenWnhK4LCTlVPadp9rOPd4k38iBB8BPlwqcTmM+pCeKgGzXjwf/1DQ/Y5ZCr6iX+Wjh+I9ZnHFQ32Efa+L1kDsU9xS6irYYNApfR5GCec3dCuTY/WDsOOnd8SwEtFCZoI9WqVJZm9ywPBO+BcX15YEmVDNEG58OXIqXlH5NVZEUKftQEgReFmQChjBqZAWoMPOIAkbsq4+nHfWM3MLbH++gi+M4S/iqmVfDkPiyq792MK3LIWk3/BUN74BzR6fd9dX8IgPEGEMMabIOzf6J4f8zmdjrSsZ5uYUt+kE2HTLv9OGpyZuoT04aRbZcOst0DwzEbpkegHytyD61S0+1iUdNY35vAtxGROTk75zasYgIUJsMxFLoDDb2jGWz9aOpprebacT0677pa1DOdlY3U01ZUYYzpg99O8JIhZxZoyycpM49luIKJMR2lDyxks6WMK84yxFcsWQlUW8PslQQvOG4A8em4ze35nD9DLETyag3BLWL2VX2tn2woYrkfh+zAs5JieYKEMWQe2aBsyQkeYQpDIJ9XcQOSvMoLPxPFnPPXQU3GNaRTRUUjBFpvujn9sB7PRAAsjIIrzAD5jGeG47LIbSWl2FQJf6Nd9D72zzJxT5TGZNjc9CVEBBKrCataPufn+aUg9oX/wCPNJ4MrmYlca5uS3tK/NYRcBFQIwFN2+7G9Pz6OloaKiDvVPXRhzj951cW6tYv7AY/TVVpsxuDDSGn1o2wDCV/tFViqx7ijSVgGYg1YDFNEsCFHjc9lzPtZeeDZq+LiIt7BimiK26Ik2wCHiCi2+fv7xf6nOwYWxDDnMC0b154h5GXZl1ThYkCf7B7vgxpe9anRgLlaAnQkXxpA==");
        System.out.println(A);
        Map<String, String> paraMap = WXPayUtil.xmlToMap(A);
        System.out.println(paraMap.get("out_refund_no"));

    /*    Map<String, String> paraMap = WXPayUtil.xmlToMap(B);
        System.out.println(paraMap.get("out_refund_no"));
        System.out.println(B);*/
    }
}
