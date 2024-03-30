import service from '../util/request'

export function getBlogList(pageNum) {
	return service({
		url: 'blogs',
		method: 'GET',
		params: {
			pageNum
		}
	})
}