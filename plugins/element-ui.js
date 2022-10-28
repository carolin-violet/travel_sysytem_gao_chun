import Vue from 'vue'


import ElementUI from 'element-ui'
Vue.use(ElementUI)



import VueVirtualScroller from 'vue-virtual-scroller'
Vue.use(VueVirtualScroller)
Vue.component('RecycleScroller', VueVirtualScroller.RecycleScroller)
