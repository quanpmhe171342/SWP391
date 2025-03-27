/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO.Config;

import java.util.Random;

/**
 *
 * @author d
 */
public class VNPayConfig {

    public static final String VNP_VERSION = "2.1.0";
    public static final String VNP_COMMAND = "pay";
    public static final String VNP_PAYURL = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";

    // Thông tin merchant
    public static final String VNP_TMNCODE = "W6MVWQ4G";
    public static final String VNP_HASHSECRET = "XZR87G00O3Q1CSZ8KPR4PILQ4KVRHBXN";

    // Cấu hình chung
    public static final String VNP_CURRCODE = "VND";
    public static final String VNP_LOCALE = "vn";
    public static final String VNP_RETURN_URL = "http://localhost:9999/SWP391/payment";

    public static String getRandomNumber(int len) {
        Random rnd = new Random();
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
