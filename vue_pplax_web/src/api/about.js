import service from '../util/request'

export function getAbout() {
	return service({
		url: 'about',
		method: 'GET'
	})
}