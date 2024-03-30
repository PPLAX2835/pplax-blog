import service from '../util/request'

export function getBlogById(token, id) {
	return service({
		url: 'blog',
		method: 'GET',
		headers: {
			Authorization: token,
		},
		params: {
			id
		}
	})
}

export function checkBlogPassword(blogPasswordForm) {
	return service({
		url: 'checkBlogPassword',
		method: 'POST',
		data: {
			...blogPasswordForm
		}
	})
}

export function getSearchBlogList(query) {
	return service({
		url: 'searchBlog',
		method: 'GET',
		params: {
			query
		}
	})
}