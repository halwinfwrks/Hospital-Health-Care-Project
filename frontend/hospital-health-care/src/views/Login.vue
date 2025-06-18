<template>
  <div>
    <div>
        
    </div>
    <div class="min-h-screen flex items-center justify-center bg-gray-100">
      <form
        @submit.prevent="handleLogin"
        class="bg-white p-6 rounded-xl shadow-md w-full max-w-sm"
      >
        <h2 class="text-2xl font-semibold text-center mb-6">Đăng nhập</h2>

        <div class="mb-4">
          <label class="block text-sm font-medium mb-1" for="username"
            >Tài khoản</label
          >
          <input
            v-model="username"
            type="text"
            id="username"
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-blue-200"
            required
          />
        </div>

        <div class="mb-6">
          <label class="block text-sm font-medium mb-1" for="password"
            >Mật khẩu</label
          >
          <input
            v-model="password"
            type="password"
            id="password"
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-blue-200"
            required
          />
        </div>

        <button
          type="submit"
          class="w-full bg-blue-600 text-white py-2 px-4 rounded hover:bg-blue-700"
        >
          Đăng nhập
        </button>

        <p v-if="message" class="text-center mt-4 text-red-500">
          {{ message }}
        </p>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

const username = ref("");
const password = ref("");
const message = ref("");

function handleLogin() {
  if (username.value === "admin" && password.value === "123") {
    message.value = "Đăng nhập thành công!";
  } else {
    message.value = "Tài khoản hoặc mật khẩu không đúng.";
  }
}
</script>

<style scoped>
/* Optional custom style */
</style>
