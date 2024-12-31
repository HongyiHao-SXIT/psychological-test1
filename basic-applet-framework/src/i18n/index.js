import Vue from 'vue'
import VueI18n from 'vue-i18n'
import zhCN from './langs/zhCN.js'
import en from './langs/en.js'

Vue.use(VueI18n)

const messages = {
    en,
    zhCN
}

const i18n = new VueI18n({
    locale: 'zhCN',  //默认中文，也可根据系统语言动态设置
    messages
})

export default i18n