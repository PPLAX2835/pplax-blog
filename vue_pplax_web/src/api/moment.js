import service from '../util/request'

export function getMomentListByPageNum(token, pageNum) {
	return service({
		url: 'moments',
		method: 'GET',
		headers: {
			Authorization: token,
		},
		params: {
			pageNum
		}
	})
}

export function likeMoment(id) {
	return service({
		url: `moment/like/${id}`,
		method: 'POST',
	})
}