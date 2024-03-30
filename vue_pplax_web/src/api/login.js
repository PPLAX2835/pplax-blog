import service from '../util/request'

export function login(loginForm) {
	return service({
		url: 'login',
		method: 'POST',
		data: {
			...loginForm
		}
	})
}
