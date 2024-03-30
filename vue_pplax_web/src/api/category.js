import service from '../util/request'

export function getBlogListByCategoryName(categoryName, pageNum) {
	return service({
		url: 'category',
		method: 'GET',
		params: {
			categoryName,
			pageNum
		}
	})
}