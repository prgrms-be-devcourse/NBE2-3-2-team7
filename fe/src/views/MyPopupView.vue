<script setup>
const goBack = () => {
  window.history.back();
}
</script>

<template>
  <main class="flex-grow flex flex-col items-center">
    <section class="flex mt-4 w-full justify-center">
      <form id="edit-popup-form" class="max-w-4xl px-4 flex flex-col w-full bg-white" enctype="multipart/form-data">
        <div class="flex justify-between pb-2 border-b border-gray-300">
          <a href="/user/popup" class="font-bold p-2">&#10094;&#10094; 목록</a>
          <button onclick="deletePopupStore()" class="p-2 text-red-500 rounded-lg font-bold text-sm hover:text-red-700 transition-colors">팝업 삭제</button>
        </div>
        <div class="flex sm:space-x-2 space-x-0 sm:flex-row flex-col">
          <div class="space-y-4 flex flex-col flex-shrink-0 justify-center items-center p-8">
            <img id="thumbnail-image" src="#" class="w-40 h-40 border border-gray-300 object-cover bg-white" alt="">
            <input type="file" id="thumbnail-real-upload" accept="image/gif, image/png, image/jpeg" class="hidden">
            <button id="thumbnail-custom-upload" onclick="uploadThumbnail()" class="p-2 bg-[#3FB8AF] text-white font-bold rounded-lg transition-colors hover:bg-[#2c817c]" type="button">이미지 변경하기</button>
            <span class="text-xs font-bold block">2MB 이하로 업로드 해주세요.</span>
          </div>
          <div class="flex flex-col flex-grow p-8 space-y-4">
            <div class="bg-white min-w-60 border px-4 border-gray-300 rounded-md p-2">
              <label for="popup-title" class="font-bold">팝업 스토어 제목</label>
              <input id="popup-title" type="text" class="w-full h-9 p-1 flex-grow focus:outline-[#3FB8AF]" placeholder="제목을 입력하시기 바랍니다." required>
            </div>
            <div class="flex space-x-4">
              <div class="bg-white px-4 py-2 flex-1 border border-gray-300 rounded-md">
                <label for="category" class="font-bold">유형</label>
                <select id="category" class="w-full h-9 p-1 focus:outline-[#3FB8AF]">
                  <option value="식품">식품</option>
                  <option value="화장품">화장품</option>
                  <option value="패션">패션</option>
                  <option value="애니">애니</option>
                  <option value="아이돌">아이돌</option>
                  <option value="스포츠">스포츠</option>
                  <option value="음악">음악</option>
                  <option value="테크">테크</option>
                  <option value="인테리어">인테리어</option>
                  <option value="문화/예술">문화/예술</option>
                  <option value="게임">게임</option>
                  <option value="헬스">헬스</option>
                  <option value="음료">음료</option>
                  <option value="책">책</option>
                  <option value="친환경">친환경</option>
                </select>
              </div>
              <div class="bg-white px-4 py-2 flex-1 border border-gray-300 rounded-md">
                <label for="location" class="font-bold">지역</label>
                <select id="location" class="w-full h-9 p-1 focus:outline-[#3FB8AF]">
                  <option value="서울">서울</option>
                  <option value="부산">부산</option>
                  <option value="대구">대구</option>
                  <option value="인천">인천</option>
                  <option value="광주">광주</option>
                  <option value="대전">대전</option>
                  <option value="울산">울산</option>
                  <option value="세종">세종</option>
                  <option value="경기">경기</option>
                  <option value="충북">충북</option>
                  <option value="충남">충남</option>
                  <option value="전북">전북</option>
                  <option value="전남">전남</option>
                  <option value="경북">경북</option>
                  <option value="경남">경남</option>
                  <option value="강원">강원</option>
                  <option value="제주">제주</option>
                </select>
              </div>
            </div>
            <div>
              <div class="bg-white px-4 py-2 flex-1 border border-gray-300 rounded-md">
                <label for="quick-calendar" class="font-bold">임대 기간</label>
                <input type="text" id="quick-calendar" placeholder="기간을 선택하세요." class="w-full focus:outline-[#3FB8AF] h-9 p-1">
              </div>
            </div>
          </div>
        </div>
        <div class="flex flex-col">
          <div class="pb-2 border-b border-gray-300 flex justify-between">
            <span>이미지 목록 (최대 10개)</span>
            <span id="images-count">0개</span>
          </div>
          <ul id="image-list-box" class="mt-4 mb-4">
            <!-- <li class="flex items-center space-x-4 px-8 py-2 hover:bg-gray-200">
              <img src="" class="w-20 h-20 border border-gray-300 object-contain bg-gray-300">
              <span class="text-xs font-bold flex-1">이미지 파일명.png</span>
              <button type="button" onclick="removeImage()">&#10060;</button>
            </li> -->
          </ul>
          <div class="flex justify-between border-t pt-2 border-gray-300">
            <span class="text-xs font-bold flex-1">이미지 하나당 2MB 이하로 업로드 해주세요.</span>
            <input type="file" id="images-real-upload" accept="image/gif, image/png, image/jpeg" class="hidden" multiple>
            <button type="button" id="images-custom-upload" onclick="uploadImages()" class="py-2 px-4 bg-[#3FB8AF] flex-grow-0 text-white font-bold rounded-lg transition-colors hover:bg-[#2c817c]">이미지 추가</button>
          </div>
        </div>
        <div class="flex flex-col mt-4 border-gray-300">
          <div class="pb-2 border-b border-gray-300">
            <span>추가 설정</span>
          </div>
          <div class="p-8 flex flex-col space-y-4">
            <div class="flex justify-between space-x-4">
              <div class="bg-white px-4 py-2 flex-1 border border-gray-300 rounded-md">
                <label for="age" class="font-bold">주요 타겟</label>
                <select id="age" class="w-full h-9 p-1 focus:outline-[#3FB8AF]">
                  <option value="전체">전체</option>
                  <option value="10대">10대</option>
                  <option value="20대">20대</option>
                  <option value="30대">30대</option>
                  <option value="40대">40대</option>
                  <option value="50대">50대</option>
                  <option value="60대 이상">60대 이상</option>
                </select>
              </div>
              <div class="bg-white px-4 py-2 min-w-60 flex-1 rounded-md border border-gray-300">
                <div class="flex justify-between space-x-2">
                  <span class="font-bold">희망 면적</span>
                  <label id="place-range" class="text-center"></label>
                </div>
                <div class="h-9 p-1 flex items-center">
                  <div id="place-slider" class="w-full focus:outline-[#3FB8AF]"></div>
                </div>
              </div>
            </div>
            <div class="bg-white px-4 py-2 flex-1 border border-gray-300 rounded-md">
              <label for="detail-text" class="font-bold">상세 설명</label>
              <textarea id="detail-text" class="w-full h-40 focus:outline-[#3FB8AF]" placeholder="설명을 입력해주시기 바랍니다." required></textarea>
            </div>
          </div>
          <div class="flex justify-between border-t pt-2 border-gray-300">
            <span class="text-xs font-bold flex-1">하단의 [수정하기]를 눌러 변경 사항을 저장해주세요.</span>
            <button type="submit" class="py-2 px-4 bg-[#3FB8AF] flex-grow-0 text-white font-bold rounded-lg transition-colors hover:bg-[#2c817c]">수정하기</button>
          </div>
        </div>
      </form>
    </section>
  </main>
</template>