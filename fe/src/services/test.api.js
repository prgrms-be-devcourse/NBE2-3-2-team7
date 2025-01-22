import { jsonRequest } from './api';

const testApi = async () => {
	const response = await jsonRequest('/test/data', 'GET');
	return response.data;
}

export default { testApi };