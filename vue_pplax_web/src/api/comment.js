import service from '../util/request'

export function getCommentListByQuery(token, query) {
	return service({
		url: 'comments',
		method: 'GET',
		headers: {
			Authorization: token,
		},
		params: {
			...query
		}
	})
}

export function submitComment(token, form) {
	return service({
		url: 'comment',
		method: 'POST',
		headers: {
			Authorization: token,
		},
		data: {
			...form
		}
	})
}