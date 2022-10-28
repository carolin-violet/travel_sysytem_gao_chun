import defaultSettings from '@/settings'

const title = defaultSettings.title || '高淳旅游服务管理系统'

export default function getPageTitle(pageTitle) {
  if (pageTitle) {
    return `${pageTitle} - ${title}`
  }
  return `${title}`
}
