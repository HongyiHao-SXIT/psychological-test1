import Vue from 'vue';
import uView from 'uview-ui';
import formRules from '@/utils/formRules';
import setting from '@/setting';
import request from '@/utils/request.js'
import i18n from './i18n/index.js'
import App from './App';
import store from "./store/index.js"

import '@/style/tailwind.css';
import '@/style/index.scss';


Vue.config.productionTip = false;

Vue.prototype.$rules = formRules;

Vue.prototype.$baseUrl = setting.baseUrl;
Vue.prototype.$uploadUrl = setting.baseUrl + 'api/common/file/upload';
Vue.prototype.$appId=setting.appId
Vue.prototype.$request=request
Vue.prototype._i18n = i18n
Vue.prototype.$showToast = (title, icon = 'none', otherOptions) => {
  uni.showToast({ title, icon, ...otherOptions });
};

// #ifdef H5

// #endif

App.mpType = 'app';
Vue.use(uView);

const app = new Vue({
  ...App,
  i18n,
  store
});
app.$mount();
