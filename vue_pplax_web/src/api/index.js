import service from '../util/request'

export function getHitokoto() {
	return service({
		url: 'https://v1.hitokoto.cn/?c=a',
		method: 'GET'
	})
}

export function getSite() {
	return service({
		url: 'site',
		method: 'GET'
	})
}