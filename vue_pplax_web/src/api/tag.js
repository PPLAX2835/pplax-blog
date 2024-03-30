import service from '../util/request'

export function getBlogListByTagName(tagName, pageNum) {
	return service({
		url: 'tag',
		method: 'GET',
		params: {
			tagName,
			pageNum
		}
	})
}