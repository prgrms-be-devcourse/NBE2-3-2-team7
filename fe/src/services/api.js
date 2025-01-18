import axios from 'axios';

const api = axios.create({
	baseURL: 'http://localhost:8080/api',
	timeout: 10000,
});

export const jsonRequest = (url, method, data) => {
	return api({
		url,
		method,
		headers: {
			'Content-Type': 'application/json',
		},
		data,
	});
};

export const multipartRequest = (url, method, formData) => {
	return api({
		url,
		method,
		headers: {
			'Content-Type': 'multipart/form-data',
		},
		data: formData,
	});
};

export default api;