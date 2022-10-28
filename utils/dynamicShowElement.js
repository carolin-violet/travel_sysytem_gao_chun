// 传入需要滚动到可视区域再显示的元素

export default function(elementList) {
  const showTop = window.innerHeight * 4 / 5

  elementList.forEach(el => {
    // box.getBoundingClientRect().top为box元素距离顶部的距离
    const boxTop = el.getBoundingClientRect().top

    if (boxTop < showTop) {
      el.classList.add('show')
    } else {
      el.classList.remove('show')
    }
  })
}
