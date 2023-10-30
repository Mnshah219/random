// Discount coupons
// 1. X% off on all item
// 2. X% off on next item
// 3. X% off on nth item of kth type
enum ProductType {
    BOOK;
    PEN;
}
class Product {
    String name;
    int id;
    ProductType type;
    double price;
}
enum CouponType {
    AllProductDiscountCoupon;
    NextProductDiscountCoupon;
    NthProductOfTypeKDiscountCoupon;
}
abstract class DiscountCoupon {
    double discount;
    String name;
    int id;
    void calculateDiscount(Product product, int couponPosition, int productPosition) {
        product.price = product.price - (discount * product.price)/100;
    }
}

class AllProductDiscountCoupon extends DiscountCoupon { 

}
class NextProductDiscountCoupon {
    int productsSeen = 0;
    void calculateDiscount(Product product, int couponPosition, int productPosition) {
        if (productsSeen == 0)
            product.price = product.price - (discount * product.price)/100;
        else {
            if (productPosition > couponPosition)
                productsSeen++;
        }
    }
}
class NthProductOfTypeKDiscountCoupon {
    int productsSeen = 0;
    ProductType productType;
    int k = 0;
    constructor (ProductType productType) {
        this.productType = productType;
    }
    void calculateDiscount(Product product, int couponPosition, int productPosition) {
        if (productsSeen == k)
            product.price = product.price - (discount * product.price)/100;
        else {
            if (productPosition > couponPosition && this.productType == product.type)
                productsSeen++;
        }
    }
}
enum CartItemType {
    PRODUCT;
    DISCOUNT_COUPON;
}
class CartItem {
    CartItemType type;
    Product product;
    DiscountCoupon coupon;
}
class Cart {
    public static void main(String[] args) {
        // assume this is initialised
        CartItem[] items = new CartItem[10];
        int price = 0;
        for (int i = 0; i < items.length; i++) {
            if (items[i].type == CartItemType.PRODUCT) {
                for (int j = 0; j < items.length; j++) {
                    if (items[j].type == CartItemType.DISCOUNT_COUPON) {
                        items[j].coupon.calculateDiscount(items[i].product, i, j);
                    }
                }
                price += items[i].price;
            }
        }
        System.out.println(price);
    }
}