<script setup>
import { useRoute } from 'vue-router';
import { useRouter } from 'vue-router';

const route = useRoute();
const router = useRouter();

const name = route.query.name;
const image = route.query.image;
const email = route.query.email;
const tel = route.query.tel;
const role = route.query.role;
const businessId = route.query.businessId;

const goBack = () => {
  router.back();
};

</script>

<template>
  <main class="flex-grow flex flex-col items-center">
    <section class="flex mt-4 w-full bg-white justify-center">
      <div class="bg-white max-w-2xl w-full">
        <form id="edit-user-form" class="min-w-5xl space-y-12 p-8 flex-col"
              enctype="multipart/form-data" onsubmit="return false;">
          <div class="flex sm:flex-row flex-col justify-between sm:space-y-0 space-y-8">
            <div class="space-y-4 flex flex-col justify-center items-center">
              <img id="profile-image" :src="image" class="rounded-full w-40 h-40 border border-gray-300 object-cover bg-gray-300" alt="">
              <input type="file" id="profile-real-upload" accept="image/gif, image/png, image/jpeg" class="hidden">
              <button id="profile-custom-upload" onclick="uploadImage()" class="p-2 bg-[#3FB8AF] text-white font-bold rounded-lg transition-colors hover:bg-[#2c817c]" type="button">이미지 변경하기</button>
              <span class="text-xs font-bold">2MB 이하로 업로드 해주세요.</span>
            </div>
            <div class="flex space-x-4 justify-center">
              <div class="flex flex-col space-y-4">
                <span class="h-9 block p-1 font-bold text-gray-700 flex-shrink-0">이메일</span>
                <span class="h-9 block p-1 font-bold text-gray-700 flex-shrink-0">비밀번호</span>
                <span class="h-9 block p-1 font-bold text-gray-700 flex-shrink-0">비밀번호 확인</span>
                <span class="h-9 block p-1 font-bold text-gray-700 flex-shrink-0">이름</span>
                <span class="h-9 block p-1 font-bold text-gray-700 flex-shrink-0">전화번호</span>
                <span v-if="role === 'landlord'" class="h-9 block p-1 font-bold text-gray-700 flex-shrink-0">사업자등록번호</span>
                <span class="h-9 block p-1 font-bold text-gray-700 flex-shrink-0"> {{ role === 'landlord' ? '상호명' : '닉네임' }}</span>
              </div>
              <div class="flex flex-col space-y-4">
                <span class="h-9 p-1 font-bold text-gray-700" id="user-email">{{ email }}</span>
                <input type="password" id="password" class="border p-1 h-9 border-gray-300 focus:outline-[#3FB8AF]" placeholder="변경할 비밀번호 입력" autocomplete="new-password">
                <input type="password" id="password-check" class="border p-1 h-9 border-gray-300 focus:outline-[#3FB8AF]" placeholder="비밀번호 확인">
                <span class="h-9 p-1 font-bold text-gray-700" id="user-name">{{ name }}</span>
                <span class="h-9 p-1 font-bold text-gray-700" id="user-tel">{{ tel }}</span>
                <span v-if="role === 'landlord'" class="h-9 p-1 font-bold text-gray-700" id="landlord-num">{{ businessId }}</span>
                <input type="text" id="brand" class="border p-1 h-9 border-gray-300 focus:outline-[#3FB8AF]" :placeholder="role === 'landlord' ? '상호명 입력' : '닉네임 입력'" />
              </div>
            </div>
          </div>
          <div class="flex justify-between w-full">
            <button id="delete-account-btn" type="button" class="text-red-500 font-bold p-2 hover:text-red-700 transition-colors rounded-md hover:bg-red-50">회원 탈퇴</button>
            <!-- 추후 수정 ) 수정 내용 유무에 따라 데이터 전달할 것 -->
            <button type="submit" class="text-white bg-[#3FB8AF] hover:bg-[#2c817c] p-2 rounded-md transition-colors font-bold" @click="goBack">정보 수정</button>
          </div>
        </form>
      </div>
    </section>
  </main>
</template>