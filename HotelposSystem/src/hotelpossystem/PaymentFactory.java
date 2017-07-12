package hotelpossystem;

/**
 *
 * @author XiaoSong
 */
public class PaymentFactory {

    public Payment getPayMethod(String payType) {
        if (payType.equalsIgnoreCase("PAYBYCARD")) {
            return new PayByCard();
        }

        if (payType.equalsIgnoreCase("PAYBYCASH")) {
            return new PayByCash();
        }
        return null;
    }
}
