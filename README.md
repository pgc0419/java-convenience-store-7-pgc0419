# 프리코스 4주차 - 편의점
---
## 디렉토리 구조
├── Application.java <br> 
├── Controller <br>
│    ├── DateController.java <br>
│    ├── ProductsController.java <br>
│    ├── PayController.java <br>
│    ├── ReceiptController.java <br>
│    └── Start.java <br>
├── Model <br>
│    ├── Date.java <br>
│    ├── Products.java <br>
│    ├── Pay.java <br>
│    ├── Promotion.java <br>
│    ├── Membership.java <br>
│    ├── Receipt.java <br>
│    └── ReceiptForm.java <br>
├── Reader <br>
│    ├── ProductsReader.java <br>
│    └── PromotionsReader.java <br>
├── Updater <br>
│    └── ProductsMDUpdater.java <br>
├── Validate <br>
│    ├── InputValidate.java <br>
│    └── YNValidate.java <br>
└── View <br>
     ├── InputView.java <br>
     └── OutputView.java <br>
     
---
## 구현 기능 목록
- [ ] Application.java <br>
     - [ ] Start.java 새로운 선언자로 선언하여 프로그래밍 가동 <br>
---
- [ ] Controller <br>
     - [X] ProductsController.java <br>
          - [X] 상품명 리스트와 갯수 리스트로 변환 <br>
          - [X] 상품 재고 업데이트 <br>
          - [X] 예외 처리 <br>
     - [ ] PayController.java <br>
          - [ ] 최종 결제 금액 계산 <br>
     - [ ] DateController.java <br>
          - [ ] 프로모션 날짜와 현재 날짜 비교 <br> 
     - [ ] ReceiptController.java <br>
          - [ ] 영수증 업데이트 <br>
     - [ ] Start.java <br>
          - [ ] 프로그램 가동을 위한 코드 연결 <br>
---
- [ ] Model <br>
     - [X] Products.java <br>
          - [X] 모델 선언 <br>
     - [ ] Pay.java <br>
          - [ ] 할인 미 포함 된 지불 해야 하는 금액 계산 <br>
     - [ ] Promotions.java <br>
          - [ ] 할인률 계산 <br>
     - [ ] Date.java <br>
          - [ ] 날짜 계산 <br>
     - [ ] Membership.java <br>
          - [ ] 할인률 계산 <br>
     - [ ] Receipt.java <br>
          - [ ] 영수증 필드에 추가하는 로직 <br>
     - [ ] ReceiptForm.java <br>
          - [ ] (Enum) 영수증 상수 필드 제작 <br>
---
- [X] Reader <br>
     - [X] ProductsReader <br>
          - [X] products.md 불러오기 <br>
     - [X] PromotionsReader <br>
          - [X] promotions.md 불러오기 <br> 
---
- [X] Updater <br>
     - [X] ProductsMDUpdater <br>
          - [X] products.md 업데이트 <br>
---
- [ ] Validate <br>
     - [X] InputValidate.java <br>
          - [X] [ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요. <br>
          - [X] [ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요. <br>
          - [X] [ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요. <br>
     - [ ] YNValidate.java <br>
          - [ ] [ERROR] 잘못된 입력입니다. 다시 입력해 주세요. <br>
---
- [ ] View <br>
     - [X] InputView.java <br>
          - [X] "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])" <br>
          - [X] 쉼표를 기준으로 스플릿 <br>
          - [X] "현재 {상품명}은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)" <br>
          - [X] "현재 {상품명} {수량}개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)" <br>
          - [X] "멤버십 할인을 받으시겠습니까? (Y/N)" <br>
          - [X] "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)" <br>
     - [ ] OutputView.java <br>
          - [ ] "안녕하세요. W편의점입니다. \n 현재 보유하고 있는 상품입니다." <br>
          - [ ] 업데이트 된 products.md 출력 <br>
          - [ ] 영수증 출력 <br>

