# Android Pokemon shooting game
![그림1](https://user-images.githubusercontent.com/55947154/113499521-8c14e000-9551-11eb-839a-2732f3c40470.png)   
모바일 응용 설계 팀 프로젝트(3인 참여, 2020.07~2020.07, A+)   
> 나의 역할   
  > 설계, 프레임워크, 리팩토링(디자인패턴), 주요 기능 개발(game state, player, missile, item 등), 배포 파일 생성 등   
<br>

### 게임 설명
- **포켓몬을 테마로 한 슈팅 게임**   
  로켓단(enemy)이 쏘는 미사일(포켓볼)에 맞으면 포켓몬(player)의 체력이 감소   
- **특징**
  - 포켓몬에 따라 미사일과 진화, 필살기가 다름   
  - 진화 아이템을 먹으면 진화하여 빠르고 강력해짐   
![포켓몬 데모](https://user-images.githubusercontent.com/55947154/113508355-84bdf880-958a-11eb-91ba-09a8052ed038.gif)   

 - **기능**
    - 스마트폰을 움직여 캐릭터 이동, 터치하여 공격(센서 활용)
    - 필살기 (애니메이션, 지속 데미지 & 광범위 공격)   
    - 아이템 별 효과(진화, 생명력)      
    - 진화 시 적을 관통하는 미사일로 업그레이드   
    - 몬스터의 다양한 이동 패턴    
    - 보스 몬스터의 유도 미사일  
    - 충돌 후 1초간 무적 상태  
    - 적 제거 시 폭발 애니메이션   
    - 충돌 시 진동      
    - 스테이지 별 난이도 증가   
    - 게임 스테이트(Intro, Game, Clear, End) 전환
    - 점수 및 랭킹 기록(SqlLite)   
<br>

### 프로젝트 개요
**1. Framework를 만들고 이를 기반으로 게임 제작**   
 ![그림222](https://user-images.githubusercontent.com/55947154/113499722-23c6fe00-9553-11eb-8bfe-0b76400ae98b.png)   
 *프레임워크 제작 이유: 게임 개발에 필요한 기반 틀과 기능을 미리 만들어 게임 제작 전반에 재사용*   
[프레임워크 README](https://github.com/cjl0701/GameFramework/blob/master/README.md "github link")   
   

**2. 디자인 패턴 적용하여 리팩토링**   
  *디자인 패턴 적용 이유: 구조 파악에 용이하며, 변경에 유연하여 객체지향 효과 극대화, 중복 코드를 줄여 가독성 향상*   
  >- 적용 사항 개요   
  > 캐릭터, 진화 상태에 따라 다른 action -> *State pattern*   
  > argument에 따라 다양한 unit 객체 생성 -> *Factory method pattern*   
  > 플레이어는 하나만 생성되야 함 -> *Singleton pattern*   
<br>


### 구조
**전체 시스템 구조**   
![그림3](https://user-images.githubusercontent.com/55947154/113500653-b1f2b280-955a-11eb-89c1-c4fe64f50288.png)   
  - 게임의 상태에 따라 update, render, event handle 메소드 등이 달라짐.   
이를 동일한 인터페이스로 처리하기 위해 *State 패턴* 적용
  - 클래스 구조를 복잡한 graph 구조가 아닌 단순한 tree 구조로 사용하기 위해 AppManager를 통해 리소스의 인스턴스를 전달    
어디서든 전역적으로 호출할 수 있으므로 *Singleton 패턴*을 적용해 논리적 오류와 충돌을 방지하고 리소스를 절약


<br>

**게임 작동 구조**   
![그림4](https://user-images.githubusercontent.com/55947154/113500657-b4eda300-955a-11eb-850d-afd78e9191e4.png)   
  - 캐릭터, 진화 상태에 따라 아이템에 대한 반응, 공격 등 action이 달라짐    
State에 따른 action의 내용이 달라지므로 *State 패턴* 적용   
  - 플레이어, 몬스터, 아이템 등은 조건과 인자에 따라 인스턴스가 달리 생성됨   
이를 위해 인스턴스를 동적으로 생성할 때 유용한 *Factory method 패턴* 적용   
  - 게임 내 플레이어는 하나만 생성되어야 하므로 *Singleton 패턴* 적용   

<br>  

### 사용한 기술
- Java
- Android Studio
- 다양한 안드로이드 API 활용
  - 터치, 방향 센서를 이용한 조작
  - 사운드, 진동(vibrator) 효과
  - SqlLite를 이용한 점수 기록 및 랭킹

     
    
