import service from '../util/request'

export function getArchives() {
	return service({
		url: 'archives',
		method: 'GET'
	})
}