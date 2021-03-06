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
### Layout - Tabview
- viewpager2와 Tablayout을 이용해서 탭 레이아웃 구현했습니다.
- 스와이프와 하단 탭 메뉴 선택을 통해 레이아웃을 이동할 수 있습니다.
- Tab1과 Tab2는 스와이프로 이동가능하게 했으나, 지도가 위치한 Tab3에 접근한 경우 스와이프 이벤트를 막도록 설정했습니다
- 상단에는 커스텀 Toolbar를 만들어 tab 이동 시 title이 변경되도록 설정했습니다

### TAB 1 - Contacts
![Screen Shot 2022-07-05 at 9 37 33 PM](https://user-images.githubusercontent.com/88198439/177329174-916248fe-9c83-41ed-88c4-c78eccb84a60.png)

Major features
- 핸드폰에 내장된 연락처를 불러옵니다. 
- 우측 하단의 + 버튼을 누르면 연락처를 추가할 수 있는 화면으로 이동할 수 있습니다.
    - 추가한 연락처는 디바이스에 자동으로 저장됩니다. 
    - 연락처에 최소 한 가지 이상 항목을 입력하지 않으면 연락처를 저장할 수 없습니다.    
- 각 연락처 탭을 눌러 해당 인물의 상세정보를 확인할 수 있습니다. 
    - 상세정보에는 인물의 이름, 번호와 이메일이 표시됩니다.

기술 설명
- List View를 이용하여 저장된 인물정보를 보여줍니다. 
- Context에 있는 ContentResolver 객체를 사용하여 내장된 연락처 정보를 불러옵니다. 
    - 각 ContactsContract.Data._ID_ 별로 CommonDataKinds.Phone.DISPLAY_NAME, CommonDataKinds.Phone.NUMBER, CommonDataKinds.Email.DATA를 access해 이름, 번호, 이메일 정보를 읽을 수 있습니다.
- ContentProviderOperation를 이용해 디바이스 데이터에 접근하여 연락처를 추가할 수 있습니다.
- 연락처 세부정보 탭 실행과 연락처 추가 탭 실행은 activity로 구현했습니다.

### TAB 2 - Gallery
![Screen Shot 2022-07-05 at 8 25 57 PM](https://user-images.githubusercontent.com/88198439/177317275-9e760c1d-5337-4426-881a-34fd2be87289.png)

Major features
- 카이스트 주변 맛집의 음식 사진을 한눈에 볼 수 있습니다. 
- 사진을 클릭하면 음식점의 사진이 확대되고, 음식점 이름, 카테고리, 위치 정보를 알 수 있습니다.
- Show on Map 버튼을 누르면 탭 3에 있는 지도에서 실제 위치로 이동할 수 있습니다.

기술 설명
- Recycler View와 Card View를 이용하여 갤러리 탭을 구현했습니다. 
- 각 사진을 클릭 시 JSON 파일에 저장된 음식점 정보를 가져옵니다.
- 상세 페이지로 이동할 때 Activity이 전환되도록 했고, intent를 이용해 데이터를 전달받습니다.


### TAB3 - Map
![Screen Shot 2022-07-05 at 8 30 31 PM](https://user-images.githubusercontent.com/88198439/177317947-94a15d5d-7f83-4e08-b158-20eef324d656.png)
![Screen Shot 2022-07-05 at 8 29 44 PM](https://user-images.githubusercontent.com/88198439/177317840-7d4681a9-fa64-486e-b833-be0b21ff9326.png)


Major features
- 지도 상에서 주변 맛집의 위치를 한눈에 볼 수 있습니다.
- 저장소에 저장된 음식점을 검색할 수 있습니다.
- 현재 위치로부터 음식점까지의 실제 거리와 지도 상의 위치를 한눈에 파악할 수 있습니다.

기술 설명
- 네이버지도 API와 GPS 기능을 이용해 지도 상의 현재 위치를 가져옵니다.
- onRequestPermissionsResult() 함수를 이용해 기기 상의 권한 여부를 확인합니다. 
- naverMap.moveCamera() 함수를 이용해 지도상 카메라를 이동시킵니다.
- 리스트를 보여주는 레이아웃은 hannesa2 라이브러리를 사용해 아래에서 위로 스와이프 할 수 있도록 했습니다.
- 리스트에서는 filter 기능을 이용헤 검색 기능을 구현하였습니다.

