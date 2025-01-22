<script setup>
const goBack = () => {
  window.history.back();
}
</script>

<template>
  <main class="flex-grow flex flex-col items-center">
    <section class="flex mt-4 w-full justify-center">
      <form id="edit-place-form" class="max-w-4xl px-4 flex flex-col w-full bg-white" enctype="multipart/form-data">
        <div class="flex justify-between pb-2 border-b border-gray-300">
          <a href="javascript:void(0);" class="font-bold p-2" @click="goBack">&#10094;&#10094; 목록</a>
          <button onclick="deleteRentalPlace()" class="p-2 text-red-500 rounded-lg font-bold text-sm hover:text-red-700 transition-colors">임대지 삭제</button>
        </div>
        <div class="flex sm:space-x-2 space-x-0 sm:flex-row flex-col">
          <div class="space-y-4 flex flex-col flex-shrink-0 justify-center items-center p-8">
            <img id="thumbnail-image" src="" class="w-40 h-40 border border-gray-300 object-cover bg-white" alt="">
            <input type="file" id="thumbnail-real-upload" accept="image/gif, image/png, image/jpeg" class="hidden">
            <button id="thumbnail-custom-upload" onclick="uploadThumbnail()" class="p-2 bg-[#3FB8AF] text-white font-bold rounded-lg transition-colors hover:bg-[#2c817c]" type="button">이미지 변경하기</button>
            <span class="text-xs font-bold block">2MB 이하로 업로드 해주세요.</span>
          </div>
          <div class="flex flex-col flex-grow p-8 space-y-4">
            <div class="bg-white min-w-60 border px-4 border-gray-300 rounded-md p-2">
              <label for="place-title" class="font-bold">임대지 이름</label>
              <input id="place-title" type="text" class="w-full h-9 p-1 flex-grow focus:outline-[#3FB8AF]" placeholder="제목을 입력하시기 바랍니다." required>
            </div>
            <div class="bg-white px-4 py-2 flex-1 border border-gray-300 rounded-md">
              <label for="price" class="font-bold">금액 <span class="font-bold text-xs">(숫자만 입력해주시기 바랍니다.)</span></label>
              <div class="flex items-center space-x-2">
                <input id="price" type="text" placeholder="금액을 입력해주시기 바랍니다." class="w-full h-9 p-1 focus:outline-[#3FB8AF]">
                <span class="div min-w-20 font-bold">원 / 일</span>
              </div>
            </div>
            <div>
              <div class="bg-white px-4 py-2 flex-1 border border-gray-300 rounded-md">
                <span class="font-bold">임대지 주소</span>
                <div class="flex flex-col space-y-2">
                  <div class="flex justify-between items-center space-x-4">
                    <input type="text" placeholder="우편번호" id="postcode" class="py-2 min-w-28 focus:outline-[#3FB8AF]" readonly required>
                    <label for="postcode" class="hidden"></label>
                    <button type="button" onclick="daumPostcode()" class="py-2 px-4 bg-[#3FB8AF] flex-shrink-0 text-white font-bold rounded-lg transition-colors hover:bg-[#2c817c]">주소 찾기</button>
                  </div>
                  <label for="addr" class="hidden"></label>
                  <input type="text" placeholder="주소" id="addr" class="py-2 focus:outline-[#3FB8AF]" readonly required>
                  <label for="addr-detail" class="hidden"></label>
                  <input type="text" placeholder="상세 주소" id="addr-detail" class="py-2 focus:outline-[#3FB8AF]" required>
                </div>
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
            <span>인프라 설정</span>
          </div>
          <div class="m-8 border border-gray-300 rounded-lg">
            <div class="p-2 mx-2 border-b border-gray-300">
              <span class="text-xs font-bold">선택된 인프라 목록</span>
              <ul id="my-infra-box" class="m-2 flex border-gray-300 overflow-x-auto space-x-2 whitespace-nowrap list_scrollbar py-2 cursor-default">
                <!-- <li class="text-xs px-3 py-2 flex hover:border-red-500 transition-colors items-center border-2 border-[#3fb8af] rounded-full space-x-2">
                  <span class="font-bold">인프라 1</span>
                  <button type="button" >&#10060;</button>
                </li> -->

              </ul>
            </div>
            <div class="p-2 mx-2">
              <span class="text-xs font-bold">버튼을 눌러 인프라를 추가하시기 바랍니다.</span>
              <div class="overflow-x-auto space-x-2 whitespace-nowrap list_scrollbar py-2 m-2 cursor-default">
                <button type="button" onclick="addInfra(this)" class="border-gray-500 border-2 bg-[#3fb8af] px-3 py-2 text-xs font-bold text-white rounded-full focus:outline-white hover:bg-[#2c817c] transition-colors" data-val="엘리베이터">엘리베이터</button>
                <button type="button" onclick="addInfra(this)" class="border-gray-500 border-2 bg-[#3fb8af] px-3 py-2 text-xs font-bold text-white rounded-full focus:outline-white hover:bg-[#2c817c] transition-colors" data-val="와이파이">와이파이</button>
                <button type="button" onclick="addInfra(this)" class="border-gray-500 border-2 bg-[#3fb8af] px-3 py-2 text-xs font-bold text-white rounded-full focus:outline-white hover:bg-[#2c817c] transition-colors" data-val="휠체어 접근">휠체어 접근</button>
                <button type="button" onclick="addInfra(this)" class="border-gray-500 border-2 bg-[#3fb8af] px-3 py-2 text-xs font-bold text-white rounded-full focus:outline-white hover:bg-[#2c817c] transition-colors" data-val="반려동물 허용">반려동물 허용</button>
                <button type="button" onclick="addInfra(this)" class="border-gray-500 border-2 bg-[#3fb8af] px-3 py-2 text-xs font-bold text-white rounded-full focus:outline-white hover:bg-[#2c817c] transition-colors" data-val="버스 정류장">버스 정류장</button>
                <button type="button" onclick="addInfra(this)" class="border-gray-500 border-2 bg-[#3fb8af] px-3 py-2 text-xs font-bold text-white rounded-full focus:outline-white hover:bg-[#2c817c] transition-colors" data-val="지하철역">지하철역</button>
                <button type="button" onclick="addInfra(this)" class="border-gray-500 border-2 bg-[#3fb8af] px-3 py-2 text-xs font-bold text-white rounded-full focus:outline-white hover:bg-[#2c817c] transition-colors" data-val="주차">주차</button>
                <button type="button" onclick="addInfra(this)" class="border-gray-500 border-2 bg-[#3fb8af] px-3 py-2 text-xs font-bold text-white rounded-full focus:outline-white hover:bg-[#2c817c] transition-colors" data-val="에어컨">에어컨</button>
                <button type="button" onclick="addInfra(this)" class="border-gray-500 border-2 bg-[#3fb8af] px-3 py-2 text-xs font-bold text-white rounded-full focus:outline-white hover:bg-[#2c817c] transition-colors" data-val="난방">난방</button>
                <button type="button" onclick="addInfra(this)" class="border-gray-500 border-2 bg-[#3fb8af] px-3 py-2 text-xs font-bold text-white rounded-full focus:outline-white hover:bg-[#2c817c] transition-colors" data-val="주방">주방</button>
                <button type="button" onclick="addInfra(this)" class="border-gray-500 border-2 bg-[#3fb8af] px-3 py-2 text-xs font-bold text-white rounded-full focus:outline-white hover:bg-[#2c817c] transition-colors" data-val="화장실">화장실</button>
                <button type="button" onclick="addInfra(this)" class="border-gray-500 border-2 bg-[#3fb8af] px-3 py-2 text-xs font-bold text-white rounded-full focus:outline-white hover:bg-[#2c817c] transition-colors" data-val="보안">보안</button>
              </div>
            </div>
          </div>
        </div>
        <div class="flex flex-col mt-4 border-gray-300">
          <div class="pb-2 border-b border-gray-300">
            <span>추가 설정</span>
          </div>
          <div class="p-8 flex flex-col space-y-4">
            <div class="flex justify-between space-x-4">
              <div class="bg-white px-4 py-2 flex-1 border border-gray-300 rounded-md">
                <label for="age" class="font-bold">주변 연령대</label>
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
              <div class="bg-white px-4 py-2 flex-1 border border-gray-300 rounded-md">
                <label for="area" class="font-bold">면적<span class="font-bold text-xs">(평)</span></label>
                <div class="flex items-center space-x-2">
                  <input id="area" type="text" placeholder="면적" class="w-full h-9 p-1 focus:outline-[#3FB8AF]">
                  <span class="div font-bold">평</span>
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