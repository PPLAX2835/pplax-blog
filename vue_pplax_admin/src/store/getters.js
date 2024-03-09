const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  avatar: state => state.user.avatar,
  nickname: state => state.user.name,
  menu: state => state.user.menu,
  userRoleName: state => state.user.roleName,
  roleList: state => state.role.roleList,
  visitedViews: state => state.tagsView.visitedViews,
  cachedViews: state => state.tagsView.cachedViews,
}
export default getters
