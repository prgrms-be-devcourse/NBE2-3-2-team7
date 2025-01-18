import { jsonRequest } from './api';

const testApi = () => {
	return jsonRequest('/test/data', 'GET')
	.then(response => response.data)
	.catch(error => {
		throw error;
	});
}

export default { testApi };