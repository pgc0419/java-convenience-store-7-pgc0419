package store.View;

import camp.nextstep.edu.missionutils.Console;

public class InputView {
    private static String mockInput; // 테스트용 입력값 저장

    public static void mockInput(String input) {
        mockInput = input;
    }

    public static String[] readProduct() {
        if (mockInput != null) { // 테스트 환경일 경우 mockInput 사용
            return mockInput.split(",");
        }
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        String input = Console.readLine();
        return input.split(",");
    }

    public String promotion() {
        System.out.println("현재 {상품명}은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)");
        return Console.readLine().trim();
    }

    public String promotionSale() {
        System.out.println("현재 {상품명} {수량}개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)");
        return Console.readLine().trim();
    }

    public String membershipSale() {
        System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
        return Console.readLine().trim();
    }

    public String moreBuy() {
        System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
        return Console.readLine().trim();
    }
}
