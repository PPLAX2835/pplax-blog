import service from '../util/request'

export function getData() {
	return service({
		url: 'friends',
		method: 'GET'
	})
}

export function addViewsByNickname(nickname) {
	return service({
		url: 'friend',
		method: 'POST',
		params: {
			nickname
		}
	})
}