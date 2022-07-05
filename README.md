# 카이냠 KAI-YUM
- 카이스트 주변 맛집을 소개해주는 어플리케이션입니다. 
- 친구들의 연락처와 이메일을 저장할 수 있습니다. 
- 먹고 싶은 음식 사진을 선택하면 지도 상에서 위치를 볼 수 있습니다. 

### A. 개발 팀원
- GIST 전기전자컴퓨터전공 남유성
- KAIST 전산학부 김가현 

### B. 개발 환경
- Language: Kotlin
- IDE: Android Studio
- Target Device: Galaxy S7


### C. 어플리케이션 소개 


### TAB 1 - Contacts

Major features
- 핸드폰에 내장된 연락처를 불러옵니다. 
- 우측 하단의 + 버튼을 누르면 연락처를 추가할 수 있는 화면으로 이동할 수 있습니다.
    - 추가한 연락처는 디바이스에 자동으로 저장됩니다. 
- 각 연락처 탭을 눌러 해당 인물의 상세정보를 확인할 수 있습니다. 
    - 상세정보에는 인물의 이름, 번호와 이메일이 표시됩니다. 

기술 설명
- List View를 이용하여 저장된 인물정보를 보여줍니다. 
- Context에 있는 ContentResolver 객체를 사용하여 내장된 연락처 정보를 불러올 수 있습니다.
- ContentProviderOperation를 이용해 디바이스 데이터에 접근하여 연락처를 추가할 수 있습니다. 

### TAB 2 - Gallery
![Screen Shot 2022-07-05 at 8 25 57 PM](https://user-images.githubusercontent.com/88198439/177317275-9e760c1d-5337-4426-881a-34fd2be87289.png)

Major features
- 카이스트 주변 맛집의 음식 사진을 한눈에 볼 수 있습니다. 
- 사진을 클릭하면 음식점의 사진이 확대되고, 음식점 이름, 카테고리, 위치 정보를 알 수 있습니다.
- Show on Map 버튼을 누르면 탭 3에 있는 지도에서 실제 위치로 이동할 수 있습니다.

기술 설명
- Recycler View와 Card View를 이용하여 갤러리 탭을 구현했습니다. 
- 각 사진을 클릭 시 JSON 파일에 저장된 음식점 정보를 가져옵니다


### TAB3 - Map
![Screen Shot 2022-07-05 at 8 30 31 PM](https://user-images.githubusercontent.com/88198439/177317947-94a15d5d-7f83-4e08-b158-20eef324d656.png)
![Screen Shot 2022-07-05 at 8 29 44 PM](https://user-images.githubusercontent.com/88198439/177317840-7d4681a9-fa64-486e-b833-be0b21ff9326.png)


Major features
- 지도 상에서 주변 맛집의 위치를 한눈에 볼 수 있습니다.
- 저장소에 저장된 음식점을 검색할 수 있습니다.
- 현재 위치로부터 음식점까지의 실제 거리와 지도 상의 위치를 한눈에 파악할 수 있습니다.

기술 설명
- 네이버지도 API와 GPS 기능을 이용해 지도 상의 현재 위치를 가져온다
- 





