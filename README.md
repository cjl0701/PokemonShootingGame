# Android - Pokemon shooting game
###  - 이전에 만들어 둔 shooting game 용 framework를 기반으로 제작
###  - 디자인 패턴 적용



* Desing pattern 적용 사항
  - 다양한 캐릭터, 진화 상태에 따라 다른 action -> State pattern 적용
  - 조건과 인자에 따라 다양한 unit의 객체 생성 -> Factory pattern 적용
  - 플레이어는 하나만 생성되야 함 -> Singleton pattern 적용
    
* 방향 센서, vibrator, DB(sqlite) 등 적용
