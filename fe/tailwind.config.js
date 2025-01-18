/** @type {import('tailwindcss').Config} */
import tailwindScrollbar from 'tailwind-scrollbar';

export default {
	content: [
		"./index.html",
		"./src/**/*.{vue,js,jsx}"
	],
	theme: {
		extend: {},
	},
	plugins: [
		tailwindScrollbar({ nocompatible: true }),
	],
}

