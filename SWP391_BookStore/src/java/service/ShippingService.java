/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author TungPham
 */
public class ShippingService {

    public Float getShipFee(String ward) {
    switch (ward) {
        case "Cau Giay":
            return 50000f;
        case "Hoang Mai":
            return 100000f;
        case "Ba Dinh":
            return 60000f;
        case "Dong Da":
            return 70000f;
        case "Bac Tu Liem":
            return 65000f;
        case "Ha Dong":
            return 85000f;
        case "Hai Ba Trung":
            return 55000f;
        case "Hoang Lien":
            return 75000f;
        case "Long Bien":
            return 90000f;
        case "Nam Tu Liem":
            return 80000f;
        case "Tay Ho":
            return 75000f;
        case "Thanh Xuan":
            return 60000f;
        case "Son Tay":
            return 95000f;
        case "My Duc":
            return 100000f;
        case "Ba Vi":
            return 100000f;
        case "Phuc Tho":
            return 95000f;
        case "Dan Phuong":
            return 90000f;
        case "Hoai Duc":
            return 85000f;
        case "Quoc Oai":
            return 90000f;
        case "Thach That":
            return 95000f;
        case "Chuong My":
            return 95000f;
        case "Thanh Oai":
            return 85000f;
        case "Thuong Tin":
            return 90000f;
        case "Phu Xuyen":
            return 100000f;
        case "Ung Hoa":
            return 100000f;
        case "Me Linh":
            return 85000f;
        case "Soc Son":
            return 95000f;
        default:
            return 0f;
    }
}
}
