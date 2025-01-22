import { jsonRequest } from "./api";
// eslint-disable-next-line no-unused-vars
const { VITE_BUSINESSMAN_DECODE_API_KEY, VITE_BUSINESSMAN_ENCODE_API_KEY } = import.meta.env;

export const validateBusinessman = async (arr) => {
	const request = { "businesses": arr }

	const response = await jsonRequest(
		`https://api.odcloud.kr/api/nts-businessman/v1/validate?serviceKey=${VITE_BUSINESSMAN_ENCODE_API_KEY}`, 
		'POST', request
	);
	return response.data
};

export const statusBusinessman = async (arr) => {
	const request = { "b_no": arr }

	const response = await jsonRequest(
		`https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=${VITE_BUSINESSMAN_ENCODE_API_KEY}`,
		'POST', request
	);
	return response.data
};