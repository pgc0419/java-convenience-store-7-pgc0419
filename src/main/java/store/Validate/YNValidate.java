package store.Validate;

public class YNValidate {
    public static void YNNotNull(String yn) {
        if (yn == null || yn.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
        }
    }

    public static void YNFormatValid(String yn) {
        if (yn == "Y"|| yn == "N") {
            throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
        }
    }
}
