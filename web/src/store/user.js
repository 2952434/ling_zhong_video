import { defineStore } from "pinia";
import { getCode } from '@/api/user.js'
let useUserStore = defineStore({
  id: 'user',
  state: () => {
    return {
      token: localStorage.getItem('token') || ''
    }
  },
  actions: {
    getEmailCode(email) {
      return new Promise((resolve, reject) => {
        getCode(email).then(res => {
          resolve(res)
        }).catch(err => {
          reject(err)
        })
      })
    }

  },
  getters: {

  }
})
export default useUserStore;