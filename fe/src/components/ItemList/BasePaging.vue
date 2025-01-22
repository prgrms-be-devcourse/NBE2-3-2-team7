<script setup>
</script>

<template>
	<nav id="paging" class="isolate inline-flex border rounded-md shadow-sm" aria-label="Pagination">
		<button onclick="setPage(${startPage})"
			class="relative inline-flex items-center rounded-l-md px-2 py-2 text-gray-400 hover:bg-gray-100">
			<span class="sr-only">이전</span>
			<svg class="size-5" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true" data-slot="icon">
				<path fill-rule="evenodd"
					d="M11.78 5.22a.75.75 0 0 1 0 1.06L8.06 10l3.72 3.72a.75.75 0 1 1-1.06 1.06l-4.25-4.25a.75.75 0 0 1 0-1.06l4.25-4.25a.75.75 0 0 1 1.06 0Z"
					clip-rule="evenodd" />
			</svg>
		</button>
		<span v-if="startPage > 1"
			class="relative inline-flex items-center px-4 py-2 text-sm font-semibold text-white bg-[#3FB8AF]">
			{{ }}
		</span>
		<button v-else
			class="relative inline-flex items-center px-4 py-2 text-sm font-semibold text-gray-900 hover:bg-gray-100">
			{{ }}
		</button>
		<button onclick="setPage(${nextGroupPage})"
			class="relative inline-flex items-center rounded-r-md px-2 py-2 text-gray-400 hover:bg-gray-100">
			<span class="sr-only">다음</span>
			<svg class="size-5" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true" data-slot="icon">
				<path fill-rule="evenodd"
					d="M8.22 5.22a.75.75 0 0 1 1.06 0l4.25 4.25a.75.75 0 0 1 0 1.06l-4.25 4.25a.75.75 0 0 1-1.06-1.06L11.94 10 8.22 6.28a.75.75 0 0 1 0-1.06Z"
					clip-rule="evenodd" />
			</svg>
		</button>
	</nav>
</template>

<style scoped></style>
